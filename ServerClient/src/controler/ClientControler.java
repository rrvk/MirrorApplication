package controler;

import gui.MainGui;

public class ClientControler {
	private MainGui gui;
	private int mode=0;
	
	public void setGui(MainGui gui) {
		this.gui=gui;		
	}

	public void setCoordinaten(Integer x, Integer y) {
		gui.getFrame().setLocation(x, y);		
	}

	public void setSize(Integer h, Integer w) {
		gui.getFrame().setSize(w, h);		
	}

	public void setState(int state) {
		gui.getFrame().setState(state);		
	}

	public void setMirrorTekst(String tekst) {
		gui.getTxtMirrorField().setText(tekst);		
	}

	public void changeMode(int mode) {
		this.mode=mode;
		if (mode==MainControler.modeExtend){
			gui.getFrame().setVisible(false);
		}
		else if(mode==MainControler.modeMiror){
			gui.getFrame().setVisible(true);
		}		
	}

	public void activateSceen() {
		if (mode==MainControler.modeExtend){
			gui.getFrame().setVisible(true);
		}		
	}

}
