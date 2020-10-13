package com.jjesuxyz.cronometro.DBData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;




/**
 * The DBAccess class is used to get access and manipulate the local database.
 * It opens it in writable mode and provide functions and methods to insert and
 * extract data from the database tables. It also provides functions to delete
 * database table records. It also contains some functions that were used to
 * debug this class.
 *
 */
public class DBAccess {

    private Context context;
                                  //Variable to access database.
    private SQLiteDatabase database;
    private DBAccessHelper dbAccessHelper;



    /**
     * Class constructor, DBAccess(Context), used to create an object to access
     * the local database, and to open that database in a writable mode. It
     * receives one parameter that is a link to one of the user interfaces that
     * made the request.
     *
     * @param context type Context
     */
    public DBAccess(Context context) {
        this.context = context;
                                  //Getting access to the database class helper.
        dbAccessHelper = new DBAccessHelper(this.context);

        try {
                                  //Opening the local database in a writable mode.
            open();
        }
        catch (SQLException sqle) {
                                  //if any error opening the local Db,
            sqle.printStackTrace();
        }
    }   //End of the DBAccess constructor  function



    /**
     * The open() function is used to get a link to the local database and open
     * it in a writable mode.
     *
     * @throws SQLException threw when there is a problem to get access to the
     * local DB.
     */
    private void open() throws SQLException {
                                  //Connection to local Db is on database var.
        database = dbAccessHelper.getWritableDatabase();
                                  //Just debugging la connection to DB.
        if (database == null) {
            l("DB is NULL DBAccess->open()");
        }
    }   //End of open() function



    /**
     * close() function is used to close the local database and release resources.
     * It does not receive any parameter nor returns any value.
     *
     */
    public void close() {
                                 //Database connection object.
        if (database != null) {
            database.close();
        }
                                  //Database connection helper object.
        if (dbAccessHelper != null) {
            dbAccessHelper.close();
        }
    }   //End of close() function



    /**
     * insertRecordsIntoLapTimeTable(ArrayList<String>) function is used to
     * insert all the lap time records into the local database table. It needs
     * one parameter of type ArrayList of strings. Each of these strings is
     * holds the time that each lap lasted.
     * It does not need the table name since it is used to insert records only
     * into the lap time table.
     * It returns the id of the record inserted or -1 if there was an error.
     *
     * @param lapInfo type String
     * @return type long
     */
    public long insertRecordsIntoLapTimeTable(ArrayList<String> lapInfo) {
                                  //Var to return info about success of error
                                  //about the action this function does.
        long insertedRows = 0;
                                  //Setting the column values into a
                                  //ContentValues obj.
        ContentValues lapTimeValuesList = new ContentValues();

        for (String str : lapInfo) {
            String[] strArr = str.split("~");
            lapTimeValuesList.put(DBContractInfo.CO_NAME_DATETIME, strArr[0]);
            lapTimeValuesList.put(DBContractInfo.CO_NAME_LAP_TIMES, strArr[1]);
            lapTimeValuesList.put(DBContractInfo.CO_NAME_DISTANCE, strArr[2]);
            lapTimeValuesList.put(DBContractInfo.CO_NAME_LOCATION, strArr[3]);

            try {
                                  //Inserting values into the database table.
                insertedRows = database.insert(DBContractInfo.TABLE_NAME, null, lapTimeValuesList);
            }
            catch (SQLException sqle) {
                                  //Catching error if any.
                sqle.printStackTrace();
            }
                                  //Code for debugging purposes.
            if (insertedRows < 0) {
                l("Error inserting record clase-DBAccess->insertRecordIntoSingerListTable()");
            }
        }

        return insertedRows;
    }   //End of the insertRecordsIntoLapTimeTable(ArrayList<String>) function



    /**
     * getAllRecordsFromDBLapTimeTable() function is used to get all the records
     * from the local database lt_info table. It calls the function query to
     * execute the query.  It gets a cursor from this function call querying the
     * local database.
     * This function returns a cursor holding the data from the database lt_info
     * table.
     *
     * @return type Cursor
     */
    public Cursor getAllRecordsFromDBLapTimeTable() {
        String[] columns = {DBContractInfo.CO_NAME_DATETIME, DBContractInfo.CO_NAME_LAP_TIMES,
                            DBContractInfo.CO_NAME_DISTANCE, DBContractInfo.CO_NAME_LOCATION};

        Cursor cursor;

        cursor = database.query(DBContractInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }   //End of getAllRecordsFromDBLapTimeTable() function



    /**
     * The isTableEmpty(String) function is used to check if the local database
     * table is empty. It returns true if the local database table being analyzed
     * is NOT empty. It returns false if the local database table being analyzed
     * is empty.
     * It receives one string parameter. This parameter is the name of the table
     * to be checked.
     * It returns a boolean value.
     *
     * @param tableName type String
     * @return type boolean
     */
    public boolean isTableEmpty(String tableName) {
        boolean empty = false;
        Cursor cursor;
                                  //Querying db table.
        cursor = database.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
                                  //Checking if cursor is empty or null.
        if (cursor != null && cursor.moveToFirst() && cursor.getInt(0) == 0) {
                                  //Setting the return value.
            empty = true;
        }
                                  //Closing the cursor.
        cursor.close();

        return empty;
    }   //End of isTableEmpty(String) function



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
    }   //End of function


}   //End of DBAccess Class


/*************************END OF DBAccess.java FILE***************************/


