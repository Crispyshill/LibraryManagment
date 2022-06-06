package UI.sharedUI.checkOut;
import UI.ruleSet.RuleException;
import UI.ruleSet.RuleSet;
import UI.ruleSet.RuleSetFactory;
import business.*;
import business.exceptions.BookCopyException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ViewCheckOut extends JPanel{

    public static ViewCheckOut INSTANCE = new ViewCheckOut();
    private JTable myTable;
    private JPanel printMemberCheckOutPanel;
    private SystemController ci = new SystemController();

    private ViewCheckOut() {
        printMemberCheckOutPanel = new JPanel(new BorderLayout());
        myTable = loadTableData();
        myTable.setDefaultEditor(Object.class , null);
        printMemberCheckOutPanel.add(new JScrollPane(myTable));
    }

    private JTable loadTableData() {
        String column[]={"ISBN", "TITLE", "COPY N0.", "MEMBER ID"};

        DefaultTableModel model = new DefaultTableModel(null, column);

        model = listCheckoutRecord(model);

        return new JTable(model);
    }
    public void refreshCheckOutList() {
        String column[]={"ISBN", "TITLE", "COPY N0.", "MEMBER ID"};
        DefaultTableModel model = new DefaultTableModel(null, column);
        model = listCheckoutRecord(model);
        myTable.setModel(model);

        DefaultTableModel newModel = (DefaultTableModel) myTable.getModel();
        newModel.fireTableDataChanged();
    }
    public  JScrollPane getSearchCheckoutPanel(){ return new JScrollPane(printMemberCheckOutPanel); }

    DefaultTableModel listCheckoutRecord(DefaultTableModel model){

        HashMap<String , LibraryMember> libraryMemberHashMap = ci.getMembers();
        if( libraryMemberHashMap !=null){

            for(String key : libraryMemberHashMap.keySet()){
                LibraryMember member = libraryMemberHashMap.get(key);
                for(CheckOutEntry entry : member.getRecord().getEntries()){
                    model.insertRow(0, new  Object[]{
                            entry.getCopy().getBook().getIsbn(),
                            entry.getCopy().getBook().getTitle(),
                            entry.getCopy().getCopyNum(),
                            member.getMemberId()
                    });
                }
            }
        }

        return model;
    }
}
