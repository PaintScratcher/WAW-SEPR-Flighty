import java.awt.Font;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.GameContainer;


public class Controls{
	
	
	private double heading;
	private int altitude;
	private TrueTypeFont font;
	private TextField headingControlTB;
	private TextField altControlTB;
	
	public Controls(GameContainer gc){
		this.heading = headingControl();
		this.altitude = altControl();
		System.out.println("CONTROL CLASS HERE");
		
		Font awtFont = new Font("Courier",Font.BOLD,30);
		font = new TrueTypeFont(awtFont, false);
		headingControlTB = new TextField(gc,font,100,100,100,100);
		altControlTB = new TextField(gc,font,100,100,100,100);	
	}

	public int altControl() {
		// TODO Auto-generated method stub
		return 0;
	}


	public double headingControl() {
			return 0.1;
	}
	
	public void update(GameContainer gc){
		Input input = gc.getInput();
		System.out.println(headingControlTB.getText());
		System.out.println(altControlTB.getText());
	}
	
	public void render(GameContainer gc, Graphics g)throws SlickException {
		headingControlTB.render(gc,g);
		altControlTB.render(gc,g);
		System.out.println("RENDERING NOW! :D");
	}

}
