package com.jjesuxyz.cronometro.DBData;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;




/**
 * DBAccessHelper class is used to create a local database and its tables. It
 * does not make any other function for this app, so far. Final variables
 * defined in this class may be used in other classes of this app.
 *
 */
public class DBAccessHelper extends SQLiteOpenHelper {
                                  //Global class variables declaration
                                  //and definition.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dblap.db";
    private static final String SQL_CREATE_TABLE =
                                    "CREATE TABLE " + DBContractInfo.TABLE_NAME + "(" +
                                    DBContractInfo.CO_NAME_ID +           " INTEGER PRIMARY KEY, " +
                                    DBContractInfo.CO_NAME_DATETIME +     " TEXT, " +
                                    DBContractInfo.CO_NAME_LAP_TIMES +    " TEXT, " +
                                    DBContractInfo.CO_NAME_DISTANCE +     " TEXT, " +
                                    DBContractInfo.CO_NAME_LOCATION +     " TEXT );";

    private static final String SQL_DELETE_ENTRIES =
                                    "DROP TABLE IF EXISTS " + DBContractInfo.TABLE_NAME;



    /**
     * DBAccessHelper(Context) class constructor is used pass data to the super
     * class of this class. It needs a Context object as a parameter. This
     * parameter is passed to the super function.
     *
     * @param context type Context
     */
    public DBAccessHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }   //End of DBAccessHelper(@Nullable Context) constructor



    /**
     * onCreate(SQLiteDatabase) function is used to create a database and its
     * table. This function is called by the system. The parameter it receives
     * is a database object.
     *
     *  @param db type SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
                                  //Creating database tables.
            db.execSQL(SQL_CREATE_TABLE);
        }
        catch(SQLException sqle) {
                                  //Catching error if any.
            sqle.printStackTrace();
        }
    }   //End of onCreate(SQLiteDatabase) function



    /**
     * The onUpgrade(SQLiteDatabase, int, int) function is not used in this
     * version of this app.
     *
     * @param db type SQLiteDatabase
     * @param oldVersion type int
     * @param newVersion type int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }   //End of onUpgrade(SQLiteDatabase, int, int) function.



    /**
     * The onDowngrade(SQLiteDatabase, int, int) function is not used in this
     * version of this app.
     *
     * @param db type SQLiteDatabase
     * @param oldVersion type int
     * @param newVersion type int
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }   //End of onDowngrade(SQLiteDatabase, int, int) function

}   //End of DBAccessHelper Class


/***********************************END OF DBAccessHelper.java FILE***********************************/

