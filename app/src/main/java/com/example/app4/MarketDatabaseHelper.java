package com.example.app4;
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MarketDatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME="MarkedStand";
    private static final int DBVER=1; //Wersja bazy

    MarketDatabaseHelper(Context context){
        super(context,DBNAME,null,DBVER);
}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Utworzenie pustej tabeli, pierwsze uruchomienie aplikacji
        String sqlString=
                "CREATE TABLE STAND (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "NAME TEXT, QUANTITY INTEGER)";

        sqLiteDatabase.execSQL(sqlString);

        ContentValues itemValues = new ContentValues();

        for(int i=0; i<MainActivity.comRange.length; i++){
            //
            itemValues.clear();
            itemValues.put("NAME",MainActivity.comRange[i]);
            itemValues.put("QUANTITY",0);

            sqLiteDatabase.insert("STAND",null,itemValues); //-1 error
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }
} //class MarketDatabaseHelper
