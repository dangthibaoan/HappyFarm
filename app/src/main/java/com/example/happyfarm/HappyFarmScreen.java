package com.example.happyfarm;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Objects;

public class HappyFarmScreen extends AppCompatActivity {

    ImageView img_bg, imgAvatar, imgCoin, imgOrderDone, imgTimeSkip, imgLua, imgCachua, imgCarot, imgKho, imgDat, imgCay, imgShop, imgPhanbon, imgReact, imgNuoc, imgOrderNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_happy_farm_screen);

        img_bg = findViewById(R.id.img_bg);
        img_bg.setImageResource(R.drawable.bg_03);

        imgAvatar = findViewById(R.id.imgAvatar);
        imgAvatar.setImageResource(R.drawable.gieohat);

        imgCoin = findViewById(R.id.imgMoney);
        imgCoin.setImageResource(R.drawable.farmcoin);

        imgOrderDone = findViewById(R.id.imgDhht);
        imgOrderDone.setImageResource(R.drawable.order_done);

        imgTimeSkip = findViewById(R.id.imgTimeSkip);
        imgTimeSkip.setImageResource(R.drawable.timeskip);

        imgLua = findViewById(R.id.imglua);
        imgLua.setImageResource(R.drawable.ruonglua);

        imgCachua = findViewById(R.id.imgcachua);
        imgCachua.setImageResource(R.drawable.ruongcachua);

        imgCarot = findViewById(R.id.imgcarot);
        imgCarot.setImageResource(R.drawable.ruongcarot);

        imgKho = findViewById(R.id.imgStorage);
        imgKho.setImageResource(R.drawable.ic_storage);

        imgDat = findViewById(R.id.imgDat);
        imgDat.setImageResource(0);

        imgCay = findViewById(R.id.imgCay);
        imgCay.setImageResource(0);

        imgShop = findViewById(R.id.imgShop);
        imgShop.setImageResource(R.drawable.icon_shop);

        imgPhanbon = findViewById(R.id.imgBonphan);
        imgPhanbon.setImageResource(R.drawable.bonphan);

        imgReact = findViewById(R.id.imgReact);
        imgReact.setImageResource(R.drawable.lamdat);

        imgNuoc = findViewById(R.id.imgTuoinc);
        imgNuoc.setImageResource(R.drawable.tuoinc);

        imgOrderNew = findViewById(R.id.imgOrder);
        imgOrderNew.setImageResource(R.drawable.order);

    }
}