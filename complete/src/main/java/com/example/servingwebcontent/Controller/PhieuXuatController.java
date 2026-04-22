package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.AlertChecker;
import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.*;
import com.example.servingwebcontent.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/phieuxuat")
public class PhieuXuatController {

    @GetMapping("")
    public String danhSach(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            model.addAttribute("danhSachPhieu", new PhieuXuatDB().getAll());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));
        } catch (Exception e) {
            System.out.println("PhieuXuatController - danhSach error: " + e);
        }
        return "phieuxuat/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            model.addAttribute("danhSachHang", new HangHoaDB().getAll());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
        } catch (Exception e) {
            System.out.println("PhieuXuatController - addForm error: " + e);
        }
        return "phieuxuat/form";
    }

    @PostMapping("/save")
    public String save(@RequestParam String maPhieuXuat,
                       @RequestParam String lyDoXuat,
                       @RequestParam String ghiChu,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            PhieuXuat p = new PhieuXuat(
                maPhieuXuat,
                SessionHelper.getMaNV(session),
                LocalDate.now().toString(),
                lyDoXuat,
                ghiChu
            );
            new PhieuXuatDB().insert(p);
            ghiLichSu(session, "THEM", maPhieuXuat, null, "DRAFT");
            redirectAttributes.addFlashAttribute("success", "Tạo phiếu xuất thành công: " + maPhieuXuat);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieuxuat";
    }

    @GetMapping("/submit/{maPhieu}")
    public String submit(@PathVariable String maPhieu,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            new PhieuXuatDB().updateTrangThai(maPhieu, "PENDING", null, null);
            ghiLichSu(session, "SUA", maPhieu, "DRAFT", "PENDING");
            redirectAttributes.addFlashAttribute("success", "Đã gửi phiếu chờ duyệt: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieuxuat";
    }

   
    @GetMapping("/approve/{maPhieu}")
    public String approve(@PathVariable String maPhieu,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            PhieuXuatDB db = new PhieuXuatDB();
            PhieuXuat p = db.getById(maPhieu);

            if (p == null || !p.coTheDuyet()) {
                redirectAttributes.addFlashAttribute("error", "Phiếu không ở trạng thái PENDING!");
                return "redirect:/phieuxuat";
            }

      
            AlertChecker checker = new AlertChecker();
            HangHoaDB hangHoaDB = new HangHoaDB();
            ChiTietPhieuDB ctDB = new ChiTietPhieuDB();

            for (ChiTietPhieu ct : ctDB.getByMaPhieu(maPhieu)) {
                HangHoa h = hangHoaDB.getById(ct.maHang);
                if (h != null) {
                    int soLuongMoi = h.soLuongTon - ct.soLuong;
                    hangHoaDB.updateSoLuong(ct.maHang, Math.max(soLuongMoi, 0));

                    checker.checkXuatBatThuong(ct.maHang, ct.soLuong, SessionHelper.getMaNV(session));
                }
            }

            db.updateTrangThai(maPhieu, "DONE", SessionHelper.getMaNV(session), LocalDate.now().toString());

       
            checker.runAllChecks();

            ghiLichSu(session, "DUYET", maPhieu, "PENDING", "DONE");
            redirectAttributes.addFlashAttribute("success", "Đã duyệt và trừ kho: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieuxuat";
    }

    @GetMapping("/reject/{maPhieu}")
    public String reject(@PathVariable String maPhieu,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            new PhieuXuatDB().updateTrangThai(maPhieu, "REJECTED", SessionHelper.getMaNV(session), LocalDate.now().toString());
            ghiLichSu(session, "TU_CHOI", maPhieu, "PENDING", "REJECTED");
            redirectAttributes.addFlashAttribute("success", "Đã từ chối phiếu: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieuxuat";
    }

    @GetMapping("/delete/{maPhieu}")
    public String delete(@PathVariable String maPhieu,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            PhieuXuatDB db = new PhieuXuatDB();
            PhieuXuat p = db.getById(maPhieu);
            if (p != null && !"DRAFT".equals(p.trangThai)) {
                redirectAttributes.addFlashAttribute("error", "Chỉ được xóa phiếu ở trạng thái DRAFT!");
                return "redirect:/phieuxuat";
            }
            new ChiTietPhieuDB().deleteByMaPhieu(maPhieu);
            db.delete(maPhieu);
            ghiLichSu(session, "XOA", maPhieu, "DRAFT", null);
            redirectAttributes.addFlashAttribute("success", "Đã xóa phiếu: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieuxuat";
    }

    private void ghiLichSu(HttpSession session, String loai, String maDT, String cu, String moi) {
        try {
            LichSuThaoTac ls = new LichSuThaoTac();
            ls.maLichSu = "LS" + System.currentTimeMillis();
            ls.maNV = SessionHelper.getMaNV(session);
            ls.loaiThaoTac = loai;
            ls.doiTuong = "PHIEU_XUAT";
            ls.maDoiTuong = maDT;
            ls.giaTriCu = cu;
            ls.giaTriMoi = moi;
            ls.thoiGian = LocalDateTime.now().toString();
            new LichSuThaoTacDB().insert(ls);
        } catch (Exception e) {
            System.out.println("PhieuXuatController - ghiLichSu error: " + e);
        }
    }
}