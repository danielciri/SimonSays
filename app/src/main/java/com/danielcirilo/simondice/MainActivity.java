package com.danielcirilo.simondice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;



public class MainActivity extends AppCompatActivity  {
    private Button btnStart;
    private Button btnScores;
    private TextView tvPrueba;
    private AppCompatButton btnAmarillo;
    private AppCompatButton btnAzul;
    private AppCompatButton btnVerde;
    private AppCompatButton btnRojo;
    private ArrayList<Integer> colores;
    private View.OnClickListener listener;
    private int secuenciaPosicion;
    private int turno;
    private SoundPool soundPool;
    private int audio1;
    private int audio2;
    private int audio3;
    private int audio4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btStart);
        tvPrueba = findViewById(R.id.tvPuntaje);
        btnScores = findViewById(R.id.btScores);
        btnAmarillo = findViewById(R.id.btnAmarillo);
        btnRojo = findViewById(R.id.btnRojo);
        btnVerde = findViewById(R.id.btnVerde);
        btnAzul = findViewById(R.id.btnAzul);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool=new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        }
        audio1=soundPool.load(this,R.raw.audio1,1);
        audio2=soundPool.load(this,R.raw.audio2,1);
        audio3=soundPool.load(this,R.raw.audio3,1);
        audio4=soundPool.load(this,R.raw.audio4,1);


        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.btStart:
                        esconderBotones();
                        turno = 0;
                        colores = new ArrayList<>();
                        colores.add(random());
                        play();
                        break;
                    case R.id.btScores:
                        Intent intent = new Intent(MainActivity.this, BestScores.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        btnStart.setOnClickListener(listener);
        btnScores.setOnClickListener(listener);

        View.OnClickListener listenerColores = new View.OnClickListener() {
            int numero;
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.btnVerde:
                        soundPool.play(audio1,1,1,1,0,1);
                        numero = 1;
                        break;
                    case R.id.btnRojo:
                        soundPool.play(audio2,1,1,1,0,1);
                        numero = 2;
                        break;
                    case R.id.btnAmarillo:
                        soundPool.play(audio3,1,1,1,0,1);
                        numero = 3;
                        break;
                    case R.id.btnAzul:
                        soundPool.play(audio4,1,1,1,0,1);
                        numero = 4;
                        break;
                }
                if(validaJugada(numero,secuenciaPosicion)){
                    if(secuenciaPosicion<colores.size()-1){
                        secuenciaPosicion++;
                    }else{
                        colores.add(random());
                        turno++;
                        play();


                    }
                }else{
                    resetearPartida();
                }
            }
        };
        btnVerde.setOnClickListener(listenerColores);
        btnRojo.setOnClickListener(listenerColores);
        btnAmarillo.setOnClickListener(listenerColores);
        btnAzul.setOnClickListener(listenerColores);


    }

    private int random() {
        int aleatorio = 0;
        for (int i = 0; i <5 ; i++) {
            Random rnd = new Random();
            aleatorio = rnd.nextInt(4 - 1 + 1) + 1;

        }
        return aleatorio;
    }

    private void activarBotones(){
        btnAzul.setEnabled(true);
        btnRojo.setEnabled(true);
        btnVerde.setEnabled(true);
        btnAmarillo.setEnabled(true);

    }
    private void esconderBotones(){
        btnStart.setVisibility(View.INVISIBLE);
        btnScores.setVisibility(View.INVISIBLE);

    }
    private void mostrarBotones(){
        btnStart.setVisibility(View.VISIBLE);
        btnScores.setVisibility(View.VISIBLE);
        btnStart.setText(R.string.reinicioGame);
    }
    private void desactivarBotones(){
        btnAzul.setEnabled(false);
        btnRojo.setEnabled(false);
        btnVerde.setEnabled(false);
        btnAmarillo.setEnabled(false);
    }

    public boolean validaJugada(int numero, int posicion){
        if(numero == colores.get(posicion)){
            return true;
        }
        else{
            return false;
        }

    }


    public void iluminarBotones(int position){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void run() {

                switch (colores.get(position)){
                    case 1:
                        btnVerde.post(new Runnable() {
                            @Override
                            public void run() {

                                soundPool.play(audio1,1,1,1,0,1);
                                btnVerde.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#72eb77")));
                            }
                        });

                        break;
                    case 2:
                        btnRojo.post(new Runnable() {
                            @Override
                            public void run() {

                                soundPool.play(audio2,1,1,1,0,1);
                                btnRojo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#cf5560")));
                            }
                        });
                        break;
                    case 3:
                        btnAmarillo.post(new Runnable() {
                            @Override
                            public void run() {
                                soundPool.play(audio3,1,1,1,0,1);
                                btnAmarillo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#dbea6e")));
                            }
                        });
                        break;
                    case 4:
                        btnAzul.post(new Runnable() {
                            @Override
                            public void run() {
                                soundPool.play(audio4,1,1,1,0,1);
                                btnAzul.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#76b6e7")));
                            }
                        });
                        break;

                }
                resetColor(position);
            }


        }).start();
    }

    public void resetColor(int position){
        runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                switch (colores.get(position)){
                    case 1:

                        btnVerde.post(new Runnable() {
                            @Override
                            public void run() {
                                btnVerde.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#24d32c")));
                            }
                        });
                        break;
                    case 2:
                        btnRojo.post(new Runnable() {
                            @Override
                            public void run() {
                                btnRojo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#da0f21")));
                            }
                        });
                        break;
                    case 3:

                        btnAmarillo.post(new Runnable() {
                            @Override
                            public void run() {
                                btnAmarillo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#d9f702")));
                            }
                        });
                        break;
                    case 4:

                        btnAzul.post(new Runnable() {
                            @Override
                            public void run() {
                                btnAzul.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2287d7")));
                            }
                        });
                        break;
                }
                if(position<colores.size()-1){
                    iluminarBotones(position+1);

                }else{
                    playJugador();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    public void playJugador(){
        activarBotones();
        secuenciaPosicion = 0;

    }
    private void play(){
        tvPrueba.setText(String.valueOf(turno));
        desactivarBotones();
        iluminarBotones(0);
        savePrefrences();
    }
    public void resetearPartida(){
        secuenciaPosicion = 0;
        colores = new ArrayList<>();
        colores.add(random());
        turno = 0;
        desactivarBotones();
        mostrarBotones();


    }
    public void savePrefrences(){
        SharedPreferences prfs = getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
        int posicion1 = prfs.getInt("posicion1",0);
        int posicion2 = prfs.getInt("posicion2",0);
        int posicion3 = prfs.getInt("posicion3",0);
        int posicion4 = prfs.getInt("posicion4",0);
        int posicion5 = prfs.getInt("posicion5",0);
        SharedPreferences.Editor editor = prfs.edit();
        if(turno >= posicion1){
            editor.putInt("posicion5",posicion4);
            editor.putInt("posicion4",posicion3);
            editor.putInt("posicion3",posicion2);
            editor.putInt("posicion2",posicion1);
            editor.putInt("posicion1",turno);
        }else if(turno >= posicion2){
            editor.putInt("posicion5",posicion4);
            editor.putInt("posicion4",posicion3);
            editor.putInt("posicion3",posicion2);
            editor.putInt("posicion2",turno);

        }else if(turno >= posicion3){
            editor.putInt("posicion5",posicion4);
            editor.putInt("posicion4",posicion3);
            editor.putInt("posicion3",turno);
        }
        else if(turno >= posicion4){
            editor.putInt("posicion5",posicion4);
            editor.putInt("posicion4",turno);
        } else if(turno >= posicion5){
            editor.putInt("posicion5",turno);

        }
        editor.apply();
    }

}