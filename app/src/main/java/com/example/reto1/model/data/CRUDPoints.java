package com.example.reto1.model.data;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reto1.app.Organizador;
import com.example.reto1.model.driver.DBDriver;
import com.example.reto1.model.entity.Points;

import java.util.ArrayList;

public class CRUDPoints {

    public static void insertPoint(  Points points){
        DBDriver driver = DBDriver.getInstance(Organizador.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();

        String sql = "INSERT INTO $TABLE($ID,$POINTS) VALUES('$VID',$VPOINTS)";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_POINT)
                .replace("$ID", DBDriver.POINT_ID)
                .replace("$POINTS", DBDriver.POINT_POINTS)

                .replace("$VID", points.getId())
                .replace("$VPOINTS", ""+points.getPoints());

        db.execSQL(sql);
        db.close();
    }
    public static void updatePoints(  Points points){
        DBDriver driver = DBDriver.getInstance(Organizador.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();

        String sql = "UPDATE $TABLE SET $POINTS=$VALUE WHERE $ID=$VID" ;
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_POINT)
                .replace("$ID", DBDriver.POINT_ID)
                .replace("$POINTS", DBDriver.POINT_POINTS)

                .replace("$VID", points.getId())
                .replace("$VALUE", ""+points.getPoints());

        db.execSQL(sql);
        db.close();
    }



    public static Points getPoint(){
        DBDriver driver = DBDriver.getInstance(Organizador.getAppContext());
        SQLiteDatabase db = driver.getReadableDatabase();
        Points point = null;

        String sql = "SELECT * FROM $TABLE WHERE $FID = '1'";
        sql = sql
                .replace("$TABLE",DBDriver.TABLE_POINT)
                .replace("$FID",DBDriver.POINT_ID);


        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){

                int points = cursor.getInt(cursor.getColumnIndex(DBDriver.POINT_POINTS));
                String id = cursor.getString(cursor.getColumnIndex(DBDriver.POINT_ID));
                point = new Points(id, points);

        }

        db.close();
        return point;
    }



    public static void deteleTask(Points task) {
        DBDriver driver = DBDriver.getInstance(Organizador.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        String sql = "DELETE FROM $TABLE WHERE $ID = '$FID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_POINT)
                .replace("$ID",DBDriver.POINT_ID)
                .replace("$FID",task.getPoints()+"");
        db.execSQL(sql);
        db.close();
    }






}
