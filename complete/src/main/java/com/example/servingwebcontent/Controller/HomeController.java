package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.AlertChecker;
import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root(HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";

        try {
            System.out.println("=== LOADING DASHBOARD ===");
            AlertChecker checker = new AlertChecker();
            checker.runAllChecks();
            HangHoaDB hangHoaDB = new HangHoaDB();
            NhanVienDB nhanVienDB = new NhanVienDB();
            NhaCungCapDB nhaCungCapDB = new NhaCungCapDB();
            PhieuNhapDB phieuNhapDB = new PhieuNhapDB();
            PhieuXuatDB phieuXuatDB = new PhieuXuatDB();
            CanhBaoDB canhBaoDB = new CanhBaoDB();

            model.addAttribute("tongHangHoa", hangHoaDB.getAll().size());
            model.addAttribute("tongNhanVien", nhanVienDB.getAll().size());
            model.addAttribute("tongNhaCungCap", nhaCungCapDB.getAll().size());
            model.addAttribute("phieuNhapChoDuyet", phieuNhapDB.getByTrangThai("PENDING").size());
            model.addAttribute("phieuXuatChoDuyet", phieuXuatDB.getByTrangThai("PENDING").size());
            model.addAttribute("tongCanhBao", canhBaoDB.countChuaXuLy());
            model.addAttribute("hangTonKhoThap", hangHoaDB.getTonKhoThap());
            model.addAttribute("canhBaoChuaXuLy", canhBaoDB.getChuaXuLy());

            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));

        } catch (Exception e) {
            System.out.println("HomeController - dashboard error: " + e);
            e.printStackTrace();
        }

        return "dashboard";
    }
}