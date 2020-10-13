package com.jjesuxyz.cronometro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;




/**
 * ListViewAdaptadorDB class is used by the ListView widget to define the layout
 * of each row in the ShowDBLapInfo UI ListView. In its constructor function this
 * class receives as parameter a reference to the MainActivity UI.  It also
 * receives a xml file id. It also receives an ArrayList that contains all the
 * lap time info that will be displayed in the ListView.
 *
 * Created by jjesu on 7/18/2018.
 */
public class ListViewAdaptadorDB extends ArrayAdapter {

    private Context context;
                                  //ArrayList to hold lap time data.
    private ArrayList<String> arrList;
                                  //Xml layout file holds info about each row
                                  //in the ListView on the ShowDBLapInfo UI.
    private int resource;




    /**
     * ListViewAdaptadorDB() is the constructor of this class. This is the only
     * constructor that this class needs. It receives from the local databases,
     * as parameters, the list of lap times info, the xml file id to be inflated,
     * and the app Context.
     * It initializes some local class variables with the parameters it receives.
     *
     * @param context type Context
     * @param resource type int
     * @param arrayStrLaps type ArrayList
     */
    public ListViewAdaptadorDB(@NonNull Context context, @LayoutRes int resource, ArrayList<String> arrayStrLaps) {
        super(context, resource, arrayStrLaps);

        this.context = context;
                                  //ArrayList holding lap time data from local Db.
        arrList = arrayStrLaps;
                                  //ArrayList cannot be empty.
        if (arrList.size() <= 0) {
            arrList.add("AT THIS MOMENT_DB_IS_EMPTY");
            arrList.add("AT THIS MOMENT_DB_IS_EMPTY");
            arrList.add("AT THIS MOMENT_DB_IS_EMPTY");
            arrList.add("AT THIS MOMENT_DB_IS_EMPTY");
            arrList.add("AT THIS MOMENT_DB_IS_EMPTY");
        }
                                  //Xml layout file to be inflate for each row.
        this.resource = resource;

    }   //End of ListViewAdaptadorDB() constructor



    /**
     * getView() function is used to customize each row of the ListView that
     * show users the list of lap times records that the databases contains.
     * Each row contains four TextView elements that are customized by this
     * function. Each row shows the date and time the lap was completed. The
     * time lapse that the lap lasted. The distance traveled, and the location
     * where the lap was completed.
     *
     * @param position type int
     * @param convert type View
     * @param parent type ViewGroup
     *
     * @return type View
     */
    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                  //First time that ListView rows are populated.
        if (convert == null) {
            convert = inflater.inflate(resource, parent,false);
        }
                                  //Splitting string records from the local Db.
        String[] strArr = arrList.get(position).split("_");
                                  //Setting the date and time lap row.
        TextView txtvwDateTime = (TextView) convert.findViewById(R.id.txtvwDateTimeDBId);
        txtvwDateTime.setText(strArr[0]);
                                  //Setting the lapsed time the lap lasted.
        TextView txtvwLapDur = (TextView) convert.findViewById(R.id.txtvwLapDuraDBId);
        txtvwLapDur.setText(strArr[1]);
                                 //Setting the distance the lap was traveled.
        TextView txtvwLapDist = (TextView) convert.findViewById(R.id.txtvwLapDistDBId);
        txtvwLapDist.setText(strArr[2]);
                                  //Setting the location the lap was traveled.
        TextView txtvwLapLoca = (TextView) convert.findViewById(R.id.txtvwLapLocDBId);
        txtvwLapLoca.setText(strArr[3]);
                                  //Setting different row text color.
        if (position % 2 == 0) {
            txtvwDateTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorLetrasEnable));
            txtvwLapDur.setTextColor(ContextCompat.getColor(getContext(), R.color.colorLetrasEnable));
            txtvwLapDist.setTextColor(ContextCompat.getColor(getContext(), R.color.colorLetrasEnable));
            txtvwLapLoca.setTextColor(ContextCompat.getColor(getContext(), R.color.colorLetrasEnable ));
        }
                                  //Returning the row fully formatted.
        return convert;

    }   //End of getView() function



    /**
     * updateValues(String[]) function is not used, so far, in this version of the app
     *
     * @param arrStr type String[]
     */
    public void updateValues(String[] arrStr) {
                                  //Clearing the ArrayLIst to update it with new
                                  //lap info.
        this.arrList.clear();
                                  //Updating the internal ArrayLIst with new data.
        this.addAll(arrList);

    }   //End of updateValues() function





}   //End of ListViewAdaptadorDB class



/*******************END OF ListViewAdaptadorDB.java FILE**********************/
