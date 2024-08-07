USE master;
GO ALTER DATABASE DuAn1_Final
SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO DROP DATABASE DuAn1_Final;

GO 
CREATE DATABASE DuAn1_Final
GO 
USE DuAn1_Final

GO 
create TRIGGER trg_Unique_MaSP ON SanPham
AFTER
INSERT,
    UPDATE AS BEGIN IF EXISTS (
        SELECT 1
        FROM inserted i
            JOIN SanPham s ON i.MaSP = s.MaSP
        WHERE i.ID <> s.ID
    ) BEGIN RAISERROR ('DaTonTai', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END;
IF EXISTS (
    SELECT 1
    FROM inserted i
        JOIN SanPham s ON i.TenSP = s.TenSP
    WHERE i.ID <> s.ID
) BEGIN RAISERROR ('DaTonTai', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END;
END;
go -- Create table SanPham
    CREATE TABLE SanPham (
        ID INT PRIMARY KEY IDENTITY (1, 1),
        MaSP varchar(max),
        TenSP nvarchar (max),
        MoTa nvarchar (max),
        HienThi nvarchar (max) default 'Hien'
    );
-- Create table NhaCungCap
CREATE TABLE NhaCungCap (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaNhaCungCap varchar(max),
    TenNhaCungCap nvarchar (max),
    LienHe nvarchar (13),
    DiaChi nvarchar (max)
);
-- Create table MauSac
CREATE TABLE MauSac (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaMauSac varchar(max),
    TenMauSac nvarchar (max),
    TrangThai nvarchar (max)
);
-- Create table Size
CREATE TABLE Size (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaSize varchar(max),
    TenSize nvarchar (max),
    TrangThai nvarchar (max)
);
-- Create table ChatLieu
CREATE TABLE ChatLieu (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaChatLieu varchar(max),
    TenChatLieu nvarchar (max),
    TrangThai nvarchar (max)
);
-- Create table DoDay
CREATE TABLE DoDay (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaDoDay varchar(max),
    TenDoDay nvarchar (max),
    TrangThai nvarchar (max)
);
-- Create table SanPhamChiTiet
CREATE TABLE SanPhamChiTiet (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaSPCT varchar(max),
    TenSPCT nvarchar (max),
    SoLuong INT,
    IdSanPham INT,
    IdMauSac INT,
    IdSize INT,
    IdChatLieu INT,
    IdDoDay INT,
    IdNhaCungCap INT,
    NgayTao DATE,
    GiaNhap DECIMAL(10, 2),
    GiaBan DECIMAL(10, 2),
    TrangThai nvarchar (max),
    HienThi nvarchar (max) default 'Hien',
    FOREIGN KEY (IdSanPham) REFERENCES SanPham (ID),
    FOREIGN KEY (IdMauSac) REFERENCES MauSac (ID),
    FOREIGN KEY (IdSize) REFERENCES Size (ID),
    FOREIGN KEY (IdChatLieu) REFERENCES ChatLieu (ID),
    FOREIGN KEY (IdDoDay) REFERENCES DoDay (ID),
    FOREIGN KEY (IdNhaCungCap) REFERENCES NhaCungCap (ID)
);
-- Create table NhanVien
CREATE TABLE NhanVien (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaNhanVien varchar(max),
    HoTen nvarchar (max),
    SoDT VARCHAR(10),
    CCCD VARCHAR(12),
    NgaySinh DATE,
    ChucVu nvarchar (max),
    GioiTinh bit,
    DiaChi nvarchar (max),
    TaiKhoan varchar(50),
    MatKhau varchar(10),
    NgayTao DATE,
    TrangThai BIT DEFAULT 1
);
-- Create table KhachHang
CREATE TABLE KhachHang (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaKhachHang varchar(max),
    HoTen nvarchar (max),
    GioiTinh bit,
    SoDT VARCHAR(15) UNIQUE,
    DiaChi nvarchar (max),
    NgayTao DATE,
    NguoiTao nvarchar (max),
    TrangThai bit default 1
);
-- Create table Voucher
CREATE TABLE Voucher (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaVoucher varchar(max),
    GiaTriVoucher DECIMAL(10, 2),
    NgayBatDau DATE,
    NgayKetThuc DATE,
    SoLuong INT,
    MoTa nvarchar (max),
    TrangThai Int
);
create table PhuongThucThanhToan (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    TenPhuongThucTT NVARCHAR (max),
    TrangThai bit DEFAULT 1
) -- Create table HoaDon
CREATE TABLE HoaDon (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    MaHD varchar(max),
    IDKhachHang INT,
    IDNhanVien int,
    IDVoucher int,
    TongGiaTriHoaDon DECIMAL(10, 2),
    ThanhToan DECIMAL(10, 2),
    IDPhuongThucTT int,
    NgayTao DATE,
    TrangThai int,
    GhiChu NVARCHAR (max) FOREIGN KEY (IDKhachHang) REFERENCES KhachHang (ID),
    FOREIGN KEY (IDNhanVien) REFERENCES NhanVien (ID),
    FOREIGN KEY (IDVoucher) REFERENCES Voucher (ID),
    FOREIGN Key (IDPhuongThucTT) REFERENCES PhuongThucThanhToan (ID)
);
-- Create table HoaDonCT
CREATE TABLE HoaDonCT (
    ID INT PRIMARY KEY IDENTITY (1, 1),
    IDHoaDon INT,
    IDCTSP INT,
    DonGia DECIMAL(10, 2),
    TrangThai bit,
    SoLuong INT,
    FOREIGN KEY (IDHoaDon) REFERENCES HoaDon (ID),
    FOREIGN KEY (IDCTSP) REFERENCES SanPhamChiTiet (ID)
);
INSERT INTO SanPham (MaSP, TenSP, MoTa, HienThi)
VALUES (
        'SP001',
        N'Quần dài Adidas Originals',
        N'Quần dài thể thao nam Adidas Originals',
        'Hien'
    ),
    (
        'SP002',
        N'Quần dài Adidas Sportswear',
        N'Quần dài thể thao nam Adidas Sportswear',
        'Hien'
    ),
    (
        'SP003',
        N'Quần dài Adidas Terrex',
        N'Quần dài outdoor nam Adidas Terrex',
        'Hien'
    ),
    (
        'SP004',
        N'Quần dài Adidas Performance',
        N'Quần dài thể thao nam Adidas Performance',
        'Hien'
    ),
    (
        'SP005',
        N'Quần dài Adidas Essentials',
        N'Quần dài cơ bản nam Adidas Essentials',
        'Hien'
    ),
    (
        'SP006',
        N'Quần dài Adidas Adicolor',
        N'Quần dài thời trang nam Adidas Adicolor',
        'Hien'
    ),
    (
        'SP007',
        N'Quần dài Adidas Z.N.E.',
        N'Quần dài thể thao nam Adidas Z.N.E.',
        'Hien'
    ),
    (
        'SP008',
        N'Quần dài Adidas 4DFWD',
        N'Quần dài chạy bộ nam Adidas 4DFWD',
        'Hien'
    ),
    (
        'SP009',
        N'Quần dài Adidas Primeblue',
        N'Quần dài nam Adidas Primeblue từ nhựa tái chế',
        'Hien'
    ),
    (
        'SP010',
        N'Quần dài Adidas Tiro',
        N'Quần dài bóng đá nam Adidas Tiro',
        'Hien'
    );
-- NhaCungCap table
INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, LienHe, DiaChi)
VALUES (
        'NCC001',
        N'Công ty TNHH Adidas Việt Nam',
        '0123456789',
        N'123 Đường Lê Lợi, Quận 1, TP. HCM'
    ),
    (
        'NCC002',
        N'Công ty CP Thể Thao Sài Gòn',
        '0987654321',
        N'456 Đường Nguyễn Huệ, Quận 1, TP. HCM'
    ),
    (
        'NCC003',
        N'Tổng công ty CP Phong Phú',
        '0909123456',
        N'789 Đường Nguyễn Trãi, Quận 5, TP. HCM'
    ),
    (
        'NCC004',
        N'Công ty TNHH May Việt Tiến',
        '0918765432',
        N'159 Đường Hai Bà Trưng, Quận 3, TP. HCM'
    ),
    (
        'NCC005',
        N'Công ty CP Dệt May Thành Công',
        '0965432198',
        N'357 Đường Sư Vạn Hạnh, Quận 10, TP. HCM'
    ),
    (
        'NCC006',
        N'Công ty TNHH Thương mại Quốc tế Hoàng Phúc',
        '0932165478',
        N'753 Đường 3/2, Quận 10, TP. HCM'
    ),
    (
        'NCC007',
        N'Công ty TNHH Thể Thao Động Lực',
        '0945678912',
        N'951 Đường Cách Mạng Tháng 8, Quận 3, TP. HCM'
    ),
    (
        'NCC008',
        N'Công ty CP XNK Tổng hợp I Việt Nam',
        '0978912345',
        N'258 Đường Võ Văn Tần, Quận 3, TP. HCM'
    ),
    (
        'NCC009',
        N'Công ty TNHH MTV Thương mại Thái Bình',
        '0991234567',
        N'456 Đường Lý Thường Kiệt, Quận Tân Bình, TP. HCM'
    ),
    (
        'NCC010',
        N'Công ty TNHH Thương mại Dịch vụ Tổng hợp Vina',
        '0912345678',
        N'789 Đường Trường Chinh, Quận Tân Bình, TP. HCM'
    );
-- MauSac table
INSERT INTO MauSac (MaMauSac, TenMauSac, TrangThai)
VALUES ('MS001', N'Đen', 'ConHang'),
    ('MS002', N'Xanh navy', 'ConHang'),
    ('MS003', N'Xám', 'ConHang'),
    ('MS004', N'Trắng', 'ConHang'),
    ('MS005', N'Xanh dương', 'ConHang'),
    ('MS006', N'Đỏ', 'ConHang'),
    ('MS007', N'Xanh lá', 'ConHang'),
    ('MS008', N'Cam', 'ConHang'),
    ('MS009', N'Vàng', 'ConHang'),
    ('MS010', N'Tím', 'ConHang');
-- Size table
INSERT INTO Size (MaSize, TenSize, TrangThai)
VALUES ('SZ001', 'S', 'ConHang'),
    ('SZ002', 'M', 'ConHang'),
    ('SZ003', 'L', 'ConHang'),
    ('SZ004', 'XL', 'ConHang'),
    ('SZ005', 'XXL', 'ConHang'),
    ('SZ006', '28', 'ConHang'),
    ('SZ007', '30', 'ConHang'),
    ('SZ008', '32', 'ConHang'),
    ('SZ009', '34', 'ConHang'),
    ('SZ010', '36', 'ConHang');
-- ChatLieu table
INSERT INTO ChatLieu (MaChatLieu, TenChatLieu, TrangThai)
VALUES ('CL001', N'Polyester', 'ConHang'),
    ('CL002', N'Cotton', 'ConHang'),
    ('CL003', N'Nylon', 'ConHang'),
    ('CL004', N'Spandex', 'ConHang'),
    ('CL005', N'Fleece', 'ConHang'),
    ('CL006', N'Vải dệt kim', 'ConHang'),
    ('CL007', N'Vải dù', 'ConHang'),
    ('CL008', N'Vải thun', 'ConHang'),
    ('CL009', N'Vải len', 'ConHang'),
    ('CL010', N'Vải kaki', 'ConHang');
-- DoDay table
INSERT INTO DoDay (MaDoDay, TenDoDay, TrangThai)
VALUES ('DD001', N'Mỏng', 'ConHang'),
    ('DD002', N'Vừa', 'ConHang'),
    ('DD003', N'Dày', 'ConHang'),
    ('DD004', N'Rất mỏng', 'ConHang'),
    ('DD005', N'Rất dày', 'ConHang'),
    ('DD006', N'Nhẹ', 'ConHang'),
    ('DD007', N'Trung bình', 'ConHang'),
    ('DD008', N'Nặng', 'ConHang'),
    ('DD009', N'Siêu nhẹ', 'ConHang'),
    ('DD010', N'Siêu dày', 'ConHang');
-- SanPhamChiTiet table
INSERT INTO SanPhamChiTiet (
        MaSPCT,
        TenSPCT,
        SoLuong,
        IdSanPham,
        IdMauSac,
        IdSize,
        IdChatLieu,
        IdDoDay,
        IdNhaCungCap,
        NgayTao,
        GiaNhap,
        GiaBan,
        TrangThai,
        HienThi
    )
VALUES (
        'SPCT001',
        N'Quần dài Adidas Originals đen size S',
        50,
        1,
        1,
        1,
        1,
        2,
        1,
        '2024-07-01',
        300000,
        500000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT002',
        N'Quần dài Adidas Sportswear xanh navy size M',
        50,
        2,
        2,
        2,
        2,
        2,
        1,
        '2024-07-01',
        320000,
        550000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT003',
        N'Quần dài Adidas Terrex xám size L',
        50,
        3,
        3,
        3,
        3,
        3,
        2,
        '2024-07-01',
        350000,
        600000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT004',
        N'Quần dài Adidas Performance trắng size XL',
        50,
        4,
        4,
        4,
        4,
        2,
        2,
        '2024-07-01',
        330000,
        580000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT005',
        N'Quần dài Adidas Essentials xanh dương size XXL',
        50,
        5,
        5,
        5,
        5,
        2,
        3,
        '2024-07-01',
        280000,
        480000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT006',
        N'Quần dài Adidas Adicolor đỏ size 30',
        50,
        6,
        6,
        7,
        6,
        1,
        3,
        '2024-07-01',
        310000,
        520000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT007',
        N'Quần dài Adidas Z.N.E. xanh lá size 32',
        50,
        7,
        7,
        8,
        7,
        3,
        4,
        '2024-07-01',
        370000,
        620000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT008',
        N'Quần dài Adidas 4DFWD cam size 34',
        50,
        8,
        8,
        9,
        8,
        1,
        4,
        '2024-07-01',
        400000,
        650000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT009',
        N'Quần dài Adidas Primeblue vàng size 36',
        50,
        9,
        9,
        10,
        9,
        2,
        5,
        '2024-07-01',
        380000,
        630000,
        N'Còn hàng',
        N'Hien'
    ),
    (
        'SPCT010',
        N'Quần dài Adidas Tiro tím size M',
        50,
        10,
        10,
        2,
        10,
        2,
        5,
        '2024-07-01',
        340000,
        570000,
        N'Còn hàng',
        N'Hien'
    );
-- NhanVien table
INSERT INTO NhanVien (
        MaNhanVien,
        HoTen,
        SoDT,
        CCCD,
        NgaySinh,
        ChucVu,
        GioiTinh,
        DiaChi,
        TaiKhoan,
        MatKhau,
        NgayTao,
        TrangThai
    )
VALUES (
        'NV001',
        N'Nguyễn Văn An',
        '0901234567',
        '001234567890',
        '1990-01-01',
        N'nv',
        1,
        N'456 Đường Nguyễn Trãi, Quận 5, TP.HCM',
        'nva',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV002',
        N'Trần Thị Bình',
        '0912345678',
        '001234567891',
        '1992-05-15',
        N'nv',
        0,
        N'789 Đường Lý Thường Kiệt, Quận 10, TP.HCM',
        'ttb',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV003',
        N'Lê Văn Cường',
        '0923456789',
        '001234567892',
        '1988-09-20',
        N'nv',
        1,
        N'101 Đường Cách Mạng Tháng 8, Quận 3, TP.HCM',
        'lvc',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV004',
        N'Phạm Thị Dung',
        '0934567890',
        '001234567893',
        '1995-03-10',
        N'nv',
        0,
        N'202 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM',
        'ptd',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV005',
        N'Hoàng Văn Em',
        '0945678901',
        '001234567894',
        '1993-07-25',
        N'nv',
        1,
        N'303 Đường Võ Văn Tần, Quận 3, TP.HCM',
        'hve',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV006',
        N'Ngô Thị Phương',
        '0956789012',
        '001234567895',
        '1991-11-30',
        N'nv',
        0,
        N'404 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM',
        'ntp',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV007',
        N'Đặng Văn Quang',
        '0967890123',
        '001234567896',
        '1994-02-14',
        N'nv',
        1,
        N'505 Đường Nguyễn Thị Minh Khai, Quận 1, TP.HCM',
        'dvq',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV008',
        N'Mai Thị Hoa',
        '0978901234',
        '001234567897',
        '1989-06-05',
        N'nv',
        0,
        N'606 Đường Trần Hưng Đạo, Quận 1, TP.HCM',
        'mth',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV009',
        N'Trương Văn Khoa',
        '0989012345',
        '001234567898',
        '1996-10-18',
        N'nv',
        1,
        N'707 Đường Lê Hồng Phong, Quận 5, TP.HCM',
        'tvk',
        '123',
        '2023-01-01',
        1
    ),
    (
        'NV010',
        N'Lý Thị Lan',
        '0990123456',
        '001234567899',
        '1987-12-22',
        N'nv',
        0,
        N'808 Đường Hai Bà Trưng, Quận 1, TP.HCM',
        'ltl',
        '123',
        '2023-01-01',
        1
    );
-- KhachHang table
INSERT INTO KhachHang (
        MaKhachHang,
        HoTen,
        GioiTinh,
        SoDT,
        DiaChi,
        NgayTao,
        NguoiTao
    )
VALUES (
        'KH000',
        N'Khách Bán Lẻ',
        1,
        '0969477050',
        N'Dia CHi Admin',
        '2024-07-01',
        N'admin'
    ),
    (
        'KH001',
        N'Lê Văn Cường',
        1,
        '0923456789',
        N'101 Đường Cách Mạng Tháng 8, Quận 3, TP. HCM',
        '2024-07-01',
        N'admin'
    ),
    (
        'KH002',
        N'Phạm Thị Dung',
        0,
        '0934567890',
        N'202 Đường Nguyễn Văn Cừ, Quận 5, TP. HCM',
        '2024-07-01',
        N'admin'
    ),
    (
        'KH003',
        N'Trần Minh Đức',
        1,
        '0945678901',
        N'303 Đường Lê Đại Hành, Quận 11, TP. HCM',
        '2024-07-02',
        N'admin'
    ),
    (
        'KH004',
        N'Nguyễn Thị Em',
        0,
        '0956789012',
        N'404 Đường Nguyễn Thị Minh Khai, Quận 1, TP. HCM',
        '2024-07-02',
        N'admin'
    ),
    (
        'KH005',
        N'Hoàng Văn Phúc',
        1,
        '0967890123',
        N'505 Đường Võ Thị Sáu, Quận 3, TP. HCM',
        '2024-07-03',
        N'admin'
    ),
    (
        'KH006',
        N'Đặng Thị Giang',
        0,
        '0978901234',
        N'606 Đường Trần Phú, Quận 5, TP. HCM',
        '2024-07-03',
        N'admin'
    ),
    (
        'KH007',
        N'Bùi Văn Hùng',
        1,
        '0989012345',
        N'707 Đường Nguyễn Đình Chiểu, Quận 3, TP. HCM',
        '2024-07-04',
        N'admin'
    ),
    (
        'KH008',
        N'Mai Thị Lan',
        0,
        '0990123456',
        N'808 Đường Lý Tự Trọng, Quận 1, TP. HCM',
        '2024-07-04',
        N'admin'
    ),
    (
        'KH009',
        N'Trương Văn Khánh',
        1,
        '0901234567',
        N'909 Đường Nguyễn Trãi, Quận 5, TP. HCM',
        '2024-07-05',
        N'admin'
    ),
    (
        'KH010',
        N'Lý Thị Mai',
        0,
        '0912345678',
        N'1010 Đường Điện Biên Phủ, Quận Bình Thạnh, TP. HCM',
        '2024-07-05',
        N'admin'
    );
-- Voucher table
INSERT INTO Voucher (
        MaVoucher,
        GiaTriVoucher,
        NgayBatDau,
        NgayKetThuc,
        SoLuong,
        MoTa,
        TrangThai
    )
VALUES (
        'VC001',
        50000,
        '2024-07-01',
        '2024-12-31',
        100,
        N'Giảm giá 50,000đ cho quần dài Adidas',
        1
    ),
    (
        'VC002',
        100000,
        '2024-07-01',
        '2024-12-31',
        50,
        N'Giảm giá 100,000đ cho đơn hàng từ 1,000,000đ',
        2
    ),
    (
        'VC003',
        150000,
        '2024-07-01',
        '2024-12-31',
        30,
        N'Giảm giá 150,000đ cho đơn hàng từ 1,500,000đ',
        3
    ),
    (
        'VC004',
        200000,
        '2024-07-01',
        '2024-12-31',
        20,
        N'Giảm giá 200,000đ cho đơn hàng từ 2,000,000đ',
        2
    ),
    (
        'VC005',
        250000,
        '2024-07-01',
        '2024-12-31',
        10,
        N'Giảm giá 250,000đ cho đơn hàng từ 2,500,000đ',
        2
    ),
    (
        'VC006',
        75000,
        '2024-07-01',
        '2024-12-31',
        80,
        N'Giảm giá 75,000đ cho quần dài Adidas Originals',
        2
    ),
    (
        'VC007',
        125000,
        '2024-07-01',
        '2024-12-31',
        40,
        N'Giảm giá 125,000đ cho đơn hàng từ 1,250,000đ',
        2
    ),
    (
        'VC008',
        175000,
        '2024-07-01',
        '2024-12-31',
        25,
        N'Giảm giá 175,000đ cho đơn hàng từ 1,750,000đ',
        2
    ),
    (
        'VC009',
        225000,
        '2024-07-01',
        '2024-12-31',
        15,
        N'Giảm giá 225,000đ cho đơn hàng từ 2,250,000đ',
        2
    ),
    (
        'VC010',
        300000,
        '2024-07-01',
        '2024-12-31',
        5,
        N'Giảm giá 300,000đ cho đơn hàng từ 3,000,000đ',
        2
    );
-- PhuongThucThanhToan table
-- Bảng PhuongThucThanhToan
INSERT INTO PhuongThucThanhToan (TenPhuongThucTT, TrangThai)
VALUES (N'Tiền mặt', 1),
    (N'Chuyển khoản', 1);
-- Bảng HoaDon
INSERT INTO HoaDon (
        MaHD,
        IDKhachHang,
        IDNhanVien,
        IDVoucher,
        TongGiaTriHoaDon,
        ThanhToan,
        IDPhuongThucTT,
        NgayTao,
        TrangThai,
        GhiChu
    )
VALUES (
        'HD001',
        1,
        1,
        1,
        950000,
        950000,
        1,
        '2024-07-01',
        0,
        N'Ghi chú cho HD001'
    ),
    (
        'HD002',
        2,
        2,
        NULL,
        500000,
        500000,
        2,
        '2024-07-02',
        0,
        N'Ghi chú cho HD002'
    ),
    (
        'HD003',
        3,
        3,
        2,
        1450000,
        1450000,
        1,
        '2024-07-03',
        0,
        N'Ghi chú cho HD003'
    ),
    (
        'HD004',
        4,
        4,
        NULL,
        1100000,
        1100000,
        2,
        '2024-07-04',
        1,
        N'Ghi chú cho HD004'
    ),
    (
        'HD005',
        5,
        5,
        3,
        1800000,
        1800000,
        1,
        '2024-07-05',
        1,
        N'Ghi chú cho HD005'
    ),
    (
        'HD006',
        6,
        6,
        NULL,
        570000,
        570000,
        2,
        '2024-07-06',
        0,
        N'Ghi chú cho HD006'
    ),
    (
        'HD007',
        7,
        7,
        4,
        2250000,
        2250000,
        1,
        '2024-07-07',
        1,
        N'Ghi chú cho HD007'
    ),
    (
        'HD008',
        8,
        8,
        NULL,
        1300000,
        1300000,
        2,
        '2024-07-08',
        0,
        N'Ghi chú cho HD008'
    ),
    (
        'HD009',
        9,
        9,
        5,
        2700000,
        2700000,
        1,
        '2024-07-09',
        1,
        N'Ghi chú cho HD009'
    ),
    (
        'HD010',
        10,
        10,
        NULL,
        630000,
        630000,
        2,
        '2024-07-10',
        0,
        N'Ghi chú cho HD010'
    );
-- HoaDonCT table
INSERT INTO HoaDonCT (IDHoaDon, IDCTSP, DonGia, TrangThai, SoLuong)
VALUES (1, 1, 500000, 1, 1),
    (1, 2, 550000, 1, 1),
    (2, 3, 600000, 1, 1),
    (3, 4, 580000, 1, 1),
    (3, 5, 480000, 1, 2),
    (4, 6, 520000, 1, 1),
    (4, 7, 620000, 1, 1),
    (5, 8, 650000, 1, 2),
    (5, 9, 630000, 1, 1),
    (6, 10, 570000, 1, 1),
    (7, 1, 500000, 1, 2),
    (7, 2, 550000, 1, 2),
    (7, 3, 600000, 1, 1),
    (8, 4, 580000, 1, 1),
    (8, 5, 480000, 1, 1),
    (8, 6, 520000, 1, 1),
    (9, 7, 620000, 1, 2),
    (9, 8, 650000, 1, 2),
    (9, 9, 630000, 1, 1),
    (10, 10, 570000, 1, 1);

use  DuAn1_Final
select * from SanPhamChiTiet
	
SELECT 
                    spct.id,
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
                    NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID
                WHERE spct.hienthi = 'hien'
                ORDER BY spct.SoLuong asc

SELECT 
    (SELECT COUNT(*) FROM HoaDon) AS TotalOrders,
    (SELECT SUM(TongGiaTriHoaDon) FROM HoaDon) AS TotalRevenue,
    (SELECT SUM(SoLuong * GiaNhap) FROM SanPhamChiTiet) AS TotalCost,
    (SELECT SUM(TongGiaTriHoaDon) FROM HoaDon) - (SELECT SUM(SoLuong * GiaNhap) FROM SanPhamChiTiet) AS TotalProfit


