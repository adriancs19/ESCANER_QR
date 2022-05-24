package com.example.escanerqrpani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.FileProvider;


import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MainActivity4 extends AppCompatActivity {

    EditText txtObservacion;
    Session session;
    TextView txtNombrArchivo;

    String contra;
    Button btnEnviar;
    Button btnCancelar;
    TextView getTxtNombrArchivo;
    Button btnRegresar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        getTxtNombrArchivo = findViewById(R.id.txtArchiv);
        btnCancelar = findViewById(R.id.button4);
        txtObservacion = findViewById(R.id.editTextTextMultiLine);


        Bundle datosCorreo = this.getIntent().getExtras();
        String asuntoCorreo = datosCorreo.getString("intentAsunto");
        String nombArchiCorreo = datosCorreo.getString("intentNombArch");
        String usuario1Correo = datosCorreo.getString("intentUsuario1");
        String usuario2Correo = datosCorreo.getString("intentUsuario2");
        String usuario3Correo = datosCorreo.getString("intentUsuario3");
        String oficinaCorreo = datosCorreo.getString("intentOficina");
        String regionCorreo = datosCorreo.getString("intentRegion");
        String contadCorreo = datosCorreo.getString("intentConta");

        String auxOfiRegiCorr = oficinaCorreo+" / " +regionCorreo;

        String auxAsuntoCorreo = "Escaneo de "+asuntoCorreo;
        String auxUsuarios = usuario1Correo + "/"+usuario2Correo+"/"+usuario3Correo;

         txtNombrArchivo = findViewById(R.id.textView17);
         txtNombrArchivo.setText(nombArchiCorreo);



        btnEnviar = findViewById(R.id.button3);



        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {



                String getdatotxtView = txtNombrArchivo.getText().toString();

                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + getdatotxtView;
                File newdir = new File(dir);


                Uri urr = FileProvider.getUriForFile(MainActivity4.this, BuildConfig.APPLICATION_ID, newdir);
               // Log.i("tagmau", urr.toString());

                            String observCorreo1 = txtObservacion.getText().toString();



                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                        i.setType("text/plain");

                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"panihandhelp@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, asuntoCorreo);
                        i.putExtra(Intent.EXTRA_TEXT, "Nombre del Archvio: "+nombArchiCorreo+"\r\n Oficina y Región: "+ auxOfiRegiCorr +   "\r\nUsuarios: "+auxUsuarios+
                                "\r\nCantidad de activos escaneados: "+contadCorreo +     "\r\n\r\nObservaciones: "+observCorreo1 );
                        i.putExtra(Intent.EXTRA_STREAM, urr);
                        startActivity(Intent.createChooser(i, "Enviar e-mail mediante:"));


                            //  MethodCorreo(direccionCorreo, auxAsuntoCorreo, nombArchiCorreo, auxUsuarios, auxOfiRegiCorr, contadCorreo, observCorreo1);

                           // Toast.makeText(MainActivity4.this, "Correo fue enviado con éxito", Toast.LENGTH_SHORT).show()





                          //  System.exit(0);


                    }
                });





        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v6) {

               finishAffinity();




            }
        });





    }//fin onCreate

    public void MethodCorreo(String correoPara,String correoAsunto,String correoNombArchi, String correoUsuarios,String correoOficiReg, String correoConta, String correoObser) {

        contra = "maudani2410";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        try {
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correoPara, contra);
                }
            });

            if (session!=null){

                String filepath = "/storage/emulated/0/file/"+correoNombArchi;
                Log.d("tagFile",filepath);
                BodyPart adjunto = new MimeBodyPart();
                MimeMultipart multiParte = new MimeMultipart();
                BodyPart texto = new MimeBodyPart();

                adjunto.setDataHandler(new DataHandler(new FileDataSource(correoNombArchi)));
                adjunto.setFileName(correoNombArchi);

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correoPara));
                texto.setText("Nombre del Archvio: "+correoNombArchi+"\r\n Oficina y Región: "+ correoOficiReg +   "\r\nUsuarios: "+correoUsuarios+
                        "\r\nCantidad de activos escaneados: "+correoConta +     "\r\n\r\nObservaciones: "+correoObser   );

                multiParte.addBodyPart(texto);
                multiParte.addBodyPart(adjunto);

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoPara));
                message.setSubject(correoAsunto);
                message.setContent(multiParte);
                message.setText("Nombre del Archvio: "+correoNombArchi+"\r\n Oficina y Región: "+ correoOficiReg +   "\r\nUsuarios: "+correoUsuarios+
                        "\r\nCantidad de activos escaneados: "+correoConta +     "\r\nObservaciones: "+correoObser  );


                Transport.send(message, message.getAllRecipients());

                /*
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correoPara));
                message.setSubject(correoAsunto);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoPara));
                message.setContent(
                        "Nombre del Archvio: "+correoNombArchi +
                       "<br>Oficina y Región: "+ correoOficiReg+
                        "<br>Usuarios: "+correoUsuarios+
                        "<br>Cantidad de activos escaneados: "+correoConta +
                        "<br>Observaciones: "+correoObser, "text/html; charset=utf-8");

                Transport.send(message);

                 */
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }//fin metodoCorreo

    public void regre(View vv){
        Intent intReg = new Intent(MainActivity4.this,MainActivity.class);
        startActivity(intReg);
        finishAffinity();
    }

}