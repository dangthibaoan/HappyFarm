package com.example.happyfarm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happyfarm.Model.SanPham;
import com.example.happyfarm.R;

import java.util.List;

public class SPAdapter extends BaseAdapter {
    Context context;
    List<SanPham> list;

    public SPAdapter(Context context, List<SanPham> list) {
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
        convertView = inflater.inflate(R.layout.item_sanpham, null);

        SanPham sanPham = list.get(position);

        ImageView img = convertView.findViewById(R.id.imgSP);
        switch (sanPham.getAnhMinhHoa()) {
            case "img_lua":
                img.setImageResource(R.drawable.ic_lua);
                break;
            case "img_cachua":
                img.setImageResource(R.drawable.ic_cachua);
                break;
            case "img_carot":
                img.setImageResource(R.drawable.ic_carot);
                break;
        }

        TextView txtSP = convertView.findViewById(R.id.txtTensp);
        txtSP.setText(sanPham.getTenSanPham());

        TextView txtDes = convertView.findViewById(R.id.txtMota);
        txtDes.setText(sanPham.getMoTaSanPham());

        return convertView;
    }
}
