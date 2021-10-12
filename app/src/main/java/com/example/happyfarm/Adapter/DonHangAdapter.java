package com.example.happyfarm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happyfarm.Model.DonHang;
import com.example.happyfarm.R;

import java.util.List;

public class DonHangAdapter extends BaseAdapter {
    Context context;
    List<DonHang> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"InflateParams", "ViewHolder", "DefaultLocale"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_donhang, null);
        DonHang donHang = list.get(position);

        ImageView imgNongsan = convertView.findViewById(R.id.imgNongsan);
        switch (donHang.getNongSanID()) {
            case 1:
                imgNongsan.setImageResource(R.drawable.ic_lua);
                break;
            case 2:
                imgNongsan.setImageResource(R.drawable.ic_lua);
                break;
            case 3:
                imgNongsan.setImageResource(R.drawable.ic_carot);
                break;
        }

        TextView txtSolg = convertView.findViewById(R.id.txtSolg);
        txtSolg.setText(String.format("x%d",donHang.getSoLuongMua()));

        ImageView imgCoin = convertView.findViewById(R.id.imgCoin);
        imgCoin.setImageResource(R.drawable.farmcoin);

        TextView txtCoin = convertView.findViewById(R.id.txtCoin);
        txtCoin.setText(String.valueOf(donHang.getTienHang()));

        ImageView imgGiaohang = convertView.findViewById(R.id.imgGiaohang);
        imgGiaohang.setImageResource(R.drawable.ic_ok);
        imgGiaohang.setOnClickListener(view -> {

        });

        ImageView imgBoqua = convertView.findViewById(R.id.imgBoqua);
        imgBoqua.setImageResource(R.drawable.ic_cancel);
        imgBoqua.setOnClickListener(view -> {

        });
        return convertView;
    }
}
