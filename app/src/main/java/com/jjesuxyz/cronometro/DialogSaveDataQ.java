package com.jjesuxyz.cronometro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;




/**
 * DialogSaveDataQ class is used to create a dialog widget to ask users to save
 * or not save the lap time data. This dialog box shows users a question asking
 * if data should be saved or not. It shows also two buttons. One button is to
 * answer yes and another to answer not on saving lap time data into the local
 * database. Either answer will call a function defined in the MainActivity class.
 * The prototype of this function is defined in the interface define in this
 * class.
 */
public class DialogSaveDataQ extends DialogFragment {

    /**
     * DialogoDataListener interface is used by this class to transmit the answer
     * the user chose. Any class needing this imformation should use this
     * interface.
     */
    interface DialogoDataListener {
        void onClickingOkOrNotOkBtn(boolean saveQ);
    }


    /**
     * onCreateDialog(Bundle) callback function is used to create a dialog box
     * widget to ask users a question and it also shows two buttons for the
     * users to answer the question.
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DialogoDataListener activity = (DialogoDataListener) getActivity();

        return new AlertDialog.Builder(getActivity())
                        .setTitle("Saving Data")
                        .setMessage("Want to Save Data Laps?")
                                  //Button to answer Yes on saving data.
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                  //The users answer is the boolean parameter.
                                    activity.onClickingOkOrNotOkBtn(true);
                                }
                            })
                                  //Button to answer No on saving data.
                .setNegativeButton("Not", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                  //The users answer is the boolean parameter.
                                    activity.onClickingOkOrNotOkBtn(false);
                                }
                            })
                        .create();
    }   //End of funtion onCreateDialog(Bundle)

}   //End of DialogSaveDataQ Class


/***********************END OF DialogSaveDataQ.java FILE***********************/

