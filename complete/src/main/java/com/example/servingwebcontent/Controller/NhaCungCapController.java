package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.LichSuThaoTacDB;
import com.example.servingwebcontent.Database.NhaCungCapDB;
import com.example.servingwebcontent.Model.LichSuThaoTac;
import com.example.servingwebcontent.Model.NhaCungCap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/nhacungcap")
public class NhaCungCapController {

    @GetMapping("")
    public String danhSach(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            model.addAttribute("danhSachNCC", new NhaCungCapDB().getAll());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));
        } catch (Exception e) {
            System.out.println("NhaCungCapController - danhSach error: " + e);
        }
        return "nhacungcap/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("currentUser", SessionHelper.getUser(session));
        return "nhacungcap/form";
    }

    @PostMapping("/save")
    public String save(@RequestParam String maNCC,
                       @RequestParam String tenNCC,
                       @RequestParam String diaChi,
                       @RequestParam String soDienThoai,
                       @RequestParam String email,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, soDienThoai, email);
            new NhaCungCapDB().insert(ncc);
            ghiLichSu(session, "THEM", maNCC, null, tenNCC);
            redirectAttributes.addFlashAttribute("success", "Thêm nhà cung cấp thành công: " + tenNCC);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhacungcap";
    }

    @GetMapping("/edit/{maNCC}")
    public String editForm(@PathVariable String maNCC,
                           HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            model.addAttribute("nhaCungCap", new NhaCungCapDB().getById(maNCC));
            model.addAttribute("currentUser", SessionHelper.getUser(session));
        } catch (Exception e) {
            System.out.println("NhaCungCapController - editForm error: " + e);
        }
        return "nhacungcap/form";
    }

    @PostMapping("/update")
    public String update(@RequestParam String maNCC,
                         @RequestParam String tenNCC,
                         @RequestParam String diaChi,
                         @RequestParam String soDienThoai,
                         @RequestParam String email,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            NhaCungCapDB db = new NhaCungCapDB();
            NhaCungCap cu = db.getById(maNCC);
            db.update(new NhaCungCap(maNCC, tenNCC, diaChi, soDienThoai, email));
            ghiLichSu(session, "SUA", maNCC, cu != null ? cu.tenNCC : "", tenNCC);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công: " + tenNCC);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhacungcap";
    }

    @GetMapping("/delete/{maNCC}")
    public String delete(@PathVariable String maNCC,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            NhaCungCapDB db = new NhaCungCapDB();
            NhaCungCap ncc = db.getById(maNCC);
            db.delete(maNCC);
            ghiLichSu(session, "XOA", maNCC, ncc != null ? ncc.tenNCC : "", null);
            redirectAttributes.addFlashAttribute("success", "Đã xóa nhà cung cấp: " + maNCC);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhacungcap";
    }

    private void ghiLichSu(HttpSession session, String loai, String maDT, String cu, String moi) {
        try {
            LichSuThaoTac ls = new LichSuThaoTac();
            ls.maLichSu = "LS" + System.currentTimeMillis();
            ls.maNV = SessionHelper.getMaNV(session);
            ls.loaiThaoTac = loai;
            ls.doiTuong = "NHA_CUNG_CAP";
            ls.maDoiTuong = maDT;
            ls.giaTriCu = cu;
            ls.giaTriMoi = moi;
            ls.thoiGian = LocalDateTime.now().toString();
            new LichSuThaoTacDB().insert(ls);
        } catch (Exception e) {
            System.out.println("NhaCungCapController - ghiLichSu error: " + e);
        }
    }
}