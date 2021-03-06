package com.example.happyfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {
    public static String USERNAME, USERID;

    Button btnLogin, btnReg;
    EditText usn, pwd, repwd;
    FirebaseFirestore db;
    Boolean loginStatus;
    int kq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login_screen);


        btnReg = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        btnReg.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_register_screen, null);
            //.....................
            usn = view2.findViewById(R.id.edtUsername);
            pwd = view2.findViewById(R.id.edtPass);
            repwd = view2.findViewById(R.id.edtRePass);

            Builder builder = new Builder(LoginScreen.this);
            builder.setTitle("????ng k??")
                    .setView(view2)
                    .setPositiveButton("????ng k??", (dialog, which) -> {
                        String loginName = usn.getText().toString().trim();
                        String pass = pwd.getText().toString().trim();
                        String rePass = repwd.getText().toString().trim();
                        db = FirebaseFirestore.getInstance();
                        db.collection("USER").whereEqualTo("usn", loginName)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        kq=0;
                                        for (QueryDocumentSnapshot ignored : Objects.requireNonNull(task.getResult()))
                                            kq = 1;
                                        if (kq==1) Toast.makeText(getApplicationContext(),"T??n ????ng nh???p ???? t???n t???i.", Toast.LENGTH_LONG).show();
                                        else doRegister(loginName, pass, rePass);
                                    } else {
                                        Log.d("Register", "onComplete: Load data error " + task.getException());
                                    }
                                });
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btnLogin.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_login_screen, null);
            //.....................

            usn = view1.findViewById(R.id.edtUsername);
            pwd = view1.findViewById(R.id.edtPass);

            Builder builder = new Builder(LoginScreen.this);
            builder.setTitle("????ng nh???p")
                    .setView(view1)
                    .setPositiveButton("????ng nh???p", (dialog, which) -> {
                        //demo
                        String loginName = usn.getText().toString().trim();
                        String pass = pwd.getText().toString().trim();
                        doLogin(loginName,pass);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public void doRegister(String loginName, String pass, String rePass){
        if (!rePass.equals(pass)) {
            Toast.makeText(getApplicationContext(),"M???t kh???u nh???p l???i kh??ng ????ng.", Toast.LENGTH_LONG).show();
        }
        else {
            TaiKhoan accDemo = new TaiKhoan();
            accDemo.Register(loginName,pass);
            //????a acc l??n firestore
            db = FirebaseFirestore.getInstance();
            db.collection("USER").document(accDemo.getUid())
                    .set(accDemo, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        USERID = accDemo.getUid();
                        Toast.makeText(getApplicationContext(), "????ng k?? acc th??nh c??ng!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginScreen.this, EnterGameActivity.class);
                        intent.putExtra("flag","Reg");
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(),"????ng k?? acc th???t b???i.", Toast.LENGTH_LONG).show();
                        Log.d("TAG", "onFailure: Add acc error " + e);
                    });
        }
    }

    //????ng nh???p
    public void doLogin(String loginName, String pass) {
        db = FirebaseFirestore.getInstance();
        db.collection("USER").whereEqualTo("usn",loginName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                            TaiKhoan user = snapshot.toObject(TaiKhoan.class);
                            if (pass.equals(user.getPwd())){
                                if (user.getUid().equals("0000")){
                                    Toast.makeText(getApplicationContext(), "T??i kho???n n??y kh??ng c?? d??? li???u!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    loginStatus=true;
                                    btnLogin.setEnabled(true);
                                    USERID = user.getUid();
                                    USERNAME = user.getUsn();
                                    break;
                                }
                            } else
                                loginStatus=false;
                        }
                        if (!loginStatus)
                            Toast.makeText(getApplicationContext(), "Sai t??n ????ng nh???p ho???c m???t kh???u!", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getApplicationContext(),"Ch??o m???ng "+USERNAME +" ????ng nh???p!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, EnterGameActivity.class);
                            intent.putExtra("flag","Login");
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "L???i!", Toast.LENGTH_SHORT).show();
                        Log.d("Login", "onComplete: Load data error " + task.getException());
                    }
                });

    }
}