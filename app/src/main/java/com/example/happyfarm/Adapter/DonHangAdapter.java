package com.example.happyfarm.Adapter;

import static java.lang.String.format;

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

    public DonHangAdapter(Context context, List<DonHang> list) {
        this.context = context;
        this.list = list;
    }

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

        DonHang donHang = (DonHang) getItem(position);

        ImageView imgNongSan = convertView.findViewById(R.id.imgNongsan);
        TextView txtSolg = convertView.findViewById(R.id.txtSolg);
        ImageView imgCoin = convertView.findViewById(R.id.imgCoin);
        TextView txtCoin = convertView.findViewById(R.id.txtCoin);

        switch (donHang.getNongSanID()) {
            case 1:
                imgNongSan.setImageResource(R.drawable.ic_lua);
                break;
            case 2:
                imgNongSan.setImageResource(R.drawable.ic_cachua);
                break;
            case 3:
                imgNongSan.setImageResource(R.drawable.ic_carot);
                break;
            default:
                imgNongSan.setImageResource(R.drawable.icon_order);
                throw new IllegalStateException("Unexpected value: " + donHang.getNongSanID());
        }

        txtSolg.setText(format("x %d",donHang.getSoLuongMua()));
        imgCoin.setImageResource(R.drawable.farmcoin);
        txtCoin.setText(format("%d",donHang.getTienHang()));

        return convertView;
    }
}
