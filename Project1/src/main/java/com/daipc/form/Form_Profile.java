/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.daipc.form;

import com.daipc.model.ChiTietSP;
import com.daipc.model.HoaDonModel;
import com.daipc.model.NhanVien;
import com.daipc.modelUI.DashedBorder;
import com.daipc.modelUI.GradientPainter;
import com.daipc.repo.HoaDonDao;
import com.daipc.table.TableCustom;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author vitvu
 */
public class Form_Profile extends javax.swing.JPanel {

    private HoaDonDao hdDao = new HoaDonDao();
    private String MaNV = null;
    private String MaHD = null;
    private String mkCu = null;
    private String selectedItem = null;
    NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
    private int i = -1;

    public Form_Profile(NhanVien nv) {
        initComponents();
        userInfor(nv);
        init();
        showHD(0);
        tblHD.setRowSelectionInterval(0, 0);
    }

    public void init() {
        cbo_loctheo.removeAllItems();
        DefaultComboBoxModel modelLoctheo = (DefaultComboBoxModel) cbo_loctheo.getModel();
        modelLoctheo.addElement("Ngày");
        modelLoctheo.addElement("Tuần");
        modelLoctheo.addElement("Tháng");

        GradientPainter grdptr = new GradientPainter();
        grdptr.applyGradient(panelBorder1, new Color(255, 148, 105), new Color(253, 144, 155));
        grdptr.applyGradient(panelBorder6, new Color(0, 196, 132), new Color(21, 216, 222));

        TableCustom.apply(jScrollPane2, TableCustom.TableType.DEFAULT);

//        panelBorder4.setBorder(new EmptyBorder(0, 0, 5, 0));
//          panelBorder4.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
        Color borderColor = new Color(0, 0, 0); // Màu sắc của border
        float[] dashPattern = {10, 5}; // Định dạng nét đứt
        int thickness = 2; // Độ dày của border
        panelBorder10.setBorder(new DashedBorder(borderColor, dashPattern, thickness));
//        panelBorder11.setBorder(new DashedBorder(borderColor, dashPattern, thickness));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
//              load lai data khi co thay doi ben form khac

                init();
            }

        });
    }

    public void handleFilterDate(String selectedItem) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusWeeks(1);
        LocalDate oneMonthAgo = today.minusMonths(1);

        String todayfmt = today.format(formatter);
        String owafmt = oneWeekAgo.format(formatter);
        String oma = oneMonthAgo.format(formatter);

        if (selectedItem != null) {
            if (selectedItem.equalsIgnoreCase("Ngày")) {
                this.fillTable(todayfmt, todayfmt);
                lable_don.setText("đơn / ngày");
                label_ngay.setText("VNĐ / ngày");
            }
            if (selectedItem.equalsIgnoreCase("Tuần")) {
                this.fillTable(todayfmt, owafmt);
                lable_don.setText("đơn / Tuần");
                label_ngay.setText("VNĐ / Tuần");
            }
            if (selectedItem.equalsIgnoreCase("Tháng")) {
                this.fillTable(todayfmt, oma);
                lable_don.setText("đơn / Tháng");
                label_ngay.setText("VNĐ / Tháng");
            }
        }
    }

    public void userInfor(NhanVien nv) {
        this.MaNV = nv.getMaNhanVien();
        this.mkCu = nv.getMatKhau();

        txt_ngaytao.setText(nv.getNgayTao().toString());
        txt_mnv.setText(nv.getMaNhanVien());
        txt_hoten.setText(nv.getHoTen());
        txt_ngaysinh.setText(nv.getNgaySinh().toString());
        if (nv.isGioiTinh()) {
            rdo_nam.setSelected(true);
        } else {
            rdo_nu.setSelected(true);
        }
        txt_sdt.setText(nv.getSoDT());
        txt_cccd.setText(nv.getCccd());
        txt_cv.setText(nv.getChucVu().equalsIgnoreCase("ql") ? "Quản lý" : "Nhân viên bán hàng");
        if (nv.isTrangThai()) {
            rdo_danglam.setSelected(true);
        } else {
            rdo_danghi.setSelected(true);
        }
        txt_tk.setText(nv.getTaiKhoan());

        if (rdo_nam.isSelected()) {
            rdo_nu.setEnabled(false);
        } else {
            rdo_nam.setEnabled(false);
        }
        if (rdo_danglam.isSelected()) {
            rdo_danghi.setEnabled(false);
        } else {
            rdo_danglam.setEnabled(false);
        }
    }

    public void fillTable(String dateToday, String dateTarget) {
        if (this.MaNV != null) {
            int stt = 1;
            BigDecimal tongGiaTri = BigDecimal.ZERO;
            System.out.println("Tu " + dateToday + " den " + dateTarget);
            ArrayList<HoaDonModel> listHD = hdDao.getHDforNV(this.MaNV, dateToday, dateTarget);
            DefaultTableModel tblModel = (DefaultTableModel) tblHD.getModel();
            tblModel.setRowCount(0);

            for (HoaDonModel hoaDon : listHD) {
                tongGiaTri = tongGiaTri.add(hoaDon.getDonGia());

                tblModel.addRow(new Object[]{
                    stt++,
                    hoaDon.getMaHD(),
                    hoaDon.getNgayTao(),
                    numberFormat.format(hoaDon.getThanhToan()),
                    hoaDon.getTrangThai().equals("1") ? "Đã thanh toán" : "Chưa thanh toán"
                });
            }

            txt_SoLuongDH.setText(Integer.toString(stt - 1));
            txt_tongDT.setText(numberFormat.format(tongGiaTri).toString());
        }
    }
    
    public void showHD(int i) {
        this.MaHD = tblHD.getValueAt(i, 1).toString();
        ArrayList<HoaDonModel> hoadonModel = hdDao.getDataByMaHD(this.MaHD);
        HoaDonModel data = hoadonModel.get(0);
        
        this.fillSPCT(data.getMaHD());
        
        txt_mhd.setText(data.getMaHD());
        txt_tennv.setText(data.getTenNguoiTao());
        txt_ngaytao.setText(data.getNgayTao().toString());
        txt_tenkh.setText(data.getTenKH());
        txt_sdtkh.setText(data.getSdt());
        txt_tonggt.setText(numberFormat.format(data.getDonGia()).toString());
        txt_giamgia.setText(numberFormat.format(data.getDonGia().subtract(data.getThanhToan())).toString());
        txt_tt.setText(numberFormat.format(data.getThanhToan()));
        txt_phuongthuc.setText(data.getPttt().toString());
        txt_trangthai.setText(data.getTrangThai().equals(1) ? "Chưa thanh toán" : "Đã thanh toán");
    }
    
    public void fillSPCT(String maHD) {
        ArrayList<ChiTietSP> list = hdDao.getSPCTByMaHD(maHD);
        ChiTietSP ctsp = new ChiTietSP();
        panelShowSP.setLayout(new MigLayout("wrap 4", "[grow] [grow] [grow] [grow]", ""));
        panelShowSP.removeAll();
        for (ChiTietSP product : list) {
            BigDecimal tong = BigDecimal.valueOf(product.getSoLuong());
            String tensp = (String) product.getTenSP() + " - " + product.getTenMauSac() + " - " + product.getTenSize();
            String tien = (String) numberFormat.format(product.getGiaBan()).toString();
            String soluong = (String) "x" + product.getSoLuong();
            String thanhtien = (String) numberFormat.format(product.getGiaBan().multiply(tong)).toString();

            String productNameHTML = "<html>" + tensp.replaceAll("(.{20})", "$1<br/>") + "</html>";
            JLabel productNameLabel = new JLabel(productNameHTML);
            JLabel tienlabel = new JLabel(tien);
            JLabel jsoluong = new JLabel(soluong);
            JLabel thanhtienLabel = new JLabel(thanhtien);
            panelShowSP.add(productNameLabel, "left");
            panelShowSP.add(tienlabel, "left");
            panelShowSP.add(jsoluong, "center");
            panelShowSP.add(thanhtienLabel, "right");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGioiTinh = new javax.swing.ButtonGroup();
        btnGroupChucVu = new javax.swing.ButtonGroup();
        panelBorder5 = new com.daipc.swing.PanelBorder();
        cbo_loctheo = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        jLabel1 = new javax.swing.JLabel();
        panelBorder1 = new com.daipc.swing.PanelBorder();
        jLabel4 = new javax.swing.JLabel();
        panelBorder6 = new com.daipc.swing.PanelBorder();
        jLabel5 = new javax.swing.JLabel();
        panelBorder2 = new com.daipc.swing.PanelBorder();
        txt_tongDT = new javax.swing.JLabel();
        label_ngay = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelBorder7 = new com.daipc.swing.PanelBorder();
        jLabel3 = new javax.swing.JLabel();
        lable_don = new javax.swing.JLabel();
        txt_SoLuongDH = new javax.swing.JLabel();
        panelBorder8 = new com.daipc.swing.PanelBorder();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        panelBorder12 = new com.daipc.swing.PanelBorder();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_mnv = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_hoten = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txt_ngaysinh = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        rdo_nam = new javax.swing.JRadioButton();
        rdo_nu = new javax.swing.JRadioButton();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        txt_cccd = new javax.swing.JTextField();
        txt_cv = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        rdo_danglam = new javax.swing.JRadioButton();
        rdo_danghi = new javax.swing.JRadioButton();
        jLabel39 = new javax.swing.JLabel();
        txt_tk = new javax.swing.JTextField();
        btn_doimk = new com.daipc.swing.Button();
        txt_ngaytao = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelBorder3 = new com.daipc.swing.PanelBorder();
        jLabel18 = new javax.swing.JLabel();
        txt_mhd = new javax.swing.JLabel();
        panelBorder4 = new com.daipc.swing.PanelBorder();
        jLabel10 = new javax.swing.JLabel();
        txt_tennv = new javax.swing.JLabel();
        txt_ngaytaohd = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_tenkh = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_sdtkh = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panelBorder10 = new com.daipc.swing.PanelBorder();
        panelShowSP = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelBorder9 = new com.daipc.swing.PanelBorder();
        jLabel23 = new javax.swing.JLabel();
        txt_trangthai = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_giamgia = new javax.swing.JLabel();
        txt_tt = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_phuongthuc = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_tonggt = new javax.swing.JLabel();

        panelBorder5.setPreferredSize(new java.awt.Dimension(1324, 1200));

        cbo_loctheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_loctheoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("THỐNG KÊ");

        panelBorder1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Đơn hàng");

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBorder6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Doanh thu");

        javax.swing.GroupLayout panelBorder6Layout = new javax.swing.GroupLayout(panelBorder6);
        panelBorder6.setLayout(panelBorder6Layout);
        panelBorder6Layout.setHorizontalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder6Layout.setVerticalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        panelBorder2.setBackground(new java.awt.Color(255, 255, 255));

        txt_tongDT.setFont(new java.awt.Font("Helvetica Neue", 1, 26)); // NOI18N
        txt_tongDT.setForeground(new java.awt.Color(0, 196, 132));
        txt_tongDT.setText("jLabel8");

        label_ngay.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        label_ngay.setForeground(new java.awt.Color(204, 204, 204));
        label_ngay.setText("VNĐ / ngày");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/moneyicon.png"))); // NOI18N

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tongDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addComponent(label_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(41, 41, 41))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addComponent(txt_tongDT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_ngay)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBorder7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/shoppingbags.png"))); // NOI18N

        lable_don.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        lable_don.setForeground(new java.awt.Color(204, 204, 204));
        lable_don.setText("đơn / ngày");

        txt_SoLuongDH.setFont(new java.awt.Font("Helvetica Neue", 1, 26)); // NOI18N
        txt_SoLuongDH.setForeground(new java.awt.Color(255, 148, 105));
        txt_SoLuongDH.setText("17");

        javax.swing.GroupLayout panelBorder7Layout = new javax.swing.GroupLayout(panelBorder7);
        panelBorder7.setLayout(panelBorder7Layout);
        panelBorder7Layout.setHorizontalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_SoLuongDH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorder7Layout.createSequentialGroup()
                        .addComponent(lable_don, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 151, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(40, 40, 40))
        );
        panelBorder7Layout.setVerticalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addGroup(panelBorder7Layout.createSequentialGroup()
                        .addComponent(txt_SoLuongDH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lable_don)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hoá Đơn", "Ngày Tạo", "Tổng Giá Trị", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHD);

        javax.swing.GroupLayout panelBorder8Layout = new javax.swing.GroupLayout(panelBorder8);
        panelBorder8.setLayout(panelBorder8Layout);
        panelBorder8Layout.setHorizontalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelBorder8Layout.setVerticalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
        );

        panelBorder12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel27.setText("THÔNG TIN NHÂN VIÊN");

        jLabel28.setText("Mã Nhân viên:");

        txt_mnv.setEditable(false);

        jLabel30.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel30.setText("Ngày vào làm:");

        jLabel31.setText("Họ tên:");

        txt_hoten.setEditable(false);

        jLabel32.setText("Ngày sinh:");

        txt_ngaysinh.setEditable(false);

        jLabel33.setText("Giới tính:");

        btnGroupGioiTinh.add(rdo_nam);
        rdo_nam.setSelected(true);
        rdo_nam.setText("Nam");
        rdo_nam.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnGroupGioiTinh.add(rdo_nu);
        rdo_nu.setText("Nữ");
        rdo_nu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel35.setText("Số ĐT:");

        jLabel36.setText("Số cccd:");

        jLabel37.setText("Chức vụ");

        txt_sdt.setEditable(false);

        txt_cccd.setEditable(false);

        txt_cv.setEditable(false);

        jLabel38.setText("Trạng thái");

        btnGroupChucVu.add(rdo_danglam);
        rdo_danglam.setSelected(true);
        rdo_danglam.setText("Đang làm");
        rdo_danglam.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnGroupChucVu.add(rdo_danghi);
        rdo_danghi.setText("Đã nghỉ");
        rdo_danghi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel39.setText("Tài khoản");

        txt_tk.setEditable(false);

        btn_doimk.setText("Đổi mật khẩu");
        btn_doimk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doimkActionPerformed(evt);
            }
        });

        txt_ngaytao.setText("2022/11/12");

        javax.swing.GroupLayout panelBorder12Layout = new javax.swing.GroupLayout(panelBorder12);
        panelBorder12.setLayout(panelBorder12Layout);
        panelBorder12Layout.setHorizontalGroup(
            panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder12Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder12Layout.createSequentialGroup()
                        .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35))
                        .addGap(18, 18, 18)
                        .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_sdt)
                            .addComponent(txt_ngaysinh)
                            .addGroup(panelBorder12Layout.createSequentialGroup()
                                .addComponent(rdo_nam, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_nu, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                                .addGap(94, 94, 94))
                            .addComponent(txt_hoten)
                            .addComponent(txt_mnv))
                        .addGap(66, 66, 66))
                    .addGroup(panelBorder12Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder12Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(20, 20, 20)
                        .addComponent(txt_cv))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder12Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(txt_cccd))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder12Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder12Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(rdo_danglam, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(rdo_danghi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(37, 37, 37))
                    .addGroup(panelBorder12Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_doimk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tk))
                        .addGap(1, 1, 1)))
                .addGap(50, 50, 50))
        );
        panelBorder12Layout.setVerticalGroup(
            panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel30)
                    .addComponent(txt_ngaytao))
                .addGap(18, 18, 18)
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txt_mnv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(txt_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txt_hoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(txt_cv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txt_ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(rdo_danglam)
                    .addComponent(rdo_danghi))
                .addGap(18, 18, 18)
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(rdo_nam)
                    .addComponent(rdo_nu)
                    .addComponent(jLabel39)
                    .addComponent(txt_tk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_doimk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelBorder3.setBackground(new java.awt.Color(255, 255, 255));
        panelBorder3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelBorder3.setPreferredSize(new java.awt.Dimension(451, 749));

        jLabel18.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel18.setText("MÃ HOÁ ĐƠN");

        txt_mhd.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        txt_mhd.setForeground(new java.awt.Color(153, 153, 153));
        txt_mhd.setText("ORD001");

        panelBorder4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel10.setText("Nhân viên:");

        txt_tennv.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txt_tennv.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_tennv.setText("Mesut Ozil");

        txt_ngaytaohd.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txt_ngaytaohd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_ngaytaohd.setText("2024/08/05");

        jLabel13.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel13.setText("Ngày tạo:");

        jLabel14.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel14.setText("Khách hàng:");

        txt_tenkh.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txt_tenkh.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_tenkh.setText("Kai Havertz");

        jLabel16.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel16.setText("Số ĐT:");

        txt_sdtkh.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txt_sdtkh.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_sdtkh.setText("0559491153");

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel6.setText("Mặt hàng");

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel7.setText("Đơn giá");

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel8.setText("T.Tiền");

        jLabel20.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel20.setText("SL");

        javax.swing.GroupLayout panelBorder4Layout = new javax.swing.GroupLayout(panelBorder4);
        panelBorder4.setLayout(panelBorder4Layout);
        panelBorder4Layout.setHorizontalGroup(
            panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder4Layout.createSequentialGroup()
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_sdtkh, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(txt_ngaytaohd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_tenkh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_tennv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelBorder4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel20)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );
        panelBorder4Layout.setVerticalGroup(
            panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_tennv, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_ngaytaohd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_tenkh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_sdtkh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel20))
                .addContainerGap())
        );

        panelBorder10.setBackground(new java.awt.Color(255, 255, 255));
        panelBorder10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBorder10.setForeground(new java.awt.Color(221, 221, 221));

        javax.swing.GroupLayout panelBorder10Layout = new javax.swing.GroupLayout(panelBorder10);
        panelBorder10.setLayout(panelBorder10Layout);
        panelBorder10Layout.setHorizontalGroup(
            panelBorder10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );
        panelBorder10Layout.setVerticalGroup(
            panelBorder10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelShowSPLayout = new javax.swing.GroupLayout(panelShowSP);
        panelShowSP.setLayout(panelShowSPLayout);
        panelShowSPLayout.setHorizontalGroup(
            panelShowSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelShowSPLayout.setVerticalGroup(
            panelShowSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel23.setText("Trạng thái:");

        txt_trangthai.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txt_trangthai.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_trangthai.setText("Đã thanh toán");

        javax.swing.GroupLayout panelBorder9Layout = new javax.swing.GroupLayout(panelBorder9);
        panelBorder9.setLayout(panelBorder9Layout);
        panelBorder9Layout.setHorizontalGroup(
            panelBorder9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_trangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBorder9Layout.setVerticalGroup(
            panelBorder9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txt_trangthai))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel21.setText("Thanh toán:");

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel9.setText("Giảm giá:");

        txt_giamgia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_giamgia.setText("70.000 VNĐ");

        txt_tt.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txt_tt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_tt.setText("350.000 VNĐ");

        jLabel25.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel25.setText("Phương thức TT:");

        txt_phuongthuc.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txt_phuongthuc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_phuongthuc.setText("Chuyển khoản");

        jLabel29.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel29.setText("Tổng giá trị:");

        txt_tonggt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_tonggt.setText("70.000 VNĐ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addComponent(txt_phuongthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(txt_giamgia, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_tt, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_tonggt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txt_tonggt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_giamgia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txt_tt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txt_phuongthuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelBorder3Layout = new javax.swing.GroupLayout(panelBorder3);
        panelBorder3.setLayout(panelBorder3Layout);
        panelBorder3Layout.setHorizontalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelShowSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBorder4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addComponent(panelBorder10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18)
                            .addComponent(txt_mhd)))
                    .addGroup(panelBorder3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        panelBorder3Layout.setVerticalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_mhd)
                .addGap(18, 18, 18)
                .addComponent(panelBorder4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelShowSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(panelBorder3);

        javax.swing.GroupLayout panelBorder5Layout = new javax.swing.GroupLayout(panelBorder5);
        panelBorder5.setLayout(panelBorder5Layout);
        panelBorder5Layout.setHorizontalGroup(
            panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder5Layout.createSequentialGroup()
                .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelBorder12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBorder8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelBorder5Layout.createSequentialGroup()
                                .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panelBorder7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panelBorder6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(panelBorder5Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cbo_loctheo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBorder5Layout.setVerticalGroup(
            panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelBorder5Layout.createSequentialGroup()
                        .addComponent(panelBorder12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbo_loctheo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBorder7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelBorder6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorder5, javax.swing.GroupLayout.DEFAULT_SIZE, 1221, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorder5, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_doimkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doimkActionPerformed
        new Popup_DoiMK(MaNV, mkCu).setVisible(true);
    }//GEN-LAST:event_btn_doimkActionPerformed

    private void cbo_loctheoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_loctheoActionPerformed
        selectedItem = (String) cbo_loctheo.getSelectedItem();
        handleFilterDate(selectedItem);
    }//GEN-LAST:event_cbo_loctheoActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
       i = tblHD.getSelectedRow();
       this.showHD(i);
    }//GEN-LAST:event_tblHDMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupChucVu;
    private javax.swing.ButtonGroup btnGroupGioiTinh;
    private com.daipc.swing.Button btn_doimk;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbo_loctheo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel label_ngay;
    private javax.swing.JLabel lable_don;
    private com.daipc.swing.PanelBorder panelBorder1;
    private com.daipc.swing.PanelBorder panelBorder10;
    private com.daipc.swing.PanelBorder panelBorder12;
    private com.daipc.swing.PanelBorder panelBorder2;
    private com.daipc.swing.PanelBorder panelBorder3;
    private com.daipc.swing.PanelBorder panelBorder4;
    private com.daipc.swing.PanelBorder panelBorder5;
    private com.daipc.swing.PanelBorder panelBorder6;
    private com.daipc.swing.PanelBorder panelBorder7;
    private com.daipc.swing.PanelBorder panelBorder8;
    private com.daipc.swing.PanelBorder panelBorder9;
    private javax.swing.JPanel panelShowSP;
    private javax.swing.JRadioButton rdo_danghi;
    private javax.swing.JRadioButton rdo_danglam;
    private javax.swing.JRadioButton rdo_nam;
    private javax.swing.JRadioButton rdo_nu;
    private javax.swing.JTable tblHD;
    private javax.swing.JLabel txt_SoLuongDH;
    private javax.swing.JTextField txt_cccd;
    private javax.swing.JTextField txt_cv;
    private javax.swing.JLabel txt_giamgia;
    private javax.swing.JTextField txt_hoten;
    private javax.swing.JLabel txt_mhd;
    private javax.swing.JTextField txt_mnv;
    private javax.swing.JTextField txt_ngaysinh;
    private javax.swing.JLabel txt_ngaytao;
    private javax.swing.JLabel txt_ngaytaohd;
    private javax.swing.JLabel txt_phuongthuc;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JLabel txt_sdtkh;
    private javax.swing.JLabel txt_tenkh;
    private javax.swing.JLabel txt_tennv;
    private javax.swing.JTextField txt_tk;
    private javax.swing.JLabel txt_tongDT;
    private javax.swing.JLabel txt_tonggt;
    private javax.swing.JLabel txt_trangthai;
    private javax.swing.JLabel txt_tt;
    // End of variables declaration//GEN-END:variables
}
