package com.example.happyfarm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.happyfarm.Model.TaiKhoan;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Thread wait = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                } finally {
                    startActivity(new Intent(MainActivity.this, LoginScreen.class));
                }
            }
        };

        TaiKhoan accDemo = new TaiKhoan();
        accDemo.Register("admin","0000");
        accDemo.setUid("0000");

        //đưa accDemo lên firestore
        db = FirebaseFirestore.getInstance();
        db.collection("USER").document(accDemo.getUid())
                .set(accDemo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    wait.start();
                    Log.d("TAG", "onSuccess: Add acc success");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(),"Lỗi", Toast.LENGTH_LONG).show();
                    Log.d("TAG", "onFailure: Add acc demo error " + e);
                });
    }
}