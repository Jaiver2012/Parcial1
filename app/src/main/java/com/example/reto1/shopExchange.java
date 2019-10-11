package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto1.model.data.CRUDPoints;
import com.example.reto1.model.entity.ItemList;
import com.example.reto1.model.entity.Points;

import java.util.ArrayList;

public class shopExchange extends AppCompatActivity {


    ListView shopList;
    ArrayAdapter<ItemList> adapter;
    ArrayList<ItemList> list;
    private  MainActivity mainA;
    private TextView txt_Points;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_exchange);
        txt_Points=findViewById(R.id.txt_Points);

        mainA= new MainActivity();
        list= new ArrayList<>();
        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        shopList = findViewById(R.id.list);
        shopList.setAdapter(adapter);

        btnBack=findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Mapa.class);
                startActivity(i);
            }
        });

        ItemList item1= new ItemList("Lapicero Icesi -- 20 pts",20);
        ItemList item2= new ItemList("Cuaderno -- 30 pts",30);
        ItemList item3= new ItemList("Libreta Icesi -- 40 pts",40);
        ItemList item4= new ItemList("Camisa Icesi -- 80 pts",80);
        ItemList item5= new ItemList("Saco Icesi -- 100 pts",100);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);

        txt_Points.setText("Tu puntaje es: "+CRUDPoints.getPoint().getPoints());

        shopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                exchange(list.get(pos));

            }
        });

    }

    public void exchange(ItemList item){

        Points p=CRUDPoints.getPoint();
        int points= p.getPoints();

        if(item.getValue()<=points){

            points= (points-item.getValue());
            p.setPoints(points);
            CRUDPoints.updatePoints(p);

            txt_Points.setText("Tu puntaje es: "+points);
            Toast.makeText(getApplicationContext(),"Se realizó el canje",Toast.LENGTH_LONG).show();

        }
        else{
            txt_Points.setText("Tu puntaje es: "+points);
            Toast.makeText(getApplicationContext(),"No tienes suficientes puntos para canjear",Toast.LENGTH_LONG).show();
        }

    }


}
