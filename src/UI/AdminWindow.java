package UI;

import UI.sharedUI.*;
import UI.sharedUI.book.*;
import UI.sharedUI.member.*;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AdminWindow extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;

    public static final AdminWindow INSTANCE = new AdminWindow();

    ControllerInterface ci = new SystemController();

    private boolean isInitialized = false;

    JList<ListItem> linkList;
    JPanel cards;

    List<ListItem> itemList = new ArrayList<>();

    private  JPanel adminDashboardPanel;

    public JScrollPane addBookPane, addBookCopyPane, searchBookPane;
    public JTable memberListJTable,bookListJTable;

    //Singleton class
    private AdminWindow() {
        setSize(Setting.APP_WIDTH, Setting.APP_HEIGHT);
        UIController.INSTANCE.adminWindow = this;

        memberListJTable = MemberUI.INSTANCE.getMemberList();
        bookListJTable = BookGui.INSTANCE.getBookList();
        addBookPane = BookGui.INSTANCE.getAddBookPanel();
        addBookCopyPane = AddBookCopyPanel.INSTANCE.getAddBookCopyPanel();
        searchBookPane = SearchBookPanel.INSTANCE.getSearchBookPanel();
    }

    public void constructSideBarMenu(){
        for(String item : Setting.ADMIN_MENU){
            itemList.add(new ListItem(item, true));
        }
    }

    public void init() {
        // Construct sideBarMenu ListItems
        constructSideBarMenu();

        // Create sidebar
        createLinkLabels();

        // create main panels
        createMainPanels();

        // link my sidebar
        linkList.addListSelectionListener(event -> {
            String value = linkList.getSelectedValue().getItemName();
            boolean allowed = linkList.getSelectedValue().highlight();
            CardLayout cl = (CardLayout) (cards.getLayout());

            if (!allowed) {
                value = itemList.get(0).getItemName();
                linkList.setSelectedIndex(0);
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

        // create admin panel
        setAdminDashboardPanel();

        // Assign crossponding panels to crsossponding Cards
        setCards();

    }

    public void setCards(){
        // book related panels
        //JPanel searchBookPanel = SearchBookPanel.INSTANCE.getSearchBookPanel();
        JPanel addBookPanel = BookGuii.INSTANCE.getAddBookPanel();

        // member related panels
        JPanel searchMemberPanel = SearchMemberPanel.INSTANCE.getsearchMemberPanel();
        JPanel addMemberPanel = MemberUI.INSTANCE.getAddMemberPanel();
        JPanel editOrDeletePanel = EditOrDeleteMember.INSTANCE.getAddMemberPanel();

        // logout panel
        JPanel logoutPanel = Logout.INSTANCE.getLoginPanel();

        // Dashboard panel
        cards = new JPanel(new CardLayout());

        cards.add(adminDashboardPanel, itemList.get(0).getItemName());
        cards.add(addMemberPanel, itemList.get(1).getItemName());
        cards.add(addBookPanel, itemList.get(2).getItemName());
        cards.add(logoutPanel, itemList.get(3).getItemName());
    }

    public void setAdminDashboardPanel() {
        adminDashboardPanel = new JPanel(new BorderLayout());
        JLabel jLTitle = new JLabel();
        jLTitle.setText("Administrator Dashboard");
        jLTitle.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLTitle.setForeground(new java.awt.Color(170, 98, 0));

        jLTitle.setBorder(new EmptyBorder(20,190,20,100));
        adminDashboardPanel.add(jLTitle,BorderLayout.NORTH);
        UIManager.put("TabbedPane.selected", Color.red);
        UIManager.put("TabbedPane.focus", new java.awt.Color(170, 98, 0));
        JTabbedPane tp = new JTabbedPane();

        tp.setFocusable(false);
        tp.setPreferredSize(new Dimension(Setting.APP_WIDTH - Setting.DIVIDER, Setting.APP_HEIGHT ));

        tp.add("View Books",new JScrollPane(bookListJTable));
        tp.add("Add Book", addBookPane);
        tp.add("Add Book Copy", addBookCopyPane);
        tp.add("Search Book", searchBookPane);

        tp.setFont(Setting.DEFUALT_FONT);
        tp.setForeground(Utility.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        adminDashboardPanel.add(tp , BorderLayout.CENTER);
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

        DefaultListModel<ListItem> model = new DefaultListModel<>();

        for(ListItem item : itemList){
            model.addElement(item);
        }

        linkList = new JList<ListItem>(model);
        linkList.setCellRenderer(new DefaultListCellRenderer() {

            @SuppressWarnings("rawtypes")
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {

                Component c = super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
                if (value instanceof ListItem) {
                    ListItem nextItem = (ListItem) value;
                    setText(nextItem.getItemName());
                    if (nextItem.highlight()) {
                        setForeground(Utility.LINK_AVAILABLE);
                    } else {
                        setForeground(Utility.LINK_NOT_AVAILABLE);
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
