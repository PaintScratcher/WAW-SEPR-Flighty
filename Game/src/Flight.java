import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Flight {

	//FIELDS
	private double x, y, target_altitude, current_heading, target_heading;
	private int current_altitude; 
	private boolean turning_right, turning_left;
	private FlightPlan flight_plan;
	private int MAXIMUM_ALTITUDE = 30000;
	private int MINIMUM_ALTITUDE = 27000;
	int flight_num;
	Image img;
	int flight_button_x;
	Color color;
	boolean selected;
	int entryNum;
	Airspace airspace;
	

	

	//CONSTRUCTOR
	Flight(Airspace a){
		this.x = 0;
		this.y = 0;
		this.target_altitude = 0;
		this.current_altitude = generate_altitude();
		this.target_heading = 0;
		this.current_heading = 0;
		this.turning_right = false;
		this.turning_left = false;
		this.flight_plan = new FlightPlan(a);
		this.flight_button_x = a.getFlight_button_x();
		this.color=Color.white;
		this.selected=false;
		this.airspace=a;
		//current_heading=calc.calculate_heading_to_first_waypoint(this, this.flight_plan.getPointByIndex(0).getXCoOrd(), this.flight_plan.getPointByIndex(0).getXCoOrd());
		
	}
	
	//METHODS
	
	
	public int generate_altitude(){
		Random rand = new Random();
		return rand.nextInt((MAXIMUM_ALTITUDE-MINIMUM_ALTITUDE)+1) + MINIMUM_ALTITUDE;	
	}
	
	public void turn_flight_left(int degree_turned_by){
		
		this.turning_right = false;
		this.turning_left = true;
		
		if (((int)this.current_heading - degree_turned_by) <= 0){
			this.target_heading = 360 - (degree_turned_by-(int)this.current_heading);
		}
		else{
			this.target_heading -=  degree_turned_by;
		}
	}
	
	public void turn_flight_right(int degree_turned_by){
		
		this.turning_left = false;
		this.turning_right = true;
		
		if (((int)this.current_heading + degree_turned_by) >= 360){
			this.target_heading = (degree_turned_by-(360-(int)this.current_heading));
		}
		else{
			this.target_heading += degree_turned_by;
		}
	}
	
	public void give_heading(int new_heading){
		this.turning_right = false;
		this.turning_left = false;
		//new_heading = new_heading % 360;
		this.target_heading = new_heading;
	}
	
	public void set_altitude_lower(){
		if ((this.current_altitude-1000)<MINIMUM_ALTITUDE){
			this.target_altitude = MINIMUM_ALTITUDE;
		}
		else{
			this.target_altitude -= 1000;
		}
	}
	
	public void set_altitude_higher(){
		if ((this.current_altitude+1000)<MAXIMUM_ALTITUDE){
			this.target_altitude = MAXIMUM_ALTITUDE;
		}
		else{
			this.target_altitude += 1000;
		}
	}
	
	public void update_x_y_coordinates(){
		double velocity= (this.flight_plan.getVelocity())/1000;

		this.x += velocity * Math.sin(Math.toRadians(this.current_heading)) ;

		this.y -= velocity * Math.cos(Math.toRadians(this.current_heading)) ; 

	}
	
	public void update_altitude(){
		if (this.current_altitude > this.target_altitude){
			this.current_altitude -= 1;
		}
		
		else if (this.current_altitude < this.target_altitude){
			this.current_altitude += 1;
		}
	}
	
	public void update_current_heading(){
		double rate = 0.3;
		if ((int)this.target_heading!=(int)this.current_heading){		
			if (this.turning_right == true){// If plane is already turning right or user has told it to turn right
				this.current_heading += rate;
				if ((int)this.current_heading == 360){
					this.current_heading = 0;
				}	
			}
			
			//if plane is already turning left or user has told it to turn left
			else if (this.turning_left == true){
				this.current_heading -= rate;
					if ((int)this.current_heading == 0){
						this.current_heading = 360;
					}	
			}
			
			// If plane has been given a heading so no turning direction specified
			// Below works out whether it should turn left or right to that heading.
			else{
				
				if (this.target_heading-this.current_heading==180){
					this.turning_right = true;
					this.current_heading +=rate;
				}
				else if ((this.current_heading+180)>= 359){
					
					if (this.target_heading < this.current_heading ){
						this.turning_right = true;
						this.current_heading +=rate;
						if ((int)this.current_heading == 360){
							this.current_heading = 0;
						}
					}
					else if((180 - (360 - this.current_heading))>this.target_heading){
						this.turning_right = true;
						this.current_heading +=rate;
						if ((int)this.current_heading == 360){
							this.current_heading = 0;
						}
					}
					else{
						this.turning_left = true;
						this.current_heading -=rate;
						if ((int)this.current_heading == 0){
							this.current_heading = 360;
						}
					}
				}
				else{
					if( (this.target_heading > this.current_heading) && (this.target_heading < this.current_heading+180) ){
						this.turning_right = true;
						this.current_heading +=rate;
						if ((int)this.current_heading == 360){
							this.current_heading = 0;
						}
					}
					else{
						this.turning_left = true;
						this.current_heading -=rate;
						if ((int)this.current_heading == 0){
							this.current_heading = 360;
						}
					}
				}
			}
		}
	}
	
	public boolean check_if_flight_at_waypoint()
	{
		// The line below is just so there are no errors
		return true;
	}
		
	
	// UPDATE, RENDER, INIT
	
	public void update(GameContainer gc){
		
		Input input = gc.getInput();
		this.update_current_heading();
		this.update_x_y_coordinates();
		int posX=Mouse.getX();
		int posY=Mouse.getY();
		if(airspace.getEntry_counter()<this.entryNum) {
			this.x=600;
			this.y=700;
		}
		else if((posX>this.flight_button_x&&posX<this.flight_button_x+100)&&(posY>0&&posY<100)) {

				if(selected==false){
						this.color=Color.yellow;
						if(input.isKeyDown(input.KEY_UP)) {
							this.give_heading(0);
						}
						if(input.isKeyDown(input.KEY_LEFT)) {
							this.give_heading(270);
						}
						if(input.isKeyDown(input.KEY_DOWN)) {
							this.give_heading(180);
						}
						if(input.isKeyDown(input.KEY_RIGHT)) {
							this.give_heading(90);
						}
					}
				}
		else {
			this.color=Color.white;
		}

	}
	
	public void init() throws SlickException{
		img = new Image("plane.png");
		
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.drawString("Flight "+this.flight_num, (int)this.x-20, (int)this.y+20);
		g.drawOval((int)this.x-40, (int)this.y-40, 100, 100);
		g.fillRoundRect((int)this.flight_button_x, 500, 100, 100, 5);
		img.draw((int)this.x, (int)this.y);
		
		img.setRotation((int)current_heading);
	}
	
	
	
	// MUTATORS AND ACCESSORS

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getCurrent_heading() {
		return this.current_heading;
	}

	public void setCurrent_heading(double current_heading) {
		this.current_heading = current_heading;
	}

	public double getTarget_heading() {
		return this.target_heading;
	}

	public void setTarget_heading(double target_heading) {
		this.target_heading = target_heading;
	}

	public double getTarget_altitude() {
		return this.target_altitude;
	}

	public void setTarget_altitude(double target_altitude) {
		this.target_altitude = target_altitude;
	}

	public int getAltitude() {
		return this.current_altitude;
	}

	public void setAltitude(int altitude) {
		this.current_altitude = altitude;
	}

	public boolean isTurning_right() {
		return this.turning_right;
	}

	public void setTurning_right(boolean turning_right) {
		this.turning_right = turning_right;
	}

	public boolean isTurning_left() {
		return this.turning_left;
	}

	public void setTurning_left(boolean turning_left) {
		this.turning_left = turning_left;
	}
	public void setFlight_num(int i) {
		this.flight_num=i;
	}
	public void setEntryNum(int entryNum) {
		this.entryNum = entryNum;
	}
	//tostring function to display a flight object so we can read it
	@Override
	public String toString() {
		return "X: "+this.x+" Y: "+this.y+" Flight Number: "+this.flight_num;
	}
	
	

}
