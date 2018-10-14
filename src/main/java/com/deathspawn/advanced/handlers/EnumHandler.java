package com.deathspawn.advanced.handlers;

public class EnumHandler {

	public enum GUI_ID {

		GEM_ENCHANTER(0), ENERGY_GENERATOR(1);

		private int GUI_ID;

		private GUI_ID(int Gui_id) {
			this.GUI_ID = Gui_id;
		}

		public int getGUI_ID() {
			return GUI_ID;
		}

	}

	public enum IO {
		INPUT(0), OUTPUT(1);

		private int ID;

		private IO(int id) {
			this.ID = id;
		}

		public int getID() {
			return ID;
		}
	}

}
