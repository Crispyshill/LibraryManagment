package business.Controllers;
/*
 * This class is used to add new book into the library system
 * Uses DataAccessFacade class.
 */
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.List;

public class BookController {

    //add other actions related to books
    public void addNewBook(Book book, DataAccess dataAccessFacade) {
        dataAccessFacade.saveNewBook(book);
    }
}
