package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.LichSuThaoTac;

public class LichSuThaoTacDB {

    public ArrayList<LichSuThaoTac> getAll() {
        ArrayList<LichSuThaoTac> list = new ArrayList<LichSuThaoTac>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM LichSuThaoTac ORDER BY thoiGian DESC");
            while (rs.next()) {
                LichSuThaoTac ls = new LichSuThaoTac();
                ls.maLichSu = rs.getString("maLichSu");
                ls.maNV = rs.getString("maNV");
                ls.loaiThaoTac = rs.getString("loaiThaoTac");
                ls.doiTuong = rs.getString("doiTuong");
                ls.maDoiTuong = rs.getString("maDoiTuong");
                ls.giaTriCu = rs.getString("giaTriCu");
                ls.giaTriMoi = rs.getString("giaTriMoi");
                ls.thoiGian = rs.getString("thoiGian");
                ls.ghiChu = rs.getString("ghiChu");
                list.add(ls);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("LichSuThaoTacDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<LichSuThaoTac> getByMaNV(String maNV) {
        ArrayList<LichSuThaoTac> list = new ArrayList<LichSuThaoTac>();
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM LichSuThaoTac WHERE maNV = ? ORDER BY thoiGian DESC"
            );
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LichSuThaoTac ls = new LichSuThaoTac();
                ls.maLichSu = rs.getString("maLichSu");
                ls.maNV = rs.getString("maNV");
                ls.loaiThaoTac = rs.getString("loaiThaoTac");
                ls.doiTuong = rs.getString("doiTuong");
                ls.maDoiTuong = rs.getString("maDoiTuong");
                ls.giaTriCu = rs.getString("giaTriCu");
                ls.giaTriMoi = rs.getString("giaTriMoi");
                ls.thoiGian = rs.getString("thoiGian");
                ls.ghiChu = rs.getString("ghiChu");
                list.add(ls);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("LichSuThaoTacDB - getByMaNV error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<LichSuThaoTac> getByDoiTuong(String doiTuong, String maDoiTuong) {
        ArrayList<LichSuThaoTac> list = new ArrayList<LichSuThaoTac>();
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM LichSuThaoTac WHERE doiTuong = ? AND maDoiTuong = ? ORDER BY thoiGian DESC"
            );
            ps.setString(1, doiTuong);
            ps.setString(2, maDoiTuong);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LichSuThaoTac ls = new LichSuThaoTac();
                ls.maLichSu = rs.getString("maLichSu");
                ls.maNV = rs.getString("maNV");
                ls.loaiThaoTac = rs.getString("loaiThaoTac");
                ls.doiTuong = rs.getString("doiTuong");
                ls.maDoiTuong = rs.getString("maDoiTuong");
                ls.giaTriCu = rs.getString("giaTriCu");
                ls.giaTriMoi = rs.getString("giaTriMoi");
                ls.thoiGian = rs.getString("thoiGian");
                ls.ghiChu = rs.getString("ghiChu");
                list.add(ls);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("LichSuThaoTacDB - getByDoiTuong error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public void insert(LichSuThaoTac ls) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO LichSuThaoTac (maLichSu, maNV, loaiThaoTac, doiTuong, maDoiTuong, giaTriCu, giaTriMoi, thoiGian, ghiChu) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, ls.maLichSu);
            ps.setString(2, ls.maNV);
            ps.setString(3, ls.loaiThaoTac);
            ps.setString(4, ls.doiTuong);
            ps.setString(5, ls.maDoiTuong);
            ps.setString(6, ls.giaTriCu);
            ps.setString(7, ls.giaTriMoi);
            ps.setString(8, ls.thoiGian);
            ps.setString(9, ls.ghiChu);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("LichSuThaoTacDB - insert success: " + ls.maLichSu);
        } catch (Exception e) {
            System.out.println("LichSuThaoTacDB - insert error: " + e);
            e.printStackTrace();
        }
    }
}