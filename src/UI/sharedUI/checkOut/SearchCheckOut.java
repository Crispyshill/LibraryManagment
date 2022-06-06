package UI.sharedUI.checkOut;
import UI.ruleSet.RuleException;
import UI.ruleSet.RuleSet;
import UI.ruleSet.RuleSetFactory;
import business.*;
import business.exceptions.BookCopyException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SearchCheckOut extends JPanel{

    public static  SearchCheckOut INSTANCE = new SearchCheckOut();
    // form elements
    private final String[] printMemberAttributes = {"Member ID"};
    private final JTextField[] printMemberFields = new JTextField[printMemberAttributes.length];
    // Gui elements
    private JTable myTable;
    private JPanel printMemberCheckOutPanel;

    private SystemController ci = new SystemController();
    private JButton printButton = new JButton("Print");
    public JTextField[] getMemberFields() {
        return printMemberFields;
    }

    private SearchCheckOut() {
        printMemberCheckOutForm();
        myTable = loadTableData();
        myTable.setDefaultEditor(Object.class , null);
        printMemberCheckOutPanel.add(new JScrollPane(myTable) , BorderLayout.AFTER_LAST_LINE);
    }

    private JTable loadTableData() {
        String column[]={"ISBN", "TITLE", "COPY N0.", "MEMBER ID"};

        DefaultTableModel model = new DefaultTableModel(null, column);

        return new JTable(model);
    }

    private void printMemberCheckOutForm() {

        printMemberCheckOutPanel = new JPanel(new BorderLayout());
        JPanel addFormPanel = searchCheckOutForm();

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(new searchBookListener());

        addFormPanel.add(searchBtn);

        printMemberCheckOutPanel.add(addFormPanel, BorderLayout.CENTER);

    }

    public  JScrollPane getSearchCheckoutPanel(){ return new JScrollPane(printMemberCheckOutPanel); }

    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        printMemberFields[jtextFieldIndex] = new JTextField(20);

        JPanel nameForm = new JPanel();
        nameForm.add(label, BorderLayout.NORTH);
        nameForm.add(printMemberFields[jtextFieldIndex], BorderLayout.CENTER);

        return nameForm;
    }

    private JPanel searchCheckOutForm() {

        JPanel bookFormPanel = new JPanel();
        for (int i = 0; i < printMemberFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(printMemberAttributes[i], i));
        }
        return bookFormPanel;
    }

    public void clearFormFields(){
        for(JTextField field : printMemberFields){
            field.setText("");
        }
    }

    private class searchBookListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {

                RuleSet bookRules = RuleSetFactory.getRuleSet(SearchCheckOut.this);
                bookRules.applyRules(SearchCheckOut.this);

                String memberId = printMemberFields[0].getText().trim();

                // check if member already exists
                if(!ci.allMemberIds().contains(memberId))
                    throw new BookCopyException("No Member  with ID  =  " + printMemberFields[0].getText().trim() + " found");

                int counter = searchMemberRecord(memberId);

                if(counter == 0)
                    System.out.println("No Checkout record with Member ID = " + memberId);
                else{
                    System.out.println("checkouts records found  for Member ID = " + memberId);
                    printButton.setEnabled(true);
                }

                clearFormFields();

            } catch (BookCopyException |  RuleException | NumberFormatException  ex) {
                System.out.println("Error " + ex.getMessage());
            }
        }
    }

    int searchMemberRecord(String memberId){
        HashMap<String , LibraryMember> libraryMemberHashMap = ci.getMembers();
        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        int record = 0 ;

        if( libraryMemberHashMap !=null){
            model.setRowCount(0);
            for(String key : libraryMemberHashMap.keySet()){
                LibraryMember member = libraryMemberHashMap.get(key);
               if(member.getMemberId().equals(memberId))
                for(CheckOutEntry entry : member.getRecord().getEntries()){
                    model.insertRow(0, new  Object[]{entry.getCopy().getBook().getIsbn(), entry.getCopy().getBook().getTitle(), entry.getCopy().getCopyNum(), member.getMemberId()});
                    record++;
                }
            }
        }

        return record;
    }
}
