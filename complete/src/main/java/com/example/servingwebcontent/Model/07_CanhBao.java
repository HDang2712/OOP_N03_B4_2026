package com.example.servingwebcontent.Model;

public class CanhBao {
    public String maCanhBao;
    public String loaiCanhBao;  
    public String maHang;       
    public String noiDung;     
    public String thoiGian;     
    public String trangThai;    
    public String maNVXuLy;     

    public CanhBao() {
        this.trangThai = "CHUA_XU_LY";
    }

    public CanhBao(String maCanhBao, String loaiCanhBao, String maHang,
                   String noiDung, String thoiGian) {
        this.maCanhBao = maCanhBao;
        this.loaiCanhBao = loaiCanhBao;
        this.maHang = maHang;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.trangThai = "CHUA_XU_LY";
    }

    public void xuLy(String maNV) {
        this.trangThai = "DA_XU_LY";
        this.maNVXuLy = maNV;
    }

    public boolean chuaXuLy() {
        return "CHUA_XU_LY".equals(this.trangThai);
    }

    public String getMaCanhBao() { return maCanhBao; }
    public void setMaCanhBao(String maCanhBao) { this.maCanhBao = maCanhBao; }

    public String getLoaiCanhBao() { return loaiCanhBao; }
    public void setLoaiCanhBao(String loaiCanhBao) { this.loaiCanhBao = loaiCanhBao; }

    public String getMaHang() { return maHang; }
    public void setMaHang(String maHang) { this.maHang = maHang; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMaNVXuLy() { return maNVXuLy; }
    public void setMaNVXuLy(String maNVXuLy) { this.maNVXuLy = maNVXuLy; }

    public void display() {
        System.out.println("Cảnh báo: " + maCanhBao + " | Loại: " + loaiCanhBao +
                " | Hàng: " + maHang + " | Nội dung: " + noiDung +
                " | Thời gian: " + thoiGian + " | Trạng thái: " + trangThai);
    }
}