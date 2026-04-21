package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.NhanVien;

public class NhanVienDB {
    public ArrayList<NhanVien> getAll() {
        ArrayList<NhanVien> list = new ArrayList<NhanVien>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM NhanVien");
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.maNV = rs.getString("maNV");
                nv.hoTen = rs.getString("hoTen");
                nv.email = rs.getString("email");
                nv.soDienThoai = rs.getString("soDienThoai");
                nv.vaiTro = rs.getString("vaiTro");
                nv.matKhau = rs.getString("matKhau");
                list.add(nv);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("NhanVienDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public NhanVien getById(String maNV) {
        NhanVien nv = null;
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM NhanVien WHERE maNV = ?");
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nv = new NhanVien();
                nv.maNV = rs.getString("maNV");
                nv.hoTen = rs.getString("hoTen");
                nv.email = rs.getString("email");
                nv.soDienThoai = rs.getString("soDienThoai");
                nv.vaiTro = rs.getString("vaiTro");
                nv.matKhau = rs.getString("matKhau");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("NhanVienDB - getById error: " + e);
            e.printStackTrace();
        }
        return nv;
    }

    public NhanVien login(String maNV, String matKhau) {
        NhanVien nv = null;
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM NhanVien WHERE maNV = ? AND matKhau = ?"
            );
            ps.setString(1, maNV);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nv = new NhanVien();
                nv.maNV = rs.getString("maNV");
                nv.hoTen = rs.getString("hoTen");
                nv.email = rs.getString("email");
                nv.soDienThoai = rs.getString("soDienThoai");
                nv.vaiTro = rs.getString("vaiTro");
                nv.matKhau = rs.getString("matKhau");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("NhanVienDB - login error: " + e);
            e.printStackTrace();
        }
        return nv; 
    }

    public void insert(NhanVien nv) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO NhanVien (maNV, hoTen, email, soDienThoai, vaiTro, matKhau) " +
                "VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, nv.maNV);
            ps.setString(2, nv.hoTen);
            ps.setString(3, nv.email);
            ps.setString(4, nv.soDienThoai);
            ps.setString(5, nv.vaiTro);
            ps.setString(6, nv.matKhau);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("NhanVienDB - insert success: " + nv.maNV);
        } catch (Exception e) {
            System.out.println("NhanVienDB - insert error: " + e);
            e.printStackTrace();
        }
    }

    public void update(NhanVien nv) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE NhanVien SET hoTen=?, email=?, soDienThoai=?, vaiTro=?, matKhau=? WHERE maNV=?"
            );
            ps.setString(1, nv.hoTen);
            ps.setString(2, nv.email);
            ps.setString(3, nv.soDienThoai);
            ps.setString(4, nv.vaiTro);
            ps.setString(5, nv.matKhau);
            ps.setString(6, nv.maNV);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("NhanVienDB - update success: " + nv.maNV);
        } catch (Exception e) {
            System.out.println("NhanVienDB - update error: " + e);
            e.printStackTrace();
        }
    }

    public void delete(String maNV) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM NhanVien WHERE maNV = ?");
            ps.setString(1, maNV);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("NhanVienDB - delete success: " + maNV);
        } catch (Exception e) {
            System.out.println("NhanVienDB - delete error: " + e);
            e.printStackTrace();
        }
    }
}