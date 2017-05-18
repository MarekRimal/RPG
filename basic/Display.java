package basic;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width, heigth;
	
	//Constructor for the window
	public Display(String title, int width, int heigth){
		
		this.title = title;
		this.width = width;
		this.heigth = heigth;
		
		createDisplay(); 
	}
	
	//Window parameters
	private void createDisplay(){
		
		frame = new JFrame(title);
		frame.setSize(width, heigth);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, heigth));
		canvas.setMaximumSize(new Dimension(width, heigth));
		canvas.setMinimumSize(new Dimension(width, heigth));
		canvas.setFocusable(false); //Jinak by nemusela fungovat kleveska treba
		
		frame.add(canvas);
		
		//For image optimalization - not that important
		frame.pack();
	}
	
	//GETTERS
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
}
