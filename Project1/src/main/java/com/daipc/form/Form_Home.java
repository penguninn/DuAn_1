package com.daipc.form;

import com.daipc.ScrollBar.ScrollbarCustom;
import com.daipc.UI.PanelChart;
import com.daipc.blankchart.BlankPlotChart;
import com.daipc.chart.Chart;
import com.daipc.component.Card;
import com.daipc.model.ChiTietSP;
import com.daipc.model.ThongKe;
import com.daipc.modelUI.Model_Card;
import com.daipc.repo.QuanLiThongKe;
import com.daipc.swing.PanelBorder;
import com.daipc.table.TableCustom;
import com.formdev.flatlaf.extras.components.FlatRadioButton;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import com.formdev.flatlaf.extras.components.FlatTable;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class Form_Home extends javax.swing.JPanel {

    private JPanel panel;
    private JLayeredPane layerPane;
    private PanelChart panelChart;
    private Chart chart;
    private PanelBorder panelBorder;
    private PanelBorder panelHeader;
    private JPanel panelTable;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int index = 0;
    private JRadioButton chartDas;
    private JRadioButton tableDas;
    private ButtonGroup groupBtn;
    private JLabel labelHeader;
    private JTabbedPane tabbedPane;
    private JPanel doanhThu;
    private JPanel khachHang;
    private JPanel nhanVien;
    private JPanel sanPham;
    private JTable doanhThuTable;
    private JTable sanPhamTable;
    private JTable nhanVienTable;
    private JTable khachHangTable;

    private QuanLiThongKe QLTK = new QuanLiThongKe();
    private DefaultTableModel modelSP = new DefaultTableModel(new String[] {"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá Bán", "Màu Sắc", "Size", "Chất Liệu", "Độ Dày", "Số Lượng"}, 0);
    private DefaultTableModel modelDT;
    private DefaultTableModel modelNV;
    private DefaultTableModel modelKH;
    private List<ChiTietSP> listSP;
    private List<ChiTietSP> listDT;
    private List<ChiTietSP> listNV;
    private List<ChiTietSP> listKH;

    public Form_Home() {
        initComponents();
        init();
    }

    public void init() {
        panel = new JPanel();
        layerPane = new JLayeredPane();
        panelChart = new PanelChart();
        chart = new Chart();
        panelBorder = new PanelBorder();
        panelHeader = new PanelBorder();
        panelTable = new JPanel();
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        groupBtn = new ButtonGroup();
        chartDas = new JRadioButton();
        tableDas = new FlatRadioButton();
        tabbedPane = new JTabbedPane();
        doanhThu = new JPanel(new MigLayout("fill"));
        sanPham = new JPanel(new MigLayout("fill"));
        nhanVien = new JPanel(new MigLayout("fill"));
        khachHang = new JPanel(new MigLayout("fill"));
        doanhThuTable = new JTable();
        sanPhamTable = new JTable();
        nhanVienTable = new JTable();
        khachHangTable = new JTable();
        
        labelHeader = new JLabel("Tổng Hợp Thống Kê");
        labelHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelHeader.setForeground(Color.BLACK);

        panel.setBackground(new Color(234, 234, 234));
        panelChart.setBackground(Color.white);
        panelTable.setBackground(Color.white);
        cardPanel.setBackground(Color.white);
        panelBorder.setBackground(Color.white);
        panelHeader.setBackground(Color.white);
        
        cardPanel.setLayout(cardLayout);
        cardPanel.add(panelChart, "Chart");
        cardPanel.add(panelTable, "Table");
        
        tabbedPane.add(doanhThu);
        tabbedPane.add(sanPham);
        tabbedPane.add(nhanVien);
        tabbedPane.add(khachHang);
        
        loadDoanhThuData();
        loadSanPhamData();
        loadNhanVienData();
        loadKhachHangData();
        
        FlatScrollPane scrollPaneDoanhThu = new FlatScrollPane();
        scrollPaneDoanhThu.setViewportView(doanhThuTable);
        FlatScrollPane scrollPaneSanPham = new FlatScrollPane();
        scrollPaneSanPham.setViewportView(sanPhamTable);
        FlatScrollPane scrollPaneNhanVien = new FlatScrollPane();
        scrollPaneNhanVien.setViewportView(nhanVienTable);
        FlatScrollPane scrollPaneKhachHang = new FlatScrollPane();
        scrollPaneKhachHang.setViewportView(khachHangTable);

        doanhThu.add(scrollPaneDoanhThu, "grow");
        sanPham.add(scrollPaneSanPham, "grow");
        nhanVien.add(scrollPaneNhanVien, "grow");
        khachHang.add(scrollPaneKhachHang, "grow");
        
        TableCustom.apply(scrollPaneDoanhThu, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollPaneSanPham, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollPaneNhanVien, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollPaneKhachHang, TableCustom.TableType.DEFAULT);

        tabbedPane.addTab("Doanh Thu", doanhThu);
        tabbedPane.addTab("Sản Phẩm", sanPham);
        tabbedPane.addTab("Nhân Viên", nhanVien);
        tabbedPane.addTab("Khách Hàng", khachHang);
        
        
        
        panelTable.setLayout(new MigLayout("fill"));
        panelTable.add(tabbedPane, "grow");

        groupBtn.add(chartDas);
        groupBtn.add(tableDas);
        chartDas.setText("Biểu đồ");
        chartDas.setSelected(true);
        tableDas.setText("Bảng");
        panelHeader.setLayout(new MigLayout("fill", "20[]80[]50[grow]", "10[]"));
        panelHeader.add(labelHeader, "align left");
        panelHeader.add(chartDas, "center");
        panelHeader.add(tableDas, "left");

        panelBorder.setLayout(new MigLayout("fill", "0[fill]0", "0[]8[grow]5"));
        panelBorder.add(panelHeader, "h 50!, wrap");
        panelBorder.add(cardPanel, "grow");

        panel.setLayout(new MigLayout("fill", "10[fill]10", "10[]8[grow]10"));
        panel.add(layerPane, "h 120!, wrap");
        panel.add(panelBorder, "grow");
        layerPane.setLayout(new GridLayout(0, 4, 10, 0));

        ThongKe tk = QLTK.getCardThongKe();
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        Model_Card model1 = new Model_Card("1", "Tổng Đơn Hàng", String.valueOf(tk.getDonHang()), "", "#FF3333", "#FF6666");
        Model_Card model2 = new Model_Card("2", "Tổng Doanh Thu", numberFormat.format(tk.getDoanhThu()) + " VNĐ", "", "#9999FF", "#0066FF");
        Model_Card model3 = new Model_Card("3", "Tổng Chi Phí", numberFormat.format(tk.getChiPhi()) + " VNĐ", "", "#66FF66", "#00CCCC");
        Model_Card model4 = new Model_Card("4", "Tổng Lợi Nhuận", numberFormat.format(tk.getLoiNhuan()) + " VNĐ", "", "#FFCC00", "#FF6633");
        layerPane.add(new Card(model1));
        layerPane.add(new Card(model2));
        layerPane.add(new Card(model3));
        layerPane.add(new Card(model4));

        this.setLayout(new MigLayout("fill", "0[fill]0", "0[grow]0"));
        this.add(panel, "grow");
        
        chartDas.addActionListener(e -> {
            index = 0;
            swicthForm();
        });

        tableDas.addActionListener(e -> {
            index = 1;
            swicthForm();
        });
    }

    public void swicthForm() {
        switch (index) {
            case 0 -> {
                cardLayout.show(cardPanel, "Chart");
            }

            case 1 -> {
                cardLayout.show(cardPanel, "Table");
            }
            default ->
                throw new AssertionError();
        }
    }
    
    private void loadDoanhThuData() {
        // Sample data for Doanh Thu table
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {
                {"Tháng 1", "10000000"},
                {"Tháng 2", "15000000"}
            },
            new String[] {"Tháng", "Doanh Thu"}
        );
        doanhThuTable.setModel(model);
    }

    private void loadSanPhamData() {
        modelSP.setRowCount(0);
        listSP = QLTK.getThongKeSP();
        for (ChiTietSP sp : listSP) {
            modelSP.addRow(sp.getSPCT());
        }
        sanPhamTable.setModel(modelSP);
    }

    private void loadNhanVienData() {
        // Sample data for Nhân Viên table
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {
                {"NV001", "Nguyễn Văn A", "Kế toán"},
                {"NV002", "Trần Thị B", "Nhân sự"}
            },
            new String[] {"Mã NV", "Tên NV", "Chức Vụ"}
        );
        nhanVienTable.setModel(model);
    }

    private void loadKhachHangData() {
        // Sample data for Khách Hàng table
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {
                {"KH001", "Lê Văn C", "0123456789"},
                {"KH002", "Hoàng Thị D", "0987654321"}
            },
            new String[] {"Mã KH", "Tên KH", "Số Điện Thoại"}
        );
        khachHangTable.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        model_Card1 = new com.daipc.modelUI.Model_Card();

        setBackground(new java.awt.Color(234, 234, 234));
        setForeground(new java.awt.Color(255, 255, 255));
        setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1188, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.modelUI.Model_Card model_Card1;
    // End of variables declaration//GEN-END:variables
}
