package com.example.happyfarm.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import androidx.fragment.app.DialogFragment;

import com.example.happyfarm.HappyFarmScreen;
import com.example.happyfarm.R;

public class DialogDonHang extends DialogFragment {
    GridView gridView;
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_don_hang, null);
        //.....................
        gridView = view2.findViewById(R.id.gvDonHang);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Đơn hàng")
                .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //gọi func làm mới đơn hàng

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
        return builder.create();
    }
}
