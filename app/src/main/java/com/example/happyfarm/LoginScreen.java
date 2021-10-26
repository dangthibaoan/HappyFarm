package com.example.happyfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happyfarm.Model.TaiKhoan;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {
    public static String USERNAME, USERID;
    public static int FARMCOIN, FARMLEVEL, STAMINA, LUA, CACHUA, CAROT;

    Button btnLogin, btnReg;
    EditText usn, pwd, repwd;
    FirebaseFirestore db;
    int kq;
    Boolean loginStatus;

    //Thread thread1, thread2, thread3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login_screen);
        db = FirebaseFirestore.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this::onClick2);

        btnReg = findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(this::onClick);

    }

    private void onClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_register_screen, null);
        //.....................
        usn = view2.findViewById(R.id.edtUsername);
        pwd = view2.findViewById(R.id.edtPass);
        repwd = view2.findViewById(R.id.edtRePass);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
        builder.setTitle("Đăng ký")
                .setView(view2)
                .setPositiveButton("Đăng ký", (dialog, which) -> {
                    //demo
                    String loginName = usn.getText().toString().trim();
                    String pass = pwd.getText().toString().trim();
                    String rePass = repwd.getText().toString().trim();
                    if (!rePass.equals(pass)) {
                        Toast.makeText(getApplicationContext(),"Mật khẩu nhập lại không đúng.", Toast.LENGTH_LONG).show();
                    } else if (kiemTra(loginName)==0) {
                        TaiKhoan accDemo = new TaiKhoan();
                        accDemo.Register(loginName,pass);
                        //đưa acc lên firestore
                        db = FirebaseFirestore.getInstance();
                        db.collection("User").document(accDemo.getUid())
                                .set(accDemo)
                                .addOnSuccessListener(aVoid -> {
                                    USERID = accDemo.getUid();
                                    Toast.makeText(getApplicationContext(), "Đăng ký acc thành công!", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "onSuccess: Add food success");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(), "Đăng ký acc thất bại!", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "onFailure: Add food error " + e);
                                });

                        if (USERID.equals(accDemo.getUid())){
                            Intent intent = new Intent(LoginScreen.this, EnterGameActivity.class);
                            intent.putExtra("flag",false);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Tên đăng nhập đã tồn tại.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //vẫn crash =.=
    private int kiemTra(String usn){
        kq=0;
        db = FirebaseFirestore.getInstance();
        db.collection("User").whereEqualTo("usn",usn)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                            TaiKhoan user = snapshot.toObject(TaiKhoan.class);
                            if (usn.equals(user.getUsn())) {
                                kq=1;
                                break;
                            }
                        }
                        kq=0;
                    } else {
                        Log.d("Register", "onComplete: Load data error " + task.getException());
                    }
                });
        return kq;
    }

    private void onClick2(View view) {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_login_screen, null);
        //.....................

        usn = view1.findViewById(R.id.edtUsername);
        pwd = view1.findViewById(R.id.edtPass);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
        builder.setTitle("Đăng nhập")
                .setView(view1)
                .setPositiveButton("Đăng nhập", (dialog, which) -> {
                    //demo
                    String loginName = usn.getText().toString().trim();
                    String pass = pwd.getText().toString().trim();

                    db = FirebaseFirestore.getInstance();
                    db.collection("User")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                                        TaiKhoan user = snapshot.toObject(TaiKhoan.class);
                                        if (loginName.equals(user.getUsn()) && pass.equals(user.getPwd())){
                                            loginStatus=true;
                                            btnLogin.setEnabled(true);
                                            USERID = user.getUid();
                                            USERNAME = user.getUsn();
                                            break;
                                        }
                                    }
                                    if (!loginStatus) {
                                        btnLogin.setEnabled(true);
                                        Toast.makeText(getApplicationContext(),"Sai tên đăng nhập hoặc mật khẩu!",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Chào mừng "+USERNAME +" đăng nhập!",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginScreen.this, EnterGameActivity.class);
                                        intent.putExtra("flag",true);
                                        startActivity(intent);
                                    }
                                } else {
                                    Log.d("Login", "onComplete: Load data error " + task.getException());
                                }
                            });

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}