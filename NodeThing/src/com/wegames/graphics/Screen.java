/**
 * 
 */
package com.wegames.graphics;

import com.wegames.level.Level;

/**
 * @author Neptune
 *
 */
public class Screen {
	public int width, height;
	public int[] pixels;
	
	/**
	 * 
	 */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[this.width*this.height];
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i]=0xff000000;
		}
	}
	
	public void drawRect(int xPos, int yPos, int width, int height, int color) {
		for (int y = 0; y < height; y++) {
			if((y+yPos-Level.cameraPosY)<0||(y+yPos-Level.cameraPosY)>=this.height) continue;
			for (int x = 0; x < width; x++) {
				if((x+xPos-Level.cameraPosX)<0||(x+xPos-Level.cameraPosX)>=this.width) continue;
				pixels[(x+xPos-Level.cameraPosX)+(y+yPos-Level.cameraPosY)*width]=color;
			}
		}
	}
	
	public void drawCircle(int diameter, int xPos, int yPos, int color) {
		for (int y = 0; y <= diameter; y++) {
			if(((y-diameter/2)+yPos-Level.cameraPosY)<0||((y-diameter/2)+yPos-Level.cameraPosY)>=height) continue;
			for (int x = 0; x <= diameter; x++) {
				if(((x-diameter/2)+xPos-Level.cameraPosX)<0||((x-diameter/2)+xPos-Level.cameraPosX)>=width) continue;
				if(Math.sqrt(Math.pow(x-diameter/2, 2)+Math.pow(y-diameter/2, 2))<=diameter/2) 
					pixels[((x-diameter/2)+xPos-Level.cameraPosX)+((y-diameter/2)+yPos-Level.cameraPosY)*width]=color;
			}
		}
	}
	public void drawLine(int x0, int y0, int x1, int y1, int color) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = (x0 < x1) ? 1 : -1;
		int sy = (y0 < y1) ? 1 : -1;

		int err = dx - dy;

		while (true) {
			if(x0-Level.cameraPosX>=0&&x0-Level.cameraPosX<width&&y0-Level.cameraPosY>=0&&y0-Level.cameraPosY<height)pixels[x0-Level.cameraPosX+(y0-Level.cameraPosY)*width]=color;
			
			if (x0 == x1 && y0 == y1) {
				break;
			}
			
			int e2 = 2 * err;
			
			if (e2 > -dy) {
				err -= dy;
				x0 += sx;
			}
			
			if (e2 < dx) {
				err += dx;
				y0 += sy;
			}
		}
	}
	
}