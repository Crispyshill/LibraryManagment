package UI.sharedUI.member;

import UI.Setting;
import UI.Utility;
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

public class EditOrDeleteMember extends JPanel{

    private final String[] memeberAttributes ;
    private final JTextField[] memberFields ;

    private final ControllerInterface ci = new SystemController();
    public static EditOrDeleteMember INSTANCE = new EditOrDeleteMember();


    private EditOrDeleteMember() {
        memeberAttributes = new String[] {"Member ID", "First Name", "Last Name", "Phone Number", "Street", "City", "State", "Zip"};
        memberFields = new JTextField[memeberAttributes.length];
        addMemberForm();
    }

    public JTextField[] getMemberFields() {
        return memberFields;
    }
    public JPanel getAddMemberPanel() {
        return this;
    }

    private void addMemberForm() {

        JLabel panelTitle = new JLabel("Edit or Delete Member");
        panelTitle.setFont(Setting.DEFUALT_FONT);
        panelTitle.setForeground(Utility.DARK_BLUE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        titlePanel.add(panelTitle, BorderLayout.CENTER);
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);

        // Middle form
        JPanel memberFormPanel = createMemberForm();

        JButton editMemberBtn = new JButton("Edit");
        editMemberBtn.addActionListener(new EditMember());

        JButton searchMemberBtn = new JButton("Search");
        searchMemberBtn.addActionListener(new SearchMember());

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setForeground(Color.red);
        deleteBtn.addActionListener(new DeleteMember());

        JPanel btnSPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSPanel.add(searchMemberBtn);
        btnSPanel.add(editMemberBtn);
        btnSPanel.add(deleteBtn);


        // top panel
        JPanel combine = new JPanel(new BorderLayout());
        combine.add(titlePanel, BorderLayout.NORTH);
        combine.add(memberFormPanel, BorderLayout.CENTER);
        combine.add(btnSPanel, BorderLayout.SOUTH);

        this.add(combine);
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


    public void clearFormFields(){
        for(JTextField field : memberFields){
            field.setText("");
        }

    }

    private class EditMember implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


        }
    }

    private class DeleteMember implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }

        private void removeRowFromTable(String id) {

        }
    }

    private class SearchMember implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {



        }
    }
}
