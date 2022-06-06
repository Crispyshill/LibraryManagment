package UI.sharedUI.checkOut;

import UI.Setting;
import UI.ruleSet.RuleException;
import UI.ruleSet.RuleSet;
import UI.ruleSet.RuleSetFactory;
import business.*;
import business.exceptions.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
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

    private SystemController ci = new SystemController();

    public  JScrollPane getCheckOutPanel() {
        return new JScrollPane(this);
    }

    private void addCheckoutForm() {

        JPanel memberFormPanel = createCheckOutForm();

        JButton addBMemberBtn = new JButton("CheckOut Book");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBMemberBtn.setPreferredSize(Setting.BTN_DIMENSION);
        addBMemberBtn.addActionListener(new CheckOutUI.checkOutListener());
        btnPanel.add(addBMemberBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(new Dimension(300, 150));

        container.add(new JScrollPane());
        container.add(memberFormPanel, BorderLayout.CENTER);
        container.add(btnPanel, BorderLayout.SOUTH);

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
            try {
                RuleSet bookRules = RuleSetFactory.getRuleSet(CheckOutUI.this);
                bookRules.applyRules(CheckOutUI.this);

                String memberId = checkOutFields[0].getText().trim();
                String isbn = checkOutFields[1].getText().trim();

                // Check if member is available
                if( !ci.checkMemberId(memberId) || ci.getMembers().get(memberId) == null)
                    System.out.println("No member found with Member ID = "+ memberId);
                else {
                    Book book = ci.getBooks().get(isbn);
                    if (book == null)
                        System.out.println("No book with ISBN = " + isbn + " found");
                    else {
                        if (!book.isAvailable()) {
                            System.out.println("The book with ISBN  = " + isbn + " is not currently available");
                            throw new CheckOutException("The book with ISBN  = " + isbn + " is not currently available");
                        } else {
                            BookCopy copy = book.getNextAvailableCopy();

                            if (copy == null) {
                                System.out.println("No copies of this book available currently");
                                throw new CheckOutException("No copies of this book available currently");
                            } else {
                                ci.checkOutBook(memberId, isbn);
                                clearFormFields();
                                ViewCheckOut.INSTANCE.refreshCheckOutList();
                            }
                        }
                    }
                }

            } catch (NumberFormatException ex){
                System.out.println("Input for Max days should be a number");
            } catch (CheckOutException | LibrarySystemException | RuleException ex ) {
                System.out.println("Error :" + ex.getMessage());
            }
        }
    }

    public void clearFormFields(){
        for(JTextField field : checkOutFields){
            field.setText("");
        }
    }

}
