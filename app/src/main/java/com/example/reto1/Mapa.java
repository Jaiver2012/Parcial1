package com.example.reto1;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class Mapa extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLongClickListener {

    private Button but_addMarker;

    private GoogleMap mMap;

    private Polygon IcesiArea;

    private Marker miUbicacion;

    private TextView information;

    private ArrayList<Marker> marcadores;

    private boolean toco;

    private String nombre;

    private Geocoder geocoder;

    private List<Address> addresses;

    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 11);

        information = findViewById(R.id.information);
        marcadores = new ArrayList<>();
        toco = false;


        geocoder = new Geocoder(this, Locale.getDefault());

        but_addMarker = findViewById(R.id.btn_addMarker);
        but_addMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), addMarker.class);
                startActivityForResult(i, 11);


                toco = true;
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
        LatLng icesi = new LatLng(3.341215, -76.530454);
        miUbicacion = mMap.addMarker(new MarkerOptions().position(icesi).title("Ubicación actual").icon(BitmapDescriptorFactory.fromResource(R.drawable.position5)));


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(icesi, 15));

        //SOLICITUD DE UBICACION

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == RESULT_OK) {
            String nameMarker = data.getExtras().getString("nombre");
            nombre = nameMarker;
        }
    }

    //Cada vez que yo me mueva el va cambiando
    @Override
    public void onLocationChanged(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        miUbicacion.setPosition(pos);

        try {
            addresses = geocoder.getFromLocation(miUbicacion.getPosition().latitude, miUbicacion.getPosition().longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            miUbicacion.setSnippet(address);
            //miUbicacion.setTitle(address);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));


        information.setVisibility(View.VISIBLE);

        if (!marcadores.isEmpty()) {
            updateDistances(marcadores);


            Marker m1 = shorterMarker(marcadores, miUbicacion);

            double di = distanceBetween(m1, miUbicacion);
            if (di < 30) {
                information.setText("Usted esta en: " + m1.getTitle());
            } else {
                information.setText("Lugar mas cercano: " + m1.getTitle() + " a :" + String.format("%.3f", di) + "m");
            }


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

    public Marker shorterMarker(ArrayList<Marker> lista, Marker actual) {


        double menor = Double.MAX_VALUE;
        Marker mark = null;

        for (int i = 0; i < lista.size(); i++) {

            if (distanceBetween(lista.get(i), actual) < menor) {

                menor = distanceBetween(lista.get(i), actual);
                mark = lista.get(i);
            }
        }

        return mark;
    }

    public double distanceBetween(Marker a, Marker b) {

        double distance = Math.sqrt(Math.pow(a.getPosition().latitude - b.getPosition().latitude, 2) + Math.pow(a.getPosition().longitude - b.getPosition().longitude, 2));

        distance = distance * 111.12 * 1000;

        return distance;
    }

    public void updateDistances(ArrayList<Marker> lista) {
        for (int i = 0; i < lista.size(); i++) {
            double d = distanceBetween(lista.get(i), miUbicacion);
            lista.get(i).setSnippet("Distancia de usted:" + String.format("%.3f", d) + "m");

        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        if (toco) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
            marker.setTitle(nombre);
            double d = distanceBetween(marker, miUbicacion);
            marker.setSnippet("Distancia de usted:" + String.format("%.3f", d) + "m");


            marcadores.add(marker);

            toco = false;

        }

    }
}
