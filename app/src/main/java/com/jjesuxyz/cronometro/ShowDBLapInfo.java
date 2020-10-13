package com.jjesuxyz.cronometro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;




/**
 * ShowDBLapInfo class is used to show data to the user. This data is retrieved
 * from the local database tables, and it uses a ListView widget to display this
 * data. This class does not access directly the local database. It does not
 * manipulate data retrieved from the database. That manipulation is done by
 * another class.
 *
 */
public class ShowDBLapInfo extends Activity {



    /**
     * onCreate(Bundle) callback function is used to get data from DB and display
     * this data to the user using a ListView. It also sets the ListView click
     * listener class and the ListView adapter.
     *
     * @param savedInstanceState type Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
                                  //Inflating layout file of this class.
        setContentView(R.layout.db_lap_info);
                                  //Initializing array to hold data from DB and
                                  //use in ListView.
        ArrayList<String> arrayList = new ArrayList<>();
                                  //Getting access to local DB.
        DBDataMngmt dbDataMngmt = new DBDataMngmt(getApplicationContext());
        arrayList.addAll(dbDataMngmt.getArrayListFromLapTimeTable());
        ListViewAdaptadorDB listVwAdaptadorDB = new ListViewAdaptadorDB(getApplicationContext(),
                                                            R.layout.listvw_lap_info, arrayList);
                                  //ListView to display lap data from Db.
        ListView listView;
        listView = (ListView) findViewById(R.id.listvwLapId);
                                  //Setting ListView Adapter.
        listView.setAdapter(listVwAdaptadorDB);
        listView.setTextFilterEnabled(true);
                                  //Button to close this activity.
        Button btnCloseActivity;
                                  //Getting a reference to the real button.
        btnCloseActivity = (Button) findViewById(R.id.btnCloseActId);
                                  //Setting button click listener.
        btnCloseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                  //Terminating this activity.
                finish();
            }
        });
    }   //End of onCreate callback function




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
    }   //End of l() function

}   //End of the ShowDBLapInfo Class



/**********************END OF ShowDBLapInfo.java FILE***********************/
