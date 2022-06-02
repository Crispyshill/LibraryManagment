package UI.sharedUI;

import UI.*;

import java.awt.*;

public class UtilGui {

    private static LibWindow[] allWindows = {
            AdminWindow.INSTANCE,
            LoginWindow.INSTANCE
            //LibrarianDashboard.INSTANCE,
    };

    public static void hideAllWindows() {
        for(LibWindow frame: allWindows) {
            frame.setVisible(false);
        }
    }

    public static final Dimension BTN_DIMENSION = new Dimension(150, 30);
    public static final Dimension PANEL_DIMENSION =new Dimension(Setting.APP_WIDTH/2 + 100 , Setting.APP_HEIGHT/2);

}
