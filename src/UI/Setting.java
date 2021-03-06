package UI;

import java.awt.*;

public class Setting {

    public static final String APP_NAME = "LMS";
    public static final int APP_WIDTH = 900;
    public static final int APP_HEIGHT = 600;
    public static final int DIVIDER = 200;
    public static  final Font DEFUALT_FONT = new Font("Tahoma", 1, 13);
    public static final Color DARK_BLUE = Color.BLACK.darker();
    public static final Color LINK_AVAILABLE = DARK_BLUE;
    public static final Color LINK_NOT_AVAILABLE = Color.gray;

    public static final Dimension BTN_DIMENSION = new Dimension(150, 30);
    public static final Dimension PANEL_DIMENSION =new Dimension(Setting.APP_WIDTH/2 + 100 , Setting.APP_HEIGHT/2);

    public static final String[] ALL_MENU = {
            "Books",
            "Member",
            "Checkout",
            "Logout",
    };

    public static final String[] LIBRARIAN_MENU = {
            "Books",
            "Member",
            "Checkout",
            "Logout",
    };

    public static final String[] ADMIN_MENU = {
            "Books",
            "Member",
            "Logout",
    };

    private static LibWindow[] allWindows = {
            AdminLibWindow.INSTANCE,
            LoginWindow.INSTANCE,
            LibrarianWindow.INSTANCE,
            AdminWindow.INSTANCE
    };

    public static void hideAllWindows() {
        AdminLibWindow.INSTANCE.setVisible(false);
        LoginWindow.INSTANCE.setVisible(false);
        AdminWindow.INSTANCE.setVisible(false);
        LibrarianWindow.INSTANCE.setVisible(false);
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }
}
