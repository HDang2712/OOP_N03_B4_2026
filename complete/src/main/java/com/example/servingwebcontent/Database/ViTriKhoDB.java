package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.ViTriKho;

public class ViTriKhoDB {

    public ArrayList<ViTriKho> getAll() {
        ArrayList<ViTriKho> list = new ArrayList<ViTriKho>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM ViTriKho ORDER BY khuVuc, tenViTri");
            while (rs.next()) {
                ViTriKho v = new ViTriKho();
                v.maViTri = rs.getString("maViTri");
                v.tenViTri = rs.getString("tenViTri");
                v.khuVuc = rs.getString("khuVuc");
                v.sucChua = rs.getInt("sucChua");
                v.dangSuDung = rs.getInt("dangSuDung");
                v.moTa = rs.getString("moTa");
                list.add(v);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("ViTriKhoDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public ViTriKho getById(String maViTri) {
        ViTriKho v = null;
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ViTriKho WHERE maViTri = ?");
            ps.setString(1, maViTri);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                v = new ViTriKho();
                v.maViTri = rs.getString("maViTri");
                v.tenViTri = rs.getString("tenViTri");
                v.khuVuc = rs.getString("khuVuc");
                v.sucChua = rs.getInt("sucChua");
                v.dangSuDung = rs.getInt("dangSuDung");
                v.moTa = rs.getString("moTa");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("ViTriKhoDB - getById error: " + e);
            e.printStackTrace();
        }
        return v;
    }

    public void insert(ViTriKho v) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO ViTriKho (maViTri, tenViTri, khuVuc, sucChua, dangSuDung, moTa) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, v.maViTri);
            ps.setString(2, v.tenViTri);
            ps.setString(3, v.khuVuc);
            ps.setInt(4, v.sucChua);
            ps.setInt(5, v.dangSuDung);
            ps.setString(6, v.moTa);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("ViTriKhoDB - insert success: " + v.maViTri);
        } catch (Exception e) {
            System.out.println("ViTriKhoDB - insert error: " + e);
            e.printStackTrace();
        }
    }

    public void update(ViTriKho v) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE ViTriKho SET tenViTri=?, khuVuc=?, sucChua=?, dangSuDung=?, moTa=? WHERE maViTri=?"
            );
            ps.setString(1, v.tenViTri);
            ps.setString(2, v.khuVuc);
            ps.setInt(3, v.sucChua);
            ps.setInt(4, v.dangSuDung);
            ps.setString(5, v.moTa);
            ps.setString(6, v.maViTri);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("ViTriKhoDB - update success: " + v.maViTri);
        } catch (Exception e) {
            System.out.println("ViTriKhoDB - update error: " + e);
            e.printStackTrace();
        }
    }

    public void delete(String maViTri) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ViTriKho WHERE maViTri = ?");
            ps.setString(1, maViTri);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("ViTriKhoDB - delete success: " + maViTri);
        } catch (Exception e) {
            System.out.println("ViTriKhoDB - delete error: " + e);
            e.printStackTrace();
        }
    }
}