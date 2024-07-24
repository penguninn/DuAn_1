package com.daipc.form;

import com.daipc.ScrollBar.ScrollbarCustom;
import com.daipc.customTable.PanelButtonCellEditor;
import com.daipc.customTable.PanelButtonCellRender;
import com.daipc.customTable.QuantityCellEditor;
import com.daipc.model.ChiTietSP;
import com.daipc.model.GioHang;
import com.daipc.model.HoaDonCho;
import com.daipc.repo.QuanLiBanHang;
import com.daipc.table.TableCustom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.daipc.customTable.TableEvent;
import com.daipc.enumm.TrangThaiCRUD;
import com.daipc.model.HoaDon;
import com.daipc.model.KhachHang;
import com.daipc.model.Voucher;
import javax.swing.JScrollBar;

public class Form_Sell extends javax.swing.JPanel {

    private QuanLiBanHang QLBH = new QuanLiBanHang();

    private List<HoaDonCho> listHDC = new ArrayList<>();
    private int selectedRowHDC = -1;

    private List<GioHang> listGH = new ArrayList<>();
    private int selectedRowGH = -1;

    private List<ChiTietSP> listSP = new ArrayList<>();
    private int selectedRowSP = -1;

    private List<KhachHang> listKH = new ArrayList<>();
    private int selectedRowKH = -1;

    private List<Voucher> listVoucher = new ArrayList<>();
    private List<HoaDon> listHD = new ArrayList<>();

    private DefaultTableModel modelHDC;
    private DefaultTableModel modelGH;
    private DefaultTableModel modelSP;

    public Form_Sell() {
        initComponents();
        TableCustom.apply(scrollHoaDonCho, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollGioHang, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollDanhSachSanPham, TableCustom.TableType.DEFAULT);

        modelHDC = (DefaultTableModel) tblHoaDonCho.getModel();
        modelGH = (DefaultTableModel) tblGioHang.getModel();
        modelSP = (DefaultTableModel) tblDanhSachSanPham.getModel();

        scrollMoTa.setVerticalScrollBar(new ScrollbarCustom());
        ScrollbarCustom sp = new ScrollbarCustom();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scrollMoTa.setHorizontalScrollBar(sp);

        initTableHD();
        initTableGH();
        loadDataHDC();
        loadDataSP();
        loadDataVoucher();
    }

    public void loadDataHDC() {
        modelHDC.setRowCount(0);
        listHDC.clear();
        listHDC.addAll(
                QLBH.selectAllHDC(
                        """
                            select hd.id, hd.MaHD, kh.HoTen, hd.NguoiTao, hd.idvoucher, hd.thanhtoan, hd.NgayTao, hd.TrangThai, hd.TongGiaTriHoaDon, kh.sodt
                            from hoadon hd
                            inner join KhachHang kh on hd.IDKhachHang = kh.ID
                            INNER join NhanVien nv on hd.IDNhanVien = nv.ID
                            WHERE hd.TrangThai = ?
                        """, 0));
        for (HoaDonCho hd : listHDC) {
            modelHDC.addRow(hd.getHDC());
        }
        selectedRowHDC = listHDC.size() - 1;
        if (selectedRowHDC >= 0) {
            tblHoaDonCho.setRowSelectionInterval(selectedRowHDC, selectedRowHDC);
        }
    }

    public void loadDataGH() {
        modelGH.setRowCount(0);
        for (GioHang gh : listGH) {
            modelGH.addRow(gh.getGioHang());
        }
        selectedRowGH = listGH.size() - 1;
        if (selectedRowGH >= 0) {
            tblGioHang.setRowSelectionInterval(selectedRowGH, selectedRowGH);
        }
    }

    public void loadDataSP() {
        modelSP.setRowCount(0);
        listSP.clear();
        listSP.addAll(QLBH.selectAllSPCT());
        for (ChiTietSP sp : listSP) {
            modelSP.addRow(sp.getSPCT());
        }
        selectedRowSP = listSP.size() - 1;
        if (selectedRowSP >= 0) {
            tblDanhSachSanPham.setRowSelectionInterval(selectedRowSP, selectedRowSP);
        }
    }

    public void loadDataVoucher() {
        listVoucher.clear();
        listVoucher.addAll(QLBH.getAllVoucher());
        for (Voucher v : listVoucher) {
            cboVoucher.addItem(v.getMaVoucher());
        }
        cboVoucher.setSelectedIndex(-1);
    }

    public void initTableHD() {
        TableEvent event = new TableEvent() {
            int rowHDC = 0;
            String sqlSofDelete = """
                                        update hoadon set trangthai = 2 where id = ?
                                    """;

            @Override
            public void onDelete(int row) {
                if (tblHoaDonCho.isEditing()) {
                    tblHoaDonCho.getCellEditor().stopCellEditing();
                }
                rowHDC = row;
                int id = listHDC.get(row).getId();
                if (QLBH.update(sqlSofDelete, id) == TrangThaiCRUD.ThanhCong) {
                    JOptionPane.showMessageDialog(null, "Xóa Hóa Đơn Chờ Thành Công !!!");
                    loadDataHDC();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa Hóa Đơn Chờ Thất Bại !!!");
                }

            }

            @Override
            public void quantity(int qty) {

            }

        };
        tblHoaDonCho.getColumnModel().getColumn(5).setCellRenderer(new PanelButtonCellRender(tblHoaDonCho));
        tblHoaDonCho.getColumnModel().getColumn(5).setCellEditor(new PanelButtonCellEditor(event));
    }

    public void initTableGH() {

        TableEvent event = new TableEvent() {
            int rowGH = 0;

            @Override
            public void onDelete(int row) {
                if (tblGioHang.isEditing()) {
                    tblGioHang.getCellEditor().stopCellEditing();
                }
                rowGH = row;
                listGH.remove(row);
                loadDataGH();
            }

            @Override
            public void quantity(int qty) {

            }

        };

        tblGioHang.getColumnModel().getColumn(5).setCellRenderer(new PanelButtonCellRender(tblGioHang));
        tblGioHang.getColumnModel().getColumn(5).setCellEditor(new PanelButtonCellEditor(event));
        tblGioHang.getColumnModel().getColumn(3).setCellEditor(new QuantityCellEditor(event));
    }

    public void selectionRowGH(int id) {
        listGH.clear();
        listGH.addAll(
                QLBH.selectAllGH(
                        """
                            select spct.MaSPCT, spct.TenSPCT, spct.GiaBan, hdct.SoLuong, spct.GiaBan * hdct.SoLuong, hdct.TrangThai from HoaDonCT hdct 
                                                        join SanPhamChiTiet spct on hdct.IDCTSP = spct.id
                                                        where hdct.IDHoaDon = ? and hdct.TrangThai = ?    
                        """, id, 1));
        loadDataGH();
    }

    public String autoTangMaHD() {
        listHD.clear();
        listHD.addAll(QLBH.selectAllHD());
        String maHDCuoiCung = listHD.get(listHD.size() - 1).getMaHD();
        return String.format("HD%03d", Integer.parseInt(maHDCuoiCung.substring(2)) + 1);
    }

    public void clearFormHD() {
        txtTenKH.setText("Khách Bán Lẻ");
        txtSDT.setText("");
        txtMaHD.setText("");
        txtThanhToan.setText("0");
        txtTongTien.setText("0");
        txtTienKhachDua.setText("");
        txtTienThua.setText("0");
        txtGhiChu.setText("");
        cboVoucher.setSelectedIndex(-1);
        cboHinhThucTT.setSelectedIndex(-1);
    }

    public void showDetails(HoaDonCho hdc) {
        txtTenKH.setText(hdc.getTenKhachHang());
        txtSDT.setText(hdc.getSDT());
        txtMaHD.setText(hdc.getMaHD());
        txtTongTien.setText(String.valueOf(hdc.getTong()));
        cboVoucher.setSelectedItem(hdc.getVoucher());
        txtThanhToan.setText(String.valueOf(hdc.getThanhToan()));

    }

//    @SuppressWarnings("unchecked");
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.daipc.swing.PanelBorder();
        scrollHoaDonCho = new javax.swing.JScrollPane();
        tblHoaDonCho = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        panelBorder8 = new com.daipc.swing.PanelBorder();
        scrollDanhSachSanPham = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        panelBorder6 = new com.daipc.swing.PanelBorder();
        scrollGioHang = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        panelBorder7 = new com.daipc.swing.PanelBorder();
        jLabel12 = new javax.swing.JLabel();
        txtSDT = new com.daipc.textfield.TextField();
        jLabel13 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JLabel();
        btnTaoHD = new com.daipc.swing.Button();
        btnKiemTra = new com.daipc.swing.Button();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnHuy = new com.daipc.swing.Button();
        btnLamMoi = new com.daipc.swing.Button();
        scrollMoTa = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThanhToan = new com.daipc.swing.Button();
        txtMaHD = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JLabel();
        cboHinhThucTT = new com.daipc.combobox.Combobox();
        txtTienKhachDua = new com.daipc.textfield.TextField();
        cboVoucher = new com.daipc.combobox.Combobox();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(234, 234, 234));

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        tblHoaDonCho.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblHoaDonCho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Tên Khách Hàng", "Người Tạo", "Ngày Tạo", "Tổng", "Thao Tác"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonCho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChoMouseClicked(evt);
            }
        });
        scrollHoaDonCho.setViewportView(tblHoaDonCho);

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setText("Hóa Đơn Chờ");

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollHoaDonCho, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addGap(10, 10, 10)
                .addComponent(scrollHoaDonCho, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBorder8.setBackground(new java.awt.Color(255, 255, 255));

        tblDanhSachSanPham.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Màu Sắc", "Kích Thước", "Chất Liệu", "Độ Dày", "Nhà Cung Cấp", "Tồn Kho"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSanPhamMouseClicked(evt);
            }
        });
        scrollDanhSachSanPham.setViewportView(tblDanhSachSanPham);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setText("Sản Phẩm");

        javax.swing.GroupLayout panelBorder8Layout = new javax.swing.GroupLayout(panelBorder8);
        panelBorder8.setLayout(panelBorder8Layout);
        panelBorder8Layout.setHorizontalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDanhSachSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelBorder8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder8Layout.setVerticalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder8Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollDanhSachSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelBorder6.setBackground(new java.awt.Color(255, 255, 255));

        tblGioHang.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền", "Thao Tác"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.setRowHeight(40);
        scrollGioHang.setViewportView(tblGioHang);

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setText("Giỏ Hàng");

        javax.swing.GroupLayout panelBorder6Layout = new javax.swing.GroupLayout(panelBorder6);
        panelBorder6.setLayout(panelBorder6Layout);
        panelBorder6Layout.setHorizontalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelBorder6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder6Layout.setVerticalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBorder7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setText("Khách Hàng");

        txtSDT.setLabelText("SĐT Khách Hàng");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setText("Khách Hàng");

        txtTenKH.setText("Khách Bán Lẻ ");

        btnTaoHD.setText("Tạo");
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        btnKiemTra.setText("Kiểm Tra");
        btnKiemTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKiemTraActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setText("Mã Hóa Đơn");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setText("Tổng Tiền");

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setText("Thanh Toán");

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setText("Tiền Thừa Trả KHách");

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel21.setText("Ghi Chú");

        btnHuy.setText("Hủy Hóa Đơn");

        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        scrollMoTa.setViewportView(txtGhiChu);

        btnThanhToan.setText("Thanh Toán");

        txtMaHD.setText("...");

        txtTongTien.setText("0");

        txtThanhToan.setText("0");

        txtTienThua.setText("0");

        cboHinhThucTT.setLabeText("Hình Thức Thanh Toán");

        txtTienKhachDua.setLabelText("Tiền Khách Đưa");

        cboVoucher.setLabeText("Voucher");

        jLabel3.setText("VNĐ");

        jLabel6.setText("VNĐ");

        jLabel8.setText("VNĐ");

        jLabel18.setText("VNĐ");

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setText("Đơn Hàng");

        javax.swing.GroupLayout panelBorder7Layout = new javax.swing.GroupLayout(panelBorder7);
        panelBorder7.setLayout(panelBorder7Layout);
        panelBorder7Layout.setHorizontalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollMoTa)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                        .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorder7Layout.createSequentialGroup()
                                .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelBorder7Layout.createSequentialGroup()
                                .addComponent(txtTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8))
                            .addGroup(panelBorder7Layout.createSequentialGroup()
                                .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3))
                            .addGroup(panelBorder7Layout.createSequentialGroup()
                                .addComponent(txtThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))))
                    .addComponent(cboVoucher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel21)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel18))
                                .addGroup(panelBorder7Layout.createSequentialGroup()
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnKiemTra, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cboHinhThucTT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
            .addGroup(panelBorder7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorder7Layout.createSequentialGroup()
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder7Layout.setVerticalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTenKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKiemTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMaHD)
                    .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTongTien)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(cboVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtThanhToan)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTienThua)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(cboHinhThucTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorder7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDanhSachSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachSanPhamMouseClicked
        if (evt.getClickCount() == 2) {
            selectedRowHDC = tblHoaDonCho.getSelectedRow();
            selectedRowSP = tblDanhSachSanPham.getSelectedRow();
            int soLuong = 0;
            String inputValue = JOptionPane.showInputDialog(null, "Nhập số lượng:", "", JOptionPane.QUESTION_MESSAGE);
            if (inputValue != null) {
                try {
                    soLuong = Integer.parseInt(inputValue);
                    if (soLuong < 0) {
                        JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Lỗi định dạng. Nhấp số nguyên lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
            String sqlInsert = """
                                insert into HoaDonCT (IDHoaDon, IDCTSP, DonGia, TrangThai, SoLuong) 
                                Values (?, ?, ?, ?, ?)
                               """;
            if (soLuong > 0) {

                ChiTietSP sp = listSP.get(selectedRowSP);
                HoaDonCho hdc = listHDC.get(selectedRowHDC);

                QLBH.update(sqlInsert, hdc.getId(), sp.getId(), sp.getGiaBan(), 1, soLuong);
                selectionRowGH(hdc.getId());
            }

        }
    }//GEN-LAST:event_tblDanhSachSanPhamMouseClicked

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        clearFormHD();
        txtMaHD.setText(autoTangMaHD());
        String sqlInsert = """
                            INSERT INTO HoaDon 
                            (MaHD, IDKhachHang, IDNhanVien, IDVoucher, TongGiaTriHoaDon, ThanhToan, NgayTao, TrangThai)
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?) 
                           """;
        if (QLBH.update(sqlInsert, txtMaHD.getText(), 1, 1, 1, 0, 0, LocalDate.now(), 0) == TrangThaiCRUD.ThanhCong) {
            JOptionPane.showMessageDialog(null, "Tạo Hóa Đơn Thành Công !!!");
        }
        loadDataHDC();
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void tblHoaDonChoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChoMouseClicked
        selectedRowHDC = tblHoaDonCho.getSelectedRow();
        int id = listHDC.get(selectedRowHDC).getId();
        selectionRowGH(id);
        showDetails(listHDC.get(selectedRowHDC));
    }//GEN-LAST:event_tblHoaDonChoMouseClicked

    private void btnKiemTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKiemTraActionPerformed
        String sqlUpdate = """
                            Update HoaDon 
                            set IDKhachHang = ? where id = ?
                           """;
        selectedRowHDC = tblHoaDonCho.getSelectedRow();
        int id = listHDC.get(selectedRowHDC).getId();
        listKH.addAll(QLBH.getAllKH());
        for (KhachHang kh : listKH) {
            if (txtSDT.getText().equals(kh.getSoDT())) {
                if (QLBH.update(sqlUpdate, kh.getId(), id) == TrangThaiCRUD.ThanhCong) {
                    JOptionPane.showMessageDialog(null, "Cập Nhật Khách Hàng Thành Công !!!");
                }
            }
        }
        loadDataHDC();

    }//GEN-LAST:event_btnKiemTraActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearFormHD();
    }//GEN-LAST:event_btnLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.swing.Button btnHuy;
    private com.daipc.swing.Button btnKiemTra;
    private com.daipc.swing.Button btnLamMoi;
    private com.daipc.swing.Button btnTaoHD;
    private com.daipc.swing.Button btnThanhToan;
    private com.daipc.combobox.Combobox cboHinhThucTT;
    private com.daipc.combobox.Combobox cboVoucher;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.daipc.swing.PanelBorder panelBorder1;
    private com.daipc.swing.PanelBorder panelBorder6;
    private com.daipc.swing.PanelBorder panelBorder7;
    private com.daipc.swing.PanelBorder panelBorder8;
    private javax.swing.JScrollPane scrollDanhSachSanPham;
    private javax.swing.JScrollPane scrollGioHang;
    private javax.swing.JScrollPane scrollHoaDonCho;
    private javax.swing.JScrollPane scrollMoTa;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDonCho;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JLabel txtMaHD;
    private com.daipc.textfield.TextField txtSDT;
    private javax.swing.JLabel txtTenKH;
    private javax.swing.JLabel txtThanhToan;
    private com.daipc.textfield.TextField txtTienKhachDua;
    private javax.swing.JLabel txtTienThua;
    private javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables
}
