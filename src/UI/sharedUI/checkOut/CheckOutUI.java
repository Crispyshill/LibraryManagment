package UI.sharedUI.checkOut;

import UI.Setting;
import UI.sharedUI.book.BookUI;
import UI.sharedUI.book.SearchBookPanel;
import business.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CheckOutUI extends JPanel{
    private final String[] checkOutAttributes = {"Member ID", "ISBN"};
    public JTextField[] getCheckOutFields() {
        return checkOutFields;
    }
    private final JTextField[] checkOutFields = new JTextField[checkOutAttributes.length];
    private JPanel addCheckoutForm;
    public static final CheckOutUI INSTANCE = new CheckOutUI();
    CheckOutUI() {
        addCheckoutForm();
    }
    private ControllerInterface ci = new SystemController();
    public  JScrollPane getCheckOutPanel() {
        return new JScrollPane(addCheckoutForm);
    }
    private void addCheckoutForm() {
        JPanel addFormPanel = createCheckOutForm();

        JButton checkoutBtn = new JButton("Check Out");
        checkoutBtn.setPreferredSize(Setting.BTN_DIMENSION);
        checkoutBtn.addActionListener(new checkOutListener());

        JPanel checkoutBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        checkoutBtnPanel.add(checkoutBtn );

        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(Setting.PANEL_DIMENSION);

        container.add(new JScrollPane());
        container.add(addFormPanel, BorderLayout.CENTER);
        container.add(checkoutBtnPanel, BorderLayout.SOUTH);

        this.add(container);
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

        String column[]={"Member ID", "ISBN","COPY N0.", "CHECKOUT DATE", "DUE DATE"};
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
            //TO DO
        }
    }
}
