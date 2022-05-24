package com.example.escanerqrpani;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.*;

public class MainActivity3 extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultQr;
    String  txtResultQr;
    int contador = 0;
    TextView TxtContador;
    Button btnGuardar;
    Button btnCancelar;
    Button btnTerminar;
    public char evaluar = 'N';
    public char pass;
    public char pass2;
    public char[] textArray = new char[400];

    String encabezado = "NUMERO DE ACTIVO, PLACA ANTERIOR, DESCRIPCION, SERIE,OFICINA,REGION,FAMILIA,SUBFAMILIA,MARCA,MODELO,FECHA Y HORA"+"\r\n"+"\r\n";

    String prueb = null;

    TextView txtView4;
    TextView txt1;
    TextView txt2;


    Spinner spinnerEtiqueta;
    TextView txtView2;
    TextView txtEtiqute;

    String getActivUsua1;
    String getActivUsua2;
    String getActivUsua3;

    String getActivOfic;
    String getActivRegi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



        //spinnerEtiqueta = (Spinner)findViewById(R.id.spinner4);
       // spinnerEtiqueta.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spinnerValue));

        //usua2 = getIntent().getStringExtra("Usuario2");
        //txtView2 = findViewById(R.id.textView14);
        //txtView2.setText(usua2);

        TxtContador = findViewById(R.id.textView13);
        scannerView = findViewById( R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannerView);
        resultQr = findViewById(R.id.textView4);
        resultQr.setMovementMethod(new ScrollingMovementMethod());


    getActivUsua1 = getIntent().getStringExtra("usu1");

    Bundle datos = this.getIntent().getExtras();
  String usuario1 = datos.getString("usu1");
   String usuario2 = datos.getString("usu2");
    String usuario3 = datos.getString("usu3");
    String oficin = datos.getString("ofic");
    String regio = datos.getString("regi");

Log.i("tagToast",oficin+"/"+regio);


        SimpleDateFormat fechaFormat= new SimpleDateFormat("dd-MM-yyyy");
        String fecha = fechaFormat.format(new Date());

        SimpleDateFormat fechaHoraFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fechaHora = fechaHoraFormat.format(new Date());  //fecha para registros y correo

    //  String abrevOficina = MetodoOficina(oficin);
     // String abrevRegion = MetodoRegion(regio);

      //String filee  = abrevOficina+abrevRegion+"_"+fecha+".csv";
        String filee = oficin + regio +"_"+fecha+ ".csv";

        try {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
           Log.i("TagFile",directory.toString());
            File file = new File(directory, filee);
            FileOutputStream crearArchivo;



           crearArchivo = new FileOutputStream(file,true);
          crearArchivo.write(encabezado.getBytes());
       crearArchivo.flush();
           crearArchivo.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultQr.setText(result.getText());

                     //   String contenido = resultQr.getText().toString();
                        //for(int i = 0; i < resultQr.length(); i++){
                         //char  pass = contenido.charAt(i);
                           // String passvalue = String.valueOf(pass);


                       // }//for

                        txtResultQr = resultQr.getText().toString();
                     //   Log.d("tagg",txtResultQr);

                    }

                });

            }
        });


        btnGuardar = findViewById(R.id.button6);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(resultQr.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity3.this,"No hay información para guardar ",Toast.LENGTH_SHORT).show();
                }else{



                   // String abrevOficina = MetodoOficina(oficin);
                    //String abrevRegion = MetodoRegion(regio);
                    EvaluarTextoAgregarComas(txtResultQr,oficin,regio); //3parametros
                    Metodocontador();
                    Escaner();
                    btnTerminar.setEnabled(true);
                    Toast.makeText(MainActivity3.this,"Información guardada con éxito ",Toast.LENGTH_SHORT).show();

                }


            }
        });



        btnCancelar = findViewById(R.id.button2);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {

                resultQr.setText("");
                Escaner();



            }
        });


    btnTerminar = findViewById(R.id.button7);
    btnTerminar.setEnabled(false);

        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {

             AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
                builder.setTitle("¡ADVERTENCIA!"+"\r\n");
                builder.setMessage("¿Seguro que desea terminar?");
                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Aqui ir a activity4 con todas las variables
                       // String abrevOficina2 = MetodoOficina(oficin);
                        //String abrevRegion2 = MetodoRegion(regio);
                        SimpleDateFormat fechaFormat2= new SimpleDateFormat("dd-MM-yyyy");
                        String fech2 = fechaFormat2.format(new Date());

                        TextView txtConta = findViewById(R.id.textView13);
                        String nombArchFull = oficin+regio+"_"+fech2+".csv";

                        Intent intentDats = new Intent(vi.getContext(),MainActivity4.class);
                        intentDats.putExtra("intentAsunto",oficin);
                        intentDats.putExtra("intentNombArch",nombArchFull);
                        intentDats.putExtra("intentUsuario1",usuario1);
                        intentDats.putExtra("intentUsuario2",usuario2);
                        intentDats.putExtra("intentUsuario3",usuario3);
                        intentDats.putExtra("intentOficina",oficin);
                        intentDats.putExtra("intentRegion",regio);
                        TextView txtCantidad = findViewById(R.id.textView13);

                        String contadorCorreo = txtCantidad.getText().toString();
                        intentDats.putExtra("intentConta",contadorCorreo);

                        startActivity(intentDats);
                        finish();
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
               AlertDialog dialog = builder.create();
               dialog.show();
            }
        });

    }//fin onCreate

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    public void Escaner (){

        super.onResume();
        codeScanner.startPreview();
        resultQr.setText("");
    }

    private void  Metodocontador(){
        contador ++;
        String conta = String.valueOf(contador);
        TxtContador.setText(conta);

    }

    @Override public void onBackPressed() { moveTaskToBack(true); }


    public void EvaluarTextoAgregarComas(String getTxtView4, String getOficina,String getRegion) {



        SimpleDateFormat fechaHoraFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fechaHora2 = fechaHoraFormat2.format(new Date());  //fecha para registros y correo
        SimpleDateFormat fechaFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        String fecha2 = fechaFormat2.format(new Date());
        String nombArchFull = getOficina + getRegion + "_" + fecha2 + ".csv";

        for (int y = 0; y < getTxtView4.length(); y++) {

            pass2 = getTxtView4.charAt(y);

            if (pass2 == ',') {

                evaluar = 'P';

                break;
            } else {

                evaluar = 'N';

            }//else

        }//1for

        if (evaluar == 'P') {



            String textConSalto = "\r\n";

            String textFull = getTxtView4 + "," + fechaHora2 + textConSalto;


            /*+++++++++++++++++++++++++++++++++++++++++++++++++++++Pruebas guardar texto con , aqui **********************************/

            try {
                String filename = nombArchFull;
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                File file = new File(directory, filename);
                FileOutputStream crearArchivo;


                //crearArchivo = openFileOutput(filename,MODE_APPEND);  //esto lo guarda en la memoria interna
                crearArchivo = new FileOutputStream(file, true);
                crearArchivo.write(textFull.getBytes());
                crearArchivo.flush();
                crearArchivo.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {

            //  Log.d("tag5", "no tiene coma ");
            //aqui agregar coma al texto y crear archivo csv
            //   String contenido = txt1.getText().toString();

            for (int i = 0; i < getTxtView4.length(); i++) {
                pass = getTxtView4.charAt(i);
                // String passvalue = String.valueOf(pass);
                textArray[i] = pass;
                if (pass == '\n') {

                    textArray[i] = ',';

                    //  Log.i("Tag","entro salto de linea");
                } else {
                    //  Log.i("Tag1","No entro en el if");
                }


            }//2for
            String texString = String.valueOf(textArray);


            FileOutputStream fileOutputStream = null;
            String textConSalto = "\r\n";

            String textFull = texString + "," + fechaHora2 + textConSalto;

            try {

                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                File file = new File(directory, nombArchFull);
                FileOutputStream crearArchivo;


                crearArchivo = new FileOutputStream(file, true);
                crearArchivo.write(textFull.getBytes());
                crearArchivo.flush();
                crearArchivo.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }//fin metodo EvaluarTextoAgregarComas

public void ValidarTxt (){

}//fin validar



}