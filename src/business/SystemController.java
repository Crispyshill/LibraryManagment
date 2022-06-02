package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	public BookCopy[] getBookCopies(String ISBN){
		for (Book b : da.readBooksMap().values()){
			if(b.getIsbn().equals(ISBN)){
				return b.getCopies();
			}
		}
		return new BookCopy[]{};
	}

	@Override
	public HashMap<String, Book> getBooks() {

		return da.readBooksMap();
	}

	@Override
	public HashMap<String, LibraryMember> getMembers() {

		return da.readMemberMap();
	}
}
