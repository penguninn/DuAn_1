/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.customTable;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author DaiPc
 */
public class QuantityCellEditor extends DefaultCellEditor {

    private JSpinner spinner;
    private JFormattedTextField formatTextField;
    private TableEvent event;

    public QuantityCellEditor(TableEvent event) {
        super(new JCheckBox());
        spinner = new JSpinner();
        this.event = event;
        SpinnerNumberModel spinnerModel = (SpinnerNumberModel) spinner.getModel();
        spinnerModel.setMinimum(0);
        JSpinner.NumberEditor numEditor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField formatTextField = (JFormattedTextField) numEditor.getTextField();
        formatTextField.setHorizontalAlignment(SwingConstants.LEFT);
        DefaultFormatter f = (DefaultFormatter) formatTextField.getFormatter();
        f.setCommitsOnValidEdit(true);

        formatTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if (commitEdit(formatTextField)) {
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
        });

        formatTextField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return commitEdit((JFormattedTextField) input);
            }
        });

        
    }

    private boolean commitEdit(JFormattedTextField textField) {
        try {
            if(Integer.parseInt(textField.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên lớn hơn 0 !", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            textField.commitEdit();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập một số nguyên!", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return false;
        }

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Component com = super.getTableCellEditorComponent(table, value, isSelected, row, column);
        int qty = Integer.parseInt(value.toString());
        spinner.setValue(qty);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int) spinner.getValue();
                if (value == 0) {
                    if (JOptionPane.showConfirmDialog(null, "Xóa sản phẩm ? ", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        event.onDelete(row);
                    }
                }
            }
        });
        return spinner;
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
