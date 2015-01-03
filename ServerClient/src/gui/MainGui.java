package gui;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class MainGui {

	private JFrame frame;
	private JTextField txtIP;
	private JTextField txtPoort;
	private static JTextArea txtArLog; 
	private JButton btnVerbinden;

	/**
	 * Create the application.
	 */
	public MainGui() {
		initialize();
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public JTextField getTxtIP(){
		return txtIP;
	}
	
	public JTextField getTxtPoort(){
		return txtPoort;
	}
	
	public static JTextArea getTxtAreaLog(){
		return txtArLog;
	}
	
	public JButton getBtnVerbinden(){
		return btnVerbinden;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 330, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setBounds(10, 36, 103, 20);
		frame.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblIpAdres = new JLabel("IP adres");
		lblIpAdres.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblIpAdres);
		
		JLabel lblPoort = new JLabel("Poort");
		lblPoort.setBounds(10, 67, 46, 14);
		frame.getContentPane().add(lblPoort);
		
		txtPoort = new JTextField();
		txtPoort.setText("1234");
		txtPoort.setBounds(10, 92, 103, 20);
		frame.getContentPane().add(txtPoort);
		txtPoort.setColumns(10);
		
		btnVerbinden = new JButton("Verbinden");
		btnVerbinden.setBounds(7, 134, 106, 23);
		frame.getContentPane().add(btnVerbinden);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(123, 23, 181, 357);
		frame.getContentPane().add(scrollPane);
		
		txtArLog = new JTextArea();
		scrollPane.setViewportView(txtArLog);
		
		JLabel lblLog = new JLabel("Log:");
		lblLog.setBounds(123, 8, 46, 14);
		frame.getContentPane().add(lblLog);
	}
}
