package business;
import business.exceptions.BookCopyException;
import business.exceptions.LoginException;

import java.util.HashMap;
import java.util.List;

public interface ControllerInterface {

	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();

	HashMap<String, Book> getBooks();
	HashMap<String, LibraryMember> getMembers();

	public void addCopy(String isbn) throws BookCopyException;
	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) ;
}
