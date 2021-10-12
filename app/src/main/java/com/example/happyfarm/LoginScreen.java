package com.example.happyfarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.happyfarm.Model.RuongNongSan;
import com.example.happyfarm.Model.ThongTinTaiKhoan;
import com.example.happyfarm.Model.TaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {
    public static String USERNAME, USERID;
    public static int FARMCOIN, FARMLEVEL, STAMINA, LUA, CACHUA, CAROT;

    Button btnLogin, btnReg;
    EditText usn, pwd, repwd;
    FirebaseFirestore db;
    int kq;
    Boolean loginStatus;

    Thread thread1, thread2, thread3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login_screen);

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
                .setPositiveButton("Đăng ký", new DialogInterface.OnClickListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //demo
                        //startActivity(new Intent(MainActivity2.this,HappyFarmScreen.class));
                        String loginName = usn.getText().toString().trim();
                        String pass = pwd.getText().toString().trim();
                        String rePass = repwd.getText().toString().trim();
                        if (pass.compareTo(rePass) != 1) {
                            Toast.makeText(getApplicationContext(),"Mật khẩu nhập lại không đúng.", Toast.LENGTH_LONG).show();
                        } else if (kiemTra(loginName)==0) {
                            pushData(loginName,pass);
                        } else {
                            Toast.makeText(getApplicationContext(),"Tên đăng nhập đã tồn tại.", Toast.LENGTH_LONG).show();
                        }
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
    }

    private int kiemTra(String usn){
        db = FirebaseFirestore.getInstance();
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
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
                .setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //demo
                        String loginName = usn.getText().toString().trim();
                        String pass = pwd.getText().toString().trim();

                        db = FirebaseFirestore.getInstance();
                        db.collection("User")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
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
                                                Intent intent = new Intent(LoginScreen.this,WaitActivity.class);
                                                intent.putExtra("flag",true);
                                                startActivity(intent);
                                            }
                                        } else {
                                            Log.d("Login", "onComplete: Load data error " + task.getException());
                                        }
                                    }
                                });

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
    }

    private void pushData(String usn, String pwd) {
        TaiKhoan accDemo = new TaiKhoan("0",usn, pwd);
        //db = FirebaseFirestore.getInstance();

        //đưa acc lên firestore
        USERID = accDemo.Register();
        if (USERID.equals("Fail")) Toast.makeText(getApplicationContext(), "Đăng ký tài khoản không thành công!", Toast.LENGTH_SHORT).show();
        else{
            accDemo.setUid(USERID);
            accDemo.Update();//cập nhật lại thông tin acc vừa tạo

            Intent intent = new Intent(LoginScreen.this,WaitActivity.class);
            intent.putExtra("flag",false);
            startActivity(intent);
        }

    }
}