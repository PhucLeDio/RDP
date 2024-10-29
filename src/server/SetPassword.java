package server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetPassword extends JFrame implements ActionListener {
	static String port = "4907";
	JButton submit;
	JPanel panel;
	JTextField text1, text2;
	String value1, value2;
	JLabel label, label1, label2;
	
	public SetPassword() {
		label1 = new JLabel();
		label1.setText("Set Password!");
		text1 = new JTextField(15);
		label = new JLabel();
		this.setLayout(new BorderLayout());
		submit = new JButton("submit");
		panel = new JPanel(new GridLayout(2, 1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label);
		panel.add(submit);
		add(panel, BorderLayout.CENTER);
		submit.addActionListener(this);
		setTitle("setting password for client");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		value1 = text1.getText();
		dispose();
		try {
			new InitConnection(Integer.parseInt(port), value1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String getValue1() {
		return value1;
	}
}
