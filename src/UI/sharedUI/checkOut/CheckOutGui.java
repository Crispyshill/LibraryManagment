package UI.sharedUI.checkOut;

import UI.Setting;
import UI.Utility;
import business.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckOutGui extends JPanel{

    private final String[] checkOutAttributes = {"Member ID", "ISBN"};

    public JTextField[] getCheckOutFields() {
        return checkOutFields;
    }
    private final JTextField[] checkOutFields = new JTextField[checkOutAttributes.length];
    private JPanel addCheckoutForm;

    public static final CheckOutGui INSTANCE = new CheckOutGui();

    CheckOutGui() {
        addCheckoutForm();
    }

    private ControllerInterface ci = new SystemController();

    public  JPanel getCheckOutPanel() {
        return addCheckoutForm;
    }

    private void addCheckoutForm() {

        addCheckoutForm = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Check out Book");
        panelTitle.setFont(Setting.DEFUALT_FONT);
        panelTitle.setForeground(Utility.DARK_BLUE);
        addCheckoutForm.add(panelTitle , BorderLayout.NORTH);

        JPanel addFormPanel = createCheckOutForm();
        addCheckoutForm.add(addFormPanel , BorderLayout.CENTER);

        // add add button
        JButton checkoutBtn = new JButton("Check Out");
        checkoutBtn.addActionListener(new checkOutListener());
        JPanel checkoutBtnPanel = new JPanel(new BorderLayout());
        checkoutBtnPanel.add(checkoutBtn, BorderLayout.CENTER);

        // add to book Panel at the bottom
        addCheckoutForm.add(checkoutBtnPanel, BorderLayout.SOUTH);

    }


    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.NORTH);

        checkOutFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(checkOutFields[jtextFieldIndex], BorderLayout.NORTH);

        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.NORTH);
        nameForm.add(formPanel, BorderLayout.CENTER);

        return nameForm;
    }

    public  JTable getCheckOutList() {

        String column[]={"Member ID","ISBN","COPY N0.", "CHECKOUT DATE", "DUE DATE"};
        HashMap<String , LibraryMember> libraryMemberHashMap = ci.getMembers();
        DefaultTableModel model = new DefaultTableModel(null, column);
        if( libraryMemberHashMap !=null){

            for(String key : libraryMemberHashMap.keySet()){
                LibraryMember member = libraryMemberHashMap.get(key);
                for(CheckOutEntry entry : member.getRecord().getEntries()){
                    model.addRow(new  Object[]{ key , entry.getCopy().getBook().getIsbn(), entry.getCopy().getCopyNum(), entry.getCheckOutDate(), entry.getDueDate()});
                }
            }
        }
        return new JTable(model);
    }

    private JPanel createCheckOutForm() {

        JPanel checkoutFormPanel = new JPanel(new GridLayout(checkOutFields.length, 0));
        for (int i = 0; i < checkOutFields.length; i++) {
            checkoutFormPanel.add(getElementWithLabelBook(checkOutAttributes[i], i));
        }
        return checkoutFormPanel;
    }

    private class checkOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {


        }
    }
}
