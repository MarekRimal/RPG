package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject{

	private BufferedImage[] images; //The hovering and not
	private ClickListener clicker;
	
	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}

	@Override
	public void tick() {}

	public void render(Graphics g) {
		if(hovering){
			g.drawImage(images[1], (int) x, (int) y, width, height, null);
		}
		else{
			g.drawImage(images[0], (int) x, (int) y, width, height, null);
		}
	}

	@Override
	public void onClick() {
		
		//We just can put different images and different 
		//listeners to this clicker instead of wasting classes
		clicker.onClick();
	}
}
