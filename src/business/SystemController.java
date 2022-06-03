package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import UI.AdminWindow;
import business.Controllers.BookController;
import business.Controllers.BookCopyController;
import business.Controllers.MemberController;
import business.exceptions.BookCopyException;
import business.exceptions.LibrarySystemException;
import business.exceptions.LoginException;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	private DataAccess da = new DataAccessFacade();

	public void login(String id, String password) throws LoginException {

		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}

		currentAuth = map.get(id).getAuthorization();
		openWindow();

		System.out.println("Welcome " + id + ": role  = " + currentAuth);
	}
	@Override
	public List<String> allMemberIds() {
		
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	public List<CheckOutEntry> allOverDueBooks(){
		List<CheckOutEntry> overDueBooks = new ArrayList<>();
		for(LibraryMember m : da.readMemberMap().values()){
			overDueBooks.addAll(m.getOverDueBooks());
		}
		return overDueBooks;
	}

	@Override
	public List<String> allBookIds() {
		
		List<String> retval = new ArrayList<>();
		List<Book> retBook = new ArrayList<>();

		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	public Book getBook(String ISBN) throws LibrarySystemException {
		for(Book b : da.readBooksMap().values()){
			if(b.getIsbn().equals(ISBN)){
				return b;
			}
		}
		throw new LibrarySystemException("ISBN searched for does not exist");
	}
	public BookCopy[] getBookCopies(String ISBN) throws LibrarySystemException{
		Book b = getBook(ISBN);
		return b.getCopies();
	}



	public void addCopy(String isbn) throws BookCopyException {
		BookCopyController bcc = new BookCopyController();
		bcc.addNewBookCopy(isbn, da);
	}

	@Override
	public HashMap<String, Book> getBooks() {

		return da.readBooksMap();
	}

	@Override
	public HashMap<String, LibraryMember> getMembers() {
		return da.readMemberMap();
	}

	public void openWindow(){

		if(currentAuth.name().equals("ADMIN")){
			if(!AdminWindow.INSTANCE.isInitialized())
				AdminWindow.INSTANCE.init();
			AdminWindow.INSTANCE.setVisible(true);
		}
	}

	@Override
	public void addBook(String isbn , String title , int maxBorrowDays, List<Author> authors) throws BookCopyException{
		Book book = new Book(isbn, title, maxBorrowDays, authors);
		BookController bookController = new BookController();
		bookController.addNewBook(book, da);
	}

	public void saveBook(Book book){
		BookController bookController = new BookController();
		bookController.addNewBook(book, da);
	}

	public Address addAddress(String street, String city , String state , String zip){
		return new Address(street, city, state, zip);
	}

	public void saveLibraryMember(LibraryMember member){
		MemberController mc = new MemberController();
    mc.addNewMember(member, da);
	}

	@Override
	public LibraryMember addLibraryMember(String memberNumber, String firstName, String lastName, String phoneNumber, Address address) {
		return new LibraryMember(memberNumber, firstName, lastName, phoneNumber, address);
	}

	public boolean checkMemberId(String member_id){
		if(!allMemberIds().contains(member_id.trim()))
			return false;
		return true;

	}
}
