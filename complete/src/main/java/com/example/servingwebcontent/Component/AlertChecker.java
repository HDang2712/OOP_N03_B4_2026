package com.example.servingwebcontent.Component;

import com.example.servingwebcontent.Database.CanhBaoDB;
import com.example.servingwebcontent.Database.HangHoaDB;
import com.example.servingwebcontent.Model.CanhBao;
import com.example.servingwebcontent.Model.HangHoa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class AlertChecker {

    private HangHoaDB hangHoaDB = new HangHoaDB();
    private CanhBaoDB canhBaoDB = new CanhBaoDB();


    private static final int NGAY_CANH_BAO_HAN = 30;

    private static final int NGUONG_XUAT_BAT_THUONG = 50;

    public void runAllChecks() {
        System.out.println("=== AlertChecker: Bắt đầu kiểm tra ===");
        checkTonKhoThap();
        checkHanSuDung();
        System.out.println("=== AlertChecker: Kiểm tra xong ===");
    }


    public void checkTonKhoThap() {
        try {
            ArrayList<HangHoa> danhSachThap = hangHoaDB.getTonKhoThap();
            for (HangHoa h : danhSachThap) {
                String noiDung = "Hàng '" + h.tenHang + "' tồn kho còn " +
                        h.soLuongTon + " (ngưỡng tối thiểu: " + h.soLuongToiThieu + ")";

                if (!daCoCanHBao(h.maHang, "TON_KHO_THAP")) {
                    CanhBao cb = new CanhBao(
                        taoMaCanhBao(),
                        "TON_KHO_THAP",
                        h.maHang,
                        noiDung,
                        thoiGianHienTai()
                    );
                    canhBaoDB.insert(cb);
                    System.out.println("AlertChecker - TON_KHO_THAP: " + noiDung);
                }
            }
        } catch (Exception e) {
            System.out.println("AlertChecker - checkTonKhoThap error: " + e);
        }
    }

    public void checkHanSuDung() {
        try {
            ArrayList<HangHoa> tatCaHang = hangHoaDB.getAll();
            LocalDate homNay = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (HangHoa h : tatCaHang) {
                if (h.hanSuDung == null || h.hanSuDung.isEmpty()) continue;

                try {
                    LocalDate han = LocalDate.parse(h.hanSuDung, formatter);
                    long soNgayConLai = homNay.until(han, java.time.temporal.ChronoUnit.DAYS);

                    if (soNgayConLai <= NGAY_CANH_BAO_HAN && soNgayConLai >= 0) {
                        String noiDung = "Hàng '" + h.tenHang + "' sắp hết hạn! Còn " +
                                soNgayConLai + " ngày (hạn: " + h.hanSuDung + ")";

                        if (!daCoCanHBao(h.maHang, "HAN_SU_DUNG")) {
                            CanhBao cb = new CanhBao(
                                taoMaCanhBao(),
                                "HAN_SU_DUNG",
                                h.maHang,
                                noiDung,
                                thoiGianHienTai()
                            );
                            canhBaoDB.insert(cb);
                            System.out.println("AlertChecker - HAN_SU_DUNG: " + noiDung);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("AlertChecker - không parse được hạn: " + h.hanSuDung);
                }
            }
        } catch (Exception e) {
            System.out.println("AlertChecker - checkHanSuDung error: " + e);
        }
    }

    public void checkXuatBatThuong(String maHang, int soLuong, String maNV) {
        try {
            if (soLuong > NGUONG_XUAT_BAT_THUONG) {
                String noiDung = "Xuất bất thường! NV " + maNV + " xuất " +
                        soLuong + " sản phẩm (mã: " + maHang + ") cùng lúc";

                CanhBao cb = new CanhBao(
                    taoMaCanhBao(),
                    "XUAT_BAT_THUONG",
                    maHang,
                    noiDung,
                    thoiGianHienTai()
                );
                canhBaoDB.insert(cb);
                System.out.println("AlertChecker - XUAT_BAT_THUONG: " + noiDung);
            }
        } catch (Exception e) {
            System.out.println("AlertChecker - checkXuatBatThuong error: " + e);
        }
    }

    private boolean daCoCanHBao(String maHang, String loaiCanhBao) {
        try {
            ArrayList<CanhBao> danhSach = canhBaoDB.getChuaXuLy();
            for (CanhBao cb : danhSach) {
                if (loaiCanhBao.equals(cb.loaiCanhBao) &&
                    maHang != null && maHang.equals(cb.maHang)) {
                    return true; // đã có rồi, không tạo thêm
                }
            }
        } catch (Exception e) {
            System.out.println("AlertChecker - daCoCanHBao error: " + e);
        }
        return false;
    }

    private String taoMaCanhBao() {
        return "CB" + System.currentTimeMillis();
    }

    private String thoiGianHienTai() {
        return LocalDate.now().toString();
    }
}