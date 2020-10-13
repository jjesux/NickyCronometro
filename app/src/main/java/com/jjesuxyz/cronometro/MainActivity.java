package com.jjesuxyz.cronometro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjesuxyz.cronometro.DBData.DBAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;



/**
 *
 * MainActivity class is used to created the main user interface and to start the
 * time counting using a chronometer widget and to start the circle drawing
 * animation drove by the chronometer.
 * It also sets reference to other class that form part of this app.
 *
 */
public class MainActivity extends FragmentActivity
                                    implements
                                        DialogSaveDataQ.DialogoDataListener,
                                        DialogEnterDate.EnterDateDialogListener {
                                  //ArrayList to hold lap time values
    private ArrayList<String> lapInfo;
                                  //ArrayList to hold whole lap time records
    private ArrayList<String> arrList;
                                  //Reference to the View widget animation class
    private CronoAnimacion cronoAnimacion;
                                  //Button variables to control the chronometer app
    private Button btnStart, btnPause, btnLaps, btnStory;
                                  //Reference to the Chronomete widget
    private Chronometer chrono;
                                  //ListView Adapter class
    private ListVwAdaptador listVwAdaptador;
                                  //String array to form a whole lap time record
    private String[] arrStrLaps;
                                  //Booleans to enable/disable buttons
    private boolean boolBtnStart = false;
    private boolean boolChronoPaused = false;
    private boolean boolIsDataToSave = false;
                                  //Variables to measure different time
    private long tiempoElapsado = 0;
    private long tiempoPausado = 0;
    private long lapTimeBegin;
    private long lapSecs, lapMins, lapHrs;
                                  //Counter to index the laps inserted in the
                                  // ListView
    private int counter = 0;




    /**
     * onCreate(Bundle) function is used to initialize variables, create the
     * local database if it does not exit yet. It also sets the View widget,
     * ListView and buttons click listener class and the ListView adapter.
     *
     * @param savedInstanceState type Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                                  //ListView to show users a list of lap times
                                  // info.
        ListView listView;
                                  //Asserting database exist or has been created.
        DBAccess dbAccess = new DBAccess(getApplicationContext());
        dbAccess.close();
                                  //Initializing ArrayList to hold lap time info.
        lapInfo = new ArrayList<>();
                                  //ArrayList to hold a whole lap data record.
        arrList = new ArrayList<>();
                                  //TextView to show user information
        TextView txtvwNameDate;
                                  //Vars to hold time values are set to zero.
        lapSecs = lapMins = lapHrs = 0;
                                  //Getting a reference to the ircle animation
                                  // widget.
        cronoAnimacion = (CronoAnimacion) findViewById(R.id.vwId);
                                  //TextView CHRONOMETER title
        txtvwNameDate = (TextView) findViewById(R.id.textView);
        String str = getApplicationContext().getApplicationInfo().loadLabel(getPackageManager()).toString();
        txtvwNameDate.setText(str + " " + getDateTime("yyyy/MM/dd HH:mm:ss"));
                                  //Setting the array of strings holding lap info to ""
        arrStrLaps = new String[20];
        arrStrLaps[0] = "Lap 0: 00:00 -> 00:00";
        for (int i = 1; i < arrStrLaps.length; i++) {
            arrStrLaps[i] = "";
        }
                                  //Initializing the button listener class.
        BtnOnClickListener btnOnClickListener = new BtnOnClickListener();
                                  //Initializing button references and listeners.
        btnStart = (Button) findViewById(R.id.btnStartId);
        btnStart.setOnClickListener(btnOnClickListener);

        btnPause = (Button) findViewById(R.id.btnPauseId);
        btnPause.setOnClickListener(btnOnClickListener);

        btnLaps = (Button) findViewById(R.id.btnLapId);
        btnLaps.setOnClickListener(btnOnClickListener);

        btnStory = (Button) findViewById(R.id.btnStoryId);
        btnStory.setOnClickListener(btnOnClickListener);

                                  //Initializing the Chronometer listener.
        ChronoTickListener chroTL = new ChronoTickListener();
                                  //Reference to the Chronometer widget.
        chrono = (Chronometer) findViewById(R.id.chronometer2);
                                  //Setting the chronometer listener
        chrono.setOnChronometerTickListener(chroTL);
                                  //Setting the ListView widget adapter.
        listVwAdaptador  = new ListVwAdaptador(this, R.layout.laps_layout, arrList);
        listView = (ListView) findViewById(R.id.listVwId);
        listView.setAdapter(listVwAdaptador);
                                  //Setting the View widget animation listener.
        cronoAnimacion.setOnClickListener(btnOnClickListener);

    }   //End of onCreate() function



    /**
     * showDBLapInfo function is used to display to the user a dialog to ask him
     * to enter a date. This is the date that this app will search for the local
     * database. If this app find any match it will display those record to the
     * user in a ListView widget.  This dialog has not OK or enter or any other
     * button. Whet user is done entering the date the keyboard enter or OK
     * button has to be clicked.
     *
     * @param strDate type String
     */
    void showDBLapInfo(String strDate) {
        Toast.makeText(MainActivity.this, "VW CLICK LISTINER" + strDate, Toast.LENGTH_SHORT).show();

        FragmentManager manager = getSupportFragmentManager();
                                  //Setting the tag fragment identity??.
        Fragment fragment = manager.findFragmentByTag("fragment_enter_date");
                                  //Defining the dialog widget with fragment info.
        DialogEnterDate dialogEnterDate = new DialogEnterDate();
        dialogEnterDate.show(manager, "fragment_enter_date");
                                  //Beginning, removing and comming the fragment
                                  //transaction
        if (fragment != null){
            manager.beginTransaction().remove(fragment).commit();
        }
    }   //End of showDBLapInfo() function



    /**
     * onFinishEnteringDate(String) callback function is called by the system
     * when user finishes entering a date into the EditText widget and click the
     * keyboard OK/enter button. After the OK button is clicked another activity
     * is started to show the database records that were found and that contains
     * the date that was entered by user.  These records are displayed to the
     * user using a ListView widget.
     *
     * @param strDate type String
     */
    @Override
    public void onFinishEnteringDate(String strDate) {
        Toast.makeText(this, "Date You Entered: " + strDate.length(), Toast.LENGTH_SHORT).show();
                                  //Defining an intent to start a new activity.
        Intent intent = new Intent(this, ShowDBLapInfo.class);
                                  //Starting a new activity.
        startActivity(intent);

    }   //End of onFinishEnteringDate() function



    /**
     * wantToSavedDataQ() function is used to display to user a dialog to ask
     * if lap time data should be saved into the local database. It presents
     * user two buttons, OK and NOT, so user can decide whether or not to save
     * the lap time data.
     */
    void wantToSavedDataQ(){
        FragmentManager manager = getSupportFragmentManager();
                                  //I think tag can be any string, just to know
                                  //how to find it.
        Fragment fragment = manager.findFragmentByTag("fragment_save_data");
        if (fragment != null){
            manager.beginTransaction().remove(fragment).commit();
        }

        DialogSaveDataQ saveDataQ = new DialogSaveDataQ();
        saveDataQ.show(manager, "fragment_save_data");

    }   //End of wantToSavedDataQ() function



    /**
     * NOTE: CREO QUE ESTA FUNCION NO SE USA YA EN ESTA APP. SE USA OTRA QUE
     * TIENE UN NOMBRE PARECIDO.
     *
     * onClickingOkNoOkBtn(boolean) listener function is called by the system to catch user
     * decision about lap time data saving it or not.  User decision is passed to this function
     * as a boolean parameter. True value means save data. False means not save data.
     *
     * NOTE: I think this function is not used in any part of the program.
     *
     * @param saveQ type boolean
     */
    //@Override
    public void onClickingOkNoOkBtn(boolean saveQ) {
        //User click OK button on the Dialog to saved data
        if (saveQ == true){
            //Getting access to DB to save data
            //DBDataMngmt dbDataMngmt = new DBDataMngmt(getApplicationContext());
            //Inserting row of data into DB
            //dbDataMngmt.insertMultipleRecordsIntoLapTimeTable(lapInfo);
            Toast.makeText(this, "Lap Time Data Saved.", Toast.LENGTH_SHORT).show();
        }
        else {                          //If user does not want to save lap time data
            Toast.makeText(this, "Lap Time Data was NOT saved.", Toast.LENGTH_SHORT).show();
        }

    }   //End of onClickingOkNoOkBtn() function



    /**
     * getDateTime(String) function is used just to get the date and time that
     * is displayed in the app UI.
     *
     * @param formato
     * @return
     */
    private String getDateTime(String formato) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato, Locale.US);
        Date date = new Date();

        return simpleDateFormat.format(date);

    }   //End of getDateTime() function



    /**
     * onClickingOkOrNotOkBtn(boolean) callback function is called when any of
     * the dialog widget button is clicked. This function implemented in this
     * class because this class implements the interface defined in the class
     * that builds the dialog asking the user to save data into the local DB.
     *
     * @param saveQ
     */
    @Override
    public void onClickingOkOrNotOkBtn(boolean saveQ) {
        if (saveQ == true) {
                                  //User answers yes to save data into DB.
                                  //Getting access to local DB.
            DBDataMngmt dbDataMngmt = new DBDataMngmt(getApplicationContext());
                                  //Inserting lap time data into local DB.
            dbDataMngmt.insertMultipleRecordsIntoLapTimeTable(lapInfo);
            Toast.makeText(this, "Lap Time Data Saves", Toast.LENGTH_SHORT).show();
        }
        else {
                                  //User answers no to save data into DB.
            Toast.makeText(this, "Lap Time was NOT saved.", Toast.LENGTH_SHORT).show();
        }

    }   //End of onClickingOkNoOkBtn() function



    /**
     * BtnOnClickListener class is used to handle all the events generated by
     * all the buttons included in the main UI of this app and one animated
     * View widget. These events are managed with a switch statement, with cases
     * for each button click. One case starts/stops the chronometer, and
     * enables/disables the other two laps and pause button. The next case pauses
     * the chronometer, and enables/disables the stop and laps buttons. The
     * third case marks the lap elapsed time and it does not affect the other
     * buttons. The fourth case is to give users the option to check/see all the
     * lap info saved in the local database. There is a fifth case that is
     * generated by an animated View widget and it is handled by this fifth case.
     */
    private class BtnOnClickListener implements View.OnClickListener {

        private long lapTimeEnd, lapTimeTotal;



        /**
         * onClick(View) callback function is called by the system to handle all
         * the clicking events generated by the main UI widgets being clicked.
         * There are four buttons and one animated View widget that produce
         * clicking events.
         *
         * @param v type View
         */
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                                  //Button click to start the chronometer.
                case R.id.btnStartId:
                                  //Starting the time counting.
                    if (boolBtnStart == false) {
                                  //Setting the chronometer state to running.
                        boolBtnStart = true;
                        chrono.setBase(SystemClock.elapsedRealtime() - 0 * 1000);
                        lapTimeBegin = SystemClock.elapsedRealtime();
                                  //View animation starts to draw circle animation.
                        cronoAnimacion.setCircleSize(40, 40, cronoAnimacion.getWidth() - 40, cronoAnimacion.getHeight() - 40);
                        cronoAnimacion.setBoolChronoStarted(true);
                        chrono.start();
                                  //Resetting pause, lap and start/stop buttons.
                        btnStart.setText(R.string.btn_stop);
                        btnPause.setEnabled(true);
                        btnPause.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLetrasEnable));//Color.parseColor("#0000FF"));
                        btnLaps.setEnabled(true);
                        btnLaps.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLetrasEnable));//Color.parseColor("#0000FF"));
                                  //Setting vars to new values when chronometer is
                                  // started.
                        if (boolIsDataToSave == true) {
                            listVwAdaptador.updateValues(arrStrLaps);
                            counter = 0;
                            boolIsDataToSave = false;
                        }
                    }
                                  //Setting the chronometer state to stopped or
                                  //not running.
                    else {
                        boolBtnStart = false;
                        chrono.stop();
                                  //Letting know the View widget animation it
                                  // should stop.
                        cronoAnimacion.setBoolChronoStarted(false);
                                  //View widget animation variables are reset to zero
                        cronoAnimacion.resetAnguloAnimacion();
                                  //Disabling pause and lap buttons.
                                  //Resetting the start button.
                        btnStart.setText(R.string.btn_start);
                        btnPause.setEnabled(false);
                        btnPause.setTextColor(Color.parseColor("#686b6d"));
                        btnLaps.setEnabled(false);
                        btnLaps.setTextColor(Color.parseColor("#686b6d"));
                                  //When stop button clicked, insert last lap time
                                  // into the ListView.
                        if (boolIsDataToSave == true) {
                            btnLaps.performClick();
                                  //Ask user if data should be saved.
                            wantToSavedDataQ();
                        }
                                  //If chronometer is paused again the
                                  // time paused is set to zero.
                        lapTimeBegin = 0;
                    }
                    break;
                                  //Pausing the chronometer and almost any
                                  //other event that this app handles.
                case R.id.btnPauseId:
                                  //Pause the chronometer when it's running and
                                  //reset all variables that somehow are related
                                  //to lap time measurement.
                    if (boolChronoPaused == false) {
                                  //Changing boolean value so next click will
                                  //restart chronometer.
                        boolChronoPaused = true;
                        tiempoElapsado = 0;
                                  //Stopping the chronometer time counting.
                        chrono.stop();
                                  //Getting the exact time when the chronometer
                                  //was paused.
                        tiempoElapsado = SystemClock.elapsedRealtime() - chrono.getBase();
                        tiempoPausado = SystemClock.elapsedRealtime();
                                  //Disable start and lap buttons when
                                  //chronometer is paused.
                        btnStart.setEnabled(false);
                        btnStart.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLetrasUnable));//Color.parseColor("#686b6d"));
                        btnLaps.setEnabled(false);
                        btnLaps.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLetrasUnable));//;Color.parseColor("#686b6d"));
                    }
                    else {
                                  //Restarting the chronometer and resetting new
                                  //values to var's used to measure the lap
                                  //elapse time.
                        boolChronoPaused = false;
                        chrono.setBase(SystemClock.elapsedRealtime() - tiempoElapsado);
                                  //Calculating the elapsed paused time. The
                                  //time the chronometer remained paused.
                        tiempoPausado = SystemClock.elapsedRealtime() - tiempoPausado;
                        chrono.start();
                                  //Enabling start and lap buttons when lap
                                  //time counting it's restarted.
                        btnStart.setEnabled(true);
                        btnStart.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLetrasEnable));//Color.parseColor("#0000FF"));
                        btnLaps.setEnabled(true);
                        btnLaps.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLetrasEnable));//Color.parseColor("#0000FF"));
                    }
                    break;
                                  //Marking the end of the last lap elapsed time.
                case R.id.btnLapId:
                                  //Getting the elapsed time to this right moment.
                    lapTimeEnd = SystemClock.elapsedRealtime();
                                  //Calculating the last elapsed lap time set
                                  //as a long number.
                    lapTimeTotal = lapTimeEnd -lapTimeBegin - tiempoPausado;
                                  //Setting the paused time '= 0 secs' for the
                                  //new lap time.
                    tiempoPausado = 0;
                                  //Calculating the last lap elapsed time in
                                  //hrs/mins/secs.
                    lapSecs = (lapTimeTotal / 1000) % 60;
                    lapMins = (lapTimeTotal / (1000 * 60)) % 60;
                    lapHrs = (lapTimeTotal / ((1000 * 60) * 60)) % 60;
                    lapTimeBegin = lapTimeEnd;
                    lapTimeEnd = 0;
                                  //Letting app know that there is new lap time
                                  //data to save.
                    boolIsDataToSave = true;
                                  //Checking that the number of set lap times is OK.
                                  //Only 20 lap time marks are allowed.
                    if (counter < arrStrLaps.length) {
                                  //Getting the time elapsed since that chronometer
                                  //started.
                        arrStrLaps[counter] = "[" + counter + "]_" + chrono.getText() + "_" +
                                  //Real total value of the last lap elapsed time.
                                                String.valueOf(lapHrs) + " : " +
                                                String.valueOf(lapMins) + " : " +
                                                String.valueOf(lapSecs);
                                  //Populating the ListView adapter ArrayList
                                  //with new data.
                        arrList.add(arrStrLaps[counter]);
                                  //All these is MAY BE just for debugging purposes.
                        String strDateTime = getDateTime("MM/dd/yyyy HH:mm:ss");
                        String strDist = "20Km";
                        String strLocation = "Las Cruces";
                        lapInfo.add(strDateTime + "~" + chrono.getText().toString() + "~" + strDist +
                                "~" + strLocation);
                                  //This is just for debugging purposes.
                        l("Info: " + strDateTime + " " + chrono.getText().toString() + " " + strDist +
                                " " + strLocation);
                    }
                                  //Incrementing the number of lap times that
                                  //can be measured.
                    counter++;
                                  //Updating ListView with new lap time data.
                    listVwAdaptador.notifyDataSetChanged();
                    break;
                                  //Showing all the local DB lap time records.
                case R.id.btnStoryId:
                    Toast.makeText(MainActivity.this, "Lap Location", Toast.LENGTH_SHORT).show();
                                  //Defining the intent to start the new class.
                    Intent intent = new Intent(MainActivity.this, ShowDBLapInfo.class);
                                  //Starting the new activity.
                    startActivity(intent);
                    break;
                                  //View widget Chronometer circles animation click.
                case R.id.vwId:
                                  //Ask user to enter a date to search DB for
                                  //records with date
                    showDBLapInfo("Date");
                    break;

            }   //End of switch() statement

        }   //End of onClick() function

    }   //End of class




    /**
     * ChronoTickListener internal class is used to handle the event generated
     * by the chronometer when time changes (every second.) This inner class
     * also drive the animation of this app by calculating the seconds, minutes
     * and hours. These values are pass to the class that makes the animation.
     */
    private class ChronoTickListener implements Chronometer.OnChronometerTickListener{

        @Override
        public void onChronometerTick(Chronometer chronometer) {
            int secs, mins, hrs;
            hrs = 0;
                                  //Array to hold the string representing the time
                                  //produced by the Chronometer.
            String[] HrsMinsSecs;

            HrsMinsSecs = chronometer.getText().toString().split(":");
                                  //Sometimes the String has seconds, minutes
                                  //and hours.
            if (HrsMinsSecs.length >= 3) {
                hrs = Integer.parseInt(HrsMinsSecs[0]);
                mins = Integer.parseInt(HrsMinsSecs[1]);
                secs = Integer.parseInt(HrsMinsSecs[2]);
            }
                                  //Sometimes the String has only seconds
                                  //and minutes.
            else {
                mins = Integer.parseInt(HrsMinsSecs[0]);
                secs = Integer.parseInt(HrsMinsSecs[1]);
            }
                                  //Asking the class driving the View animation
                                  //to redraw itself with new data.
            cronoAnimacion.invalidateOnDraw(hrs, mins, secs);

        }   //End of onChronometerTick() function

    }   //End of ChronoTickLIstener class




    /**
     * The l(String) function is used only to debug this class. It uses the
     * Log.d() function to pass the information to the Android Monitor window.
     * This information contains the class name and some information about the
     * error or data about the debugging process.
     *
     * @param str type String
     */
    private void l(String str){
        Log.d("NICKY", this.getClass().getSimpleName() + " -> " + str);
    }


}   //End of the MainActivity Class


/*********************************END OF MainActivity.java FILE**********************************/


