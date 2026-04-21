package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.ChiTietPhieu;

public class ChiTietPhieuDB {

    public ArrayList<ChiTietPhieu> getByMaPhieu(String maPhieu) {
        ArrayList<ChiTietPhieu> list = new ArrayList<ChiTietPhieu>();
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM ChiTietPhieu WHERE maPhieu = ?"
            );
            ps.setString(1, maPhieu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietPhieu ct = new ChiTietPhieu();
                ct.maChiTiet = rs.getString("maChiTiet");
                ct.maPhieu = rs.getString("maPhieu");
                ct.loaiPhieu = rs.getString("loaiPhieu");
                ct.maHang = rs.getString("maHang");
                ct.soLuong = rs.getInt("soLuong");
                ct.donGia = rs.getDouble("donGia");
                ct.thanhTien = rs.getDouble("thanhTien");
                list.add(ct);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("ChiTietPhieuDB - getByMaPhieu error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public void insert(ChiTietPhieu ct) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO ChiTietPhieu (maChiTiet, maPhieu, loaiPhieu, maHang, soLuong, donGia, thanhTien) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, ct.maChiTiet);
            ps.setString(2, ct.maPhieu);
            ps.setString(3, ct.loaiPhieu);
            ps.setString(4, ct.maHang);
            ps.setInt(5, ct.soLuong);
            ps.setDouble(6, ct.donGia);
            ps.setDouble(7, ct.thanhTien);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("ChiTietPhieuDB - insert success: " + ct.maChiTiet);
        } catch (Exception e) {
            System.out.println("ChiTietPhieuDB - insert error: " + e);
            e.printStackTrace();
        }
    }

    public void deleteByMaPhieu(String maPhieu) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ChiTietPhieu WHERE maPhieu = ?");
            ps.setString(1, maPhieu);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("ChiTietPhieuDB - deleteByMaPhieu success: " + maPhieu);
        } catch (Exception e) {
            System.out.println("ChiTietPhieuDB - deleteByMaPhieu error: " + e);
            e.printStackTrace();
        }
    }

    public void delete(String maChiTiet) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ChiTietPhieu WHERE maChiTiet = ?");
            ps.setString(1, maChiTiet);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("ChiTietPhieuDB - delete success: " + maChiTiet);
        } catch (Exception e) {
            System.out.println("ChiTietPhieuDB - delete error: " + e);
            e.printStackTrace();
        }
    }
}