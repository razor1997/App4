package com.example.app4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
//
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
//
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Asortyment
    public static String[] comRange;
    String currentItemName=null;
    Integer currentItemQuantity=null;

    //Kontrolki potrzebne w wiecej niz jednej metodzie
    TextView stateTV=null;
    EditText changeET=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stateTV = (TextView) findViewById(R.id.stateTextView); //XML->Java
        changeET = (EditText) findViewById(R.id.editText); //XML->Java

        comRange= getResources().getStringArray(R.array.Flota);

        //Dostep do bazy
        final SQLiteOpenHelper DBHelper = new MarketDatabaseHelper(this);
        //Spinner i jego adapter


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.Flota,android.R.layout.simple_spinner_item);



        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner =(Spinner) findViewById(R.id.Spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view, int i, long l)
                    {

                        currentItemName=comRange[i]; //Nazwa wskazana przez spinner

                        //Realizujemy polecenie SQL
                        //SELECT QUANTITY FROM STAND WHERE NAME=currentItemName
                        try
                        {
                            SQLiteDatabase DB = DBHelper.getReadableDatabase();
                            Cursor cursor = DB.query(
                                    "STAND",
                                    new String[] {"QUANTITY"},
                                    "NAME = ?",
                                    new String[]{currentItemName},
                                    null,null,null);
                            cursor.moveToFirst();//Kursor zawsze trzeba ustawic
                            currentItemQuantity = cursor.getInt(0);//nr kolumny

                            cursor.close();

                            DB.close();
                        }
                        catch(SQLiteException e)
                        {
                            Toast.makeText(MainActivity.this,
                                    "EXCEPTION: SPINNER",Toast.LENGTH_SHORT).show();
                        }

                        stateTV.setText("Stan magazynu dla " +currentItemName+
                                " :"+currentItemQuantity);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}

                });

        //Przycisk "Skladuj"
        Button setButton = (Button) findViewById(R.id.setButton);

        setButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) { //Dodanie do bazy



                Integer changeItemQuantity =
                        Integer.parseInt(changeET.getText().toString());

                Integer newItemQuantity=currentItemQuantity+changeItemQuantity;

                //Realizujemy polecenie SQL
                //UPDATE STAND SET QUANTITY=newItemQuantity
                //WHERE NAME=currentItemName

                try
                {
                    SQLiteDatabase DB = DBHelper.getWritableDatabase();
                    ContentValues itemValues = new ContentValues();
                    itemValues.put("QUANTITY",newItemQuantity.toString());
                    DB.update("STAND",
                            itemValues,
                            "NAME=?",
                            new String[] {currentItemName});

                    DB.close();
                }
                catch (SQLiteException e)
                {
                    Toast.makeText(MainActivity.this,"EXCEPTION:SET",
                            Toast.LENGTH_SHORT).show();
                }
                stateTV.setText("Stan magazynu dla "
                        +currentItemName+" :"+newItemQuantity);
                changeET.setText("");

                currentItemQuantity=newItemQuantity;
            }

        });


        //Przycisk "Wydaj"
        Button getButton = (Button) findViewById(R.id.getButton);

        getButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Integer changeItemQuantity =
                        Integer.parseInt(changeET.getText().toString());

                Integer newItemQuantity=currentItemQuantity-changeItemQuantity;

                //UPDATE STAND SET QUANTITY=newItemQuantity
                //WHERE NAME=currentItemName
                try
                {
                    SQLiteDatabase DB = DBHelper.getWritableDatabase();
                    ContentValues itemValues = new ContentValues();
                    itemValues.put("QUANTITY",newItemQuantity.toString());

                    DB.update("STAND",
                            itemValues,
                            "NAME=?",
                            new String[] {currentItemName});

                    DB.close();
                }
                catch (SQLiteException e)
                {
                    Toast.makeText(MainActivity.this,"EXCEPTION: GET",
                            Toast.LENGTH_SHORT).show();
                }

                stateTV.setText("Stan magazynu dla "
                        +currentItemName+" :"+newItemQuantity);
                changeET.setText("");
                currentItemQuantity=newItemQuantity;
            }

        });
        Button descButton = (Button) findViewById(R.id.descButton);

        setButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) { //Dodanie do bazy


                Integer changeItemQuantity =
                        Integer.parseInt(changeET.getText().toString());

                Integer newItemQuantity=currentItemQuantity+changeItemQuantity;

                //Realizujemy polecenie SQL
                //UPDATE STAND SET QUANTITY=newItemQuantity
                //WHERE NAME=currentItemName

                try
                {
                    SQLiteDatabase DB = DBHelper.getWritableDatabase();
                    ContentValues itemValues = new ContentValues();
                    itemValues.put("Description",newItemQuantity.toString());
                    DB.update("DESCRIPTION",
                            itemValues,
                            "NAME=?",
                            new String[] {currentItemName});

                    DB.close();
                }
                catch (SQLiteException e)
                {
                    Toast.makeText(MainActivity.this,"EXCEPTION:SET",
                            Toast.LENGTH_SHORT).show();
                }
                stateTV.setText("Opis"
                        +currentItemName+" :"+newItemQuantity);
                changeET.setText("");

                currentItemQuantity=newItemQuantity;
            }

        });

    }//protected void onCreate(Bundle savedInstanceState)
}//public class Activity01 extends AppCompatActivity