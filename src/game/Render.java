package game;

public class Render implements Runnable {

	@Override
	public void run() {
		while (true) {
			if (Game.g != null)
				Game.g.repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
