package UI;

import UI.ruleSet.RuleException;
import UI.ruleSet.RuleSet;
import UI.ruleSet.RuleSetFactory;
import UI.sharedUI.checkOut.CheckOutUI;
import business.exceptions.LoginException;
import business.SystemController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class LoginWindow extends JFrame implements LibWindow {

    public static final LoginWindow INSTANCE = new LoginWindow();
    private boolean isInitialized = false;

    @Override
    public void init() {

         this.setSize(720, 400);
         createMyGUI();
        // clearFormFields();
         add(mainPanel);
         Setting.centerFrameOnDesktop(this);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void clearFormFields(){
         jTFUserName.setText("");
         jTFPassword.setText("");
    }
    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    JPanel jPBody;
    JPanel mainPanel;

    private JLabel jLTitle;
    JLabel jLUserName;
    JTextField jTFUserName;
    private JLabel jLPassword;
    JPasswordField jTFPassword;
    JButton jBLogin;

    private LoginWindow() {UIController.INSTANCE.loginWindow = this;}

    private Image requestImage() {
        Image image = null;
        try {
            image = ImageIO.read(getClass().getResource("bgImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    private void createMyGUI() {

        mainPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(requestImage(), 0, 0,this.getWidth(), this.getHeight(),null);
            }
        };

        jLTitle = new JLabel();
        jPBody = new JPanel();
        jTFUserName = new JTextField();
        jTFPassword = new JPasswordField();
        jLUserName = new JLabel();
        jLPassword = new JLabel();
        jBLogin = new JButton();

        //this is the main panel
        mainPanel.setBackground(new java.awt.Color(80, 1, 192));
        mainPanel.setToolTipText("");
        mainPanel.setLayout(null);

        jLTitle.setText("Library Management System");
        jLTitle.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLTitle.setForeground(new java.awt.Color(170, 98, 0));
        jLTitle.setBounds(80, 15, 300, 30);

        jPBody.setLayout(null);
        jPBody.add(jLTitle);

        //username
        jLUserName.setText("Username");
        jPBody.add(jLUserName);
        jLUserName.setBounds(105, 60, 70, 30);

        jPBody.add(jTFUserName);
        jTFUserName.setBounds(100, 85, 210, 25);

        //password
        jLPassword.setText("Password");
        jPBody.add(jLPassword);
        jLPassword.setBounds(105, 110, 60, 30);

        jPBody.add(jTFPassword);
        jTFPassword.setBounds(100, 135, 210, 25);
        //login button

        jBLogin.setText("Login");
        jBLogin.setBackground(new java.awt.Color(170, 98, 0));
        jBLogin.setOpaque(true);
        jBLogin.setBorderPainted(false);
        jBLogin.setFocusPainted(false);

        jPBody.add(jBLogin);
        jBLogin.setBounds(150, 190, 100, 40);
        addLoginButtonListener(jBLogin);

        mainPanel.add(jPBody);
        jPBody.setBounds(150, 70, 400, 260);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 800, 400);
        this.setResizable(false);
    }

    private void addLoginButtonListener(JButton button) {
        button.addActionListener(evt -> {
            SystemController sys = new SystemController();
            try{
                RuleSet bookRules = RuleSetFactory.getRuleSet(LoginWindow.this);
                bookRules.applyRules(LoginWindow.this);

                String username = jTFUserName.getText();
                char[] pass = jTFPassword.getPassword();
                String password = new String(pass);
                clearFormFields();
                sys.login(username,password);

            } catch(LoginException | RuntimeException | RuleException ex){
               System.out.println(ex.getMessage());
            }
        });
    }

    public JTextField getUserName() {
        return jTFUserName;
    }

    public JPasswordField getPassword() {
        return jTFPassword;
    }

}
