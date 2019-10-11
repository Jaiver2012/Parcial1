package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.example.reto1.model.entity.Points;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private SwipeButton swipeButton;
    private Spinner spinner;
    private RadioButton btnMan;
    private RadioButton btnWoman;
    private EditText et_name;


    private static Context context;

    public static  Context getContext(){
        return context;
    }

    public final static  String[] level={"Principiante", "Medio","Legendario"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context=getApplicationContext();
        setContentView(R.layout.activity_main);


        et_name=findViewById(R.id.et_name);


        swipeButton= (SwipeButton) findViewById(R.id.swipe_btn);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if(!et_name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Bienvenido "+et_name.getText(),Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(getApplicationContext(),Mapa.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Por favor escribe el nombre",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnMan= (RadioButton)findViewById(R.id.btn_men);
        Toast.makeText(getApplicationContext(),btnMan.getText(),Toast.LENGTH_LONG).show();
        btnWoman=(RadioButton)findViewById(R.id.btn_women);
        Toast.makeText(getApplicationContext(),btnWoman.getText(),Toast.LENGTH_LONG).show();

        spinner = (Spinner)findViewById(R.id.sp_level);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,level);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i) {
            case 0:
                Toast.makeText(getApplicationContext(),"Nivel principiante",Toast.LENGTH_SHORT).show();

                break;
            case 1:
                Toast.makeText(getApplicationContext(),"Nivel medio, eso!",Toast.LENGTH_SHORT).show();

                break;
            case 2:
                Toast.makeText(getApplicationContext(),"Nivel legendario. Buena suerte, vaquero",Toast.LENGTH_SHORT).show();

                break;


        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
