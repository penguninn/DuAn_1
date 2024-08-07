/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.daipc.form;

import com.daipc.enumm.HienThi;
import com.daipc.enumm.TrangThaiCRUD;
import static com.daipc.enumm.TrangThaiCRUD.DaTonTai;
import static com.daipc.enumm.TrangThaiCRUD.ThanhCong;
import static com.daipc.enumm.TrangThaiCRUD.ThatBai;
import com.daipc.model.SanPham;
import com.daipc.repo.QuanLiSanPham;
import com.daipc.repo.Repository;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

/**
 *
 * @author DaiPc
 */
public class Popup_ReviewDataImport extends javax.swing.JDialog {

    int selectedRow = -1;
    TrangThaiCRUD updateTbl;
    List<SanPham> listSP;
    QuanLiSanPham QLSP = new QuanLiSanPham();
    DefaultTableModel modelSP = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Mô Tả", "TrangThai"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * Creates new form Popup_ReviewDataImport
     */
    public Popup_ReviewDataImport(java.awt.Frame parent, boolean modal, List<SanPham> listSP) {
        super(parent, modal);
        initComponents();
        tblInfoProducts.setModel(modelSP);
        this.listSP = listSP;
        loadDataToTable();

    }

    public void loadDataToTable() {
        modelSP.setRowCount(0);
        for (SanPham sp : listSP) {
            modelSP.addRow(sp.getSanPham());
        }
        selectedRow = listSP.size() - 1;
        tblInfoProducts.setRowSelectionInterval(selectedRow, selectedRow);
    }

    public TrangThaiCRUD getUpdateTbl() {
        return updateTbl;
    }

    public void setUpdateTbl(TrangThaiCRUD updateTbl) {
        this.updateTbl = updateTbl;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.daipc.swing.PanelBorder();
        scrollInfoProducts = new javax.swing.JScrollPane();
        tblInfoProducts = new javax.swing.JTable();
        btnAddAll = new com.daipc.swing.Button();
        btnAdd = new com.daipc.swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        tblInfoProducts.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblInfoProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInfoProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInfoProductsMouseClicked(evt);
            }
        });
        scrollInfoProducts.setViewportView(tblInfoProducts);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBorder1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollInfoProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
            .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollInfoProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)))
        );

        btnAddAll.setText("Thêm Tất Cả");
        btnAddAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAllActionPerformed(evt);
            }
        });

        btnAdd.setText("Thêm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(808, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblInfoProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInfoProductsMouseClicked
        selectedRow = tblInfoProducts.getSelectedRow();
        tblInfoProducts.setRowSelectionInterval(selectedRow, selectedRow);
    }//GEN-LAST:event_tblInfoProductsMouseClicked

    private void btnAddAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAllActionPerformed
        TrangThaiCRUD status = TrangThaiCRUD.ThatBai;
        try {
            for (SanPham sp : listSP) {
                status = QLSP.update("insert into SanPham(MaSp, TenSP, MoTa) values (?, ?, ?)", sp.getMaSP(), sp.getTenSP(), sp.getMoTa());
            }
        } catch (Exception e) {
        }

        switch (status) {
            case ThanhCong:
                updateTbl = TrangThaiCRUD.ThanhCong;
                break;
            case ThatBai:
                updateTbl = TrangThaiCRUD.ThatBai;
                break;
            case DaTonTai:
                updateTbl = TrangThaiCRUD.DaTonTai;
                break;
            default:
        }
    }//GEN-LAST:event_btnAddAllActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Popup_ReviewDataImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Popup_ReviewDataImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Popup_ReviewDataImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Popup_ReviewDataImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Popup_ReviewDataImport dialog = new Popup_ReviewDataImport(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.swing.Button btnAdd;
    private com.daipc.swing.Button btnAddAll;
    private com.daipc.swing.PanelBorder panelBorder1;
    private javax.swing.JScrollPane scrollInfoProducts;
    private javax.swing.JTable tblInfoProducts;
    // End of variables declaration//GEN-END:variables
}
