package handelers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controler.MainControler;
import gui.MainGui;

public class MainGuiHandeler {
	MainGui gui;
	MainControler con;
	public MainGuiHandeler(MainControler con, MainGui gui){
		this.gui=gui;
		this.con=con;
	}
	
	public void addMirrorHandelers() {
		gui.getFrame().addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent arg0) {}
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				if (con.getServer()!=null){
					con.getServer().sendSize(gui.getFrame().getHeight(), gui.getFrame().getWidth());
				}				
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0) {
				if (con.getServer()!=null){
					con.getServer().sendCordinates(((Double) gui.getFrame().getLocation().getX()).intValue() , ((Double) gui.getFrame().getLocation().getY()).intValue());
				}				
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {}
		});
		
		gui.getFrame().addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if (con.getServer()!=null){
					con.getServer().sendState(e.getNewState());
				}
				
			}
		});
		
		gui.getFrame().addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (con.getServer()!=null){
					// alle clients sluiten
					try {
						con.getServer().closeClients();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(con.getClient()!=null){
					try {
						con.getClient().exitClient();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// aan de server melden dat client gaat sluiten
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
	
		gui.getTxtMirrorField().getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changeTekst();
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changeTekst();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				changeTekst();				
			}
			
			private void changeTekst(){
				if (con.getServer()!=null){
					con.getServer().sendMirrorField(gui.getTxtMirrorField().getText().toString());
				}
			}
		});
	}
	public void addButtonHandelers() {
		gui.getBtnVerbinden().addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// eerst controleren of er een geldig ip en poort is ingevuld
				try{
					Integer poort = Integer.parseInt(gui.getTxtPoort().getText());
					String ip = gui.getTxtIP().getText();
					// dit is nu alleen voor ip4 // TODO ook nog compitable maken voor ip6
					// ***.***.***.*** <- 15 tekens
					if (ip.length()<=15){
						// er zitten 3 punten in
						if (ip.replace(".", "").length()==ip.length()-3){
							if (con.searchOrCreate(ip,poort).equals("Server Online")){
								gui.getTxtAreaLog().append("Server is online\n");
								gui.getFrame().setTitle("Client Side");
								// server online
							}
							else{
								gui.getTxtAreaLog().append("Kan de server niet vinden\n");
								gui.getTxtAreaLog().append("Er is een nieuwe server aangemaakt\n");
								gui.getFrame().setTitle("Server Side");
								// server offline
							}
							//gui.getBtnVerbinden().setEnabled(false);
						}
						else{
							gui.getTxtAreaLog().append("Het IP adres is niet juist\n");	
						}
					}
					else{
						gui.getTxtAreaLog().append("Het IP adres is te lang\n");
					}
				}
				catch(NumberFormatException ex){
					gui.getTxtAreaLog().append("De poort is onjuist, probeer alleen cijfers in te voeren\n");
				}
				
			}
		});
		
	}
}
