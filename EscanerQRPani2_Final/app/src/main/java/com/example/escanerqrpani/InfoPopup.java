package com.example.escanerqrpani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class InfoPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_popup);

        DisplayMetrics medVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(medVentana);
        int ancho = medVentana.widthPixels;
        int alto = medVentana.heightPixels;

        getWindow().setLayout((int)(ancho*0.72),(int)(alto*0.25));
        Button cerrar = findViewById(R.id.button);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}