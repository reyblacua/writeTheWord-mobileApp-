package com.example.writetheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.writetheword.Constantes.Constantes;

public class showResults extends AppCompatActivity {

    TextView aciertos;
    Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        volver= findViewById(R.id.volver);
        aciertos=findViewById(R.id.aciertos);

        try{
            SharedPreferences sharedPreferences= getSharedPreferences(Constantes.datosUsuario, MODE_PRIVATE);
            String aciertosdesdemain= sharedPreferences.getString(Constantes.aciertos,"0");
            aciertos.setText(aciertosdesdemain.split(" ")[1]);
        }catch (Exception e){

        }

        volver.setOnClickListener(view -> {
            this.finish();
        });

    }
}