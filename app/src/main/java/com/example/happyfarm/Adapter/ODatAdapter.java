package com.example.happyfarm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.happyfarm.Model.ODat;
import com.example.happyfarm.R;

import java.util.List;

public class ODatAdapter extends BaseAdapter {
    Context context;
    List<ODat> list;

    public ODatAdapter(Context context, List<ODat> list) {
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
        convertView = inflater.inflate(R.layout.item_o_dat, null);
        ODat oDat = list.get(position);

        TextView txt = convertView.findViewById(R.id.txt);

        if (!oDat.isMoKhoa()){//nếu ô đất chưa được mở khóa
            convertView.setBackgroundResource(R.drawable.dat_locked);
            txt.setText("Chưa mở khóa");
        } else if (!oDat.isLamDat()){//nếu ô đát đã mở khóa nhưng chưa được làm đất
            convertView.setBackgroundResource(R.drawable.dat_unlocked);
            txt.setText("Chưa làm đất");
        } else if (!oDat.isGieoHat()){//nếu ô đất đã làm đất nhưng chưa được gieo hạt
            convertView.setBackgroundResource(R.drawable.dat_ok);
            txt.setText("Chưa gieo hạt");
        } else {
            switch (oDat.getoDatID() / 10) {
                case 1:
                    if (oDat.getSoNgayThuHoach() > 3)
                        convertView.setBackgroundResource(R.drawable.ns_lua01);
                    else if (oDat.getSoNgayThuHoach() > 1)
                        convertView.setBackgroundResource(R.drawable.ns_lua02);
                    else convertView.setBackgroundResource(R.drawable.ns_lua03);
                    break;
                case 2:
                    if (oDat.getSoNgayThuHoach() > 3)
                        convertView.setBackgroundResource(R.drawable.ns_cachua01);
                    else if (oDat.getSoNgayThuHoach() > 1)
                        convertView.setBackgroundResource(R.drawable.ns_cachua02);
                    else convertView.setBackgroundResource(R.drawable.ns_cachua03);
                    break;
                case 3:
                    if (oDat.getSoNgayThuHoach() > 3)
                        convertView.setBackgroundResource(R.drawable.ns_carot01);
                    else if (oDat.getSoNgayThuHoach() > 1)
                        convertView.setBackgroundResource(R.drawable.ns_carot02);
                    else convertView.setBackgroundResource(R.drawable.ns_carot03);
                    break;
            }
        }

        return convertView;
    }
}
