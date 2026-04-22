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
@RequestMapping("/phieunhap")
public class PhieuNhapController {

    @GetMapping("")
    public String danhSach(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            model.addAttribute("danhSachPhieu", new PhieuNhapDB().getAll());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));
        } catch (Exception e) {
            System.out.println("PhieuNhapController - danhSach error: " + e);
        }
        return "phieunhap/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            model.addAttribute("danhSachNCC", new NhaCungCapDB().getAll());
            model.addAttribute("danhSachHang", new HangHoaDB().getAll());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
        } catch (Exception e) {
            System.out.println("PhieuNhapController - addForm error: " + e);
        }
        return "phieunhap/form";
    }

    @PostMapping("/save")
    public String save(@RequestParam String maPhieuNhap,
                       @RequestParam String maNCC,
                       @RequestParam String ghiChu,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            PhieuNhap p = new PhieuNhap(
                maPhieuNhap,
                SessionHelper.getMaNV(session), 
                maNCC,
                LocalDate.now().toString(),
                ghiChu
            );
            new PhieuNhapDB().insert(p);
            ghiLichSu(session, "THEM", maPhieuNhap, null, "DRAFT");
            redirectAttributes.addFlashAttribute("success", "Tạo phiếu nhập thành công: " + maPhieuNhap);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieunhap";
    }

    @GetMapping("/submit/{maPhieu}")
    public String submit(@PathVariable String maPhieu,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            new PhieuNhapDB().updateTrangThai(maPhieu, "PENDING", null);
            ghiLichSu(session, "SUA", maPhieu, "DRAFT", "PENDING");
            redirectAttributes.addFlashAttribute("success", "Đã gửi phiếu chờ duyệt: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieunhap";
    }


    @GetMapping("/approve/{maPhieu}")
    public String approve(@PathVariable String maPhieu,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            PhieuNhapDB db = new PhieuNhapDB();
            PhieuNhap p = db.getById(maPhieu);

            if (p == null || !p.coTheDuyet()) {
                redirectAttributes.addFlashAttribute("error", "Phiếu không ở trạng thái PENDING!");
                return "redirect:/phieunhap";
            }

            db.updateTrangThai(maPhieu, "APPROVED", LocalDate.now().toString());

            HangHoaDB hangHoaDB = new HangHoaDB();
            ChiTietPhieuDB ctDB = new ChiTietPhieuDB();
            for (ChiTietPhieu ct : ctDB.getByMaPhieu(maPhieu)) {
                HangHoa h = hangHoaDB.getById(ct.maHang);
                if (h != null) {
                    hangHoaDB.updateSoLuong(ct.maHang, h.soLuongTon + ct.soLuong);
                }
            }

            db.updateTrangThai(maPhieu, "DONE", LocalDate.now().toString());
            new AlertChecker().runAllChecks();

            ghiLichSu(session, "DUYET", maPhieu, "PENDING", "DONE");
            redirectAttributes.addFlashAttribute("success", "Đã duyệt và cập nhật kho: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieunhap";
    }

    @GetMapping("/reject/{maPhieu}")
    public String reject(@PathVariable String maPhieu,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            new PhieuNhapDB().updateTrangThai(maPhieu, "REJECTED", LocalDate.now().toString());
            ghiLichSu(session, "TU_CHOI", maPhieu, "PENDING", "REJECTED");
            redirectAttributes.addFlashAttribute("success", "Đã từ chối phiếu: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieunhap";
    }

    @GetMapping("/delete/{maPhieu}")
    public String delete(@PathVariable String maPhieu,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            PhieuNhapDB db = new PhieuNhapDB();
            PhieuNhap p = db.getById(maPhieu);
            if (p != null && !"DRAFT".equals(p.trangThai)) {
                redirectAttributes.addFlashAttribute("error", "Chỉ được xóa phiếu ở trạng thái DRAFT!");
                return "redirect:/phieunhap";
            }
            new ChiTietPhieuDB().deleteByMaPhieu(maPhieu);
            db.delete(maPhieu);
            ghiLichSu(session, "XOA", maPhieu, "DRAFT", null);
            redirectAttributes.addFlashAttribute("success", "Đã xóa phiếu: " + maPhieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/phieunhap";
    }

    private void ghiLichSu(HttpSession session, String loai, String maDT, String cu, String moi) {
        try {
            LichSuThaoTac ls = new LichSuThaoTac();
            ls.maLichSu = "LS" + System.currentTimeMillis();
            ls.maNV = SessionHelper.getMaNV(session);
            ls.loaiThaoTac = loai;
            ls.doiTuong = "PHIEU_NHAP";
            ls.maDoiTuong = maDT;
            ls.giaTriCu = cu;
            ls.giaTriMoi = moi;
            ls.thoiGian = LocalDateTime.now().toString();
            new LichSuThaoTacDB().insert(ls);
        } catch (Exception e) {
            System.out.println("PhieuNhapController - ghiLichSu error: " + e);
        }
    }
}