package com.example.reto1.model.driver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBDriver extends SQLiteOpenHelper {

    private static DBDriver instance;

    public static synchronized DBDriver getInstance(Context context){
        if(instance == null){
            instance = new DBDriver(context);
        }
        return instance;
    }


    public static final String DBNAME = "organizador";
    public static final int DBVERSION = 1;

    //Table points
    public static final String TABLE_POINT="points";
    public static final String POINT_ID="id";
    public static final String POINT_POINTS="points";




    //
    private DBDriver(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }


    public DBDriver(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE_POINTS = "CREATE TABLE $TABLE($ID TEXT PRIMARY KEY, $POINTS INTEGER)";

        CREATE_TABLE_POINTS = CREATE_TABLE_POINTS
                .replace("$TABLE", TABLE_POINT)
                .replace("$ID", POINT_ID)
                .replace("$POINTS", POINT_POINTS);
        db.execSQL(CREATE_TABLE_POINTS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINT);

    }
}
