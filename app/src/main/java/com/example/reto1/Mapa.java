package com.example.reto1;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto1.model.data.CRUDPoints;
import com.example.reto1.model.entity.Points;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Mapa extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLongClickListener {



    private GoogleMap mMap;

    private Polygon IcesiArea;

    private Polygon wonka;

    private  Polygon biblioteca;

    private Polygon central;

    private Marker miUbicacion;


    private String nombre;

    private boolean esta;

    private int points;

    private ArrayList<Integer> values;


    private int pts;

    private Button grocery;

    private  Button question;

    private  MainActivity main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        main= new MainActivity();
        
        esta=false;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 11);


        grocery= findViewById(R.id.btn_grocery);
        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(),shopExchange.class);
                startActivity(i);

            }
        });

        question=findViewById(R.id.btn_question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(),Operation.class);
                startActivity(i);
            }
        });



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);

        IcesiArea = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.343039, -76.530756),
                new LatLng(3.343189, -76.527338),
                new LatLng(3.338680, -76.527052),
                new LatLng(3.338577, -76.531113)
        ));


        wonka = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.3414407361355245, -76.53011540141543),
                new LatLng(3.341419488779465, -76.529856540259),
                new LatLng(3.341238886166124, -76.52985317031779),
                new LatLng(3.3412565923041058, -76.53013331501775)

        ));

        biblioteca = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.3419554150743664, -76.53009285833129),
                new LatLng(3.3416791994453803, -76.53009791323494),
                new LatLng(3.341675658240291, -76.52979790397688),
                new LatLng(3.3419624975382902, -76.52979737202105)

        ));

        central = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.34227502609194, -76.52969998337794),
                new LatLng(3.341912050536237, -76.52972038010319),
                new LatLng(3.3419173623724703, -76.52954035677195),
                new LatLng(3.342275026054338, -76.52948980834202)

        ));

        LatLng icesi = new LatLng(3.341215, -76.530454);
        miUbicacion = mMap.addMarker(new MarkerOptions().position(icesi).title("Ubicación actual").icon(BitmapDescriptorFactory.fromResource(R.drawable.position5)));


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(icesi, 15));

        //SOLICITUD DE UBICACION

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);


        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);


    }


    //Cada vez que yo me mueva el va cambiando
    @Override
    public void onLocationChanged(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        miUbicacion.setPosition(pos);



        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 19));


        if(PolyUtil.containsLocation(miUbicacion.getPosition(),biblioteca.getPoints(),true)){
            grocery.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Estas en la biblioteca puedes canjear",Toast.LENGTH_LONG).show();
        }else{
            grocery.setVisibility(View.INVISIBLE);
        }

        if((PolyUtil.containsLocation(miUbicacion.getPosition(),central.getPoints(),true))|| (PolyUtil.containsLocation(miUbicacion.getPosition(),wonka.getPoints(),true))){
            question.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Estas en una zona reactiva",Toast.LENGTH_LONG).show();
        }else{
            question.setVisibility(View.INVISIBLE);
        }








    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }







    @Override
    public void onMapLongClick(LatLng latLng) {



    }
}
