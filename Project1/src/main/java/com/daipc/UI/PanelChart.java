package com.daipc.UI;

import com.daipc.chart.ModelChart;
import com.daipc.model.ThongKeDT;
import com.daipc.model.ThongKeKH;
import com.daipc.model.ThongKeNV;
import com.daipc.repo.QuanLiThongKe;
import com.daipc.swing.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class PanelChart extends javax.swing.JPanel {

    private QuanLiThongKe QLTK = new QuanLiThongKe();
    private List<ThongKeNV> listNV;
    private List<ThongKeDT> listDT;
    private List<ThongKeKH> listKH;

    public PanelChart() {
        initComponents();
        setOpaque(false);
        init();

    }

    public void init() {
        cboNhom.removeAllItems();
        cboNhom.addItem("Doanh Thu");
        cboNhom.addItem("Sản Phẩm");
        cboNhom.addItem("Nhân Viên");
        cboNhom.addItem("Khách Hàng");

        cboTime.removeAllItems();
        cboTime.addItem("Tháng");
        cboTime.addItem("Năm");
        cboTime.setSelectedIndex(0);

        cboYear.removeAllItems();
        for (Integer year : QLTK.getYears()) {
            cboYear.addItem(String.valueOf(year));
        }
        cboYear.setSelectedIndex(0);

        cboNhom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cboNhom.getSelectedIndex();
                panelLayoutNhom.setVisible(index == 0);
                switch (index) {
                    case 0 -> {
                        loadChartDT();
                    }
                    case 1 -> {
                        
                    }
                    case 2 -> {
                        thongKeNV();
                    }
                    case 3 -> {
                        thongKeKH();
                    }
                    default ->
                        throw new AssertionError();
                }
            }

        });
        cboTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cboTime.getSelectedIndex();
                CardLayout cardLayout = (CardLayout) panelLayout.getLayout();
                panelLayout.add(panelThang, "Months");
                panelLayout.add(panelNam, "Years");

                switch (index) {
                    case 0 -> {
                        cardLayout.show(panelLayout, "Months");
                        Object selectedItem = cboYear.getSelectedItem();
                        int year = Integer.parseInt(selectedItem.toString());
                        thongKeDTThang(year);
                    }
                    case 1 -> {
                        cardLayout.show(panelLayout, "Years");
                        thongKeDTNam();
                    }
                    default ->
                        throw new AssertionError();
                }
            }
        });
        cboYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = cboYear.getSelectedItem();
                int year = Integer.parseInt(selectedItem.toString());
                thongKeDTThang(year);

            }
        });

        loadChartDT();
    }

    public void thongKeNV() {
        chart.resetChart();
        listNV = QLTK.getThongKeNV();
        chart.addLegend("Số lượng hóa đơn", Color.RED);
        chart.addLegend("Số lượng khách hàng tiếp thị", Color.BLUE);
        for (ThongKeNV nv : listNV) {
            chart.addData(new ModelChart(nv.getHoTen(), new double[]{nv.getTongDonHang(), nv.getSoLuongKachHang()}));
        }
        chart.start();
    }

    public void thongKeDTNam() {
        chart.resetChart();
        listDT = QLTK.getThongKeDTYear();
        chart.addLegend("Doanh thu", Color.GREEN);
        chart.addLegend("Lợi nhuận", Color.BLUE);
        for (ThongKeDT dt : listDT) {
            chart.addData(new ModelChart(dt.getDate(), new double[]{Double.parseDouble(dt.getDoanhThu().toString()), Double.parseDouble(dt.getLoiNhuan().toString())}));
        }
        chart.start();
    }

    public void thongKeDTThang(int year) {
        chart.resetChart();
        listDT = QLTK.getThongKeDTMonth(year);
        chart.addLegend("Doanh thu", Color.GREEN);
        chart.addLegend("Lợi nhuận", Color.BLUE);
        for (ThongKeDT dt : listDT) {
            chart.addData(new ModelChart(dt.getDate(), new double[]{Double.parseDouble(dt.getDoanhThu().toString()), Double.parseDouble(dt.getLoiNhuan().toString())}));
        }
        chart.start();
    }
    
    public void thongKeKH() {
        chart.resetChart();
        listKH = QLTK.getThongKeKH();
        chart.addLegend("Tổng giá trị hóa đơn", Color.BLUE);
        for (ThongKeKH kh : listKH) {
            chart.addData(new ModelChart(kh.getHoTen(), new double[]{Double.parseDouble(kh.getTongGiaTriHD().toString())}));
        }
        chart.start();
    }

    public void loadChartDT() {
        int index = cboTime.getSelectedIndex();
        CardLayout cardLayout = (CardLayout) panelLayout.getLayout();
        panelLayout.add(panelThang, "Months");
        panelLayout.add(panelNam, "Years");

        switch (index) {
            case 0 -> {
                cardLayout.show(panelLayout, "Months");
                Object selectedItem = cboYear.getSelectedItem();
                int year = Integer.parseInt(selectedItem.toString());
                thongKeDTThang(year);
            }
            case 1 -> {
                cardLayout.show(panelLayout, "Years");
                thongKeDTNam();
            }
            default ->
                throw new AssertionError();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        chart = new com.daipc.chart.Chart();
        cboNhom = new javax.swing.JComboBox<>();
        panelLayoutNhom = new javax.swing.JPanel();
        cboTime = new javax.swing.JComboBox<>();
        panelLayout = new javax.swing.JPanel();
        panelThang = new javax.swing.JPanel();
        cboYear = new javax.swing.JComboBox<>();
        panelNam = new javax.swing.JPanel();

        panelLayoutNhom.setBackground(new java.awt.Color(255, 255, 255));

        panelLayout.setBackground(new java.awt.Color(255, 255, 255));
        panelLayout.setLayout(new java.awt.CardLayout());

        panelThang.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelThangLayout = new javax.swing.GroupLayout(panelThang);
        panelThang.setLayout(panelThangLayout);
        panelThangLayout.setHorizontalGroup(
            panelThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(724, Short.MAX_VALUE))
        );
        panelThangLayout.setVerticalGroup(
            panelThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThangLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelLayout.add(panelThang, "card3");

        panelNam.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelNamLayout = new javax.swing.GroupLayout(panelNam);
        panelNam.setLayout(panelNamLayout);
        panelNamLayout.setHorizontalGroup(
            panelNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        panelNamLayout.setVerticalGroup(
            panelNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelLayout.add(panelNam, "card4");

        javax.swing.GroupLayout panelLayoutNhomLayout = new javax.swing.GroupLayout(panelLayoutNhom);
        panelLayoutNhom.setLayout(panelLayoutNhomLayout);
        panelLayoutNhomLayout.setHorizontalGroup(
            panelLayoutNhomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayoutNhomLayout.createSequentialGroup()
                .addComponent(cboTime, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(panelLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayoutNhomLayout.setVerticalGroup(
            panelLayoutNhomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayoutNhomLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelLayoutNhomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboTime)
                    .addComponent(panelLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panelLayoutNhom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(cboNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboNhom)
                    .addComponent(panelLayoutNhom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboNhom;
    private javax.swing.JComboBox<String> cboTime;
    private javax.swing.JComboBox<String> cboYear;
    private com.daipc.chart.Chart chart;
    private javax.swing.JPanel panelLayout;
    private javax.swing.JPanel panelLayoutNhom;
    private javax.swing.JPanel panelNam;
    private javax.swing.JPanel panelThang;
    // End of variables declaration//GEN-END:variables
}
