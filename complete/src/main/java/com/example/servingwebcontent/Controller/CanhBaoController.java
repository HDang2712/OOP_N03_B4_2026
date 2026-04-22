package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.AlertChecker;
import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.CanhBaoDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/canhbao")
public class CanhBaoController {

    @GetMapping("")
    public String danhSach(HttpSession session, Model model) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            model.addAttribute("danhSachCanhBao", new CanhBaoDB().getAll());
            model.addAttribute("soChuaXuLy", new CanhBaoDB().countChuaXuLy());
            model.addAttribute("currentUser", SessionHelper.getUser(session));
            model.addAttribute("vaiTro", SessionHelper.getVaiTro(session));
        } catch (Exception e) {
            System.out.println("CanhBaoController - danhSach error: " + e);
        }
        return "canhbao/list";
    }

    @GetMapping("/xuly/{maCanhBao}")
    public String xuLy(@PathVariable String maCanhBao,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            new CanhBaoDB().markDaXuLy(maCanhBao, SessionHelper.getMaNV(session));
            redirectAttributes.addFlashAttribute("success", "Đã đánh dấu xử lý cảnh báo: " + maCanhBao);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/canhbao";
    }

    @GetMapping("/check")
    public String runCheck(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!SessionHelper.isLoggedIn(session)) return "redirect:/login";
        try {
            new AlertChecker().runAllChecks();
            redirectAttributes.addFlashAttribute("success", "Đã chạy kiểm tra hệ thống!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/canhbao";
    }
}