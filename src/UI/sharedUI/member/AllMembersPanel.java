package UI.sharedUI.member;

import UI.Setting;
import UI.Utility;
import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class AllMembersPanel extends JPanel{

    private final String[] memberAttributes ;
    private final JTextField[] memberFields ;
    private final ControllerInterface ci = new SystemController();
    public static AllMembersPanel INSTANCE = new AllMembersPanel();

    private JTable myTable;
    private JPanel allMemberPanel;


    private AllMembersPanel() {
        memberAttributes = new String[] {"Member Number", "First Name", "Last Name", "Phone Number", "Street", "City", "State", "Zip"};
        memberFields = new JTextField[memberAttributes.length];
        addMemberForm();
        myTable = loadDataToTable();
    }

    public JTextField[] getMemberFields() {
        return memberFields;
    }
    public JPanel getAllMemberPanel() {
        return allMemberPanel;
    }

    private void addMemberForm() {

        allMemberPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add Member");
        panelTitle.setFont(Setting.DEFUALT_FONT);
        panelTitle.setForeground(Utility.DARK_BLUE);
        allMemberPanel.add(panelTitle , BorderLayout.NORTH);

        JPanel memberFormPanel = createMemberForm();
        allMemberPanel.add(memberFormPanel , BorderLayout.CENTER);

        // add add button
        JButton addBMemberBtn = new JButton("Add Member");
        addBMemberBtn.addActionListener(new addMemberListener());
        JPanel addBMemberBtnPanel = new JPanel(new BorderLayout());
        addBMemberBtnPanel.add(addBMemberBtn, BorderLayout.CENTER);


        // add to book Panel at the bottom
        memberFormPanel.add(addBMemberBtnPanel, BorderLayout.SOUTH);
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
            memberFormPanel.add(getElementWithLabelMember(memberAttributes[i], i));
        }
        return memberFormPanel;
    }

    private JPanel getElementWithLabelMember(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.NORTH);

        memberFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(memberFields[jtextFieldIndex], BorderLayout.NORTH);


        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.NORTH);
        nameForm.add(formPanel, BorderLayout.CENTER);

        return nameForm;
    }
    private class addMemberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public void clearFormFields(){
        for(JTextField field : memberFields){
            field.setText("");
        }

    }

}
