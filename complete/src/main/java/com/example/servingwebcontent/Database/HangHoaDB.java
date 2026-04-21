package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.servingwebcontent.Model.HangHoa;

public class HangHoaDB {

    public ArrayList<HangHoa> getAll() {
        ArrayList<HangHoa> list = new ArrayList<HangHoa>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery("SELECT * FROM HangHoa");
            while (rs.next()) {
                HangHoa h = new HangHoa();
                h.maHang = rs.getString("maHang");
                h.tenHang = rs.getString("tenHang");
                h.phanLoai = rs.getString("phanLoai");
                h.soLuongTon = rs.getInt("soLuongTon");
                h.donGia = rs.getDouble("donGia");
                h.hanSuDung = rs.getString("hanSuDung");
                h.maNhaCungCap = rs.getString("maNhaCungCap");
                h.maViTri = rs.getString("maViTri");
                h.soLuongToiThieu = rs.getInt("soLuongToiThieu");
                list.add(h);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("HangHoaDB - getAll error: " + e);
            e.printStackTrace();
        }
        return list;
    }
    public HangHoa getById(String maHang) {
        HangHoa h = null;
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM HangHoa WHERE maHang = ?");
            ps.setString(1, maHang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                h = new HangHoa();
                h.maHang = rs.getString("maHang");
                h.tenHang = rs.getString("tenHang");
                h.phanLoai = rs.getString("phanLoai");
                h.soLuongTon = rs.getInt("soLuongTon");
                h.donGia = rs.getDouble("donGia");
                h.hanSuDung = rs.getString("hanSuDung");
                h.maNhaCungCap = rs.getString("maNhaCungCap");
                h.maViTri = rs.getString("maViTri");
                h.soLuongToiThieu = rs.getInt("soLuongToiThieu");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("HangHoaDB - getById error: " + e);
            e.printStackTrace();
        }
        return h;
    }

    public ArrayList<HangHoa> search(String keyword) {
        ArrayList<HangHoa> list = new ArrayList<HangHoa>();
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM HangHoa WHERE tenHang LIKE ? OR maHang LIKE ? OR phanLoai LIKE ?"
            );
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HangHoa h = new HangHoa();
                h.maHang = rs.getString("maHang");
                h.tenHang = rs.getString("tenHang");
                h.phanLoai = rs.getString("phanLoai");
                h.soLuongTon = rs.getInt("soLuongTon");
                h.donGia = rs.getDouble("donGia");
                h.hanSuDung = rs.getString("hanSuDung");
                h.maNhaCungCap = rs.getString("maNhaCungCap");
                h.maViTri = rs.getString("maViTri");
                h.soLuongToiThieu = rs.getInt("soLuongToiThieu");
                list.add(h);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("HangHoaDB - search error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<HangHoa> getTonKhoThap() {
        ArrayList<HangHoa> list = new ArrayList<HangHoa>();
        try {
            myDBConnection my = new myDBConnection();
            Statement sta = my.getMyConn();
            ResultSet rs = sta.executeQuery(
                "SELECT * FROM HangHoa WHERE soLuongTon < soLuongToiThieu"
            );
            while (rs.next()) {
                HangHoa h = new HangHoa();
                h.maHang = rs.getString("maHang");
                h.tenHang = rs.getString("tenHang");
                h.soLuongTon = rs.getInt("soLuongTon");
                h.soLuongToiThieu = rs.getInt("soLuongToiThieu");
                list.add(h);
            }
            rs.close();
            sta.close();
        } catch (Exception e) {
            System.out.println("HangHoaDB - getTonKhoThap error: " + e);
            e.printStackTrace();
        }
        return list;
    }

    public void insert(HangHoa h) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO HangHoa (maHang, tenHang, phanLoai, soLuongTon, donGia, hanSuDung, maNhaCungCap, maViTri, soLuongToiThieu) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, h.maHang);
            ps.setString(2, h.tenHang);
            ps.setString(3, h.phanLoai);
            ps.setInt(4, h.soLuongTon);
            ps.setDouble(5, h.donGia);
            ps.setString(6, h.hanSuDung);
            ps.setString(7, h.maNhaCungCap);
            ps.setString(8, h.maViTri);
            ps.setInt(9, h.soLuongToiThieu);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("HangHoaDB - insert success: " + h.maHang);
        } catch (Exception e) {
            System.out.println("HangHoaDB - insert error: " + e);
            e.printStackTrace();
        }
    }


    public void update(HangHoa h) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE HangHoa SET tenHang=?, phanLoai=?, soLuongTon=?, donGia=?, " +
                "hanSuDung=?, maNhaCungCap=?, maViTri=?, soLuongToiThieu=? WHERE maHang=?"
            );
            ps.setString(1, h.tenHang);
            ps.setString(2, h.phanLoai);
            ps.setInt(3, h.soLuongTon);
            ps.setDouble(4, h.donGia);
            ps.setString(5, h.hanSuDung);
            ps.setString(6, h.maNhaCungCap);
            ps.setString(7, h.maViTri);
            ps.setInt(8, h.soLuongToiThieu);
            ps.setString(9, h.maHang);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("HangHoaDB - update success: " + h.maHang);
        } catch (Exception e) {
            System.out.println("HangHoaDB - update error: " + e);
            e.printStackTrace();
        }
    }

    public void delete(String maHang) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM HangHoa WHERE maHang = ?");
            ps.setString(1, maHang);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("HangHoaDB - delete success: " + maHang);
        } catch (Exception e) {
            System.out.println("HangHoaDB - delete error: " + e);
            e.printStackTrace();
        }
    }

    
    public void updateSoLuong(String maHang, int soLuongMoi) {
        try {
            myDBConnection my = new myDBConnection();
            Connection conn = my.getOnlyConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE HangHoa SET soLuongTon = ? WHERE maHang = ?"
            );
            ps.setInt(1, soLuongMoi);
            ps.setString(2, maHang);
            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("HangHoaDB - updateSoLuong: " + maHang + " -> " + soLuongMoi);
        } catch (Exception e) {
            System.out.println("HangHoaDB - updateSoLuong error: " + e);
            e.printStackTrace();
        }
    }
}