package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto1.model.data.CRUDPoints;
import com.example.reto1.model.entity.Points;

import java.util.ArrayList;
import java.util.Random;

public class Operation extends AppCompatActivity {

    private Button btn_answer;
    private TextView txt_operation;
    private EditText txt_answer;
    private  ArrayList<Integer> arr;
    private  int points;
    private TextView information;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        txt_operation=findViewById(R.id.txt_operation);
        txt_answer=findViewById(R.id.answer);

        points=0;
        arr= new ArrayList<Integer>();
        arr=arithGo();
       showProblem(arr);


       information=findViewById(R.id.information);

        btn_answer=findViewById(R.id.btn_answer);
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int resp=0;
                try{
                   resp= Integer.parseInt(txt_answer.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Lo siento!, respuesta incorrecta",Toast.LENGTH_LONG).show();
                }
                if(validationAnswer(arr,resp)){

                    btn_answer.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Felicitaciones, respuesta correcta",Toast.LENGTH_LONG).show();
                    information.setVisibility(View.VISIBLE);
                    Intent i= new Intent(getApplicationContext(),Mapa.class);
                    startActivity(i);


                }else{
                    Toast.makeText(getApplicationContext(),"Lo siento!, respuesta incorrecta",Toast.LENGTH_LONG).show();
                    btn_answer.setEnabled(false);
                    Intent i= new Intent(getApplicationContext(),Mapa.class);
                    startActivity(i);


                }

            }
        });
    }

    public boolean validationAnswer(ArrayList<Integer>ans,int resp){

        if(CRUDPoints.getPoint()==null){
            Points p1= new Points("1",0);
            CRUDPoints.insertPoint(p1);
        }

            Points point= CRUDPoints.getPoint();
            int pointActual= point.getPoints();
            boolean flag=false;
            if(ans.get(3)==resp){
                Toast.makeText(getApplicationContext(),"Felicitaciones, la respuesta esta buena",Toast.LENGTH_LONG).show();

                flag=true;
                //sumo los puntos
                int nuevo= pointActual+1;
                point.setPoints(nuevo);
                CRUDPoints.updatePoints(point);

            }else{
                Toast.makeText(getApplicationContext(),"Lo siento!, respuesta incorrecta",Toast.LENGTH_LONG).show();
               int viejo=pointActual-1;
               point.setPoints(viejo);
               CRUDPoints.updatePoints(point);
                flag=false;
            }
            return flag;



    }

    public void showProblem(ArrayList<Integer> op){
        String ope="";

        switch (op.get(2)){
            case 1:
                ope="+";
                break;

            case 2:
                ope="-";
                break;

            case 3:
                ope="*";
                break;

            case 4:
                ope="/";
                break;
        }
        txt_operation.setText(arr.get(0)+" "+ope+" "+ arr.get(1)+"="+"?");
    }

    public ArrayList<Integer> arithGo(){
        ArrayList<Integer> ope= new ArrayList<>();
        Random random= new Random(System.currentTimeMillis());
        int resultado=0;

        int n1=(random.nextInt(30)+10);
        ope.add(n1);
        int n2=(random.nextInt(20)+1);
        ope.add(n2);
        int operation=(random.nextInt(4)+1);
        ope.add(operation);
        switch (operation){
            case 1:
                resultado= n1+n2;

                break;

            case  2:

                resultado =n1-n2;
                break;

            case 3:
                resultado=n1*n2;
                break;

            case 4:
                resultado=n1/n2;

                break;

        }
        ope.add(resultado);


        return  ope;
    }

    public Button getBtn_answer() {
        return btn_answer;
    }

    public TextView getTxt_operation() {
        return txt_operation;
    }

    public EditText getTxt_answer() {
        return txt_answer;
    }

    public ArrayList<Integer> getArr() {
        return arr;
    }

    public int getPoints() {
        return points;
    }

    public TextView getInformation() {
        return information;
    }

    public void setBtn_answer(Button btn_answer) {
        this.btn_answer = btn_answer;
    }

    public void setTxt_operation(TextView txt_operation) {
        this.txt_operation = txt_operation;
    }

    public void setTxt_answer(EditText txt_answer) {
        this.txt_answer = txt_answer;
    }

    public void setArr(ArrayList<Integer> arr) {
        this.arr = arr;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setInformation(TextView information) {
        this.information = information;
    }
}
