package com.example.servingwebcontent.Model;

public class ChiTietPhieu {
    public String maChiTiet;
    public String maPhieu;
    public String loaiPhieu;     
    public String maHang;
    public int soLuong;
    public double donGia;
    public double thanhTien;   

    public ChiTietPhieu() {}

    public ChiTietPhieu(String maChiTiet, String maPhieu, String loaiPhieu,
                        String maHang, int soLuong, double donGia) {
        this.maChiTiet = maChiTiet;
        this.maPhieu = maPhieu;
        this.loaiPhieu = loaiPhieu;
        this.maHang = maHang;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia; 
    }

    
    public void tinhThanhTien() {
        this.thanhTien = this.soLuong * this.donGia;
    }

    public String getMaChiTiet() { return maChiTiet; }
    public void setMaChiTiet(String maChiTiet) { this.maChiTiet = maChiTiet; }

    public String getMaPhieu() { return maPhieu; }
    public void setMaPhieu(String maPhieu) { this.maPhieu = maPhieu; }

    public String getLoaiPhieu() { return loaiPhieu; }
    public void setLoaiPhieu(String loaiPhieu) { this.loaiPhieu = loaiPhieu; }

    public String getMaHang() { return maHang; }
    public void setMaHang(String maHang) { this.maHang = maHang; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        tinhThanhTien(); 
    }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) {
        this.donGia = donGia;
        tinhThanhTien(); 
    }

    public double getThanhTien() { return thanhTien; }

    public void display() {
        System.out.println("Chi tiết: " + maChiTiet + " | Phiếu: " + maPhieu +
                " | Hàng: " + maHang + " | SL: " + soLuong +
                " | Đơn giá: " + donGia + " | Thành tiền: " + thanhTien);
    }
}