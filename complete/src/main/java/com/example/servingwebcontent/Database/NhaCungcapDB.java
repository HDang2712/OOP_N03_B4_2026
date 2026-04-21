package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.NhaCungCap;

public class NhaCungCapDB {

    public ArrayList<NhaCungCap> getAll() {
        ArrayList<NhaCungCap> list = new ArrayList<NhaCungCap>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM NhaCungCap");
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.maNCC = rs.getString("maNCC");
                ncc.tenNCC = rs.getString("tenNCC");
                ncc.diaChi = rs.getString("diaChi");
                ncc.soDienThoai = rs.getString("soDienThoai");
                ncc.email = rs.getString("email");
                list.add(ncc);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("NhaCungCapDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public NhaCungCap getById(String maNCC) {
        NhaCungCap ncc = null;
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM NhaCungCap WHERE maNCC = ?");
            ps.setString(1, maNCC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ncc = new NhaCungCap();
                ncc.maNCC = rs.getString("maNCC");
                ncc.tenNCC = rs.getString("tenNCC");
                ncc.diaChi = rs.getString("diaChi");
                ncc.soDienThoai = rs.getString("soDienThoai");
                ncc.email = rs.getString("email");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("NhaCungCapDB - getById error: " + e);
            e.printStackTrace();
        }
        return ncc;
    }

    public ArrayList<NhaCungCap> search(String keyword) {
        ArrayList<NhaCungCap> list = new ArrayList<NhaCungCap>();
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM NhaCungCap WHERE tenNCC LIKE ? OR maNCC LIKE ?"
            );
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.maNCC = rs.getString("maNCC");
                ncc.tenNCC = rs.getString("tenNCC");
                ncc.diaChi = rs.getString("diaChi");
                ncc.soDienThoai = rs.getString("soDienThoai");
                ncc.email = rs.getString("email");
                list.add(ncc);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("NhaCungCapDB - search error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public void insert(NhaCungCap ncc) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, soDienThoai, email) VALUES (?, ?, ?, ?, ?)"
            );
            ps.setString(1, ncc.maNCC);
            ps.setString(2, ncc.tenNCC);
            ps.setString(3, ncc.diaChi);
            ps.setString(4, ncc.soDienThoai);
            ps.setString(5, ncc.email);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("NhaCungCapDB - insert success: " + ncc.maNCC);
        } catch (Exception e) {
            System.out.println("NhaCungCapDB - insert error: " + e);
            e.printStackTrace();
        }
    }

    public void update(NhaCungCap ncc) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE NhaCungCap SET tenNCC=?, diaChi=?, soDienThoai=?, email=? WHERE maNCC=?"
            );
            ps.setString(1, ncc.tenNCC);
            ps.setString(2, ncc.diaChi);
            ps.setString(3, ncc.soDienThoai);
            ps.setString(4, ncc.email);
            ps.setString(5, ncc.maNCC);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("NhaCungCapDB - update success: " + ncc.maNCC);
        } catch (Exception e) {
            System.out.println("NhaCungCapDB - update error: " + e);
            e.printStackTrace();
        }
    }

    
    public void delete(String maNCC) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM NhaCungCap WHERE maNCC = ?");
            ps.setString(1, maNCC);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("NhaCungCapDB - delete success: " + maNCC);
        } catch (Exception e) {
            System.out.println("NhaCungCapDB - delete error: " + e);
            e.printStackTrace();
        }
    }
}