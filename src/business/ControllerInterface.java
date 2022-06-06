package business;
import business.exceptions.BookCopyException;
import business.exceptions.LibraryMemberException;
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
	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws BookCopyException;
	public void saveBook(Book book);
	public List<CheckOutEntry> allOverDueBooks(String bookId);
	boolean checkMemberId(String member_id);
	Address addAddress(String street , String city , String state , String zip);
	LibraryMember addLibraryMember(String memberNumber , String firstName , String lastName , String phoneNumber , Address address);
	void saveLibraryMember(LibraryMember member) throws LibraryMemberException;
	public HashMap<String, CheckOutRecord> getCheckOutRecord();
	HashMap<CheckOutRecord, CheckOutEntry> getCheckOutEntry();

}
