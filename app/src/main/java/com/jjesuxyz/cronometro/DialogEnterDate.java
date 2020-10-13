package com.jjesuxyz.cronometro;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class DialogEnterDate extends DialogFragment
                                implements TextView.OnEditorActionListener {

    private EditText editText;

    /**
     * DialogEnterDate() default constructor neede becasuse there are not parameters.
     */
    public DialogEnterDate(){}


    interface EnterDateDialogListener{
        void onFinishEnteringDate(String strDate);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_date, container);

        editText = (EditText) view.findViewById(R.id.edTxtEnterDateId);
        editText.setOnEditorActionListener(this);
        editText.requestFocus();

        try {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        }
        catch(NullPointerException npex) {
            npex.printStackTrace();
        }

        return view;
    }








    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        EnterDateDialogListener activity = (EnterDateDialogListener) getActivity();
        activity.onFinishEnteringDate(editText.getText().toString());
        this.dismiss();

        return false;
    }
}
