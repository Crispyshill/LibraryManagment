package dataaccess;

import java.util.HashMap;

import business.Book;
import business.CheckOutEntry;
import business.CheckOutRecord;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member);
	public void saveNewBook(Book book);
	public void saveNewCheckoutRecord(CheckOutRecord record);

	public HashMap<String, CheckOutRecord> readCheckOutRecordMap();

	public void saveNewCheckoutEntry(CheckOutEntry entry);

	public HashMap<CheckOutRecord, CheckOutEntry> readCheckOutEntryMap();

}
