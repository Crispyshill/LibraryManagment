package UI;

import UI.sharedUI.Logout;
import UI.sharedUI.book.AddBookCopyPanel;
import UI.sharedUI.book.BookUI;
import UI.sharedUI.book.SearchBookPanel;
import UI.sharedUI.checkOut.CheckOutUI;
import UI.sharedUI.checkOut.SearchCheckOut;
import UI.sharedUI.checkOut.ViewCheckOut;
import UI.sharedUI.member.MemberUI;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class LibrarianWindow extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;

    public static final LibrarianWindow INSTANCE = new LibrarianWindow();

    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    JList<MenuItem> linkList;
    JPanel cards;

    List<MenuItem> itemList = new ArrayList<>();
    private  JPanel adminDashboardPanel, memberPanel, checkOutPanel;

    public JScrollPane addBookPane, addBookCopyPane;
    public JTable memberListJTable,bookListJTable;

    //Singleton class
    private LibrarianWindow() {
        setSize(Setting.APP_WIDTH, Setting.APP_HEIGHT);
        UIController.INSTANCE.librarianWindow = this;

        memberListJTable = MemberUI.INSTANCE.getMemberList();
        bookListJTable = BookUI.INSTANCE.getBookList();
        addBookPane = BookUI.INSTANCE.getAddBookPanel();
        addBookCopyPane = AddBookCopyPanel.INSTANCE.getAddBookCopyPanel();
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

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }

    public void createMainPanels() {
        setLibDashboardPanel();

        setMemberPanel();

        setCheckPanel();

        setCards();
    }

    public void setCards(){
        JPanel logoutPanel = Logout.INSTANCE.getLoginPanel();
        cards = new JPanel(new CardLayout());

        cards.add(adminDashboardPanel, itemList.get(0).getItemName());
        cards.add(memberPanel, itemList.get(1).getItemName());
        cards.add(checkOutPanel, itemList.get(2).getItemName());
        cards.add(logoutPanel, itemList.get(3).getItemName());
    }

    public void setLibDashboardPanel() {
        adminDashboardPanel = new JPanel(new BorderLayout());
        JLabel aTitle = getAdminPaneTitle();
        aTitle.setBorder(new EmptyBorder(20,245,20,100));
        adminDashboardPanel.add(aTitle,BorderLayout.NORTH);

        JTabbedPane tp = new JTabbedPane();
        tp.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tp.add("View Books",new JScrollPane(bookListJTable));
        tp.add("Search Overdue Book", SearchBookPanel.INSTANCE.getSearchBookPanel());

        tp.setFont(Setting.DEFUALT_FONT);
        tp.setForeground(Setting.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        adminDashboardPanel.add(tp , BorderLayout.CENTER);
    }

    public JLabel getAdminPaneTitle(){
        JLabel jLTitle = new JLabel();
        jLTitle.setText("Librarian Dashboard");
        jLTitle.setFont(new Font("SansSerif", 1, 18));
        jLTitle.setForeground(new Color(170, 98, 0));

        return jLTitle;
    }

    public void setMemberPanel() {
        memberPanel = new JPanel(new BorderLayout());
        JLabel title = getAdminPaneTitle();
        title.setBorder(new EmptyBorder(20,240,20,100));
        memberPanel.add(title,BorderLayout.NORTH);

        JTabbedPane tp = new JTabbedPane();
        JPanel addMemberPanel = MemberUI.INSTANCE.getAddMemberPanel();
        tp.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tp.add("View Members",new JScrollPane(memberListJTable));

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

        tp.add("View Checkout Record", ViewCheckOut.INSTANCE.getSearchCheckoutPanel());
        tp.add("Checkout Book", checkPanel);
        tp.add("Search Checkout", SearchCheckOut.INSTANCE.getSearchCheckoutPanel());

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
