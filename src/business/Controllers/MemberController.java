package business.Controllers;
/*
 * This class used to control member
 * Uses the LibraryMember class and the DataAccessFacade
 * Will add members into the file system.
 */
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.List;

public class MemberController {

    public void addNewMember(LibraryMember member, DataAccess dataAccess) {
        dataAccess.saveNewMember(member);
    }

    public LibraryMember getLibraryMember(String memberId, DataAccess dataAccess){
        return dataAccess.readMemberMap().get(memberId);
    }
}
