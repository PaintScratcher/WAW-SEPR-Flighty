package game.struct;

public class Score {
	
	// Variables
	
	/** Variable which holds current score */
	private int score;
	
	/** Variable which holds current multiplier */
	private int multiplier;
	
	// Constructor
	
	/**
	 * <p>
	 * Constructor
	 * </p>
	 * 
	 * Initialises: score = 0; multiplier = 1;
	 * 
	 */
	public Score(){
		this.score = 0;
		this.multiplier = 1;
	}
	
	// Methods 
	
	/**
	 * <p>
	 * This method adds points to the score variable and is used within the Game update(args) method. 
	 * </p>
	 * <p>
	 * Upon arrival of plane at the end of the runway: Score += 10 * multiplier points
	 * </p>
	 * <p>
	 * Upon arrival of plane through any other waypoint: Score += 5 * multiplier points
	 * </p>
	 * 
	 * @param plane Plane whose position is checked to be a waypoint or not
	 * @param currentGame Used to gain access to airport attributes
	 */
	public void addScore(Plane plane, Game currentGame){
		
		if (!plane.getFlightPlan().getCurrentRoute().isEmpty()){
			if (plane.getFlightPlan().getCurrentRoute().get(0) == currentGame.getAirport().getEndOfRunway()){
				this.score += 10 * this.multiplier;
			}
			else if (plane.getFlightPlan().getCurrentRoute().get(0) == currentGame.getAirport().getBeginningOfRunway()){
				this.score += 0;
			}
			else {
				this.score += 5 * this.multiplier;
			}
		}
	}
	
	/**
	 * <p>
	 * Method used within Game - update(args), GameWindow - render(args) methods
	 * </p>
	 * <p>
	 * Code within update(args) in Game will check whether a plane is outside of the airspace; if so, this method will be called. Likewise, code within render(args) in GameWindow
	 * will call this method if a plane is taking too long to takeoff.
	 * </p> 
	 * If the score variable is greater than or equal to 10 * multiplier, then 10 * multiplier is deducted from score. Otherwise the score is set to zero,
	 *  as 10 * multiplier would result in a negative score.  
	 */
	public void planeLeftAirspaceOrWaitingToTakeOffMinusScore(){
		this.score = (this.score >= 10 * this.multiplier) ? (this.score - 10 * this.multiplier) : 0;
	}
	
	/**
	 * <p>
	 * Method used within Game - update(args) method
	 * </p>
	 * Within the update(args) method of Game, a section of the code will test whether any plane's current flight plan length is equal to 0 i.e. whether any flight has finished
	 * its flight plan. If it has it will enter a section of code where this method is called; the method checks whether the flight has experienced any collision warnings as it flew
	 * through the airspace, if this is the case then no change occurs (as plane.getViolationOccurred() will return true). If no collision warnings have occurred the multiplier is incremented. 
	 * 
	 * @param plane Plane which is currently being checked
	 */
	public void planePilotedPerfectlyMultiplierBonus(Plane plane){
		this.multiplier = (plane.getViolationOccurred() == false && this.multiplier < 20) ? (this.multiplier + 1) : this.multiplier;
	}
	
	/**
	 * <p>
	 * Method used within Game - collisionHelper(args) method
	 * </p>
	 * Code within collisionHelper(args) will check whether any planes are close enough to each other to cause a collision warning; if so, this method will be called.
	 * If the score variable is greater than or equal to 5 * multiplier, then 5 * multiplier is deducted from the score. If then the multiplier is greater than 1, it is
	 * decremented. Otherwise, the score is set to 0 and the multiplier decremented if it is greater than 1. 
	 */
	public void planeCollisionWarningMultAndScorePenalties(){
	
			if (this.score >= 5 * this.multiplier){
				this.score -= 5 * this.multiplier;
				if (this.multiplier > 1){
					this.multiplier--;
				}
			}
			else {
				this.score = 0;
				if (this.multiplier > 1){
					this.multiplier--;
				}
			}
	}
	
	// Accessors
	
	/**
	 * 
	 * @return Current score value
	 */
	public int getScore(){
		return this.score;
	}
	
	/**
	 * Used in testing
	 * @param newScore What score is to be set as
	 */
	public void setScore(int newScore){
		this.score = newScore;
	}
	
	/**
	 * 
	 * @return Current multiplier value
	 */
	public int getMultiplier(){
		return this.multiplier;
	}
	
	/**
	 * Used in testing
	 * @param newMultiplier What the multiplier is to be set as
	 */
	public void setMultiplier(int newMultiplier){
		this.multiplier = newMultiplier;
	}
}