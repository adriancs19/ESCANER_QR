package com.example.escanerqrpani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class RegistrarRegion extends AppCompatActivity {

    public EditText NomRegion;
    public Button GuardarUsuario;
    public Button Inicio;
    public Button EliminarUsuarios;

    public static final String NAME_REGION = "Regiones.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_region);
    }

    public void NuevaRegion(){
        NomRegion=findViewById(R.id.txtoficina);

        String NombreRegion = NomRegion.getText().toString()+".txt";
        String Region = NomRegion.getText().toString()+"\n";
        FileOutputStream fileOutputStreamRegion = null;
        FileOutputStream fileOutputStreamRegiones = null;

        if (Region.isEmpty() || Region.equals("\n")){
            Toast.makeText(RegistrarRegion.this,"Debe Agregar el Nombre de la Región",Toast.LENGTH_SHORT).show();
        }else {
            try {
                fileOutputStreamRegion = openFileOutput(NombreRegion, MODE_PRIVATE);
                fileOutputStreamRegiones = openFileOutput(NAME_REGION, MODE_APPEND);
                BufferedOutputStream Escritor = new  BufferedOutputStream(fileOutputStreamRegiones);

                Escritor.write(Region.getBytes());
                Escritor.flush();
                Toast.makeText(RegistrarRegion.this,"Región Agregada con Éxito",Toast.LENGTH_SHORT).show();

                Log.d("TAG1","Fichero salvado en: "+getFilesDir()+"/"+Region);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(RegistrarRegion.this,"NO se pudo agregar la región",Toast.LENGTH_SHORT).show();
            }finally {
                if(fileOutputStreamRegion != null && fileOutputStreamRegiones != null){
                    try {
                        fileOutputStreamRegion.close();
                        fileOutputStreamRegiones.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void GuardarRegion(View viewGuardar){
        NuevaRegion();
        NomRegion.setText(" ");
    }

    public void Inicio(View viewInicio){
        Intent intent2  = new Intent(RegistrarRegion.this,MainActivity.class);
        startActivity(intent2);
        finishAffinity();
    }

    public void Salirr(View viewSal){
        finishAffinity();
    }

}