package UI;

import UI.sharedUI.*;
import UI.sharedUI.book.*;
import UI.sharedUI.checkOut.CheckOutUI;
import UI.sharedUI.member.*;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AdminWindow extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;

    public static final AdminWindow INSTANCE = new AdminWindow();

    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    JList<MenuItem> linkList;
    JPanel cards;

    List<MenuItem> itemList = new ArrayList<>();

    private  JPanel adminDashboardPanel, memberPanel, checkOutPanel;

    public JScrollPane addBookPane, addBookCopyPane, searchBookPane;
    public JTable memberListJTable,bookListJTable;

    //Singleton class
    private AdminWindow() {
        setSize(Setting.APP_WIDTH, Setting.APP_HEIGHT);
        UIController.INSTANCE.adminWindow = this;

        memberListJTable = MemberUI.INSTANCE.getMemberList();
        bookListJTable = BookUI.INSTANCE.getBookList();
        addBookPane = BookUI.INSTANCE.getAddBookPanel();
        addBookCopyPane = BookUI.INSTANCE.getAddBookCopyPanel();
        searchBookPane = SearchBookPanel.INSTANCE.getSearchBookPanel();
    }

    public void constructSideBarMenu(){
        for(String item : Setting.ADMIN_MENU){
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
        linkList.addListSelectionListener(event -> {
            String value = linkList.getSelectedValue().getItemName();
            boolean allowed = linkList.getSelectedValue().highlight();
            CardLayout cl = (CardLayout) (cards.getLayout());

            if (value == null) {
                value = itemList.get(0).getItemName();
                linkList.setSelectedIndex(0);
                linkList.getSelectedValue().setHighlight(true);
            }

            cl.show(cards, value);
        });

        linkList.setBackground(new Color(170, 98, 0));
        linkList.setVisibleRowCount(4);
        linkList.setFixedCellHeight(40);
        linkList.setSelectionForeground(Color.BLACK);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
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
        setAdminDashboardPanel();

        setMemberPanel();

        setCheckPanel();

        setCards();
    }

    public void setCards(){
        JPanel logoutPanel = Logout.INSTANCE.getLoginPanel();
//        JPanel checkPanel = CheckOutUI.INSTANCE.getCheckOutPanel();
        cards = new JPanel(new CardLayout());

        cards.add(adminDashboardPanel, itemList.get(0).getItemName());
        cards.add(memberPanel, itemList.get(1).getItemName());
        cards.add(checkOutPanel, itemList.get(2).getItemName());
        cards.add(logoutPanel, itemList.get(3).getItemName());
    }

    public void setAdminDashboardPanel() {
        adminDashboardPanel = new JPanel(new BorderLayout());
        JLabel aTitle = getAdminPaneTitle();
        aTitle.setBorder(new EmptyBorder(20,230,20,100));
        adminDashboardPanel.add(aTitle,BorderLayout.NORTH);

        JTabbedPane tp = new JTabbedPane();
        tp.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tp.add("View Books",new JScrollPane(bookListJTable));
        tp.add("Add Book", addBookPane);
        tp.add("Add Book Copy", addBookCopyPane);
        tp.add("Search Book", searchBookPane);

        tp.setFont(Setting.DEFUALT_FONT);
        tp.setForeground(Setting.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
//                tp.getComponentAt(0).revalidate();
//                tp.getComponentAt(0).repaint();
//                adminDashboardPanel.revalidate();
//                adminDashboardPanel.repaint();
            }
        };

        tp.addChangeListener(changeListener);

        adminDashboardPanel.add(tp , BorderLayout.CENTER);
    }

    public JTabbedPane refreshTabPane(JTabbedPane tp){

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("View Books",new JScrollPane(bookListJTable));
        tabs.add("Add Book", addBookPane);
        tabs.add("Add Book Copy", addBookCopyPane);
        tabs.add("Search Book", searchBookPane);

        return tabs;
    }

    public JLabel getAdminPaneTitle(){
        JLabel jLTitle = new JLabel();
        jLTitle.setText("Administrator Dashboard");
        jLTitle.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLTitle.setForeground(new java.awt.Color(170, 98, 0));
        //jLTitle.setBorder(new EmptyBorder(20,190,20,100));

        return jLTitle;
    }

    public void setMemberPanel() {
        memberPanel = new JPanel(new BorderLayout());
        JLabel title = getAdminPaneTitle();
        title.setBorder(new EmptyBorder(20,230,20,100));
        memberPanel.add(title,BorderLayout.NORTH);

        JTabbedPane tp = new JTabbedPane();
        JPanel addMemberPanel = MemberUI.INSTANCE.getAddMemberPanel();
        tp.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tp.add("View Members",new JScrollPane(memberListJTable));
        tp.add("Add Member", addMemberPanel);

        tp.setFont(Setting.DEFUALT_FONT);
        tp.setForeground(Setting.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        memberPanel.add(tp , BorderLayout.CENTER);
    }

    public void setCheckPanel() {
        checkOutPanel = new JPanel(new BorderLayout());
        JLabel title = getAdminPaneTitle();
        title.setBorder(new EmptyBorder(20,230,20,100));

        JScrollPane checkPanel = CheckOutUI.INSTANCE.getCheckOutPanel();
        JTabbedPane tp = new JTabbedPane();
        tp.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tp.add("Checkout Book", checkPanel);

        tp.setFont(Setting.DEFUALT_FONT);
        tp.setForeground(Setting.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        checkOutPanel.add(title,BorderLayout.NORTH);
        checkOutPanel.add(tp , BorderLayout.CENTER);

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

        linkList = new JList<MenuItem>(model);
        linkList.setCellRenderer(new DefaultListCellRenderer() {

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
