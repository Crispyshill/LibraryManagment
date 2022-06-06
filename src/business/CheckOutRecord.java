package business;

/*
 * This class records members checkout
 * Has OneToMany relation with CheckOutEntry
 */
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final public class CheckOutRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1558162789779372371L;
    private List<CheckOutEntry> entries;
    private LibraryMember member;

    public CheckOutRecord(LibraryMember member){
        this.member = member;
        entries = new ArrayList<>();
    }
    public void addCheckOutEntry(BookCopy bookCopy){
        entries.add(new CheckOutEntry(this , bookCopy));
    }
    public List<CheckOutEntry> getEntries() {
        return entries;
    }
    @Override
    public String toString() {
        return entries.toString();
    }

    public LibraryMember getMember() {
        return member;
    }
}
