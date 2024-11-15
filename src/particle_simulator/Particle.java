package particle_simulator;

import java.awt.Color;
import java.util.List;

public abstract class Particle {
	
	protected double mass;
	protected double volume;
	protected double xVelocity, yVelocity;
	protected double xPosition, yPosition;
	protected double gravity;
	protected double friction;
	protected double temperature;
	protected double time;
	protected String State;
	protected int screenWidth;
	protected int screenHeight;
	protected Color color; 
	protected int collisionRadius;
	
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
	 * @param time
	 * @param state
	 * @param screenWidth
	 * @param screenHeight
	 */
	
	public Particle(double mass, double volume, double xVelocity, double yVelocity, double xPosition, double yPosition,
			double gravity, double friction, double temperature, String state, int screenWidth, int screenHeight, Color color, int radius) {
		super();
		this.mass = mass;
		this.volume = volume;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.gravity = gravity;
		this.friction = friction;
		this.temperature = temperature;
		this.State = state;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.color = color;
		this.collisionRadius = radius;
	}
	
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getFriction() {
		return friction;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getCollisionRadius() {
		return collisionRadius;
	}

	public void setCollisionRadius(int collisionRadius) {
		this.collisionRadius = collisionRadius;
	}

	public void update(List<Particle> particles) {
		yVelocity += gravity;
//		xVelocity = 0;
		
		yPosition += yVelocity;
		xPosition += xVelocity;
		
		/**
		 * Bottom boundary 
		 */
		
		if (yPosition >= screenHeight - 10) {
			yPosition = screenHeight - 10;
			yVelocity = -yVelocity;
		}
		
		/**
		 * Upper boundary
		 */
		
		if (yPosition <= 0) {
			yVelocity = Math.abs(yVelocity);
//			yPosition = Math.abs(yPosition);
		}
		
		/**
		 * Right boundary
		 */
		
		if (xPosition >= screenWidth - 10) {
			xVelocity = -xVelocity;
		}
		
		/**
		 * Left Boundary
		 */
		
		if (xPosition <= 0) {
			xVelocity = Math.abs(xVelocity);
		}
		
//		// Increment RGB values for color-changing effect
//	    int red = color.getRed();
//	    int green = color.getGreen();
//	    int blue = color.getBlue();
//
//	    // Adjust each color component slightly (you can tweak these values to change the effect speed)
//	    red = (red + 4) % 256;   // Loop back to 0 if it exceeds 255
//	    green = (green + 6) % 256;
//	    blue = (blue + 8) % 256;
//
//	    // Update the particle color
//	    color = new Color(red, green, blue);
		
		for (Particle other : particles) {
			if (other != this && checkCollision(other)) {
				handleCollision(other);
			}
		}
	}
	
	private boolean checkCollision(Particle other) {
		double dx = this.xPosition - other.xPosition;
		double dy = this.yPosition - other.yPosition;
		
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		
		return distance < (this.collisionRadius + other.collisionRadius);
	}
	
	public void handleCollision(Particle other) {
	    // Swap velocities (basic elastic collision response)
	    double tempXVelocity = this.xVelocity;
	    double tempYVelocity = this.yVelocity;
	    this.xVelocity = other.xVelocity;
	    this.yVelocity = other.yVelocity;
	    other.xVelocity = tempXVelocity;
	    other.yVelocity = tempYVelocity;
	    
	    // Prevent sticking by adjusting positions
	    double dx = this.xPosition - other.xPosition;
	    double dy = this.yPosition - other.yPosition;
	    double distance = Math.sqrt((dx * dx) + (dy * dy));
	    double overlap = (this.collisionRadius + other.collisionRadius) - distance;

	    this.xPosition += (dx / distance) * (overlap / 2);
	    this.yPosition += (dy / distance) * (overlap / 2);
	    other.xPosition -= (dx / distance) * (overlap / 2);
	    other.yPosition -= (dy / distance) * (overlap / 2);
	    
	    // Damping effect
	    this.xVelocity *= 0.9;
	    this.yVelocity *= 0.9;
	    other.xVelocity *= 0.9;
	    other.yVelocity *= 0.9;
	    
	    // Increment RGB values for color-changing effect on collision
	    int red = (color.getRed() + 15) % 256;
	    int green = (color.getGreen() + 6) % 256;
	    int blue = (color.getBlue() + 25) % 256;
	    color = new Color(red, green, blue);
	    
	    // Debug print to confirm color change
//	    System.out.println("Color after collision: " + color);
	}

	
	public void getData() {
		System.out.printf("X Velocity: %d\nY Velocity: %d", xVelocity, yVelocity);
	}

}
