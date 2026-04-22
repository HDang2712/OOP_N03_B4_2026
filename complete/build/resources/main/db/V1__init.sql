CREATE TABLE NhaCungCap (
    maNCC VARCHAR(20) PRIMARY KEY,
    tenNCC VARCHAR(100) NOT NULL,
    diaChi VARCHAR(200),
    soDienThoai VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE NhanVien (
    maNV VARCHAR(20) PRIMARY KEY,
    hoTen VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    soDienThoai VARCHAR(20),
    vaiTro VARCHAR(20) NOT NULL DEFAULT 'nhan_vien', 
    matKhau VARCHAR(100) NOT NULL
);

CREATE TABLE  ViTriKho (
    maViTri VARCHAR(20) PRIMARY KEY,
    tenViTri VARCHAR(100) NOT NULL,
    khuVuc VARCHAR(10) NOT NULL,
    sucChua INT DEFAULT 0,
    dangSuDung INT DEFAULT 0,
    moTa VARCHAR(200)
);

CREATE TABLE HangHoa (
    maHang VARCHAR(20) PRIMARY KEY,
    tenHang VARCHAR(100) NOT NULL,
    phanLoai VARCHAR(50),
    soLuongTon INT DEFAULT 0,
    donGia DOUBLE DEFAULT 0,
    hanSuDung VARCHAR(20),
    maNhaCungCap VARCHAR(20),
    maViTri VARCHAR(20),
    soLuongToiThieu INT DEFAULT 10,
    FOREIGN KEY (maNhaCungCap) REFERENCES NhaCungCap(maNCC),
    FOREIGN KEY (maViTri) REFERENCES ViTriKho(maViTri)
);

CREATE TABLE  PhieuNhap (
    maPhieuNhap VARCHAR(20) PRIMARY KEY,
    maNV VARCHAR(20) NOT NULL,
    maNCC VARCHAR(20),
    ngayTao VARCHAR(20) NOT NULL,
    ngayDuyet VARCHAR(20),
    trangThai VARCHAR(20) DEFAULT 'DRAFT',
    ghiChu VARCHAR(500),
    tongGiaTri DOUBLE DEFAULT 0,
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNCC)
);

CREATE TABLE  PhieuXuat (
    maPhieuXuat VARCHAR(20) PRIMARY KEY,
    maNV VARCHAR(20) NOT NULL,
    maNVDuyet VARCHAR(20),
    ngayTao VARCHAR(20) NOT NULL,
    ngayDuyet VARCHAR(20),
    trangThai VARCHAR(20) DEFAULT 'DRAFT', 
    lyDoXuat VARCHAR(200),
    ghiChu VARCHAR(500),
    tongGiaTri DOUBLE DEFAULT 0,
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    FOREIGN KEY (maNVDuyet) REFERENCES NhanVien(maNV)
);

CREATE TABLE ChiTietPhieu (
    maChiTiet VARCHAR(20) PRIMARY KEY,
    maPhieu VARCHAR(20) NOT NULL,
    loaiPhieu VARCHAR(10) NOT NULL, 
    maHang VARCHAR(20) NOT NULL,
    soLuong INT NOT NULL DEFAULT 0,
    donGia DOUBLE DEFAULT 0,
    thanhTien DOUBLE DEFAULT 0,
    FOREIGN KEY (maHang) REFERENCES HangHoa(maHang)
);

CREATE TABLE CanhBao (
    maCanhBao VARCHAR(20) PRIMARY KEY,
    loaiCanhBao VARCHAR(50) NOT NULL, 
    maHang VARCHAR(20),
    noiDung VARCHAR(500) NOT NULL,
    thoiGian VARCHAR(30) NOT NULL,
    trangThai VARCHAR(20) DEFAULT 'CHUA_XU_LY', 
    maNVXuLy VARCHAR(20),
    FOREIGN KEY (maHang) REFERENCES HangHoa(maHang),
    FOREIGN KEY (maNVXuLy) REFERENCES NhanVien(maNV)
);

CREATE TABLE IF NOT EXISTS LichSuThaoTac (
    maLichSu VARCHAR(20) PRIMARY KEY,
    maNV VARCHAR(20) NOT NULL,
    loaiThaoTac VARCHAR(20) NOT NULL, 
    doiTuong VARCHAR(30) NOT NULL,   
    maDoiTuong VARCHAR(20) NOT NULL,
    giaTriCu TEXT,
    giaTriMoi TEXT,
    thoiGian VARCHAR(30) NOT NULL,
    ghiChu VARCHAR(500),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);


INSERT IGNORE INTO NhanVien (maNV, hoTen, email, soDienThoai, vaiTro, matKhau) VALUES
('NV001', 'Nguyen Van Admin', 'admin@kho.com', '0901234567', 'admin', 'admin123'),
('NV002', 'Tran Thi Quan Ly', 'quanly@kho.com', '0902345678', 'quan_ly', 'ql123'),
('NV003', 'Le Van Nhan Vien', 'nhanvien@kho.com', '0903456789', 'nhan_vien', 'nv123');


INSERT IGNORE INTO NhaCungCap (maNCC, tenNCC, diaChi, soDienThoai, email) VALUES
('NCC001', 'Cong ty TNHH ABC', '123 Nguyen Van Linh, HCM', '0281234567', 'abc@supplier.com'),
('NCC002', 'Cong ty CP XYZ', '456 Le Loi, Ha Noi', '0242345678', 'xyz@supplier.com'),
('NCC003', 'Nha cung cap DEF', '789 Tran Phu, Da Nang', '0236789012', 'def@supplier.com');


INSERT IGNORE INTO ViTriKho (maViTri, tenViTri, khuVuc, sucChua, dangSuDung, moTa) VALUES
('VT001', 'Ke A1', 'A', 100, 0, 'Khu vuc A - Hang dien tu'),
('VT002', 'Ke A2', 'A', 100, 0, 'Khu vuc A - Hang dien tu'),
('VT003', 'Ke B1', 'B', 200, 0, 'Khu vuc B - Hang tieu dung'),
('VT004', 'Ke B2', 'B', 200, 0, 'Khu vuc B - Hang tieu dung'),
('VT005', 'Ke C1', 'C', 150, 0, 'Khu vuc C - Hang thuc pham');


INSERT IGNORE INTO HangHoa (maHang, tenHang, phanLoai, soLuongTon, donGia, hanSuDung, maNhaCungCap, maViTri, soLuongToiThieu) VALUES
('HH001', 'Laptop Dell XPS 15', 'Dien tu', 20, 25000000, '2026-12-31', 'NCC001', 'VT001', 5),
('HH002', 'Chuot Logitech MX', 'Dien tu', 50, 1200000, '2027-06-30', 'NCC001', 'VT002', 10),
('HH003', 'Nuoc Lavie 500ml', 'Thuc pham', 200, 8000, '2025-08-01', 'NCC003', 'VT005', 50),
('HH004', 'Gao ST25 5kg', 'Thuc pham', 8, 120000, '2025-12-31', 'NCC003', 'VT005', 10),
('HH005', 'Sua Vinamilk 1L', 'Thuc pham', 100, 35000, '2025-07-15', 'NCC002', 'VT005', 20);