package com.example.servingwebcontent.Model;

public class NhaCungCap {
    public String maNCC;
    public String tenNCC;
    public String diaChi;
    public String soDienThoai;
    public String email;

    public NhaCungCap() {}

    public NhaCungCap(String maNCC, String tenNCC, String diaChi,
                      String soDienThoai, String email) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }

    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void display() {
        System.out.println("Mã NCC: " + maNCC + " | Tên: " + tenNCC +
                " | ĐT: " + soDienThoai + " | Email: " + email);
    }
}