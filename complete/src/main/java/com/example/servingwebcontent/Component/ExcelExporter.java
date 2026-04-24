package com.example.servingwebcontent.Component;

import com.example.servingwebcontent.Model.HangHoa;
import com.example.servingwebcontent.Model.PhieuNhap;
import com.example.servingwebcontent.Model.PhieuXuat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ExcelExporter {

    public byte[] xuatBaoCaoTonKho(ArrayList<HangHoa> danhSach) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Ton Kho Hang Hoa");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            // --- Dòng tiêu đề cột ---
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Mã Hàng", "Tên Hàng", "Phân Loại",
                                 "Tồn Kho", "Đơn Giá", "Hạn Sử Dụng",
                                 "Nhà Cung Cấp", "Vị Trí", "Tối Thiểu", "Trạng Thái"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- Style cảnh báo (nền đỏ nhạt) ---
            CellStyle warnStyle = workbook.createCellStyle();
            warnStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
            warnStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < danhSach.size(); i++) {
                HangHoa h = danhSach.get(i);
                Row row = sheet.createRow(i + 1); 

                row.createCell(0).setCellValue(h.maHang != null ? h.maHang : "");
                row.createCell(1).setCellValue(h.tenHang != null ? h.tenHang : "");
                row.createCell(2).setCellValue(h.phanLoai != null ? h.phanLoai : "");
                row.createCell(3).setCellValue(h.soLuongTon);
                row.createCell(4).setCellValue(h.donGia);
                row.createCell(5).setCellValue(h.hanSuDung != null ? h.hanSuDung : "");
                row.createCell(6).setCellValue(h.maNhaCungCap != null ? h.maNhaCungCap : "");
                row.createCell(7).setCellValue(h.maViTri != null ? h.maViTri : "");
                row.createCell(8).setCellValue(h.soLuongToiThieu);

                String trangThai = h.isTonKhoThap() ? "⚠ Tồn kho thấp!" : "OK";
                Cell trangThaiCell = row.createCell(9);
                trangThaiCell.setCellValue(trangThai);
                if (h.isTonKhoThap()) {
                    // Tô đỏ cột trạng thái
                    trangThaiCell.setCellStyle(warnStyle);
                }
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            System.out.println("ExcelExporter - xuatBaoCaoTonKho: " + danhSach.size() + " dòng");
            return out.toByteArray();

        } catch (Exception e) {
            System.out.println("ExcelExporter - xuatBaoCaoTonKho error: " + e);
            e.printStackTrace();
            return new byte[0];
        }
    }

    public byte[] xuatBaoCaoPhieuNhap(ArrayList<PhieuNhap> danhSach) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Phieu Nhap");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            String[] columns = {"Mã Phiếu", "Nhân Viên", "Nhà Cung Cấp",
                                 "Ngày Tạo", "Ngày Duyệt", "Trạng Thái",
                                 "Tổng Giá Trị", "Ghi Chú"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            for (int i = 0; i < danhSach.size(); i++) {
                PhieuNhap p = danhSach.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(p.maPhieuNhap != null ? p.maPhieuNhap : "");
                row.createCell(1).setCellValue(p.maNV != null ? p.maNV : "");
                row.createCell(2).setCellValue(p.maNCC != null ? p.maNCC : "");
                row.createCell(3).setCellValue(p.ngayTao != null ? p.ngayTao : "");
                row.createCell(4).setCellValue(p.ngayDuyet != null ? p.ngayDuyet : "");
                row.createCell(5).setCellValue(p.trangThai != null ? p.trangThai : "");
                row.createCell(6).setCellValue(p.tongGiaTri);
                row.createCell(7).setCellValue(p.ghiChu != null ? p.ghiChu : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            System.out.println("ExcelExporter - xuatBaoCaoPhieuNhap error: " + e);
            return new byte[0];
        }
    }

    public byte[] xuatBaoCaoPhieuXuat(ArrayList<PhieuXuat> danhSach) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Phieu Xuat");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            String[] columns = {"Mã Phiếu", "Nhân Viên", "Người Duyệt",
                                 "Ngày Tạo", "Ngày Duyệt", "Trạng Thái",
                                 "Lý Do Xuất", "Tổng Giá Trị", "Ghi Chú"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            for (int i = 0; i < danhSach.size(); i++) {
                PhieuXuat p = danhSach.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(p.maPhieuXuat != null ? p.maPhieuXuat : "");
                row.createCell(1).setCellValue(p.maNV != null ? p.maNV : "");
                row.createCell(2).setCellValue(p.maNVDuyet != null ? p.maNVDuyet : "");
                row.createCell(3).setCellValue(p.ngayTao != null ? p.ngayTao : "");
                row.createCell(4).setCellValue(p.ngayDuyet != null ? p.ngayDuyet : "");
                row.createCell(5).setCellValue(p.trangThai != null ? p.trangThai : "");
                row.createCell(6).setCellValue(p.lyDoXuat != null ? p.lyDoXuat : "");
                row.createCell(7).setCellValue(p.tongGiaTri);
                row.createCell(8).setCellValue(p.ghiChu != null ? p.ghiChu : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            System.out.println("ExcelExporter - xuatBaoCaoPhieuXuat error: " + e);
            return new byte[0];
        }
    }
}