package client;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Start {
	static String port = "4907";
	
	public static void main(String[] args) {
		String ip = JOptionPane.showInputDialog("Please enter server IP!");
		new Start().initialize(ip, Integer.parseInt(port));
	}
	
	public void initialize(String ip, int port) {
		try {
			Socket sc = new Socket(ip, port);
			new ChatClient(sc);
			System.out.println("Connecting to server");
			Authentication frame1 = new Authentication(sc);
			frame1.setSize(300, 100);
			frame1.setLocation(500, 300);
			frame1.setVisible(true);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
