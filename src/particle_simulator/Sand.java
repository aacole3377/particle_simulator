package particle_simulator;

import java.awt.Color;

public class Sand extends Particle{

	/**
	 * @param mass
	 * @param volume
	 * @param xVelocity
	 * @param yVelocity
	 * @param xPosition
	 * @param yPosition
	 * @param gravity
	 * @param friction
	 * @param temperature
	 * @param state
	 */
	
	public Sand(double mass, double volume, double xVelocity, double yVelocity, double xPosition, double yPosition,
			double gravity, double friction, double temperature, String state, int screenWidth, int screenHeight, Color color, int radius) {
		super(mass, volume, xVelocity, yVelocity, xPosition, yPosition, gravity, friction, temperature, state, screenWidth, screenHeight, Color.YELLOW, radius);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handleCollision(Particle particle) {
		double tXVelocity = this.xVelocity;
		double tYVelocity = this.yVelocity;
		
		this.xVelocity = particle.xVelocity;
		this.yVelocity = particle.yVelocity;
		
		particle.xVelocity = tXVelocity;
		particle.yVelocity = tYVelocity;
		
		
	}
	
	

}
