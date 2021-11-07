package com.example.happyfarm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happyfarm.BoQua;
import com.example.happyfarm.GiaoHang;
import com.example.happyfarm.Model.DonHang;
import com.example.happyfarm.R;

import java.util.List;

public class DonHangAdapter extends BaseAdapter {
    Context context;
    List<DonHang> list;
    private GiaoHang giaoHang;
    private BoQua boQua;

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

    static class ViewHolder {
        ImageView imgNongSan;
        TextView txtSolg;
        ImageView imgCoin;
        TextView txtCoin;
        ImageButton imgGiaohang;
        ImageButton imgBoqua;
    }

    @SuppressLint({"InflateParams", "ViewHolder", "DefaultLocale"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_donhang, null);

        holder.imgNongSan = convertView.findViewById(R.id.imgNongsan);
        holder.txtSolg = convertView.findViewById(R.id.txtSolg);
        holder.imgCoin = convertView.findViewById(R.id.imgCoin);
        holder.txtCoin = convertView.findViewById(R.id.txtCoin);
        holder.imgGiaohang = convertView.findViewById(R.id.imgGiaohang);
        holder.imgBoqua = convertView.findViewById(R.id.imgBoqua);
        convertView.setTag(holder);

        DonHang donHang = list.get(position);

        switch (donHang.getNongSanID()) {
            case 1:
                holder.imgNongSan.setImageResource(R.drawable.ic_lua);
                break;
            case 2:
                holder.imgNongSan.setImageResource(R.drawable.ic_cachua);
                break;
            case 3:
                holder.imgNongSan.setImageResource(R.drawable.ic_carot);
                break;
        }
        holder.txtSolg.setText(String.format("x%d",donHang.getSoLuongMua()));
        holder.imgCoin.setImageResource(R.drawable.farmcoin);
        holder.txtCoin.setText(String.valueOf(donHang.getTienHang()));
        holder.imgGiaohang.setImageResource(R.drawable.ic_ok);
        holder.imgGiaohang.setOnClickListener(v -> giaoHang.giaoHang(position));

        holder.imgBoqua.setImageResource(R.drawable.ic_cancel);
        holder.imgBoqua.setOnClickListener(v -> boQua.boQua(position));

        return convertView;
    }
}
