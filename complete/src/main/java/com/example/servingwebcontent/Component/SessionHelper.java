package com.example.servingwebcontent.Component;

import com.example.servingwebcontent.Model.NhanVien;
import javax.servlet.http.HttpSession;

public class SessionHelper {

    private static final String SESSION_KEY = "loggedInUser";

    public static void saveUser(HttpSession session, NhanVien nv) {
        session.setAttribute(SESSION_KEY, nv);
        System.out.println("SessionHelper - login: " + nv.maNV + " | vai trò: " + nv.vaiTro);
    }

    public static NhanVien getUser(HttpSession session) {
        return (NhanVien) session.getAttribute(SESSION_KEY);
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getUser(session) != null;
    }

    public static void logout(HttpSession session) {
        session.removeAttribute(SESSION_KEY);
        System.out.println("SessionHelper - logout thành công");
    }

    public static boolean isAdmin(HttpSession session) {
        NhanVien nv = getUser(session);
        return nv != null && nv.isAdmin();
    }

    // Kiểm tra có phải quản lý trở lên không (quản lý + admin)
    public static boolean isQuanLy(HttpSession session) {
        NhanVien nv = getUser(session);
        return nv != null && nv.isQuanLy();
    }

    public static String getMaNV(HttpSession session) {
        NhanVien nv = getUser(session);
        return nv != null ? nv.maNV : "UNKNOWN";
    }

    public static String getVaiTro(HttpSession session) {
        NhanVien nv = getUser(session);
        return nv != null ? nv.vaiTro : "";
    }
}