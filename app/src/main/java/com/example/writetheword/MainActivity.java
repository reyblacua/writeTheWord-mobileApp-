package com.example.writetheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.writetheword.Constantes.Constantes;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String[] palabras= Constantes.palabras;

    TextView palabraCorrecta, palabraDada, aciertos;
    Button reset, start;
    Chronometer chronometer ;
    Integer aciertosCount;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        palabraCorrecta=findViewById(R.id.palabraCorrecta);
        palabraDada=findViewById(R.id.palabraDada);
        aciertos=findViewById(R.id.aciertos);
        reset=findViewById(R.id.reset);
        start=findViewById(R.id.start);
        chronometer = (Chronometer) findViewById(R.id.chronmeter);
        aciertosCount=0;


        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setFormat("%s");
        chronometer.start();

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                // Si la hora actual, la hora de inicio del temporizador> = 15 segundos
                if(SystemClock.elapsedRealtime() - chronometer.getBase() >=60000)
                {
                    chronometer.stop();
                    sharedPreferences = getSharedPreferences(Constantes.datosUsuario, MODE_PRIVATE);
                    editor= sharedPreferences.edit();
                    editor.putString(Constantes.aciertos,aciertos.getText().toString());
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, showResults.class));
                }
            }
        });
        palabraDada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String respuestaCorrecta=palabraCorrecta.getText().toString();
                String respuesta=palabraDada.getText().toString();
                if(respuestaCorrecta.length()==respuesta.length()){
                    if(respuestaCorrecta.equals(respuesta)){
                        aciertosCount+=1;
                        aciertos.setText("Aciertos: "+aciertosCount.toString());
                        palabraDada.setText("");
                        generaPalabra();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        reset.setOnClickListener(view -> {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            aciertosCount=0;

        });

        start.setOnClickListener(view -> {
            chronometer.start();
            aciertos.setText("Aciertos: 0");
        });

        generaPalabra();

    }

    private void generaPalabra(){
        String nuevaPalabra="";
        Random r=new Random();
        Integer randomInt= r.nextInt(149);
        nuevaPalabra=palabras[randomInt];
        palabraCorrecta.setText(nuevaPalabra);

    }

}