package util;

import java.util.ArrayList;

import org.newdawn.slick.Input;

/** This class stores the keybindings for the game. */
public class KeyBindings {
	/** QWERTY keyboard bindings. */
	public static KeyBindings QWERTY;
	static {
		QWERTY = new KeyBindings();
		QWERTY.left = Input.KEY_LEFT;
		QWERTY.right = Input.KEY_RIGHT;
		QWERTY.up = Input.KEY_UP;
		QWERTY.down = Input.KEY_DOWN;
		QWERTY.attack = Input.KEY_R;
		QWERTY.retreat = Input.KEY_Y;
		QWERTY.ability0 = Input.KEY_Q;
		QWERTY.ability1 = Input.KEY_W;
		QWERTY.ability2 = Input.KEY_E;
		QWERTY.passive0 = Input.KEY_1;
		QWERTY.passive1 = Input.KEY_2;
		QWERTY.passive2 = Input.KEY_3;
		QWERTY.moonwalk = Input.KEY_LSHIFT;
		QWERTY.space = Input.KEY_SPACE;
		QWERTY.inventory = Input.KEY_I;
		QWERTY.menu = Input.KEY_ESCAPE;
		QWERTY.dialog = Input.KEY_SPACE;
		QWERTY.enter = Input.KEY_ENTER;
		QWERTY.period = Input.KEY_PERIOD;
		QWERTY.numPeriod = Input.KEY_DECIMAL;
		QWERTY.delete = Input.KEY_BACK;
		QWERTY.monsterSpawn = Input.KEY_F6;
		
		QWERTY.keys = new ArrayList<Integer>();
		QWERTY.keys.add(Input.KEY_0);
		QWERTY.keys.add(Input.KEY_1);
		QWERTY.keys.add(Input.KEY_2);
		QWERTY.keys.add(Input.KEY_3);
		QWERTY.keys.add(Input.KEY_4);
		QWERTY.keys.add(Input.KEY_5);
		QWERTY.keys.add(Input.KEY_6);
		QWERTY.keys.add(Input.KEY_7);
		QWERTY.keys.add(Input.KEY_8);
		QWERTY.keys.add(Input.KEY_9);

		QWERTY.numkeys = new ArrayList<Integer>();
		QWERTY.numkeys.add(Input.KEY_NUMPAD0);
		QWERTY.numkeys.add(Input.KEY_NUMPAD1);
		QWERTY.numkeys.add(Input.KEY_NUMPAD2);
		QWERTY.numkeys.add(Input.KEY_NUMPAD3);
		QWERTY.numkeys.add(Input.KEY_NUMPAD4);
		QWERTY.numkeys.add(Input.KEY_NUMPAD5);
		QWERTY.numkeys.add(Input.KEY_NUMPAD6);
		QWERTY.numkeys.add(Input.KEY_NUMPAD7);
		QWERTY.numkeys.add(Input.KEY_NUMPAD8);
		QWERTY.numkeys.add(Input.KEY_NUMPAD9);
	}
	/** DVORAK keyboard bindings. */
	public static KeyBindings DVORAK;
	static {
		DVORAK = new KeyBindings();
		DVORAK.left = Input.KEY_LEFT;
		DVORAK.right = Input.KEY_RIGHT;
		DVORAK.up = Input.KEY_UP;
		DVORAK.down = Input.KEY_DOWN;
		DVORAK.attack = Input.KEY_P;
		DVORAK.retreat = Input.KEY_F;
		DVORAK.ability0 = Input.KEY_APOSTROPHE;
		DVORAK.ability1 = Input.KEY_COMMA;
		DVORAK.ability2 = Input.KEY_PERIOD;
		DVORAK.passive0 = Input.KEY_1;
		DVORAK.passive1 = Input.KEY_2;
		DVORAK.passive2 = Input.KEY_3;
		DVORAK.moonwalk = Input.KEY_LSHIFT;
		DVORAK.space = Input.KEY_SPACE;
		DVORAK.inventory = Input.KEY_I;
		DVORAK.menu = Input.KEY_ESCAPE;
		DVORAK.dialog = Input.KEY_SPACE;
		DVORAK.enter = Input.KEY_ENTER;
		DVORAK.period = Input.KEY_PERIOD;
		DVORAK.numPeriod = Input.KEY_DECIMAL;
		DVORAK.delete = Input.KEY_BACK;
		DVORAK.monsterSpawn = Input.KEY_F6;

		DVORAK.keys = new ArrayList<Integer>();
		DVORAK.keys.add(Input.KEY_0);
		DVORAK.keys.add(Input.KEY_1);
		DVORAK.keys.add(Input.KEY_2);
		DVORAK.keys.add(Input.KEY_3);
		DVORAK.keys.add(Input.KEY_4);
		DVORAK.keys.add(Input.KEY_5);
		DVORAK.keys.add(Input.KEY_6);
		DVORAK.keys.add(Input.KEY_7);
		DVORAK.keys.add(Input.KEY_8);
		DVORAK.keys.add(Input.KEY_9);

		DVORAK.numkeys = new ArrayList<Integer>();
		DVORAK.numkeys.add(Input.KEY_NUMPAD0);
		DVORAK.numkeys.add(Input.KEY_NUMPAD1);
		DVORAK.numkeys.add(Input.KEY_NUMPAD2);
		DVORAK.numkeys.add(Input.KEY_NUMPAD3);
		DVORAK.numkeys.add(Input.KEY_NUMPAD4);
		DVORAK.numkeys.add(Input.KEY_NUMPAD5);
		DVORAK.numkeys.add(Input.KEY_NUMPAD6);
		DVORAK.numkeys.add(Input.KEY_NUMPAD7);
		DVORAK.numkeys.add(Input.KEY_NUMPAD8);
		DVORAK.numkeys.add(Input.KEY_NUMPAD9);
	}
	
	public int left;
	public int right;
	public int up;
	public int down;
	public int attack;
	public int retreat;
	public int ability0;
	public int ability1;
	public int ability2;
	public int passive0;
	public int passive1;
	public int passive2;
	public int moonwalk;
	public int space;
	public int inventory;
	public int menu;
	public int dialog;
	public int enter;
	public int period, numPeriod;
	public int delete;
	public ArrayList<Integer> keys, numkeys;
	public int monsterSpawn;
	public int debugConsole;
	
	/** Cannot be instantiated. */
	private KeyBindings() {}
}
