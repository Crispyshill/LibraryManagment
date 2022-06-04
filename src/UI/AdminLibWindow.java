package UI;

import UI.sharedUI.*;
import UI.sharedUI.book.*;
import UI.sharedUI.checkOut.CheckOutUI;
import UI.sharedUI.checkOut.SearchCheckOut;
import UI.sharedUI.member.*;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AdminLibWindow extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;

    public static final AdminLibWindow INSTANCE = new AdminLibWindow();

    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    JList<MenuItem> linkAdminList;
    JPanel adminCards;

    List<MenuItem> itemList = new ArrayList<>();

    private  JPanel adminLibPanel, memberMenuPanel, checkOutMenuPanel;

    public JScrollPane addBookAdminPane, addBookCopyAdminPane, searchBookAdminPane;
    public JTable memberListJTable,bookListJTable;

    //Singleton class
    private AdminLibWindow() {
        setSize(Setting.APP_WIDTH, Setting.APP_HEIGHT);
        UIController.INSTANCE.adminLibWindow = this;

        memberListJTable = MemberUI.INSTANCE.getMemberList();
        bookListJTable = BookUI.INSTANCE.getBookList();
        addBookAdminPane = BookUI.INSTANCE.getAddBookPanel();
        addBookCopyAdminPane = AddBookCopyPanel.INSTANCE.getAddBookCopyPanel();
        searchBookAdminPane = SearchBookPanel.INSTANCE.getSearchBookPanel();
    }

    public void constructSideBarMenu(){
        for(String item : Setting.ALL_MENU){
            itemList.add(new MenuItem(item, true));
        }
    }

    public void init() {
        // Construct sideBarMenu ListItems
        constructSideBarMenu();

        // Create sidebar -- render
        createLinkLabels();

        // create main panels
        createMainPanels();

        // link my sidebar -- menu ite with action
        linkAdminList.addListSelectionListener(event -> {
            String value = linkAdminList.getSelectedValue().getItemName();
            boolean allowed = linkAdminList.getSelectedValue().highlight();
            CardLayout cl = (CardLayout) (adminCards.getLayout());

            if (value == null) {
                value = itemList.get(0).getItemName();
                linkAdminList.setSelectedIndex(0);
                linkAdminList.getSelectedValue().setHighlight(true);
            }

            cl.show(adminCards, value);
        });

        linkAdminList.setBackground(new Color(170, 98, 0));
        linkAdminList.setVisibleRowCount(4);
        linkAdminList.setFixedCellHeight(40);
        linkAdminList.setSelectionForeground(Color.BLACK);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkAdminList, adminCards);
        splitPane.setDividerLocation(Setting.DIVIDER);

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(splitPane);

        add(splitPane);
        isInitialized = true;
        centerFrameOnDesktop(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void reloadFrame(){
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }

    public void createMainPanels() {
        setAdminPanel();

        setAdminMemberPanel();

        setAdminCheckPanel();

        setAdminCards();
    }

    public void setAdminCards(){
        JPanel logoutPanel = Logout.INSTANCE.getLoginPanel();
        adminCards = new JPanel(new CardLayout());

        adminCards.add(adminLibPanel, itemList.get(0).getItemName());
        adminCards.add(memberMenuPanel, itemList.get(1).getItemName());
        adminCards.add(checkOutMenuPanel, itemList.get(2).getItemName());
        adminCards.add(logoutPanel, itemList.get(3).getItemName());
    }

    public void setAdminPanel() {
        adminLibPanel = new JPanel(new BorderLayout());
        JLabel aTitle = getAdminPaneTitle();
        aTitle.setBorder(new EmptyBorder(20,150,20,100));
        adminLibPanel.add(aTitle,BorderLayout.NORTH);

        JTabbedPane tpAdmin = new JTabbedPane();
        tpAdmin.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tpAdmin.add("View Books",new JScrollPane(bookListJTable));
        tpAdmin.add("Add Book", BookUI.INSTANCE.getAddBookPanel());
        tpAdmin.add("Add Book Copy", AddBookCopyPanel.INSTANCE.getAddBookCopyPanel());
        tpAdmin.add("Search Book", SearchBookPanel.INSTANCE.getSearchBookPanel());

        tpAdmin.setFont(Setting.DEFUALT_FONT);
        tpAdmin.setForeground(Setting.LINK_AVAILABLE);
        tpAdmin.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        adminLibPanel.add(tpAdmin , BorderLayout.CENTER);
        adminLibPanel.repaint();
    }

    public JLabel getAdminPaneTitle(){
        JLabel jLTitle = new JLabel();
        jLTitle.setText("Administrator and Librarian Window");
        jLTitle.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLTitle.setForeground(new java.awt.Color(180, 98, 0));

        return jLTitle;
    }

    public void setAdminMemberPanel() {
        memberMenuPanel = new JPanel(new BorderLayout());
        JLabel title = getAdminPaneTitle();
        title.setBorder(new EmptyBorder(20,170,20,100));
        memberMenuPanel.add(title,BorderLayout.NORTH);

        JTabbedPane tpMem = new JTabbedPane();
        JPanel addMemberPanel = MemberUI.INSTANCE.getAddMemberPanel();
        tpMem.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tpMem.add("View Members",new JScrollPane(memberListJTable));
        tpMem.add("Add Member", addMemberPanel);

        tpMem.setFont(Setting.DEFUALT_FONT);
        tpMem.setForeground(Setting.LINK_AVAILABLE);
        tpMem.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        memberMenuPanel.add(tpMem , BorderLayout.CENTER);
    }

    public void setAdminCheckPanel() {
        checkOutMenuPanel = new JPanel(new BorderLayout());
        JLabel title = getAdminPaneTitle();
        title.setBorder(new EmptyBorder(20,170,20,100));

        JScrollPane checkPanel = CheckOutUI.INSTANCE.getCheckOutPanel();
        JTabbedPane tpCehcl = new JTabbedPane();
        tpCehcl.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        //tp.add("View Checkout Record", checkPanel);
        tpCehcl.add("Checkout Book", checkPanel);
        tpCehcl.add("Search Checkout", SearchCheckOut.INSTANCE.getSearchCheckoutPanel());

        tpCehcl.setFont(Setting.DEFUALT_FONT);
        tpCehcl.setForeground(Setting.LINK_AVAILABLE);
        tpCehcl.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        checkOutMenuPanel.add(title,BorderLayout.NORTH);
        checkOutMenuPanel.add(tpCehcl , BorderLayout.CENTER);

    }
    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    @SuppressWarnings("serial")
    public void createLinkLabels() {

        DefaultListModel<MenuItem> model = new DefaultListModel<>();

        for(MenuItem item : itemList){
            model.addElement(item);
        }

        linkAdminList = new JList<MenuItem>(model);
        linkAdminList.setCellRenderer(new DefaultListCellRenderer() {

            @SuppressWarnings("rawtypes")
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {

                Component c = super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
                if (value instanceof MenuItem) {
                    MenuItem nextItem = (MenuItem) value;
                    setText(nextItem.getItemName());
                    if (nextItem.highlight()) {
                        setForeground(Setting.LINK_AVAILABLE);
                    } else {
                        setForeground(Setting.LINK_NOT_AVAILABLE);
                    }
                    if (isSelected) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(236,243,245));
                    }
                    setFont(Setting.DEFUALT_FONT);
                } else {
                    setText("illegal item");
                }
                return c;
            }

        });
    }
}
