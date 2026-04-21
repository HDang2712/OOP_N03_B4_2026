package com.example.servingwebcontent.Model;

public class PhieuNhap {
    public String maPhieuNhap;
    public String maNV;        
    public String maNCC;       
    public String ngayTao;
    public String ngayDuyet;
    public String trangThai;  
    public String ghiChu;
    public double tongGiaTri;

    public PhieuNhap() {
        this.trangThai = "DRAFT";
    }

    public PhieuNhap(String maPhieuNhap, String maNV, String maNCC,
                     String ngayTao, String ghiChu) {
        this.maPhieuNhap = maPhieuNhap;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.ngayTao = ngayTao;
        this.ghiChu = ghiChu;
        this.trangThai = "DRAFT"; 
        this.tongGiaTri = 0;
    }

    public boolean coTheDuyet() {
        return "PENDING".equals(this.trangThai);
    }

    public boolean daHoanThanh() {
        return "DONE".equals(this.trangThai);
    }

    public String getMaPhieuNhap() { return maPhieuNhap; }
    public void setMaPhieuNhap(String maPhieuNhap) { this.maPhieuNhap = maPhieuNhap; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }

    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }

    public String getNgayDuyet() { return ngayDuyet; }
    public void setNgayDuyet(String ngayDuyet) { this.ngayDuyet = ngayDuyet; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public double getTongGiaTri() { return tongGiaTri; }
    public void setTongGiaTri(double tongGiaTri) { this.tongGiaTri = tongGiaTri; }

    public void display() {
        System.out.println("Phiếu nhập: " + maPhieuNhap + " | NV: " + maNV +
                " | NCC: " + maNCC + " | Ngày: " + ngayTao +
                " | Trạng thái: " + trangThai + " | Tổng: " + tongGiaTri);
    }
}