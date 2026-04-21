package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.PhieuNhap;

public class PhieuNhapDB {

    public ArrayList<PhieuNhap> getAll() {
        ArrayList<PhieuNhap> list = new ArrayList<PhieuNhap>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM PhieuNhap ORDER BY ngayTao DESC");
            while (rs.next()) {
                PhieuNhap p = new PhieuNhap();
                p.maPhieuNhap = rs.getString("maPhieuNhap");
                p.maNV = rs.getString("maNV");
                p.maNCC = rs.getString("maNCC");
                p.ngayTao = rs.getString("ngayTao");
                p.ngayDuyet = rs.getString("ngayDuyet");
                p.trangThai = rs.getString("trangThai");
                p.ghiChu = rs.getString("ghiChu");
                p.tongGiaTri = rs.getDouble("tongGiaTri");
                list.add(p);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("PhieuNhapDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PhieuNhap> getByTrangThai(String trangThai) {
        ArrayList<PhieuNhap> list = new ArrayList<PhieuNhap>();
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM PhieuNhap WHERE trangThai = ? ORDER BY ngayTao DESC"
            );
            ps.setString(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuNhap p = new PhieuNhap();
                p.maPhieuNhap = rs.getString("maPhieuNhap");
                p.maNV = rs.getString("maNV");
                p.maNCC = rs.getString("maNCC");
                p.ngayTao = rs.getString("ngayTao");
                p.ngayDuyet = rs.getString("ngayDuyet");
                p.trangThai = rs.getString("trangThai");
                p.ghiChu = rs.getString("ghiChu");
                p.tongGiaTri = rs.getDouble("tongGiaTri");
                list.add(p);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("PhieuNhapDB - getByTrangThai error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public PhieuNhap getById(String maPhieuNhap) {
        PhieuNhap p = null;
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PhieuNhap WHERE maPhieuNhap = ?");
            ps.setString(1, maPhieuNhap);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new PhieuNhap();
                p.maPhieuNhap = rs.getString("maPhieuNhap");
                p.maNV = rs.getString("maNV");
                p.maNCC = rs.getString("maNCC");
                p.ngayTao = rs.getString("ngayTao");
                p.ngayDuyet = rs.getString("ngayDuyet");
                p.trangThai = rs.getString("trangThai");
                p.ghiChu = rs.getString("ghiChu");
                p.tongGiaTri = rs.getDouble("tongGiaTri");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("PhieuNhapDB - getById error: " + e);
            e.printStackTrace();
        }
        return p;
    }

    public void insert(PhieuNhap p) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO PhieuNhap (maPhieuNhap, maNV, maNCC, ngayTao, trangThai, ghiChu, tongGiaTri) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, p.maPhieuNhap);
            ps.setString(2, p.maNV);
            ps.setString(3, p.maNCC);
            ps.setString(4, p.ngayTao);
            ps.setString(5, p.trangThai);
            ps.setString(6, p.ghiChu);
            ps.setDouble(7, p.tongGiaTri);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("PhieuNhapDB - insert success: " + p.maPhieuNhap);
        } catch (Exception e) {
            System.out.println("PhieuNhapDB - insert error: " + e);
            e.printStackTrace();
        }
    }

    public void updateTrangThai(String maPhieuNhap, String trangThaiMoi, String ngayDuyet) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE PhieuNhap SET trangThai=?, ngayDuyet=? WHERE maPhieuNhap=?"
            );
            ps.setString(1, trangThaiMoi);
            ps.setString(2, ngayDuyet);
            ps.setString(3, maPhieuNhap);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("PhieuNhapDB - updateTrangThai: " + maPhieuNhap + " -> " + trangThaiMoi);
        } catch (Exception e) {
            System.out.println("PhieuNhapDB - updateTrangThai error: " + e);
            e.printStackTrace();
        }
    }

    public void update(PhieuNhap p) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE PhieuNhap SET maNV=?, maNCC=?, trangThai=?, ngayDuyet=?, ghiChu=?, tongGiaTri=? " +
                "WHERE maPhieuNhap=?"
            );
            ps.setString(1, p.maNV);
            ps.setString(2, p.maNCC);
            ps.setString(3, p.trangThai);
            ps.setString(4, p.ngayDuyet);
            ps.setString(5, p.ghiChu);
            ps.setDouble(6, p.tongGiaTri);
            ps.setString(7, p.maPhieuNhap);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("PhieuNhapDB - update success: " + p.maPhieuNhap);
        } catch (Exception e) {
            System.out.println("PhieuNhapDB - update error: " + e);
            e.printStackTrace();
        }
    }

    public void delete(String maPhieuNhap) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM PhieuNhap WHERE maPhieuNhap = ?");
            ps.setString(1, maPhieuNhap);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("PhieuNhapDB - delete success: " + maPhieuNhap);
        } catch (Exception e) {
            System.out.println("PhieuNhapDB - delete error: " + e);
            e.printStackTrace();
        }
    }
}