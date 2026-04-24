package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.ExcelExporter;
import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.HangHoaDB;
import com.example.servingwebcontent.Database.PhieuNhapDB;
import com.example.servingwebcontent.Database.PhieuXuatDB;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/baocao")
public class BaoCaoController {

    @GetMapping("/tonkho")
    public ResponseEntity<byte[]> xuatTonKho(HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)) {
            return ResponseEntity.status(302).build();
        }
        try {
       
            byte[] fileBytes = new ExcelExporter()
                    .xuatBaoCaoTonKho(new HangHoaDB().getAll());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            ));
            headers.setContentDispositionFormData("attachment", "BaoCao_TonKho.xlsx");

            System.out.println("BaoCaoController - xuất tồn kho thành công");
            return ResponseEntity.ok().headers(headers).body(fileBytes);

        } catch (Exception e) {
            System.out.println("BaoCaoController - xuatTonKho error: " + e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/phieunhap")
    public ResponseEntity<byte[]> xuatPhieuNhap(HttpSession session) {
        if (!SessionHelper.isQuanLy(session)) {
            return ResponseEntity.status(403).build();
        }
        try {
            byte[] fileBytes = new ExcelExporter()
                    .xuatBaoCaoPhieuNhap(new PhieuNhapDB().getAll());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            ));
            headers.setContentDispositionFormData("attachment", "BaoCao_PhieuNhap.xlsx");

            return ResponseEntity.ok().headers(headers).body(fileBytes);

        } catch (Exception e) {
            System.out.println("BaoCaoController - xuatPhieuNhap error: " + e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/phieuxuat")
    public ResponseEntity<byte[]> xuatPhieuXuat(HttpSession session) {
        if (!SessionHelper.isQuanLy(session)) {
            return ResponseEntity.status(403).build();
        }
        try {
            byte[] fileBytes = new ExcelExporter()
                    .xuatBaoCaoPhieuXuat(new PhieuXuatDB().getAll());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            ));
            headers.setContentDispositionFormData("attachment", "BaoCao_PhieuXuat.xlsx");

            return ResponseEntity.ok().headers(headers).body(fileBytes);

        } catch (Exception e) {
            System.out.println("BaoCaoController - xuatPhieuXuat error: " + e);
            return ResponseEntity.internalServerError().build();
        }
    }
}