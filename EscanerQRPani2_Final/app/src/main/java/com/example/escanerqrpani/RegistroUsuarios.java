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

public class RegistroUsuarios extends AppCompatActivity {

    public EditText NomUsuario;



    public static final String FILE_NAME = "User.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);




    }

    public void NuevoUsuario(){
        NomUsuario=findViewById(R.id.txtoficina);

        String Usuario = NomUsuario.getText().toString()+"\n";
        FileOutputStream fileOutputStream = null;

        if (Usuario.isEmpty() || Usuario.equals("\n")){
            Toast.makeText(RegistroUsuarios.this,"Debe Agregar el Nombre del Usuario",Toast.LENGTH_SHORT).show();
        }else {
            try {
                fileOutputStream = openFileOutput(FILE_NAME, MODE_APPEND);
                BufferedOutputStream Escritor = new  BufferedOutputStream(fileOutputStream);

                Escritor.write(Usuario.getBytes());
                Escritor.flush();
                Toast.makeText(RegistroUsuarios.this,"Usuario Agregado con Éxito",Toast.LENGTH_SHORT).show();

                Log.d("TAG1","Fichero salvado en: "+getFilesDir()+"/"+FILE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(RegistroUsuarios.this,"NO se pudo agregar el usuario",Toast.LENGTH_SHORT).show();
            }finally {
                if(fileOutputStream != null){
                    try {
                        fileOutputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void EliminarUsuario(){
        String Vaciar = "-Seleccione un Técnico-\n";

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            BufferedOutputStream Escritor = new  BufferedOutputStream(fileOutputStream);

            Escritor.write(Vaciar.getBytes());
            Escritor.flush();

            Toast.makeText(RegistroUsuarios.this,"Lista de Usuarios Eliminada con Éxito",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RegistroUsuarios.this,"NO se Pudo Eliminar la Lista de Usuarios",Toast.LENGTH_SHORT).show();
        }finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    public void GuardarUsuario(View viewGuardar){
        NuevoUsuario();
        NomUsuario.setText(" ");
    }


    public void EliminarUsuarios(View viewEliminar){
        EliminarUsuario();
    }

    public void Inicio(View viewInicio){
        Intent intent2  = new Intent(RegistroUsuarios.this,MainActivity.class);
        startActivity(intent2);
        finishAffinity();
    }

    public void Salir(View viewSalir){

        finishAffinity();
    }

}