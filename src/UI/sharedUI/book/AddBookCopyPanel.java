package UI.sharedUI.book;

import UI.ruleSet.RuleException;
import UI.ruleSet.RuleSet;
import UI.ruleSet.RuleSetFactory;
import business.*;
import business.exceptions.BookCopyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddBookCopyPanel extends JPanel{
    public static  AddBookCopyPanel INSTANCE = new AddBookCopyPanel();
    // form elements
    private final String[] bookAttributes = {"ISBN"};
    private final JTextField[] bookFields = new JTextField[bookAttributes.length];

    // Gui elements
    private JPanel AddBookCopyPanel;

    private ControllerInterface ci = new SystemController();

    private AddBookCopyPanel() {
        searchBookForm();
    }

    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void searchBookForm() {

        AddBookCopyPanel = new JPanel(new BorderLayout());

        JPanel addFormPanel = createsSearchBookForm();

        JButton addBookBtn = new JButton("Add copy");
        addBookBtn.addActionListener(new AddBookCopyListener());

        addFormPanel.add(addBookBtn);

        AddBookCopyPanel.add(addFormPanel, BorderLayout.CENTER);
    }

    public  JScrollPane getAddBookCopyPanel(){ return new JScrollPane(AddBookCopyPanel); }

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


    public void clearFormFields(){
        for(JTextField field : bookFields){
            field.setText("");
        }
    }

    private class AddBookCopyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {
                RuleSet bookRules = RuleSetFactory.getRuleSet(AddBookCopyPanel.this);
                bookRules.applyRules(AddBookCopyPanel.this);

                String isbn = bookFields[0].getText().trim();

                if(!ci.allBookIds().contains(isbn))
                    throw new BookCopyException("No book with ISBN  =  " + isbn + " found");
                Book book = ci.getBooks().get(isbn);

                if(book == null)
                    throw new BookCopyException("Unable to load book details");

                book.addBookCopy();
                ci.saveBook(book);

                BookUI.INSTANCE.refreshBookList();

                System.out.println("1 Copy added successfully ");
                clearFormFields();

            } catch (BookCopyException | RuleException |NumberFormatException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

}
