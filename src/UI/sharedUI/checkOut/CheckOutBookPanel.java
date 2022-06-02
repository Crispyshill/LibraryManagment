package UI.sharedUI.checkOut;

import UI.Setting;
import UI.Utility;
import UI.sharedUI.UtilGui;
import business.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckOutBookPanel extends JPanel{

    private final String[] checkOutAttributes = {"Member ID", "ISBN"};

    public JTextField[] getCheckOutFields() {
        return checkOutFields;
    }
    private final JTextField[] checkOutFields = new JTextField[checkOutAttributes.length];

    public static final CheckOutBookPanel INSTANCE = new CheckOutBookPanel();

    CheckOutBookPanel() {  addCheckoutForm();}

    private ControllerInterface ci = new SystemController();

    public  JPanel getCheckOutPanel() {
        return this;
    }

    private void addCheckoutForm() {


        JLabel panelTitle = new JLabel(" Book checkout form");
        panelTitle.setFont(Setting.DEFUALT_FONT);
        panelTitle.setForeground(Utility.DARK_BLUE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        titlePanel.add(panelTitle, BorderLayout.CENTER);
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);

        JPanel checkoutF = createCheckOutForm();

        // add add button
        JButton addBMemberBtn = new JButton("Checkout");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBMemberBtn.setPreferredSize(UtilGui.BTN_DIMENSION);
        addBMemberBtn.addActionListener(new checkOutListener());
        btnPanel.add(addBMemberBtn);

        JPanel container = new JPanel(new BorderLayout());
        //container.setPreferredSize(UtilGui.PANEL_DIMENSION);

        // combine
        container.add(titlePanel, BorderLayout.NORTH);
        container.add(checkoutF, BorderLayout.CENTER);
        container.add(btnPanel, BorderLayout.SOUTH);


        this.add(container);
    }
    private JPanel getElementWithLabelMember(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.NORTH);

        checkOutFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(checkOutFields[jtextFieldIndex], BorderLayout.NORTH);

        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.WEST);
        nameForm.add(formPanel, BorderLayout.EAST);

        return nameForm;
    }


    private JPanel createCheckOutForm() {

        JPanel checkoutFormPanel = new JPanel(new GridLayout(checkOutFields.length , 0));
        for (int i = 0; i < checkOutFields.length; i++) {
            checkoutFormPanel.add(getElementWithLabelMember(checkOutAttributes[i], i));
        }
        return checkoutFormPanel;
    }

    private class checkOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

        }
    }
    public void clearFormFields(){
        for(JTextField field : checkOutFields){
            field.setText("");
        }

    }
}
