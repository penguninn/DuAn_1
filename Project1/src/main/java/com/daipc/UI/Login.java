package com.daipc.UI;

import java.awt.*;
import com.daipc.model.NhanVien;
import com.daipc.repo.QuanLiTaiKhoan;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

public class Login extends JPanel {

    private JFrame frame;
    private List<NhanVien> listAccount;
    private QuanLiTaiKhoan QLTK = new QuanLiTaiKhoan();
    private JLabel description;
    private String role = " ";

    public Login(JFrame frame) {
        init();
        this.frame = frame;
        listAccount = QLTK.getAccount("select * from NhanVien");
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void init() {
        this.setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        this.setBackground(new Color(0, 0, 0, 0));
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        cmdLogin = new JButton("Login");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 20 45 35 45", "fill,400:280"));
        JPanel panelButton = new JPanel(new MigLayout("insets 0", "[grow]0", "0[grow]0"));
        JButton closeButton = new JButton(new ImageIcon(getClass().getResource("/com/daipc/icon/close.png")));
        closeButton.setFocusable(false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.putClientProperty(FlatClientProperties.STYLE, " "
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        panelButton.putClientProperty(FlatClientProperties.STYLE, " "
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, " "
                + "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "margin:4,6,4,6;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");

        JLabel lbTitle = new JLabel("Chào Mừng Quay Lại !");
        description = new JLabel("Vui lòng đăng nhập để sử dụng phần mềm !");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, " "
                + "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");

        final boolean checkBorder[] = new boolean[2];
        checkBorder[0] = checkBorder[1] = true;
        Border defaultBorder = txtUsername.getBorder();
        Border marginBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
        Border compoundBorderBlue = BorderFactory.createCompoundBorder(new RoundedBorder(8, new Color(89, 183, 249), 2), BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(57, 132, 241), 2), marginBorder));
        Border compoundBorderRed = BorderFactory.createCompoundBorder(new RoundedBorder(8, new Color(255, 87, 51), 2), BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 51, 0), 2), marginBorder));

        txtUsername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (checkBorder[1]) {
                    if (checkBorder[0]) {
                        txtUsername.setBorder(compoundBorderBlue);
                    } else {
                        txtUsername.setBorder(compoundBorderRed);
                    }
                } else {
                    txtUsername.setBorder(compoundBorderRed);
                    if (!txtUsername.getText().isEmpty()) {
                        txtUsername.setBorder(compoundBorderBlue);
                        description.setText(" ");
                    }
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                txtUsername.setBorder(defaultBorder);

            }
        });

        txtPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (checkBorder[1]) {
                    if (checkBorder[0]) {
                        txtPassword.setBorder(compoundBorderBlue);
                    } else {
                        txtPassword.setBorder(compoundBorderRed);
                    }
                } else {
                    txtPassword.setBorder(compoundBorderRed);
                    if (!new String(txtPassword.getPassword()).trim().isEmpty()) {
                        txtPassword.setBorder(compoundBorderBlue);
                        description.setText(" ");
                    }
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                txtPassword.setBorder(defaultBorder);
            }
        });

        txtUsername.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!checkBorder[1]) {
                    txtUsername.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
                if (!checkBorder[0]) {
                    txtUsername.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        txtPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!checkBorder[1]) {
                    txtPassword.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
                if (!checkBorder[0]) {
                    txtPassword.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        cmdLogin.addActionListener((e) -> {
            if (checkNull()) {
                if (checkLogin(txtUsername.getText(), txtPassword.getPassword())) {
                    MainFrame.mainFrame.showMainForm(role);
                } else {
                    description.setText("Sai tên đăng nhập hoặc mật khẩu !!!");
                    description.setForeground(Color.red);
                    txtUsername.requestFocus();
                    checkBorder[0] = false;
                }
                checkBorder[1] = true;
            } else {
                checkBorder[1] = false;
            }

        });

        panelButton.add(closeButton, "cell 1 0, align right");
        panel.add(panelButton);
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Tên đăng nhập"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Mật khẩu"), "gapy 8");
        panel.add(txtPassword);
        panel.add(cmdLogin, "gapy 20");
        add(panel);

    }

    public boolean checkLogin(String username, char[] password) {
        for (NhanVien nv : listAccount) {
            if (username.equals(nv.getTaiKhoan())) {
                if (new String(password).equals(nv.getMatKhau())) {
                    role = nv.getChucVu();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkNull() {
        if (txtUsername.getText().isEmpty()) {
            description.setText("Không được để trống tên tài khoản !");
            description.setForeground(Color.red);
            txtUsername.requestFocus();
            return false;
        } else if (new String(txtPassword.getPassword()).trim().isEmpty()) {
            description.setText("Không được để trống mật khẩu !");
            description.setForeground(Color.red);
            txtPassword.requestFocus();
            return false;
        }

        return true;
    }

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton cmdLogin;

    static class RoundedBorder implements Border {

        private int radius;
        private Color color;
        private int thickness;

        RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
