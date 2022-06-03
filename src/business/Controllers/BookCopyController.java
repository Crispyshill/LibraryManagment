package business.Controllers;
/*
 * This class used to control BookCopy.
 * There is a OneToMany relationship between Book and BookCopy
 */
import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.HashMap;

public class BookCopyController {
    // operations related with book copy
    public void addNewBookCopy(String book_isbn, DataAccess dataAccessFacade) {
        for(Book b : dataAccessFacade.readBooksMap().values()){
            if(b.getIsbn().equals(book_isbn)){
                b.addBookCopy();
                return;
            }
        }
    }

    public void addNewBookCopy(String book_isbn) {

        DataAccessFacade da = new DataAccessFacade();
        HashMap bookMap = da.readBooksMap();
        Book book = (Book) bookMap.get(book_isbn);
        book.addBookCopy();

    }

}
