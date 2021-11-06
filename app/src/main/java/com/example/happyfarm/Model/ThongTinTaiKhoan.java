package com.example.happyfarm.Model;

public class ThongTinTaiKhoan {
    private String uID;
    private int tongTienNongTrai;
    private int expLevel;
    private int giaTriTheLuc;
    private int tongSoLuongLua;
    private int tongSoluongCachua;
    private int tongSoLuongCaRot;

    public ThongTinTaiKhoan(){}

    public int getTongTienNongTrai() {
        return tongTienNongTrai;
    }

    public void setTongTienNongTrai(int tongTienNongTrai) {
        this.tongTienNongTrai = tongTienNongTrai;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getGiaTriTheLuc() {
        return giaTriTheLuc;
    }

    public void setGiaTriTheLuc(int giaTriTheLuc) {
        this.giaTriTheLuc = giaTriTheLuc;
    }

    public int getTongSoLuongLua() {
        return tongSoLuongLua;
    }

    public void setTongSoLuongLua(int tongSoLuongLua) {
        this.tongSoLuongLua = tongSoLuongLua;
    }

    public int getTongSoluongCachua() {
        return tongSoluongCachua;
    }

    public void setTongSoluongCachua(int tongSoluongCachua) {
        this.tongSoluongCachua = tongSoluongCachua;
    }

    public int getTongSoLuongCaRot() {
        return tongSoLuongCaRot;
    }

    public void setTongSoLuongCaRot(int tongSoLuongCaRot) {
        this.tongSoLuongCaRot = tongSoLuongCaRot;
    }

    public void Create(){
        this.setTongTienNongTrai(0);
        this.setExpLevel(0);
        this.setGiaTriTheLuc(150);
        this.setTongSoLuongLua(0);
        this.setTongSoluongCachua(0);
        this.setTongSoLuongCaRot(0);
    }

   /* public void Update(){


        db.collection("ThongTinNongTrai")
                .document(USERID)
                .update("tongTienNongTrai",this.getTongTienNongTrai(),
                        "expLevel", this.getExpLevel(),
                        "giaTriTheLuc", this.getGiaTriTheLuc(),
                        "tongSoLuongLua", this.getTongSoLuongLua(),
                        "tongSoLuongCaChua", this.getTongSoluongCachua(),
                        "tongSoLuongCaRot", this.getTongSoLuongCaRot())//sửa lại uid trong thông tin đăng nhập
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: Add role success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
    }*/
}
