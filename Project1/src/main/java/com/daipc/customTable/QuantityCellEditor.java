/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.customTable;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public QuantityCellEditor() {
        super(new JCheckBox());
        spinner = new JSpinner();
        SpinnerNumberModel spinnerModel = (SpinnerNumberModel) spinner.getModel();
        spinnerModel.setMinimum(0);
        JSpinner.NumberEditor numEditor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField formatTextField = (JFormattedTextField) numEditor.getTextField();
        formatTextField.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultFormatter f = (DefaultFormatter) formatTextField.getFormatter();
        f.setCommitsOnValidEdit(true);

        formatTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ngăn chặn hành động mặc định của phím Enter
                    if (commitEdit(formatTextField)) {
                        // Chấp nhận giá trị và chuyển focus sang ô tiếp theo nếu giá trị hợp lệ
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                    } else {
                        // Không chuyển sang ô tiếp theo nếu giá trị không hợp lệ
                        Toolkit.getDefaultToolkit().beep(); // Âm báo hiệu lỗi
                    }
                }
            }
        });

        // Thiết lập InputVerifier để kiểm tra đầu vào khi focus thay đổi
        formatTextField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return commitEdit((JFormattedTextField) input);
            }
        });
    }

    private boolean commitEdit(JFormattedTextField textField) {
        try {
            textField.commitEdit(); // Cố gắng cam kết chỉnh sửa để kiểm tra tính hợp lệ
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
        return spinner;
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
