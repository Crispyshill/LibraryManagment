package UI.sharedUI.member;

import UI.Setting;
import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import business.exceptions.LibraryMemberException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class MemberUI extends JPanel{

    private final String[] memeberAttributes ;
    private final JTextField[] memberFields ;
    private final ControllerInterface ci = new SystemController();
    public static MemberUI INSTANCE = new MemberUI();

    private JTable myTable;
    private JPanel addMemberPanel;
    private JPanel addCopyPanel;

    private MemberUI() {
        memeberAttributes = new String[] {"Member ID", "First Name", "Last Name",  "Street", "City", "State", "Zip", "Phone Number"};
        memberFields = new JTextField[memeberAttributes.length];
        addMemberForm();
        myTable = loadDataToTable();
        myTable.setShowGrid(true);
    }

    public JTextField[] getMemberFields() {
        return memberFields;
    }
    public JPanel getAddMemberPanel() {
        return this;
    }

    private void addMemberForm() {
        JPanel memberFormPanel = createMemberForm();

        // add add button
        JButton addBMemberBtn = new JButton("Add Member");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBMemberBtn.setPreferredSize(Setting.BTN_DIMENSION);
        addBMemberBtn.addActionListener(new addMemberListener());
        btnPanel.add(addBMemberBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(Setting.PANEL_DIMENSION);

        // combine
        container.add(new JScrollPane());
        container.add(memberFormPanel, BorderLayout.CENTER);
        container.add(btnPanel, BorderLayout.SOUTH);

        this.add(container);
    }

    public JTable getMemberList(){
        return myTable;
    }

    private JTable loadDataToTable(){

        String[] column ={"MEMBER NO.","FULL NAME","PHONE NO.", "Address"};
        HashMap<String , LibraryMember> memberHashMap = ci.getMembers();
        String[][] memberData = new String[memberHashMap.size()][column.length];
        List<String> memberID = ci.allMemberIds();

        for(int i = 0 ; i < memberHashMap.size(); i++){

            LibraryMember member = memberHashMap.get(memberID.get(i));
            memberData[i][0] = member.getMemberId();
            memberData[i][1] = member.getFirstName() + " " + member.getLastName();
            memberData[i][2] = member.getTelephone();
            memberData[i][3] = member.getAddress() != null ? member.getAddress().toString() : "";
        }

        DefaultTableModel model = new DefaultTableModel(memberData, column);

        return new JTable(model);

    }
    private JPanel createMemberForm() {

        JPanel memberFormPanel = new JPanel(new GridLayout(memberFields.length, 0));
        for (int i = 0; i < memberFields.length; i++) {
            memberFormPanel.add(getElementWithLabelMember(memeberAttributes[i], i));
        }
        return memberFormPanel;
    }

    private JPanel getElementWithLabelMember(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.CENTER);

        memberFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(memberFields[jtextFieldIndex], BorderLayout.CENTER);


        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.WEST);
        nameForm.add(formPanel, BorderLayout.EAST);

        return nameForm;
    }
    private class addMemberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                if(ci.checkMemberId(memberFields[0].getText().trim()))
                    throw new LibraryMemberException("Member with  Id = " + memberFields[0].getText().trim() + " already exists");

                Address add = ci.addAddress(memberFields[4].getText(), memberFields[5].getText().trim(), memberFields[6].getText().trim(), memberFields[7].getText().trim());
                LibraryMember member = ci.addLibraryMember(memberFields[0].getText(), memberFields[1].getText(),
                        memberFields[2].getText(), memberFields[3].getText(), add);

                // Add New instance
                ci.saveLibraryMember(member);
                System.out.println("Member added successfully");

                addRowToJTable(member);
                clearFormFields();

            } catch (LibraryMemberException  ex) {
                System.out.println(ex);
            }
        }
    }

    private void addRowToJTable(LibraryMember member){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        model.insertRow(0, new  Object[]{member.getMemberId() , member.getFirstName() +" " +member.getLastName(),
                member.getTelephone(), member.getAddress().toString()});
    }

    public void clearFormFields(){
        for(JTextField field : memberFields){
            field.setText("");
        }

    }

}
