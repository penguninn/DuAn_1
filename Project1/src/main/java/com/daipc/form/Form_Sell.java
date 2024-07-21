package com.daipc.form;

import com.daipc.customTable.PanelButtonCellEditor;
import com.daipc.customTable.PanelButtonCellRender;
import com.daipc.customTable.PanelEvent;
import com.daipc.customTable.QuantityCellEditor;
import com.daipc.model.HoaDonCho;
import com.daipc.table.TableCustom;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Form_Sell extends javax.swing.JPanel {

    private DefaultTableModel modelHD;
    private DefaultTableModel modelGH;
    private DefaultTableModel modelSP;

    public Form_Sell() {
        initComponents();
        
        TableCustom.apply(scrollHoaDonCho, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollGioHang, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollDanhSachSanPham, TableCustom.TableType.DEFAULT);
        testData();
    }

    public void testData() {
        modelHD = (DefaultTableModel) tblHoaDonCho.getModel();
        modelHD.setRowCount(0);
        HoaDonCho[] hoaDonChos = new HoaDonCho[]{
            new HoaDonCho("HD001", "Nguyen Tuan Anh", "(NV) Nguyen Hong Nhung", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 2550000),
            new HoaDonCho("HD002", "Tran Van B", "(NV) Le Thi Mai", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 3450000),
            new HoaDonCho("HD003", "Le Thi C", "(NV) Hoang Van Phuc", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 1500000),
            new HoaDonCho("HD004", "Pham Van D", "(NV) Tran Thi Thao", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 2200000),
            new HoaDonCho("HD005", "Nguyen Thi E", "(NV) Vu Dinh Huy", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 4500000),
            new HoaDonCho("HD006", "Hoang Van F", "(NV) Pham Quang Trung", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 3200000),
            new HoaDonCho("HD007", "Bui Thi G", "(NV) Nguyen Hong Nhung", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 2850000),
            new HoaDonCho("HD008", "Tran Van H", "(NV) Le Thi Mai", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 2950000),
            new HoaDonCho("HD009", "Pham Thi I", "(NV) Hoang Van Phuc", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 1650000),
            new HoaDonCho("HD010", "Le Van J", "(NV) Tran Thi Thao", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")), 3100000)
        };
        for (HoaDonCho hoaDon : hoaDonChos) {
            modelHD.addRow(hoaDon.getObjects());
        }
        
        PanelEvent event = new PanelEvent() {
            @Override
            public void onDelete(int row) {
                System.out.println("Delete row " + row);
            } 
        };
        
        modelGH = (DefaultTableModel) tblGioHang.getModel();
        tblGioHang.getColumnModel().getColumn(5).setCellRenderer(new PanelButtonCellRender(tblGioHang));
        tblGioHang.getColumnModel().getColumn(5).setCellEditor(new PanelButtonCellEditor(event));
        tblGioHang.getColumnModel().getColumn(3).setCellEditor(new QuantityCellEditor());
        tblGioHang.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });   
    }

//    @SuppressWarnings("unchecked");
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.daipc.swing.PanelBorder();
        scrollHoaDonCho = new javax.swing.JScrollPane();
        tblHoaDonCho = new javax.swing.JTable();
        panelBorder8 = new com.daipc.swing.PanelBorder();
        scrollDanhSachSanPham = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        panelBorder6 = new com.daipc.swing.PanelBorder();
        scrollGioHang = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        panelBorder7 = new com.daipc.swing.PanelBorder();

        setBackground(new java.awt.Color(234, 234, 234));

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        tblHoaDonCho.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblHoaDonCho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Tên Khách Hàng", "Người Tạo", "Ngày Tạo", "Tổng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollHoaDonCho.setViewportView(tblHoaDonCho);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollHoaDonCho, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollHoaDonCho, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
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
        scrollDanhSachSanPham.setViewportView(tblDanhSachSanPham);

        javax.swing.GroupLayout panelBorder8Layout = new javax.swing.GroupLayout(panelBorder8);
        panelBorder8.setLayout(panelBorder8Layout);
        panelBorder8Layout.setHorizontalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDanhSachSanPham)
                .addContainerGap())
        );
        panelBorder8Layout.setVerticalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDanhSachSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
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

        javax.swing.GroupLayout panelBorder6Layout = new javax.swing.GroupLayout(panelBorder6);
        panelBorder6.setLayout(panelBorder6Layout);
        panelBorder6Layout.setHorizontalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 990, Short.MAX_VALUE)
            .addGroup(panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBorder6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panelBorder6Layout.setVerticalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 285, Short.MAX_VALUE)
            .addGroup(panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBorder6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        panelBorder7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBorder7Layout = new javax.swing.GroupLayout(panelBorder7);
        panelBorder7.setLayout(panelBorder7Layout);
        panelBorder7Layout.setHorizontalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );
        panelBorder7Layout.setVerticalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
                .addComponent(panelBorder7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(panelBorder8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.swing.PanelBorder panelBorder1;
    private com.daipc.swing.PanelBorder panelBorder2;
    private com.daipc.swing.PanelBorder panelBorder3;
    private com.daipc.swing.PanelBorder panelBorder4;
    private com.daipc.swing.PanelBorder panelBorder6;
    private com.daipc.swing.PanelBorder panelBorder7;
    private com.daipc.swing.PanelBorder panelBorder8;
    private javax.swing.JScrollPane scrollDanhSachSanPham;
    private javax.swing.JScrollPane scrollGioHang;
    private javax.swing.JScrollPane scrollHoaDonCho;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDonCho;
    // End of variables declaration//GEN-END:variables
}
