package UI.sharedUI.book;

import UI.Setting;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookUI extends JPanel{

    private String[] bookAttributes = {"Title", "ISBN", "Max days" , "Authors"};
    private JTextField[] bookFields = new JTextField[bookAttributes.length];
    private final String[] bookCopyAttributes = {"ISBN"};

    private final JTextField[] bookCopyFields = new JTextField[bookCopyAttributes.length];
    private JPanel addBookPanel;

    public static BookUI INSTANCE = new BookUI();
    private JTable myTable;

    private ControllerInterface ci = new SystemController();

    private BookUI() {
        addBookForm();
        myTable = loadTableData();
        myTable.setDefaultEditor(Object.class , null);
    }

    private JTable loadTableData() {

        String column[]={"ISBN","TITLE","AUTHORS", "MAX BORROW DAYS", "NUMBER OF COPIES"};
        HashMap<String , Book> bookHashMap = ci.getBooks();
        String bookData [][] = new String[bookHashMap.size()][column.length];
        List<String> bookID = ci.allBookIds();

        for(int i = 0 ; i < bookID.size(); i++){
            Book book = bookHashMap.get(bookID.get(i));
            bookData[i][0] = book.getIsbn();
            bookData[i][1] = book.getTitle();
            bookData[i][2] = book.getAuthors().toString();
            bookData[i][3] = ""+book.getMaxCheckoutLength();
            bookData[i][4] = ""+book.getNumCopies();
        }

        DefaultTableModel model = new DefaultTableModel(bookData, column);
        model.fireTableDataChanged();

        return new JTable(model);
    }

    private DefaultTableModel updateTableModel(){

        String column[]={"ISBN","TITLE","AUTHORS", "MAX BORROW DAYS", "NUMBER OF COPIES"};
        HashMap<String , Book> bookHashMap = ci.getBooks();
        String bookData [][] = new String[bookHashMap.size()][column.length];
        List<String> bookID = ci.allBookIds();

        for(int i = 0 ; i < bookID.size(); i++){
            Book book = bookHashMap.get(bookID.get(i));
            bookData[i][0] = book.getIsbn();
            bookData[i][1] = book.getTitle();
            bookData[i][2] = book.getAuthors().toString();
            bookData[i][3] = ""+book.getMaxCheckoutLength();
            bookData[i][4] = ""+book.getNumCopies();
        }

        DefaultTableModel model = new DefaultTableModel(bookData, column);

        return model;
    }

    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void addBookForm() {
        JPanel addFormPanel = createAddBookForm();

        JButton addBBookBtn = new JButton("Add Book");
        addBBookBtn.setPreferredSize(Setting.BTN_DIMENSION);

        addBBookBtn.addActionListener(new addBookListener());
        JPanel addBookBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        addBookBtnPanel.add(addBBookBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(Setting.PANEL_DIMENSION);

        container.add(new JScrollPane());
        container.add(addFormPanel, BorderLayout.CENTER);
        container.add(addBookBtnPanel, BorderLayout.SOUTH);

        addBookPanel = container;

        // add this to the current
        this.add(container);
    }

    public  JScrollPane getAddBookPanel(){ return new JScrollPane(this); }

    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.CENTER);

        bookFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(bookFields[jtextFieldIndex], BorderLayout.CENTER);

        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.WEST);
        nameForm.add(formPanel, BorderLayout.EAST);

        return nameForm;
    }

    public void refreshBookList() {
        myTable.setModel(updateTableModel());
        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        model.fireTableDataChanged();
    }
    public  JTable getBookList() {
        return this.myTable;
    }

    private JPanel createAddBookForm() {

        JPanel bookFormPanel = new JPanel(new GridLayout(bookAttributes.length, 0));

        for (int i = 0; i < bookFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(bookAttributes[i], i));
        }
        return bookFormPanel;
    }

    private void addRowToJTable( String isbn, String title, int maxBorrowDays, String authors){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        model.insertRow(0, new  Object[]{isbn , title , authors, maxBorrowDays, 1});
    }

    private class addBookListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {
                RuleSet bookRules = RuleSetFactory.getRuleSet(BookUI.this);
                bookRules.applyRules(BookUI.this);

                String title = bookFields[0].getText().trim();
                String isbn = bookFields[1].getText().trim();
                String authorNames = bookFields[3].getText().trim();

                int maxBorrowDays = Integer.parseInt(bookFields[2].getText());
                Author author = new Author(authorNames, "L.", "12212" , new Address("1000 N. 4th st.", "Fairfield", "IA", "52557"), "Student");
                List<Author> authors = new ArrayList<Author>();
                authors.add(author);
                ci.addBook(isbn, title, maxBorrowDays, (ArrayList<Author>) authors);

                System.out.println("New book added successfully");
                addRowToJTable( isbn, title, maxBorrowDays, authors.toString());

                clearFormFields();

            } catch (BookCopyException ex) {
                System.out.println("Error");
            } catch (NumberFormatException ex){
                System.out.println("Input for Max days should be a number");
            } catch (RuleException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
    public void clearFormFields(){
        for(JTextField field : bookFields) {
            field.setText("");
        }
    }

}
