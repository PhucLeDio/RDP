package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Authentication extends JFrame implements ActionListener {
	private Socket cSocket = null;
	DataOutputStream passchk = null;
	DataInputStream verification = null;
	String verify = "";
	JButton submit;
	JPanel panel;
	JLabel label, label1;
	String width = "", height = "";
	JTextField text1;
	
	Authentication(Socket cSocket) {
		label1 = new JLabel();
		label1.setText("Password");
		text1 = new JTextField(15);
		this.cSocket = cSocket;
		label = new JLabel();
		label.setText("");
		this.setLayout(new BorderLayout());
		submit = new JButton("submit");
		panel = new JPanel(new GridLayout(2, 1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label);
		panel.add(submit);
		add(panel, BorderLayout.CENTER);
		submit.addActionListener(this);
		setTitle("Login form");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String value1 = text1.getText();
		try {
			passchk = new DataOutputStream(cSocket.getOutputStream());
			verification = new DataInputStream(cSocket.getInputStream());
			passchk.writeUTF(value1);
			verify = verification.readUTF();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (verify.equals("valid")) {
			try {
				width = verification.readUTF();
				height = verification.readUTF();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			CreateFrame abc = new CreateFrame(cSocket, width, height);
			dispose();
		} else {
			System.out.println("Please enter valid passworld!");
			JOptionPane.showMessageDialog(this, "Password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}
	
}
