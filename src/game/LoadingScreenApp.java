package game;

import game.view.FullScreenLoadingCanvas;

import javax.swing.JFrame;

public class LoadingScreenApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int height = 720;
		int width = 1280;
		
		JFrame frame = new JFrame("ScreenTest");
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FullScreenLoadingCanvas canvas = new FullScreenLoadingCanvas(width, height);
		frame.setContentPane(canvas);
		canvas.start();
		
	}

}
