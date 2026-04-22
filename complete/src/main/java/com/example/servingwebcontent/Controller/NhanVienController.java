package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.LichSuThaoTacDB;
import com.example.servingwebcontent.Database.NhanVienDB;
import com.example.servingwebcontent.Model.LichSuThaoTac;
import com.example.servingwebcontent.Model.NhanVien;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @GetMapping("")
    public String danhSach(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            model.addAttribute("danhSachNV", new NhanVienDB().getAll());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));
        } catch (Exception e) {
            System.out.println("NhanVienController - danhSach error: " + e);
        }
        return "nhanvien/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        if (!SessionHelper.isAdmin(session)) return "redirect:/dashboard";
        model.addAttribute("currentUser", SessionHelper.getUser(session));
        return "nhanvien/form";
    }

    @PostMapping("/save")
    public String save(@RequestParam String maNV,
                       @RequestParam String hoTen,
                       @RequestParam String email,
                       @RequestParam String soDienThoai,
                       @RequestParam String vaiTro,
                       @RequestParam String matKhau,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isAdmin(session)) return "redirect:/dashboard";
        try {
            NhanVien nv = new NhanVien(maNV, hoTen, email, soDienThoai, vaiTro, matKhau);
            new NhanVienDB().insert(nv);
            ghiLichSu(session, "THEM", maNV, null, hoTen + " | " + vaiTro);
            redirectAttributes.addFlashAttribute("success", "Thêm nhân viên thành công: " + hoTen);
        } catch (Exception e) {
            System.out.println("NhanVienController - save error: " + e);
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhanvien";
    }

    @GetMapping("/edit/{maNV}")
    public String editForm(@PathVariable String maNV,
                           HttpSession session, Model model) {
        if (!SessionHelper.isAdmin(session)) return "redirect:/dashboard";
        try {
            model.addAttribute("nhanVien", new NhanVienDB().getById(maNV));
            model.addAttribute("currentUser", SessionHelper.getUser(session));
        } catch (Exception e) {
            System.out.println("NhanVienController - editForm error: " + e);
        }
        return "nhanvien/form";
    }

    @PostMapping("/update")
    public String update(@RequestParam String maNV,
                         @RequestParam String hoTen,
                         @RequestParam String email,
                         @RequestParam String soDienThoai,
                         @RequestParam String vaiTro,
                         @RequestParam String matKhau,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isAdmin(session)) return "redirect:/dashboard";
        try {
            NhanVienDB db = new NhanVienDB();
            NhanVien cu = db.getById(maNV);
            NhanVien nv = new NhanVien(maNV, hoTen, email, soDienThoai, vaiTro, matKhau);
            db.update(nv);
            ghiLichSu(session, "SUA", maNV,
                    cu != null ? cu.hoTen + " | " + cu.vaiTro : "",
                    hoTen + " | " + vaiTro);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công: " + hoTen);
        } catch (Exception e) {
            System.out.println("NhanVienController - update error: " + e);
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhanvien";
    }

    @GetMapping("/delete/{maNV}")
    public String delete(@PathVariable String maNV,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isAdmin(session)) return "redirect:/dashboard";
        try {
            NhanVienDB db = new NhanVienDB();
            NhanVien nv = db.getById(maNV);
            db.delete(maNV);
            ghiLichSu(session, "XOA", maNV, nv != null ? nv.hoTen : "", null);
            redirectAttributes.addFlashAttribute("success", "Đã xóa nhân viên: " + maNV);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhanvien";
    }

    private void ghiLichSu(HttpSession session, String loai, String maDT, String cu, String moi) {
        try {
            LichSuThaoTac ls = new LichSuThaoTac();
            ls.maLichSu = "LS" + System.currentTimeMillis();
            ls.maNV = SessionHelper.getMaNV(session);
            ls.loaiThaoTac = loai;
            ls.doiTuong = "NHAN_VIEN";
            ls.maDoiTuong = maDT;
            ls.giaTriCu = cu;
            ls.giaTriMoi = moi;
            ls.thoiGian = LocalDateTime.now().toString();
            new LichSuThaoTacDB().insert(ls);
        } catch (Exception e) {
            System.out.println("NhanVienController - ghiLichSu error: " + e);
        }
    }
}