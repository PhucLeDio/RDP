package server;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InitConnection {
	ServerSocket socket = null;
	DataInputStream password = null;
	DataOutputStream verify = null;
	String width = "";
	String height = "";
	
	InitConnection(int port, String value1) {
		Robot robot = null;
		Rectangle rectangle = null;
		try {
			System.out.println("Waiting for client get connected!");
			socket = new ServerSocket(port);
			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			String width = "" + dim.getWidth();
			String height = "" + dim.getHeight();
			rectangle = new Rectangle(dim);
			robot = new Robot(gDev);
			
			drawGUI();
			
			while (true) {
				Socket sc = socket.accept();
				password = new DataInputStream(sc.getInputStream());
				verify = new DataOutputStream(sc.getOutputStream());
				
				String password = this.password.readUTF();
				
				if (password.equals(value1)) {
					verify.writeUTF("valid");
					verify.writeUTF(width);
					verify.writeUTF(height);
					new SendScreen(sc, robot, rectangle);
					new ReceiveEvents(sc, robot);
					new ChatServer(sc); // New chat server for this client
				} else {
					verify.writeUTF("Invalid!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawGUI() {
		
	}
}
