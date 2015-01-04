package handelers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import controler.MainControler;
import gui.MainGui;

public class MainGuiHandeler {
	MainGui gui;
	MainControler con;
	public MainGuiHandeler(MainControler con, MainGui gui){
		this.gui=gui;
		this.con=con;
	}
	public void addMovementHandelers() {
		gui.getFrame().addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0) {
				if (con.getServer()!=null){
					// TODO rekening houden met scherm groote
					con.getServer().sendCordinates(((Double) gui.getFrame().getLocation().getX()).intValue() , ((Double) gui.getFrame().getLocation().getY()).intValue());
				}				
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		// TODO Auto-generated method stub
		
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
