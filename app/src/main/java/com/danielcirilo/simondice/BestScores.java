package com.danielcirilo.simondice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class BestScores extends AppCompatActivity {
    private TextView tvPosicion1;
    private TextView tvPosicion2;
    private TextView tvPosicion3;
    private TextView tvPosicion4;
    private TextView tvPosicion5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_scores);
        tvPosicion1 = findViewById(R.id.tvPosicion1);
        tvPosicion2 = findViewById(R.id.tvPosicion2);
        tvPosicion3 = findViewById(R.id.tvPosicion3);
        tvPosicion4 = findViewById(R.id.tvPosicion4);
        tvPosicion5 = findViewById(R.id.tvPosicion5);
        cargarPreferencias();


    }
    private void cargarPreferencias(){
        SharedPreferences pref = getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
        int posicion1 = pref.getInt("posicion1",1);
        tvPosicion1.setText(String.valueOf(posicion1));
        int posicion2 = pref.getInt("posicion2",1);
        tvPosicion2.setText(String.valueOf(posicion2));
        int posicion3 = pref.getInt("posicion3",1);
        tvPosicion3.setText(String.valueOf(posicion3));
        int posicion4 = pref.getInt("posicion4",1);
        tvPosicion4.setText(String.valueOf(posicion4));
        int posicion5 = pref.getInt("posicion5",1);
        tvPosicion5.setText(String.valueOf(posicion5));
    }
}
