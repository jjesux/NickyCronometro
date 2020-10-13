package com.jjesuxyz.cronometro;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.jjesuxyz.cronometro.DBData.DBAccess;
import java.util.ArrayList;




/**
 * DBDataMngmt class is used like a medium to get data from the local Db and
 * insert data into the local Db. It does not directly access the Db. It uses
 * another class that it directly access the local Db. This class is used also
 * to format the data from and into the DB.
 */
public class DBDataMngmt {

    private Context context;



    /**
     * DBDataMngmt Public class constructor is used to perform some global class
     * variables initialization.  It receives a Context object as the only
     * parameter it needs.
     *
     * @param context type Context
     */
    DBDataMngmt(Context context){
                                  //Getting an application context reference
        this.context = context;

    }   //End of constructor




    /**
     * insertMultipleRecordsIntoPlayListTable(ArrayList) function is used to
     * insert multiple records into the local database lap time table. It inserts
     * into the database table all the records it receives as an ArrayList
     * parameter. It does not returns any value.
     *
     * @param arrayListFilePath type ArrayList
     */
    void insertMultipleRecordsIntoLapTimeTable(ArrayList<String> arrayListFilePath){
                                  //Checking that ArrayList has been instantiated.
        if (arrayListFilePath != null) {
                                  //Getting access to the local database.
            DBAccess dbAccess = new DBAccess(context);
                                  //Inserting multiple records into the database.
            dbAccess.insertRecordsIntoLapTimeTable(arrayListFilePath);
                                  //Closing local database connection.
            dbAccess.close();
            dbAccess = null;
        }
        else {
                                  //Code just for debugging purposes.
            l("Function insertMultipleRecordsIntoLapTimeTable(AL<Str> => NULL) ");
        }

    }   //End of insertMultipleRecordsIntoLapTimeTable() function




    /**
     * getArrayListFromLapTimeTable() function is used to get all the records
     * from the local database lap time table. It returns all those records,
     * that it receives from the database, after extracting them from the Cursor
     * object. This function does not access directly the local database, it
     * just calls another function in another class that actually access the
     * database tables to complete the get data action.
     *
     * @return type ArrayList
     */
    ArrayList<String> getArrayListFromLapTimeTable(){
                                  //ArrayList holds all the DB table records.
        ArrayList<String> arrayListPLayList = new ArrayList<>();
                                  //Instantiating object to access local DB.
        DBAccess dbAccess = new DBAccess(context);
                                  //Getting all records from local DB.
        Cursor cursor = dbAccess.getAllRecordsFromDBLapTimeTable();
                                  //Extracting records from Cursor.
        while (cursor.moveToNext()) {
            String strRow = "";
                                  //Extracting all table columns data.
            for (int i = 0; i < cursor.getColumnCount() - 1; i++) {
                strRow = strRow +  cursor.getString(i) + "_";
            }
                                  //Last column to not include the '-' character.
            strRow = strRow + cursor.getString(cursor.getColumnCount() - 1);
                                  //Adding a complete row string the ArrayList.
            arrayListPLayList.add(strRow);
        }
                                  //Closing cursor to avoid memory leak.
        cursor.close();
                                  //Closing local DB.
        dbAccess.close();
                                  //Returning all records gotten from local Db.
        return arrayListPLayList;

    }   //End of getArrayListFromPlayListTable() function




    /**
     * isTableEmpty(String) function is used to check if a database table, whose
     * name it receives as a parameter, is empty.  It returns true if table is
     * empty false otherwise.
     *
     * @param strTableName type String
     * @return type boolean
     */
    public boolean isTableEmpty(String strTableName) {
                                  //Getting access to the local database.
        DBAccess dbAccess = new DBAccess(context);
                                  //Calling another function that actually
                                  //access the DB.
        boolean blIsTbEmpty = dbAccess.isTableEmpty(strTableName);
                                  //Closing the database.
        dbAccess.close();

        return blIsTbEmpty;

    }   //End of isTableEmpty() function




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

}   //End of DBDataMngmt Class


/***********************************END OF DBDataMngmt.java FILE***********************************/

