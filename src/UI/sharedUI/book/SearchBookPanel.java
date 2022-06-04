package UI.sharedUI.book;

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
import java.util.List;

public class SearchBookPanel extends JPanel{

    public static  SearchBookPanel INSTANCE = new SearchBookPanel();
    // form elements
    private final String[] bookAttributes = {"ISBN"};
    private final JTextField[] bookFields = new JTextField[bookAttributes.length];

    // Gui elements
    private JTable myTable;
    private JPanel searchBookPanel;

    private ControllerInterface ci = new SystemController();

    private SearchBookPanel() {
        searchBookForm();
        myTable = loadTableData();
        searchBookPanel.add(new JScrollPane(myTable) , BorderLayout.AFTER_LAST_LINE);
    }

    private JTable loadTableData() {

        String column[]={"ISBN","TITLE","COPY NUMBER", "MEMBER NAME", "DUE DATE"};
        DefaultTableModel model = new DefaultTableModel(null, column);
        return new JTable(model);
    }

    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void searchBookForm() {

        searchBookPanel = new JPanel(new BorderLayout());
        JPanel addFormPanel = createsSearchBookForm();

        // add add button
        JButton addBookBtn = new JButton("Search");
        addBookBtn.addActionListener(new searchBookListener());

        addFormPanel.add(addBookBtn);
        // add to book Panel at the bottom
        searchBookPanel.add(addFormPanel, BorderLayout.CENTER);

    }

    public  JScrollPane getSearchBookPanel(){ return new JScrollPane(searchBookPanel); }

    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        bookFields[jtextFieldIndex] = new JTextField(20);

        JPanel nameForm = new JPanel();
        nameForm.add(label, BorderLayout.NORTH);
        nameForm.add(bookFields[jtextFieldIndex], BorderLayout.CENTER);

        return nameForm;
    }

    private JPanel createsSearchBookForm() {

        JPanel bookFormPanel = new JPanel();
        for (int i = 0; i < bookFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(bookAttributes[i], i));
        }
        return bookFormPanel;
    }

    private void addRowToJTable(Book book){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        if(model.getRowCount() > 0)
            model.setRowCount(0);
        for(int i : book.getCopyNums()){
            model.addRow(new  Object[]{
                    book.getIsbn() ,
                    book.getTitle(),
                    "No - " + i,
                    null,
                    null}
            );
        }
    }

    private void addRowToJTable(List<CheckOutEntry> books){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        if(model.getRowCount() > 0)
            model.setRowCount(0);
        for(CheckOutEntry coe : books){
            model.addRow(new  Object[]{
                    coe.getCopy().getBook().getIsbn(),
                    coe.getCopy().getBook().getTitle(),
                    "No - " + coe.getCopy().getBook().getNumCopies(),
                    coe.getRecord().getMember().getFirstName() + " " + coe.getRecord().getMember().getLastName(),
                    coe.getDueDate()
                }
            );
        }
    }

    private void addRowToJTable(CheckOutEntry entry){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        if(model.getRowCount() > 0)
            model.setRowCount(0);

        model.addRow(new  Object[]{
                entry.getCopy().getBook().getIsbn() ,
                entry.getCopy().getBook().getTitle(),
                entry.getCopy().getCopyNum(),
                entry.getCopy().getBook().getAuthors().toString(),
                entry.getDueDate()});
    }
    public void clearFormFields(){
        for(JTextField field : bookFields){
            field.setText("");
        }

    }

    private class searchBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {
            try {
                RuleSet bookRules = RuleSetFactory.getRuleSet(SearchBookPanel.this);
                bookRules.applyRules(SearchBookPanel.this);

                String isbn = bookFields[0].getText().trim();

                if(!ci.allBookIds().contains(isbn))
                    throw new BookCopyException("No book with ISBN  =  " + bookFields[0].getText().trim() + " found");

                Book book = ci.getBooks().get(bookFields[0].getText().trim());
                List<CheckOutEntry> books = ci.allOverDueBooks();

                if(books.size() == 0)
                    throw new BookCopyException("No record found");

                addRowToJTable(books);

                System.out.println("1 Results found");

                clearFormFields();

            } catch (BookCopyException | RuleException |NumberFormatException ex) {
                  System.out.println(ex.getMessage());
            }
        }
    }

}
