package com.example.escanerqrpani;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Paint.UNDERLINE_TEXT_FLAG;

public class MainActivity extends AppCompatActivity {

    /**************Version 1.01***********************/

    /*Fecha : 20/04/2021 10:51:03 am   */

    /***********************************************/


    public  Button btnInfo;
   public String auxUsuario1 = "No hay Usuarios";
    public String auxUsuario2 = "No hay Usuarios";
    public String auxUsuario3 = "No hay Usuarios";

   public String auxOficina;
   public String auxRegional;
   public char evaluar = 'N';
   public char pass;
   public char pass2;
   public char[] textArray = new char[400];


    public Spinner Usuarios;
    public Spinner Usuarios2;
    public Spinner Usuarios3;

    public static final String FILE_NAME = "User.txt";
    public static final String NAME_REGION = "Regiones.txt";

    TextView txt1;
    TextView txt2;
   Button btnPop;
   ImageButton btnSalir;
   ImageButton btnSiguiente;

   Spinner spinnerOficina;
   Spinner spinnerRegion;
   View txthint;

   TextView txtResponsable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.iconmenu));

        spinnerRegion =findViewById(R.id.spregion);
        spinnerOficina = findViewById(R.id.spoficina);
        Usuarios = findViewById(R.id.spinner5);
        Usuarios2 = findViewById(R.id.spinner6);
        Usuarios3 = findViewById(R.id.spinner7);

        LeerRegiones();
        LeerUsuarios();

   Usuarios.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> Usuarios, View view6, int position6, long id6) {



                final String item = (String)Usuarios.getSelectedItem().toString();

                auxUsuario1 = item;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });//fin spinnerUsuario1


        Usuarios2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> Usuarios2, View view3, int position3, long id3) {


                final String item2 = (String)Usuarios2.getSelectedItem().toString();
                auxUsuario2 = item2;




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });//fin spinnerUsuario2

        Usuarios3.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> Usuarios3, View view, int position, long id) {

                //String item3 = (String)spinnerUsuarios3.getItemAtPosition(position);
                final String item3 = (String)Usuarios3.getSelectedItem().toString();
                auxUsuario3 = item3;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });//fin spinnerUsuario3



        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinnerRegion, View view1, int position1, long id1) {

                // String region = spinnerRegion.getItemAtPosition(position).toString();
                final String region = (String)spinnerRegion.getSelectedItem().toString();
                auxRegional = region;
                LeerOficinas(auxRegional);

               /* TextView tv = (TextView)view1;
                if(position1 == 0){
                    tv.setTextColor(Color.rgb( 133, 133, 133));
                    tv.setEnabled(false);

                }else{
                    tv.setTextColor(Color.BLACK);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


       spinnerOficina.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> spinnerOficina, View view4, int positi4, long id4) {

                //String oficina = (String)spinnerRegion.getItemAtPosition(position4);
                final String oficin = spinnerOficina.getSelectedItem().toString();
                auxOficina = oficin;

              /*  TextView tv = (TextView)view4;
                if(positi4 == 0){
                    tv.setTextColor(Color.rgb( 133, 133, 133));
                    tv.setEnabled(false);
                }else{
                    tv.setTextColor(Color.BLACK);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSalir = findViewById(R.id.imageButton2);
         btnSalir.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finishAffinity();

             }
         });
        //hexadecimal 01a0c6
    }//fin oncreate

    //*****************************************************************Metodos ********************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones,menu);
return true;
          }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_usur:
                Intent intent3  = new Intent(MainActivity.this,RegistroUsuarios.class);
                startActivity(intent3);
                break;

            case R.id.menu_regi:
                Intent intent4  = new Intent(MainActivity.this,RegistrarRegion.class);
                startActivity(intent4);
                break;

            case R.id.menu_ofic:
                Intent intent5  = new Intent(MainActivity.this,RegistrarOficina.class);
                startActivity(intent5);
                break;

            case R.id.menu_enviar:
                Intent intent = new Intent(this,enviarCorreo.class);
                startActivity(intent);
                break;

            case R.id.menu_info:
                Intent intent2  = new Intent(this,InfoPopup.class);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void LeerUsuarios() {
        ArrayList<String> elementos = new ArrayList<String>();
        Usuarios = (Spinner)findViewById(R.id.spinner5);
        Usuarios2 = (Spinner)findViewById(R.id.spinner6);
        Usuarios3 = (Spinner)findViewById(R.id.spinner7);

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int i = 0;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String UsuarioLinea;
            StringBuilder stringBuilder= new StringBuilder();
            while((UsuarioLinea = bufferedReader.readLine())!=null){
                stringBuilder.append(UsuarioLinea).append("\n");
                elementos.add(UsuarioLinea);
            }
        } catch (Exception e) {

        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                }catch (Exception e){}
            }
        }

       // ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
         //       android.R.layout.simple_spinner_item,elementos);

        Usuarios.setAdapter(new ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item,elementos));
        Usuarios2.setAdapter(new ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item,elementos));
        Usuarios3.setAdapter(new ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item,elementos));



     //   Usuarios.setAdapter(adapter);
      //  Usuarios2.setAdapter(adapter);
       // Usuarios3.setAdapter(adapter);
    }


    private void LeerRegiones() {
        ArrayList<String> Regiones = new ArrayList<String>();

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
            StringBuilder stringBuilder = new StringBuilder();
            while ((RegionLinea = bufferedReader.readLine()) != null) {
                stringBuilder.append(RegionLinea).append("\n");
                Regiones.add(RegionLinea);
            }
        } catch (Exception e) {

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                }
            }
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, Regiones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(adapter);

    }

    private void LeerOficinas(String region) {

        ArrayList<String> ArrayOficinas = new ArrayList<String>();

        String RegionSelecionada=region+".txt";
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int i = 0;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(RegionSelecionada);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String OficinaLinea;
            StringBuilder stringBuilder= new StringBuilder();
            while((OficinaLinea = bufferedReader.readLine())!=null){
                stringBuilder.append(OficinaLinea).append("\n");
                ArrayOficinas.add(OficinaLinea);
            }
         //   Toast.makeText(MainActivity.this,RegionSelecionada,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //Toast.makeText(MainActivity.this,"Fallo abrir el metodo",Toast.LENGTH_SHORT).show();
        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                }catch (Exception e){}
            }
        }

        //ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
        //android.R.layout.simple_spinner_item,ArrayOficinas);

        spinnerOficina.setAdapter(new ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item,ArrayOficinas));

        //(spinnerOficina.setAdapter(adapter);
    }

    public void Mensaje (View view9){



        if(spinnerOficina.getSelectedItemPosition() == 0  || spinnerRegion.getSelectedItemPosition()== 0 || Usuarios.getSelectedItemPosition() == 0 || Usuarios2.getSelectedItemPosition()==0 || Usuarios3.getSelectedItemPosition()==0)
        {

            Toast.makeText(MainActivity.this,"¡Favor de ingresar datos correctos! ",Toast.LENGTH_SHORT).show();

        }else{
           // Toast.makeText(MainActivity.this,"Elseeee! ",Toast.LENGTH_SHORT).show();

            Intent intentDato = new Intent(this, MainActivity3.class);
            intentDato.putExtra("usu1",auxUsuario1);
            intentDato.putExtra("usu2",auxUsuario2);
            intentDato.putExtra("usu3",auxUsuario3);
            intentDato.putExtra("ofic",auxRegional);
            intentDato.putExtra("regi",auxOficina);

            Log.i("tagRep",auxUsuario1+auxUsuario2+auxUsuario3);

            startActivity(intentDato);
            finish();


        }

     //Intent intenDato2 = new Intent(this,MainActivity3.class);
       // intenDato2.putExtra("usuar2",auxUsuario2);
       //startActivity(intenDato2);

/*
        Intent intenDato3 = new Intent(this,MainActivity3.class);
        intenDato3.putExtra("usu3",auxUsuario3);
        startActivity(intenDato3);
        Log.d("tag3", intenDato3.toString());

        Intent intenOficina = new Intent(this,MainActivity3.class);
        intenOficina.putExtra("oficm",auxOficina);
        startActivity(intenOficina);
        Log.d("tag4", intenOficina.toString());

        Intent inteRegion = new Intent(this,MainActivity3.class);
        inteRegion.putExtra("regionm",auxRegional);
        startActivity(inteRegion);
        Log.d("tag5", inteRegion.toString());*/



    }//fin metodo Mensaje

    public void InfoBtn(View v1){
        Intent intent2  = new Intent(this,InfoPopup.class);
        startActivity(intent2);





    }//fin metodo InfoBtn






}//Fin Class