package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addMarker extends AppCompatActivity {


    private EditText name;

    private Button but_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);
        name=findViewById(R.id.txt_name);
        but_send=findViewById(R.id.btn_send);
        but_send.setBackgroundColor(Color.RED);
        but_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i = new Intent();
                i.putExtra( "nombre", name.getText().toString() );
                setResult(RESULT_OK, i);
                Toast.makeText(getApplicationContext(),"Seleccione un punto en el mapa",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
