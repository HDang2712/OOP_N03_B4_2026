package com.example.servingwebcontent.Model;

public class PhieuXuat {
    public String maPhieuXuat;
    public String maNV;        
    public String maNVDuyet;   
    public String ngayTao;
    public String ngayDuyet;
    public String trangThai;   
    public String lyDoXuat;
    public String ghiChu;
    public double tongGiaTri;

    public PhieuXuat() {
        this.trangThai = "DRAFT";
    }

    public PhieuXuat(String maPhieuXuat, String maNV, String ngayTao,
                     String lyDoXuat, String ghiChu) {
        this.maPhieuXuat = maPhieuXuat;
        this.maNV = maNV;
        this.ngayTao = ngayTao;
        this.lyDoXuat = lyDoXuat;
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

    public String getMaPhieuXuat() { return maPhieuXuat; }
    public void setMaPhieuXuat(String maPhieuXuat) { this.maPhieuXuat = maPhieuXuat; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getMaNVDuyet() { return maNVDuyet; }
    public void setMaNVDuyet(String maNVDuyet) { this.maNVDuyet = maNVDuyet; }

    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }

    public String getNgayDuyet() { return ngayDuyet; }
    public void setNgayDuyet(String ngayDuyet) { this.ngayDuyet = ngayDuyet; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getLyDoXuat() { return lyDoXuat; }
    public void setLyDoXuat(String lyDoXuat) { this.lyDoXuat = lyDoXuat; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public double getTongGiaTri() { return tongGiaTri; }
    public void setTongGiaTri(double tongGiaTri) { this.tongGiaTri = tongGiaTri; }

    public void display() {
        System.out.println("Phiếu xuất: " + maPhieuXuat + " | NV: " + maNV +
                " | Ngày: " + ngayTao + " | Lý do: " + lyDoXuat +
                " | Trạng thái: " + trangThai + " | Tổng: " + tongGiaTri);
    }
}