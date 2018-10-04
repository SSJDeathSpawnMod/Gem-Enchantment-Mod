package com.deathspawn.advanced.client.gui;

public enum GUI_ID {
	
	GEM_ENCHANTER(0),
	ENERGY_GENERATOR(1);
	
	private int GUI_ID;
	
	private GUI_ID(int Gui_id) {
		this.GUI_ID = Gui_id;
	}

	public int getGUI_ID() {
		return GUI_ID;
	}
	
}
