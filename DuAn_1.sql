USE master;
GO
ALTER DATABASE DuAn1_Final SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO
DROP DATABASE DuAn1_Final;
GO

CREATE DATABASE DuAn1_Final
GO
USE DuAn1_Final
GO
create TRIGGER trg_Unique_MaSP
ON SanPham
AFTER INSERT, UPDATE
AS
BEGIN
    IF EXISTS (
        SELECT 1
    FROM inserted i
        JOIN SanPham s ON i.MaSP = s.MaSP
    WHERE i.ID <> s.ID
    )
    BEGIN
        RAISERROR ('DaTonTai', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;

    IF EXISTS (
        SELECT 1
    FROM inserted i
        JOIN SanPham s ON i.TenSP = s.TenSP
    WHERE i.ID <> s.ID
    )
    BEGIN
        RAISERROR ('DaTonTai', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;
END;
go
-- Create table SanPham
CREATE TABLE SanPham
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaSP varchar(max),
    TenSP nvarchar(max),
    MoTa nvarchar(max),
    HienThi nvarchar(max) default 'Hien'
);

-- Create table NhaCungCap
CREATE TABLE NhaCungCap
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaNhaCungCap varchar(max),
    TenNhaCungCap nvarchar(max),
    LienHe nvarchar(13),
    DiaChi nvarchar(max)
);

-- Create table MauSac
CREATE TABLE MauSac
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaMauSac varchar(max),
    TenMauSac nvarchar(max),
    TrangThai nvarchar(max)
);

-- Create table Size
CREATE TABLE Size
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaSize varchar(max),
    TenSize nvarchar(max),
    TrangThai nvarchar(max)
);

-- Create table ChatLieu
CREATE TABLE ChatLieu
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaChatLieu varchar(max),
    TenChatLieu nvarchar(max),
    TrangThai nvarchar(max)
);

-- Create table DoDay
CREATE TABLE DoDay
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaDoDay varchar(max),
    TenDoDay nvarchar(max),
    TrangThai nvarchar(max)
);

-- Create table SanPhamChiTiet
CREATE TABLE SanPhamChiTiet
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaSPCT varchar(max),
    TenSPCT nvarchar(max),
    SoLuong INT,
    IdSanPham INT,
    IdMauSac INT,
    IdSize INT,
    IdChatLieu INT,
    IdDoDay INT,
    IdNhaCungCap INT,
    NgayTao DATE,
    NgaySua DATE,
    GiaNhap DECIMAL(10, 2),
    GiaBan DECIMAL(10, 2),
    TrangThai nvarchar(max),
    HienThi nvarchar(max) default 'Hien',
    FOREIGN KEY (IdSanPham) REFERENCES SanPham(ID),
    FOREIGN KEY (IdMauSac) REFERENCES MauSac(ID),
    FOREIGN KEY (IdSize) REFERENCES Size(ID),
    FOREIGN KEY (IdChatLieu) REFERENCES ChatLieu(ID),
    FOREIGN KEY (IdDoDay) REFERENCES DoDay(ID),
    FOREIGN KEY (IdNhaCungCap) REFERENCES NhaCungCap(ID)
);

-- Create table NhanVien
CREATE TABLE NhanVien
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaNhanVien varchar(max),
    HoTen nvarchar(max),
    SoDT VARCHAR(10),
    CCCD VARCHAR(12),
    NgaySinh DATE,
    ChucVu nvarchar(max),
    GioiTinh bit,
    DiaChi nvarchar(max),
    TaiKhoan varchar(50),
    MatKhau varchar(10),
    NgayTao DATE,
    NgaySua DATE,
    TrangThai BIT DEFAULT 1
);

-- Create table KhachHang
CREATE TABLE KhachHang
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaKhachHang varchar(max),
    HoTen nvarchar(max),
    GioiTinh bit,
    SoDT VARCHAR(15),
    DiaChi nvarchar(max),
    NgayTao DATE,
    NgaySua DATE,
    NguoiTao nvarchar(max),
    NguoiSua VARCHAR(50)
);

-- Create table Voucher
CREATE TABLE Voucher
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaVoucher varchar(max),
    GiaTriVoucher DECIMAL(10, 2),
    NgayBatDau DATE,
    NgayKetThuc DATE,
    SoLuong INT,
    MoTa nvarchar(max)
);

-- Create table HoaDon
CREATE TABLE HoaDon
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    MaHD varchar(max),
    IDKhachHang INT,
    IDNhanVien INT,
    IDVoucher INT,
    LoaiHoaDon nvarchar(max),
    TongGiaTriHoaDon DECIMAL(10, 2),
    ThanhToan INT,
    NguoiTao nvarchar(max),
    NgayTao DATE,
    TrangThai int
        FOREIGN KEY (IDKhachHang) REFERENCES KhachHang(ID),
    FOREIGN KEY (IDNhanVien) REFERENCES NhanVien(ID),
    FOREIGN KEY (IDVoucher) REFERENCES Voucher(ID)
);

-- Create table HoaDonCT
CREATE TABLE HoaDonCT
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    IDHoaDon INT,
    IDCTSP INT,
    DonGia DECIMAL(10, 2),
    TrangThai bit,
    SoLuong INT,
    FOREIGN KEY (IDHoaDon) REFERENCES HoaDon(ID),
    FOREIGN KEY (IDCTSP) REFERENCES SanPhamChiTiet(ID)
);

-- Insert data into tables
INSERT INTO SanPham
    (MaSP, TenSP, MoTa, HienThi)
VALUES
    ('SP001', N'Áo thun nam', N'Áo thun cotton dành cho nam', 'Hien'),
    ('SP002', N'Áo sơ mi nữ', N'Áo sơ mi dài tay cho nữ', 'An'),
    ('SP003', N'Quần jeans nam', N'Quần jeans dài nam', 'An'),
    ('SP004', N'Váy đầm dạo phố', N'Váy đầm nữ đi dạo phố', 'Hien'),
    ('SP005', N'Túi xách da thật', N'Túi xách nữ da thật', 'Hien'),
    ('SP006', N'Giày thể thao nam', N'Giày thể thao nam thời trang', 'An'),
    ('SP007', N'Đồng hồ nữ', N'Đồng hồ nữ thương hiệu cao cấp', 'Hien'),
    ('SP008', N'Balo học sinh', N'Balo học sinh đi học', 'An'),
    ('SP009', N'Phụ kiện tóc nữ', N'Phụ kiện tóc nữ đa dạng', 'Hien'),
    ('SP010', N'Áo khoác nam', N'Áo khoác dành cho nam mùa đông', 'An');

INSERT INTO NhaCungCap
    (MaNhaCungCap, TenNhaCungCap, LienHe, DiaChi)
VALUES
    ('NCC001', N'Công ty TNHH A', N'0123456789', N'123 Đường ABC, Quận 1, TP. HCM'),
    ('NCC002', N'Công ty TNHH B', N'0987654321', N'456 Đường XYZ, Quận 2, TP. HCM'),
    ('NCC003', N'Công ty TNHH C', N'0369852147', N'789 Đường KLM, Quận 3, TP. HCM'),
    ('NCC004', N'Công ty TNHH D', N'0789546312', N'321 Đường QRS, Quận 4, TP. HCM'),
    ('NCC005', N'Công ty TNHH E', N'0321478965', N'654 Đường UVW, Quận 5, TP. HCM'),
    ('NCC006', N'Công ty TNHH F', N'0147852369', N'987 Đường EFG, Quận 6, TP. HCM'),
    ('NCC007', N'Công ty TNHH G', N'0963258741', N'159 Đường HIJ, Quận 7, TP. HCM'),
    ('NCC008', N'Công ty TNHH H', N'0785963214', N'357 Đường LMN, Quận 8, TP. HCM'),
    ('NCC009', N'Công ty TNHH I', N'0236549871', N'753 Đường OPQ, Quận 9, TP. HCM'),
    ('NCC010', N'Công ty TNHH K', N'0654789321', N'951 Đường STU, Quận 10, TP. HCM');

INSERT INTO MauSac
    (MaMauSac, TenMauSac, TrangThai)
VALUES
    ('MS001', N'Đen', 'ConHang'),
    ('MS002', N'Trắng', 'ConHang'),
    ('MS003', N'Xanh dương', 'ConHang'),
    ('MS004', N'Đỏ', 'ConHang'),
    ('MS005', N'Hồng', 'ConHang'),
    ('MS006', N'Vàng', 'ConHang'),
    ('MS007', N'Xám', 'ConHang'),
    ('MS008', N'Cam', 'ConHang'),
    ('MS009', N'Tím', 'ConHang'),
    ('MS010', N'Nâu', 'ConHang');

INSERT INTO Size
    (MaSize, TenSize, TrangThai)
VALUES
    ('SZ001', N'S', 'ConHang'),
    ('SZ002', N'M', 'ConHang'),
    ('SZ003', N'L', 'ConHang'),
    ('SZ004', N'XL', 'ConHang'),
    ('SZ005', N'XXL', 'ConHang'),
    ('SZ006', '34', 'ConHang'),
    ('SZ007', '36', 'ConHang'),
    ('SZ008', '38', 'ConHang'),
    ('SZ009', '40', 'ConHang'),
    ('SZ010', '42', 'ConHang');

INSERT INTO ChatLieu
    (MaChatLieu, TenChatLieu, TrangThai)
VALUES
    ('CL001', N'Cotton', 'ConHang'),
    ('CL002', N'Lụa', 'ConHang'),
    ('CL003', N'Da', 'ConHang'),
    ('CL004', N'Jean', 'ConHang'),
    ('CL005', N'Len', 'ConHang'),
    ('CL006', N'Thun', 'ConHang'),
    ('CL007', N'Satin', 'ConHang'),
    ('CL008', N'Nỉ', 'ConHang'),
    ('CL009', N'Len', 'ConHang'),
    ('CL010', N'Polyester', 'ConHang');

-- Insert data into DoDay
INSERT INTO DoDay
    (MaDoDay, TenDoDay, TrangThai)
VALUES
    ('DD001', N'Mỏng', 'ConHang'),
    ('DD002', N'Bình thường', 'ConHang'),
    ('DD003', N'Dày', 'ConHang'),
    ('DD004', N'Rộng', 'ConHang'),
    ('DD005', N'Chật', 'ConHang'),
    ('DD006', N'Co giãn', 'ConHang'),
    ('DD007', N'Thun co giãn', 'ConHang'),
    ('DD008', N'Không co giãn', 'ConHang'),
    ('DD009', N'Cao cấp', 'ConHang'),
    ('DD010', N'Phổ thông', 'ConHang');

-- Insert data into SanPhamChiTiet
INSERT INTO SanPhamChiTiet
    (MaSPCT, TenSPCT, SoLuong, IdSanPham, IdMauSac, IdSize, IdChatLieu, IdDoDay, IdNhaCungCap, NgayTao, NgaySua, GiaNhap, GiaBan, TrangThai, HienThi)
VALUES
    ('SPCT001', N'Áo thun nam đen', 100, 1, 1, 1, 1, 1, 1, '2024-07-01', '2024-07-01', 50000, 100000, N'Còn hàng', N'Hien'),
    ('SPCT002', N'Áo sơ mi nữ trắng', 150, 2, 2, 2, 2, 2, 2, '2024-07-01', '2024-07-01', 75000, 150000, N'Còn hàng', N'An'),
    ('SPCT003', N'Quần jeans nam xanh', 200, 3, 3, 3, 4, 3, 3, '2024-07-01', '2024-07-01', 120000, 250000, N'Còn hàng', N'An'),
    ('SPCT004', N'Váy đầm dạo phố hồng', 80, 4, 5, 2, 2, 3, 4, '2024-07-01', '2024-07-01', 200000, 400000, N'Còn hàng', N'Hien'),
    ('SPCT005', N'Túi xách da thật nâu', 60, 5, 10, 1, 3, 3, 5, '2024-07-01', '2024-07-01', 300000, 600000, N'Còn hàng', N'Hien'),
    ('SPCT006', N'Giày thể thao nam đỏ', 120, 6, 4, 3, 6, 2, 6, '2024-07-01', '2024-07-01', 150000, 300000, N'Còn hàng', N'An'),
    ('SPCT007', N'Đồng hồ nữ xám', 90, 7, 7, 1, 7, 4, 7, '2024-07-01', '2024-07-01', 500000, 1000000, N'Còn hàng', N'Hien'),
    ('SPCT008', N'Balo học sinh cam', 110, 8, 8, 4, 8, 5, 8, '2024-07-01', '2024-07-01', 100000, 200000, N'Còn hàng', N'An'),
    ('SPCT009', N'Phụ kiện tóc nữ tím', 50, 9, 9, 1, 9, 3, 9, '2024-07-01', '2024-07-01', 25000, 50000, N'Còn hàng', N'Hien'),
    ('SPCT010', N'Áo khoác nam đen', 70, 10, 1, 4, 10, 2, 10, '2024-07-01', '2024-07-01', 200000, 400000, N'Còn hàng', N'An');
INSERT INTO KhachHang
    (MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NgayTao, NgaySua, NguoiTao, NguoiSua)
VALUES
    ('KH000', N'Khách Bán Lẻ', NULL, NULL, NULL, '2020-01-01', NULL, 'admin', NULL),
    ('KH001', N'Nguyễn Văn Khánh', 0, '0901234567', N'123 Đường ABC, Quận 1, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH002', N'Trần Thị Lan', 1, '0987654321', N'456 Đường XYZ, Quận 2, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH003', N'Hoàng Minh Tuấn', 0, '0912345678', N'789 Đường KLM, Quận 3, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH004', N'Lê Thị Hằng', 1, '0976543210', N'321 Đường QRS, Quận 4, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH005', N'Phạm Văn Đức', 0, '0932165478', N'654 Đường UVW, Quận 5, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH006', N'Nguyễn Thị An', 1, '0948752369', N'987 Đường EFG, Quận 6, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH007', N'Trần Văn Long', 0, '0923658741', N'159 Đường HIJ, Quận 7, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH008', N'Hoàng Thị Mai', 1, '0965874123', N'357 Đường LMN, Quận 8, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH009', N'Lê Văn Hưng', 0, '0956321478', N'753 Đường OPQ, Quận 9, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin'),
    ('KH010', N'Nguyễn Thị Bảo', 1, '0914785632', N'951 Đường STU, Quận 10, TP. HCM', '2023-01-01', '2023-01-01', N'admin', N'admin');
INSERT INTO Voucher
    (MaVoucher, GiaTriVoucher, NgayBatDau, NgayKetThuc, SoLuong, MoTa)
VALUES
    ('VC002', 200000, '2023-01-01', '2023-12-31', 150, N'Giảm giá 200,000đ cho đơn hàng từ 500,000đ trở lên'),
    ('VC003', 50000, '2023-01-01', '2023-12-31', 200, N'Giảm giá 50,000đ cho đơn hàng từ 200,000đ trở lên'),
    ('VC004', 300000, '2023-01-01', '2023-12-31', 100, N'Giảm giá 300,000đ cho đơn hàng từ 1,000,000đ trở lên'),
    ('VC005', 150000, '2023-01-01', '2023-12-31', 120, N'Giảm giá 150,000đ cho đơn hàng từ 400,000đ trở lên'),
    ('VC006', 250000, '2023-01-01', '2023-12-31', 80, N'Giảm giá 250,000đ cho đơn hàng từ 800,000đ trở lên'),
    ('VC007', 100000, '2023-01-01', '2023-12-31', 180, N'Giảm giá 100,000đ cho đơn hàng từ 300,000đ trở lên'),
    ('VC008', 80000, '2023-01-01', '2023-12-31', 150, N'Giảm giá 80,000đ cho đơn hàng từ 250,000đ trở lên'),
    ('VC009', 400000, '2023-01-01', '2023-12-31', 90, N'Giảm giá 400,000đ cho đơn hàng từ 1,500,000đ trở lên'),
    ('VC010', 120000, '2023-01-01', '2023-12-31', 110, N'Giảm giá 120,000đ cho đơn hàng từ 350,000đ trở lên');
-- Insert data into NhanVien table
INSERT INTO NhanVien
    (MaNhanVien, HoTen, SoDT, CCCD, NgaySinh, ChucVu, GioiTinh, DiaChi, TaiKhoan, MatKhau, NgayTao, NgaySua, TrangThai)
VALUES
    ('NV002', N'Trần Thị Lan', '0909876543', '001234567891', '1992-02-02', N'nv', 0, N'456 Đường Hai Bà Trưng, Quận 3, TP.HCM', 'ttl', '123', '2016-04-23', GETDATE(), 1),
    ('NV001', N'Nguyễn Văn Minh', '0909123456', '001234567890', '1990-01-01', N'nv', 1, N'123 Đường Lê Lợi, Quận 1, TP.HCM', 'nvm', '123', '2018-12-08', GETDATE(), 1),
    ('NV001', N'Nguyễn Quyết Tiến', '0909123433', '001234509878', '1990-06-30', N'nv', 1, N'123 Đường Lê Lợi, Quận 10, TP.HN', 'nqt', '123', '2018-12-08', GETDATE(), 0),
    ('NV003', N'Hoàng Minh Khánh', '0909345678', '001234567892', '1988-03-03', N'nv', 1, N'789 Đường Nguyễn Huệ, Quận 1, TP.HCM', 'hmk', '123', '2023-01-12', GETDATE(), 1),
    ('NV004', N'Lê Thị Hằng', '0909234567', '001234567893', '1991-04-04', N'nv', 0, N'101 Đường Võ Thị Sáu, Quận 3, TP.HCM', 'lth', '123', '2020-01-01', GETDATE(), 1),
    ('NV005', N'Phạm Văn Đức', '0909456789', '001234567894', '1989-05-05', N'nv', 1, N'202 Đường Trần Phú, Quận 5, TP.HCM', 'pvd', '123', '2022-07-01', GETDATE(), 1),
    ('QL001', N'Nguyễn Thị An', '0909123457', '001234567895', '1985-06-06', N'ql', 0, N'303 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM', 'nta', '456', '2023-01-01', GETDATE(), 1),
    ('QL001', N'Bùi Thị Huyền', '0309123999', '001234567333', '1996-03-29', N'ql', 0, N'123 Đường Lê Lợi, Quận 10, TP.HN', 'bth', '456', '2020-01-01', GETDATE(), 0),
    ('QL002', N'Trần Văn Long', '0909765432', '001234567896', '1986-07-07', N'ql', 1, N'404 Đường Nguyễn Tri Phương, Quận 10, TP.HCM', 'tvl', '456', '2020-09-01', GETDATE(), 1);
go
INSERT INTO HoaDon
    (MaHD, IDKhachHang, IDNhanVien, IDVoucher, LoaiHoaDon, TongGiaTriHoaDon, ThanhToan, NguoiTao, NgayTao, TrangThai)
VALUES
    ('HD001', 1, 1, 1, N'Bán lẻ', 500000, 500000, N'Nguyễn Văn Minh', '2024-07-01', 1),
    ('HD002', 2, 2, 2, N'Bán lẻ', 750000, 550000, N'Trần Thị Lan', '2024-07-02', 0),
    ('HD003', 3, 3, NULL, N'Bán lẻ', 300000, 0, N'Hoàng Minh Khánh', '2024-07-03', 2),
    ('HD004', 4, 4, 3, N'Bán lẻ', 1200000, 1150000, N'Lê Thị Hằng', '2024-07-04', 1),
    ('HD005', 5, 5, NULL, N'Bán lẻ', 600000, 0, N'Phạm Văn Đức', '2024-07-05', 0);
go
-- Insert data into HoaDonCT table
INSERT INTO HoaDonCT
    (IDHoaDon, IDCTSP, DonGia, TrangThai, SoLuong)
VALUES
    (1, 1, 100000, 1, 2),
    (1, 2, 150000, 1, 2),
    (2, 3, 250000, 1, 3),
    (3, 4, 300000, 0, 1),
    (4, 5, 600000, 1, 1),
    (4, 6, 300000, 1, 2),
    (5, 7, 300000, 1, 2);
select hd.MaHD, kh.HoTen, hd.NguoiTao, hd.NgayTao, hd.TongGiaTriHoaDon, hd.TrangThai
from hoadon hd
    inner join KhachHang kh on hd.IDKhachHang = kh.ID
WHERE hd.TrangThai = 0

SELECT
    spct.MaSPCT,
    spct.TenSPCT,
    spct.GiaBan,
    ms.TenMauSac,
    s.TenSize,
    cl.TenChatLieu,
    dd.TenDoDay,
    ncc.TenNhaCungCap,
    spct.SoLuong
FROM
    SanPhamChiTiet spct
    LEFT JOIN
    SanPham sp ON spct.IdSanPham = sp.ID
    LEFT JOIN
    MauSac ms ON spct.IdMauSac = ms.ID
    LEFT JOIN
    Size s ON spct.IdSize = s.ID
    LEFT JOIN
    ChatLieu cl ON spct.IdChatLieu = cl.ID
    LEFT JOIN
    DoDay dd ON spct.IdDoDay = dd.ID
    LEFT JOIN
    NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID;
go
select hd.MaHD , spct.MaSPCT, spct.TenSPCT, spct.GiaBan, hdct.SoLuong, spct.GiaBan * hdct.SoLuong
from HoaDonCT hdct
    join SanPhamChiTiet spct on hdct.IDCTSP = spct.id
    join HoaDon hd on hdct.IDHoaDon = hd.ID
where hdct.ID = 1

select id, MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NguoiTao, NgayTao
from KhachHang
SELECT *
FROM HoaDon
SELECT *
FROM HoaDonCT
SELECT *
FROM Voucher
SELECT *
FROM KhachHang

SELECT
    ID,
    MaHD,
    IDKhachHang,
    IDNhanVien,
    IDVoucher,
    TongGiaTriHoaDon,
    ThanhToan,
    NgayTao,
    TrangThai
FROM
    HoaDon;

select hd.id, hd.MaHD, nv.HoTen, hd.NguoiTao, hd.NgayTao, hd.TongGiaTriHoaDon, hd.TrangThai
from hoadon hd
    inner join KhachHang kh on hd.IDKhachHang = kh.ID
    INNER join NhanVien nv on hd.IDNhanVien = nv.ID

