/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.customTable;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author DaiPc
 */
public class PanelButtonCellEditor extends DefaultCellEditor{
    
    private PanelEvent event;

    public PanelButtonCellEditor(PanelEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PanelButton panelButton = new PanelButton();
        panelButton.setBackground(table.getSelectionBackground());
        panelButton.initEvent(event, row);
        return panelButton;
    }
    
}
