package com.example.servingwebcontent.Model;

public class HangHoa {
    public String maHang;
    public String tenHang;
    public String phanLoai;
    public int soLuongTon;
    public double donGia;
    public String hanSuDung;
    public String maNhaCungCap;
    public String maViTri;
    public int soLuongToiThieu; 
    public HangHoa() {
        this.soLuongTon = 0;
        this.soLuongToiThieu = 10; 
    }

    public HangHoa(String maHang, String tenHang, String phanLoai,
                   int soLuongTon, double donGia, String hanSuDung,
                   String maNhaCungCap, String maViTri) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.phanLoai = phanLoai;
        this.soLuongTon = soLuongTon;
        this.donGia = donGia;
        this.hanSuDung = hanSuDung;
        this.maNhaCungCap = maNhaCungCap;
        this.maViTri = maViTri;
        this.soLuongToiThieu = 10;
    }

    public boolean isTonKhoThap() {
        return this.soLuongTon < this.soLuongToiThieu;
    }

    public String getMaHang() { return maHang; }
    public void setMaHang(String maHang) { this.maHang = maHang; }

    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }

    public String getPhanLoai() { return phanLoai; }
    public void setPhanLoai(String phanLoai) { this.phanLoai = phanLoai; }

    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public String getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(String hanSuDung) { this.hanSuDung = hanSuDung; }

    public String getMaNhaCungCap() { return maNhaCungCap; }
    public void setMaNhaCungCap(String maNhaCungCap) { this.maNhaCungCap = maNhaCungCap; }

    public String getMaViTri() { return maViTri; }
    public void setMaViTri(String maViTri) { this.maViTri = maViTri; }

    public int getSoLuongToiThieu() { return soLuongToiThieu; }
    public void setSoLuongToiThieu(int soLuongToiThieu) { this.soLuongToiThieu = soLuongToiThieu; }

    public void display() {
        System.out.println("Mã hàng: " + maHang + " | Tên: " + tenHang +
                " | Loại: " + phanLoai + " | Tồn: " + soLuongTon +
                " | Đơn giá: " + donGia + " | Hạn SD: " + hanSuDung);
    }
}