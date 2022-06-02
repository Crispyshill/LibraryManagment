package business;
import business.exceptions.LibrarySystemException;
import business.exceptions.LoginException;

import java.util.List;

public interface ControllerInterface {

	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();

	public void addCopy(String isbn) throws LibrarySystemException;
	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors);
}
