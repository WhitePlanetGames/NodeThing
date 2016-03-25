/**
 * 
 */
package com.wegames;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.wegames.graphics.Screen;
import com.wegames.level.Node;

/**
 * @author Neptune
 *
 */
public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	Thread thread;
	boolean running = false;

	int width = 1080, height = width / 16 * 9;
	JFrame frame;
	Screen screen;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	/**
	 * 
	 */
	public Main() {
		frame = new JFrame();
		Dimension dim = new Dimension(width, height);
		frame.setMinimumSize(dim);
		frame.setMaximumSize(dim);
		frame.setPreferredSize(dim);

		screen = new Screen(width, height);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		int frames = 0;
		double unprocesedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocesedSeconds += passedTime / 1000000000.0;
			while (unprocesedSeconds > secondsPerTick) {
				tick();
				unprocesedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println("Frames Per Second: " + frames + " | Ticks: " + tickCount);
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
		}
	}

	private void tick() {
		for (int i = 0; i < Node.nodes.size(); i++) {
			Node.nodes.get(i).tick();
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, frame.getWidth() * 3, frame.getHeight() * 3, null);

		g.dispose();
		bs.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.frame.add(main);
		main.frame.pack();
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setLocationRelativeTo(null);
		main.frame.setVisible(true);
		main.start();
	}

}
