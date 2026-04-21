package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.PhieuXuat;

public class PhieuXuatDB {

    public ArrayList<PhieuXuat> getAll() {
        ArrayList<PhieuXuat> list = new ArrayList<PhieuXuat>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM PhieuXuat ORDER BY ngayTao DESC");
            while (rs.next()) {
                PhieuXuat p = new PhieuXuat();
                p.maPhieuXuat = rs.getString("maPhieuXuat");
                p.maNV = rs.getString("maNV");
                p.maNVDuyet = rs.getString("maNVDuyet");
                p.ngayTao = rs.getString("ngayTao");
                p.ngayDuyet = rs.getString("ngayDuyet");
                p.trangThai = rs.getString("trangThai");
                p.lyDoXuat = rs.getString("lyDoXuat");
                p.ghiChu = rs.getString("ghiChu");
                p.tongGiaTri = rs.getDouble("tongGiaTri");
                list.add(p);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("PhieuXuatDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PhieuXuat> getByTrangThai(String trangThai) {
        ArrayList<PhieuXuat> list = new ArrayList<PhieuXuat>();
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM PhieuXuat WHERE trangThai = ? ORDER BY ngayTao DESC"
            );
            ps.setString(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuXuat p = new PhieuXuat();
                p.maPhieuXuat = rs.getString("maPhieuXuat");
                p.maNV = rs.getString("maNV");
                p.maNVDuyet = rs.getString("maNVDuyet");
                p.ngayTao = rs.getString("ngayTao");
                p.ngayDuyet = rs.getString("ngayDuyet");
                p.trangThai = rs.getString("trangThai");
                p.lyDoXuat = rs.getString("lyDoXuat");
                p.ghiChu = rs.getString("ghiChu");
                p.tongGiaTri = rs.getDouble("tongGiaTri");
                list.add(p);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("PhieuXuatDB - getByTrangThai error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public PhieuXuat getById(String maPhieuXuat) {
        PhieuXuat p = null;
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PhieuXuat WHERE maPhieuXuat = ?");
            ps.setString(1, maPhieuXuat);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new PhieuXuat();
                p.maPhieuXuat = rs.getString("maPhieuXuat");
                p.maNV = rs.getString("maNV");
                p.maNVDuyet = rs.getString("maNVDuyet");
                p.ngayTao = rs.getString("ngayTao");
                p.ngayDuyet = rs.getString("ngayDuyet");
                p.trangThai = rs.getString("trangThai");
                p.lyDoXuat = rs.getString("lyDoXuat");
                p.ghiChu = rs.getString("ghiChu");
                p.tongGiaTri = rs.getDouble("tongGiaTri");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("PhieuXuatDB - getById error: " + e);
            e.printStackTrace();
        }
        return p;
    }

    public void insert(PhieuXuat p) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO PhieuXuat (maPhieuXuat, maNV, ngayTao, trangThai, lyDoXuat, ghiChu, tongGiaTri) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, p.maPhieuXuat);
            ps.setString(2, p.maNV);
            ps.setString(3, p.ngayTao);
            ps.setString(4, p.trangThai);
            ps.setString(5, p.lyDoXuat);
            ps.setString(6, p.ghiChu);
            ps.setDouble(7, p.tongGiaTri);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("PhieuXuatDB - insert success: " + p.maPhieuXuat);
        } catch (Exception e) {
            System.out.println("PhieuXuatDB - insert error: " + e);
            e.printStackTrace();
        }
    }

    public void updateTrangThai(String maPhieuXuat, String trangThaiMoi, String maNVDuyet, String ngayDuyet) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE PhieuXuat SET trangThai=?, maNVDuyet=?, ngayDuyet=? WHERE maPhieuXuat=?"
            );
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maNVDuyet);
            ps.setString(3, ngayDuyet);
            ps.setString(4, maPhieuXuat);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("PhieuXuatDB - updateTrangThai: " + maPhieuXuat + " -> " + trangThaiMoi);
        } catch (Exception e) {
            System.out.println("PhieuXuatDB - updateTrangThai error: " + e);
            e.printStackTrace();
        }
    }

    public void delete(String maPhieuXuat) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM PhieuXuat WHERE maPhieuXuat = ?");
            ps.setString(1, maPhieuXuat);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("PhieuXuatDB - delete success: " + maPhieuXuat);
        } catch (Exception e) {
            System.out.println("PhieuXuatDB - delete error: " + e);
            e.printStackTrace();
        }
    }
}