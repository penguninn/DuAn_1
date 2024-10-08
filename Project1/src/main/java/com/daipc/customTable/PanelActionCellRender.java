/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.customTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author daipc
 */
public class PanelActionCellRender extends DefaultTableCellRenderer {
    
    private int hoverRow = -1;

    public PanelActionCellRender(JTable table) {
    table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int currentHoverRow = table.rowAtPoint(e.getPoint());
                if (hoverRow != currentHoverRow) {
                    hoverRow = currentHoverRow;
                    table.repaint();
                }
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow = -1;
                table.repaint();
            }
        });
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelActionButton panelAction = new PanelActionButton();

        if (isSelected == false && row == hoverRow) {
            panelAction.setBackground(new Color(230, 230, 230));
        } else if (isSelected == false && row % 2 != 0) {
            panelAction.setBackground(new Color(242, 242, 242));

        } else {
            panelAction.setBackground(com.getBackground());
        }

        return panelAction;
    }
    
    
}
