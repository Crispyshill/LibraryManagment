package UI;

import business.exceptions.LoginException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame implements LibWindow {

    public static final LoginWindow INSTANCE = new LoginWindow();
    private boolean isInitialized = false;

    @Override
    public void init() {

         this.setSize(520, 400);
         createMyGUI();
         add(mainPanel);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    JPanel jPBody;
    JPanel mainPanel;
    JPanel jPButtonPanel;

    private JLabel jLTitle;
    JLabel jLUserName;
    JTextField jTFUserName;
    private JLabel jLPassword;
    JPasswordField jTFPassword;
    JButton jBLogin;

    private LoginWindow() {UIController.INSTANCE.loginWindow = this;}

    private void createMyGUI() {

        mainPanel = new JPanel();
        jLTitle = new JLabel();
        jPBody = new JPanel();
        jTFUserName = new JTextField();
        jTFPassword = new JPasswordField();
        jLUserName = new JLabel();
        jLPassword = new JLabel();
        jPButtonPanel = new JPanel();
        jBLogin = new JButton();

        //this is the main panel
        mainPanel.setBackground(new java.awt.Color(80, 1, 192));
        mainPanel.setToolTipText("");
        mainPanel.setLayout(null);

        jLTitle.setText("Library Management System");
        jLTitle.setForeground(Color.orange);
        mainPanel.add(jLTitle);
        jLTitle.setBounds(170, 20, 300, 40);

        jPBody.setLayout(null);
        jPBody.add(jTFUserName);
        jTFUserName.setBounds(10, 50, 210, 25);

        jPBody.add(jTFPassword);
        jTFPassword.setBounds(10, 130, 210, 25);

        //username
        jLUserName.setText("Username");
        jPBody.add(jLUserName);
        jLUserName.setBounds(20, 20, 70, 30);

        //password
        jLPassword.setText("Password");
        jPBody.add(jLPassword);
        jLPassword.setBounds(20, 104, 60, 20);


        //login button
        BorderLayout bl = new BorderLayout();

        jPButtonPanel.setLayout(bl);
        jBLogin.setText("Login");

        jPBody.add(jBLogin);
        jBLogin.setBounds(80, 170, 100, 40);
        addLoginButtonListener(jBLogin);


        mainPanel.add(jPBody);
        jPBody.setBounds(150, 70, 230, 230);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 800, 400);
        this.setResizable(false);

    }

    private void addLoginButtonListener(JButton button) {
        button.addActionListener(evt -> {
            SystemController sys = new SystemController();
            try{

                String username = jTFUserName.getText();
                char[] pass = jTFPassword.getPassword();
                String password = new String(pass);

                sys.login(username,password);

            }catch(LoginException ex){
               System.out.println(ex.getMessage());
            }
        });
    }

}
