package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.CanhBao;

public class CanhBaoDB {

    public ArrayList<CanhBao> getAll() {
        ArrayList<CanhBao> list = new ArrayList<CanhBao>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM CanhBao ORDER BY thoiGian DESC");
            while (rs.next()) {
                CanhBao cb = new CanhBao();
                cb.maCanhBao = rs.getString("maCanhBao");
                cb.loaiCanhBao = rs.getString("loaiCanhBao");
                cb.maHang = rs.getString("maHang");
                cb.noiDung = rs.getString("noiDung");
                cb.thoiGian = rs.getString("thoiGian");
                cb.trangThai = rs.getString("trangThai");
                cb.maNVXuLy = rs.getString("maNVXuLy");
                list.add(cb);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("CanhBaoDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<CanhBao> getChuaXuLy() {
        ArrayList<CanhBao> list = new ArrayList<CanhBao>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery(
                "SELECT * FROM CanhBao WHERE trangThai = 'CHUA_XU_LY' ORDER BY thoiGian DESC"
            );
            while (rs.next()) {
                CanhBao cb = new CanhBao();
                cb.maCanhBao = rs.getString("maCanhBao");
                cb.loaiCanhBao = rs.getString("loaiCanhBao");
                cb.maHang = rs.getString("maHang");
                cb.noiDung = rs.getString("noiDung");
                cb.thoiGian = rs.getString("thoiGian");
                cb.trangThai = rs.getString("trangThai");
                cb.maNVXuLy = rs.getString("maNVXuLy");
                list.add(cb);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("CanhBaoDB - getChuaXuLy error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public void insert(CanhBao cb) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO CanhBao (maCanhBao, loaiCanhBao, maHang, noiDung, thoiGian, trangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, cb.maCanhBao);
            ps.setString(2, cb.loaiCanhBao);
            ps.setString(3, cb.maHang);
            ps.setString(4, cb.noiDung);
            ps.setString(5, cb.thoiGian);
            ps.setString(6, cb.trangThai);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("CanhBaoDB - insert success: " + cb.maCanhBao);
        } catch (Exception e) {
            System.out.println("CanhBaoDB - insert error: " + e);
            e.printStackTrace();
        }
    }

    public void markDaXuLy(String maCanhBao, String maNVXuLy) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE CanhBao SET trangThai='DA_XU_LY', maNVXuLy=? WHERE maCanhBao=?"
            );
            ps.setString(1, maNVXuLy);
            ps.setString(2, maCanhBao);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("CanhBaoDB - markDaXuLy: " + maCanhBao);
        } catch (Exception e) {
            System.out.println("CanhBaoDB - markDaXuLy error: " + e);
            e.printStackTrace();
        }
    }

    public int countChuaXuLy() {
        int count = 0;
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery(
                "SELECT COUNT(*) as total FROM CanhBao WHERE trangThai = 'CHUA_XU_LY'"
            );
            if (rs.next()) {
                count = rs.getInt("total");
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("CanhBaoDB - countChuaXuLy error: " + e);
            e.printStackTrace();
        }
        return count;
    }
}