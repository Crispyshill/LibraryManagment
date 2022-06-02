package UI;

import javax.swing.JFrame;

import java.awt.*;

public class Main {

	public static void main(String[] args) {
	      EventQueue.invokeLater(() -> 
	         {
				 AdminWindow.INSTANCE.setTitle("Library Management System");
				 AdminWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 AdminWindow.INSTANCE.init();
	             centerFrameOnDesktop(AdminWindow.INSTANCE);
				 AdminWindow.INSTANCE.setVisible(true);
	         });
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
