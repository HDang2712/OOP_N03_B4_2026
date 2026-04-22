package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.LichSuThaoTacDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/lichsu")
public class LichSuController {

    @GetMapping("")
    public String danhSach(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            model.addAttribute("danhSachLichSu", new LichSuThaoTacDB().getAll());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));
        } catch (Exception e) {
            System.out.println("LichSuController - danhSach error: " + e);
        }
        return "lichsu/list";
    }

    @GetMapping("/nhanvien/{maNV}")
    public String byNhanVien(@PathVariable String maNV,
                              HttpSession session, Model model) {
        if (!SessionHelper.isQuanLy(session)) return "redirect:/dashboard";
        try {
            model.addAttribute("danhSachLichSu", new LichSuThaoTacDB().getByMaNV(maNV));
            model.addAttribute("maNV", maNV);
            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));
        } catch (Exception e) {
            System.out.println("LichSuController - byNhanVien error: " + e);
        }
        return "lichsu/list";
    }
}