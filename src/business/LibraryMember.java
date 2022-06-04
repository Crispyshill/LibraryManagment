package business;
/*
 * LibraryMember class 
 */
import business.Controllers.BookController;
import business.exceptions.BookCopyException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckOutRecord record;

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		record = new CheckOutRecord(this);
	}
	
	public String getMemberId() {
		return memberId;
	}

	/***
	 *  Checking out book
	 * @return
	 */

	public void addCheckoutRecord(BookCopy copy){

		record.addCheckOutEntry(copy);
	}

	public CheckOutRecord getRecord() {
		return record;
	}

	public List<CheckOutEntry> getOverDueBooks(String bookId){
		List<CheckOutEntry> overDueBooks = new ArrayList();
		for(CheckOutEntry e : getRecord().getEntries()){
			// Checks if a given book is due before the current date, and adds it to overDueBooks if so
			if(e.getDueDate().isBefore(LocalDate.now()) && e.getCopy().getBook().getIsbn().equals(bookId) ){
				overDueBooks.add(e);
			}
		}
		return overDueBooks;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress() + record.toString();
	}



	private static final long serialVersionUID = -2226197306790714013L;
}
