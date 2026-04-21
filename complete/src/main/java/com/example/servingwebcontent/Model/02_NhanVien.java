package com.example.servingwebcontent.Model;

public class NhanVien {
    public String maNV;
    public String hoTen;
    public String email;
    public String soDienThoai;
    public String vaiTro; 
    public String matKhau;

    public NhanVien() {}

    public NhanVien(String maNV, String hoTen, String email,
                    String soDienThoai, String vaiTro, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.vaiTro = vaiTro;
        this.matKhau = matKhau;
    }
    public boolean isAdmin() {
        return "admin".equals(this.vaiTro);
    }

    public boolean isQuanLy() {
        return "quan_ly".equals(this.vaiTro) || isAdmin();
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public void display() {
        System.out.println("Mã NV: " + maNV + " | Họ tên: " + hoTen +
                " | Vai trò: " + vaiTro + " | Email: " + email);
    }
}