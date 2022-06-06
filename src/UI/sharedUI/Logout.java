package UI.sharedUI;

import UI.AdminWindow;
import UI.LoginWindow;
import UI.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logout {
    public static Logout INSTANCE = new Logout();
    private JPanel logoutPanel  = new JPanel();
    private Logout() {

        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setBounds(120, 120, 100, 40);
        logoutBtn.addActionListener(new LogoutListener());
        JLabel prompt  = new JLabel("Are you sure you want to logout?");
        prompt.setFont(new java.awt.Font("SansSerif", 1, 18));
        prompt.setForeground(new java.awt.Color(170, 98, 0));
        prompt.setBounds(130, 180, 500, 20);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(null);
        btnPanel.setBounds(110,110 , 300, 300);

        logoutBtn.setBackground(new java.awt.Color(170, 98, 0));
        logoutBtn.setOpaque(true);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);

        btnPanel.add(logoutBtn);//, BorderLayout.CENTER);

        logoutPanel = new JPanel();
        logoutPanel.setLayout(null);
        logoutPanel.add(prompt);// , BorderLayout.NORTH);
        logoutPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        logoutPanel.add(btnPanel);//, BorderLayout.CENTER);
    }

    public JPanel getLoginPanel(){return logoutPanel;};

    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Setting.hideAllWindows();
            LoginWindow.INSTANCE.setVisible(true);
        }
    }

}
