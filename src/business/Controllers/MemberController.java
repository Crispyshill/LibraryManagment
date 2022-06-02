package business.Controllers;
/*
 * This class used to control member
 * Uses the LibraryMember class and the DataAccessFacade
 * Will add members into the file system.
 */
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import java.util.List;

public class MemberController {

    //operation related to members

    public void addNewMember(LibraryMember member, DataAccessFacade dataAccessFacade) {
        dataAccessFacade.saveNewMember(member);
    }

}
