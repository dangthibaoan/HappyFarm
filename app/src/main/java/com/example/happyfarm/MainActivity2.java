package com.example.happyfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    Button btnLogin, btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main2);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_login_screen, null);
            //.....................


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setTitle("Đăng nhập")
                    .setView(view1)
                    .setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //demo
                            startActivity(new Intent(MainActivity2.this,HappyFarmScreen.class));

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        btnReg = findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_register_screen, null);
            //.....................


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setTitle("Đăng nhập")
                    .setView(view2)
                    .setPositiveButton("Đăng ký", new DialogInterface.OnClickListener() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //demo
                            startActivity(new Intent(MainActivity2.this,HappyFarmScreen.class));

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }
}