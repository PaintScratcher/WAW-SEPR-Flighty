import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Game extends StateBasedGame  {
	
	public static final String NAME = "ATC Game";
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	public Game(String NAME) {
		super(NAME);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
		this.enterState(MENU);
	}
	public void initStatesList(GameContainer gc) throws SlickException {
		
		//this.getState(MENU).init(gc, this);
		//this.getState(PLAY).init(gc, this);
		
		
	}
	
	public static void main(String[] args) {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game(NAME));
			appgc.setDisplayMode(1200, 600, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
}