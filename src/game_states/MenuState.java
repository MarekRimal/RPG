package game_states;
import java.awt.Graphics;

//import basic.Game;
import basic.Handler;
import graphics.Assets;
import ui.ClickListener;
import ui.UIImageButton;
import ui.UIManager;

public class MenuState extends State{

	private UIManager uiManager;
	
	public MenuState(Handler handler){
		
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(500, 250, 300, 150, Assets.btn_start, new ClickListener(){

			@Override
			public void onClick() {
				//We dont want to click on that buttons in game state
				handler.getMouseManager().setUIManager(null); 
				
				State.setState(handler.getGame().gameState);
			}
		}));
	}

	@Override
	public void tick() {
		
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		
		uiManager.render(g);
	}
	
	
	
}
