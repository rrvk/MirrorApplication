package main;

import controler.MainControler;
import gui.MainGui;
import handelers.MainGuiHandeler;

public class Main {
	public static void main(String [] args){
		// gui aanmaken en laten zien
		MainGui gui = new MainGui();
		gui.getFrame().setVisible(true);
		
		// maak een nieuwe handeler voor de main controler
		MainControler con = new MainControler();
		// zet de gui naar de controle zodat deze ook daar dingen op kan doen.
		con.setGui(gui);
		
		// maak een handeler die de gui acties opvangt.
		MainGuiHandeler handeler = new MainGuiHandeler(con, gui);
		handeler.addButtonHandelers();
		handeler.addMovementHandelers();
	}
}
