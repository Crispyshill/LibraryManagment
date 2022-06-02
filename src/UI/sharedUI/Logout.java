package UI.sharedUI;

import UI.UIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logout {

    public static Logout INSTANCE = new Logout();
    private JPanel logoutPanel  = new JPanel();

    private Logout() {

        JButton logoutBtn = new JButton("Log out");

        //logoutBtn.setPreferredSize(new Dimension(20, 30));
        logoutBtn.setBounds(120, 120, 100, 40);
        logoutBtn.addActionListener(new LogoutListener());
        JLabel prompt  = new JLabel("Are you sure you want to logout ?");
        prompt.setBounds(185, 180, 200, 20);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(null);
        btnPanel.setBounds(110,110 , 300, 300);
        
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
          UtilGui.hideAllWindows();
            UIController.INSTANCE.loginWindow.setVisible(true);
        }
    }
}
