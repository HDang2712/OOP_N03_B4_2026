package com.example.servingwebcontent.Model;

public class LichSuThaoTac {
    public String maLichSu;
    public String maNV;          
    public String loaiThaoTac;   
    public String doiTuong;      
    public String maDoiTuong;    
    public String giaTriCu;      
    public String giaTriMoi;     
    public String thoiGian;      
    public String ghiChu;

    public LichSuThaoTac() {}

    public LichSuThaoTac(String maLichSu, String maNV, String loaiThaoTac,
                         String doiTuong, String maDoiTuong,
                         String giaTriCu, String giaTriMoi, String thoiGian) {
        this.maLichSu = maLichSu;
        this.maNV = maNV;
        this.loaiThaoTac = loaiThaoTac;
        this.doiTuong = doiTuong;
        this.maDoiTuong = maDoiTuong;
        this.giaTriCu = giaTriCu;
        this.giaTriMoi = giaTriMoi;
        this.thoiGian = thoiGian;
    }

    public String getMaLichSu() { return maLichSu; }
    public void setMaLichSu(String maLichSu) { this.maLichSu = maLichSu; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getLoaiThaoTac() { return loaiThaoTac; }
    public void setLoaiThaoTac(String loaiThaoTac) { this.loaiThaoTac = loaiThaoTac; }

    public String getDoiTuong() { return doiTuong; }
    public void setDoiTuong(String doiTuong) { this.doiTuong = doiTuong; }

    public String getMaDoiTuong() { return maDoiTuong; }
    public void setMaDoiTuong(String maDoiTuong) { this.maDoiTuong = maDoiTuong; }

    public String getGiaTriCu() { return giaTriCu; }
    public void setGiaTriCu(String giaTriCu) { this.giaTriCu = giaTriCu; }

    public String getGiaTriMoi() { return giaTriMoi; }
    public void setGiaTriMoi(String giaTriMoi) { this.giaTriMoi = giaTriMoi; }

    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public void display() {
        System.out.println("Lịch sử: " + maLichSu + " | NV: " + maNV +
                " | Thao tác: " + loaiThaoTac + " | Đối tượng: " + doiTuong +
                " | Mã: " + maDoiTuong + " | Thời gian: " + thoiGian);
    }
}