package menu;

import game.Game;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public abstract class AbstractMenu {
	private ArrayList<JComponent> components;
	private KeyListener menuListener;
	public static MainMenu main = new MainMenu();
	public AbstractMenu() {
		components = new ArrayList<JComponent>();
	}

	public void openMenu() {
		for (JComponent item : components){
			Game.g.add(item);
			item.setFocusable(false);
		}
		if (Game.g.getLayout() != null)
			Game.g.setLayout(null);
		if (menuListener != null)
			Game.g.addKeyListener(menuListener);
		Game.g.activeMenu = this;
	}

	public void closeMenu() {
		for (JComponent item : components)
			Game.g.remove(item);
		Game.g.removeKeyListener(menuListener);
		Game.g.activeMenu = null;
	}

	public void changeMenu(AbstractMenu m) {
		closeMenu();
		m.openMenu();
		Game.g.activeMenu = m;
	}

	public void addComponent(JComponent component, int x, int y, int width,int height) {
		components.add(component);
		JComponent item = components.get(components.indexOf(component));
		item.setBounds(x, y, width, height);
	}

	public JComponent getComponent(int i) throws ArrayIndexOutOfBoundsException {
		return components.get(i);
	}
	public abstract void render(Graphics g);
}
