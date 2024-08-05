package com.daipc.form;

import com.daipc.ScrollBar.ScrollbarCustom;
import com.daipc.UI.PanelChart;
import com.daipc.blankchart.BlankPlotChart;
import com.daipc.chart.Chart;
import com.daipc.component.Card;
import com.daipc.modelUI.Model_Card;
import com.daipc.swing.PanelBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;

public class Form_Home extends javax.swing.JPanel {

    private MigLayout mlayout;

    private JPanel panel;
    private JLayeredPane layerPane;
    private PanelChart panel1;
    private PanelBorder panel2;
    private Chart chart;

    public Form_Home() {
        initComponents();
        init();
    }

    public void init() {
        panel = new JPanel();
        layerPane = new JLayeredPane();
        panel1 = new PanelChart();
        panel2 = new PanelBorder();
        chart = new Chart();

        panel.setBackground(new Color(234, 234, 234));
        panel1.setBackground(Color.white);
        panel2.setBackground(Color.white);

        scroll.setVerticalScrollBar(new ScrollbarCustom());
        ScrollbarCustom sp = new ScrollbarCustom();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scroll.setHorizontalScrollBar(sp);

        panel.setPreferredSize(new Dimension(1150, 1000));
        scroll.setViewportView(panel);
        
        mlayout = new MigLayout("fill", "10[fill]10", "10[fill, top]10[]10[grow]10");
        panel.setLayout(mlayout);
        panel.add(layerPane, "h 180!, wrap");
        panel.add(panel1, "h 550!, wrap");
        panel.add(panel2, "grow");

        layerPane.setLayout(new GridLayout(0, 4, 10, 0));
        Model_Card model1 = new Model_Card("1", "Doanh Thu Ngày", "$10", "Description 1", "#FF3333", "#FF6666");
        Model_Card model2 = new Model_Card("2", "Doanh Thu Tuần", "$20", "Description 2", "#9999FF", "#0066FF");
        Model_Card model3 = new Model_Card("3", "Doanh Thu Tháng", "$30", "Description 3", "#66FF66", "#00CCCC");
        Model_Card model4 = new Model_Card("4", "Doanh Thu Năm", "$40", "Description 4", "#FFCC00", "#FF6633");
        layerPane.add(new Card(model1));
        layerPane.add(new Card(model2));
        layerPane.add(new Card(model3));
        layerPane.add(new Card(model4));

       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        model_Card1 = new com.daipc.modelUI.Model_Card();
        scroll = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(234, 234, 234));
        setForeground(new java.awt.Color(255, 255, 255));
        setToolTipText("");

        scroll.setBackground(new java.awt.Color(234, 234, 234));
        scroll.setBorder(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.modelUI.Model_Card model_Card1;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
