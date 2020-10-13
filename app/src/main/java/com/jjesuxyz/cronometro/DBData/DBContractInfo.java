package com.jjesuxyz.cronometro.DBData;


/**
 * DbContractInfo class is used to hold the database table name and the table
 * column names. All the constant variables holding these names are also
 * statics variables.
 *
 */

public class DBContractInfo {

                                  //Table column names.
    static final String TABLE_NAME = "lt_info";
    static final String CO_NAME_ID = "id";
    static final String CO_NAME_DATETIME = "dt";
    static final String CO_NAME_LAP_TIMES = "lT";
    static final String CO_NAME_DISTANCE = "dist";
    static final String CO_NAME_LOCATION = "loc";



    /**
     * DbContractInf() public constructor is the only constructor in this class.
     * It is the default constructor.
     */
    private DBContractInfo(){}


}   //End of DbContractInfo Class


/***********************END OF DbContractInfo.java FILE***********************/
