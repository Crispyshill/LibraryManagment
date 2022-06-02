package UI;
import UI.Setting;
import UI.Utility;
import UI.sharedUI.UtilGui;
import business.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookGuii extends JPanel{

    private String[] bookAttributes = {"Title", "ISBN", "Max days" , "Authors"};
    private JTextField[] bookFields = new JTextField[bookAttributes.length];

    private JPanel addBookPanel;
    public static  BookGuii INSTANCE = new BookGuii();
    private JTable myTable;

    private ControllerInterface ci = new SystemController();

    private BookGuii() {
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
        return new JTable(model);
    }
    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void addBookForm() {

        JLabel panelTitle = new JLabel(" Add New Book");
        panelTitle.setFont(Setting.DEFUALT_FONT);
        panelTitle.setForeground(Utility.DARK_BLUE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        titlePanel.add(panelTitle, BorderLayout.CENTER);
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);

        JPanel addFormPanel = createAddBookForm();

        // add add button
        JButton addBBookBtn = new JButton("Add Book");
        addBBookBtn.setPreferredSize(UtilGui.BTN_DIMENSION);
        addBBookBtn.addActionListener(new addBookListiner());
        JPanel addBookBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBookBtnPanel.add(addBBookBtn);


        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(UtilGui.PANEL_DIMENSION);

        // combine
        container.add(titlePanel, BorderLayout.NORTH);
        container.add(new JScrollPane());
        container.add(addFormPanel, BorderLayout.CENTER);
        container.add(addBookBtnPanel, BorderLayout.SOUTH);

        // add this to the current
        this.add(container);
    }

    public  JPanel getAddBookPanel(){ return this; }

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


    private class addBookListiner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

        }
    }
    public void clearFormFields(){
        for(JTextField field : bookFields){
            field.setText("");
        }

    }


}
