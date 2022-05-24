package com.example.escanerqrpani;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class enviarCorreo extends AppCompatActivity {

//txtArchiv

    TextView getTxtNombreArchivo;
    Button btnExaminar;
    Button btnEnviar;
    Button btnCancelar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        switch (requestCode){

            case 10:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor =  getApplication().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.i("taginfo",displayName);
                                getTxtNombreArchivo.setText(displayName);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_correo);

        getTxtNombreArchivo = findViewById(R.id.txtArchiv);
        btnExaminar = findViewById(R.id.btnExamin);
        btnEnviar = findViewById(R.id.button9);
        btnCancelar = findViewById(R.id.button10);

        btnEnviar.setEnabled(false);

        btnExaminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnEnviar.setEnabled(true);
                Intent intentFile = new Intent(Intent.ACTION_GET_CONTENT);
                intentFile.setType("*/*");
                startActivityForResult(intentFile,10);
            }
        });

         btnEnviar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String getdatotxtView = getTxtNombreArchivo.getText().toString();

                 final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + getdatotxtView;
                 File newdir = new File(dir);


                 Uri urr = FileProvider.getUriForFile(enviarCorreo.this, BuildConfig.APPLICATION_ID, newdir);
                 Log.i("tagmau", urr.toString());


                 StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                 StrictMode.setVmPolicy(builder.build());

                 Intent i = new Intent(Intent.ACTION_SEND);
                 i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                 i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                 i.setType("text/plain");

                 i.putExtra(Intent.EXTRA_EMAIL, new String[]{"maurcr23@gmail.com"});
                 i.putExtra(Intent.EXTRA_SUBJECT, "Archivo csv");
                 i.putExtra(Intent.EXTRA_TEXT, "Observaciones:  ");
                 i.putExtra(Intent.EXTRA_STREAM, urr);
                 startActivity(Intent.createChooser(i, "Enviar e-mail mediante:"));
             }

         });

         }

         public void regreinicio(View view){
        Intent intet = new Intent(this, MainActivity.class);
        startActivity(intet);
        finish();
         }

         public void salirrr (View view2){
        finishAffinity();
         }
    }