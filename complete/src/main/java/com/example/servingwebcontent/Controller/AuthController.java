package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.Component.SessionHelper;
import com.example.servingwebcontent.Database.NhanVienDB;
import com.example.servingwebcontent.Model.NhanVien;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

   
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // Nếu đã đăng nhập thì chuyển thẳng vào dashboard
        if (SessionHelper.isLoggedIn(session)) {
            return "redirect:/dashboard";
        }
        return "login";
    }

  
    @PostMapping("/login")
    public String doLogin(@RequestParam String maNV,
                          @RequestParam String matKhau,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            System.out.println("=== LOGIN: " + maNV + " ===");
            NhanVienDB db = new NhanVienDB();
            NhanVien nv = db.login(maNV, matKhau);

            if (nv != null) {
                SessionHelper.saveUser(session, nv);
                return "redirect:/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("error", "Sai mã nhân viên hoặc mật khẩu!");
                return "redirect:/login";
            }
        } catch (Exception e) {
            System.out.println("AuthController - login error: " + e);
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            return "redirect:/login";
        }
    }

  
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionHelper.logout(session);
        return "redirect:/login";
    }
}