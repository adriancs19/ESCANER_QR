package com.example.escanerqrpani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RegistrarOficina extends AppCompatActivity {

    public EditText NomOficina;
    Spinner spinnerRegion;
    public static final String NAME_REGION = "Regiones.txt";
    Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_oficina);
        LeerRegiones();



    }


    private void LeerRegiones() {
        ArrayList<String> Regiones = new ArrayList<String>();
        spinnerRegion = (Spinner)findViewById(R.id.spRegion);

        //String text = mySpinner.getSelectedItem().toString();

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int i = 0;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(NAME_REGION);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String RegionLinea;
            StringBuilder stringBuilder= new StringBuilder();
            while((RegionLinea = bufferedReader.readLine())!=null){
                stringBuilder.append(RegionLinea).append("\n");
                Regiones.add(RegionLinea);
            }
        } catch (Exception e) {

        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                }catch (Exception e){}
            }
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,Regiones);

        spinnerRegion.setAdapter(adapter);
    }

    public void NuevaOficina(){
        NomOficina=findViewById(R.id.txtoficina);

        Spinner region;
        region=findViewById(R.id.spRegion);
        String NomRegion = region.getSelectedItem().toString()+".txt";

        String Oficina = NomOficina.getText().toString()+"\n";
        FileOutputStream fileOutputStreamOficina = null;

        if (Oficina.isEmpty() || Oficina.equals("\n")){
            Toast.makeText(RegistrarOficina.this,"Debe Agregar el Nombre de la Oficina",Toast.LENGTH_SHORT).show();
        }else {
            try {

                fileOutputStreamOficina = openFileOutput(NomRegion, MODE_APPEND);
                BufferedOutputStream Escritor = new  BufferedOutputStream(fileOutputStreamOficina);

                Escritor.write(Oficina.getBytes());
                Escritor.flush();
                Toast.makeText(RegistrarOficina.this,"Oficina Agregada con Éxito",Toast.LENGTH_SHORT).show();

                Log.d("TAG1","Fichero salvado en: "+getFilesDir()+"/"+Oficina);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(RegistrarOficina.this,"NO se pudo agregar la oficina",Toast.LENGTH_SHORT).show();
            }finally {
                if(fileOutputStreamOficina != null){
                    try {
                        fileOutputStreamOficina.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void GuardarOficina(View viewGuardar){
        NuevaOficina();
        NomOficina.setText(" ");
    }

    public void Inicio(View viewInicio){
        Intent intent2  = new Intent(RegistrarOficina.this,MainActivity.class);
        startActivity(intent2);
        finish();
    }

    public void Salir (View viewSalir){
        finishAffinity();
    }


}