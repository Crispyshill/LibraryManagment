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

    //operation related to members
    public void addNewMember(LibraryMember member, DataAccess dataAccessFacade) {
        dataAccessFacade.saveNewMember(member);
    }

}
