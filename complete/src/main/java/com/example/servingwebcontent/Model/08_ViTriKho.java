package com.example.servingwebcontent.Model;

public class ViTriKho {
    public String maViTri;
    public String tenViTri;    
    public String khuVuc;      
    public int sucChua;        
    public int dangSuDung;    
    public String moTa;

    public ViTriKho() {
        this.dangSuDung = 0;
    }

    public ViTriKho(String maViTri, String tenViTri, String khuVuc,
                    int sucChua, String moTa) {
        this.maViTri = maViTri;
        this.tenViTri = tenViTri;
        this.khuVuc = khuVuc;
        this.sucChua = sucChua;
        this.dangSuDung = 0;
        this.moTa = moTa;
    }
    public boolean conChoTrong() {
        return this.dangSuDung < this.sucChua;
    }
    public double phanTramSuDung() {
        if (sucChua == 0) return 0;
        return ((double) dangSuDung / sucChua) * 100;
    }

    public String getMaViTri() { return maViTri; }
    public void setMaViTri(String maViTri) { this.maViTri = maViTri; }

    public String getTenViTri() { return tenViTri; }
    public void setTenViTri(String tenViTri) { this.tenViTri = tenViTri; }

    public String getKhuVuc() { return khuVuc; }
    public void setKhuVuc(String khuVuc) { this.khuVuc = khuVuc; }

    public int getSucChua() { return sucChua; }
    public void setSucChua(int sucChua) { this.sucChua = sucChua; }

    public int getDangSuDung() { return dangSuDung; }
    public void setDangSuDung(int dangSuDung) { this.dangSuDung = dangSuDung; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public void display() {
        System.out.println("Vị trí: " + maViTri + " | Tên: " + tenViTri +
                " | Khu: " + khuVuc + " | Sức chứa: " + sucChua +
                " | Đang dùng: " + dangSuDung + " | " +
                String.format("%.1f", phanTramSuDung()) + "% sử dụng");
    }
}