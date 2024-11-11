package particle_simulator;

import java.awt.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.*;

public class Particle_Simulator extends Canvas {

    Timer timer;
    Sand particle;
    List<Particle> particles;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Particle Simulator");

        Particle_Simulator simulation = new Particle_Simulator();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(simulation);
        frame.setSize(800, 600);
		frame.getContentPane().setBackground(Color.black);
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> {

            Insets insets = frame.getInsets();
            int canvasWidth = 800 - insets.left - insets.right;
            int canvasHeight = 600 - insets.top - insets.bottom;

            simulation.setSize(canvasWidth, canvasHeight);
            
            simulation.particles = new ArrayList<>();

            int x = canvasWidth / 2; 
            int y = 0; 
            
//            simulation.particles.add(new Sand(1.0, 1.0, -4, 1, x, 0, y + 0.5, 0.02, 25, "Solid", canvasWidth, canvasHeight, Color.RED));
//            simulation.particles.add(new Sand(1.0, 1.0, 4, 1, x - 50,y + 0, 0.5, 0.02, 25, "Solid", canvasWidth, canvasHeight, Color.YELLOW));
//            simulation.particles.add(new Sand(1.0, 1.0, 2, 1, x + 50, y + 50, 0.5, 0.02, 25, "Solid", canvasWidth, canvasHeight, Color.BLUE));
            
            for (int i = 0; i < 10; i++) {
            	simulation.particles.add(new Sand(1.0, 1.0, 2 + (i + 3), 1, x + 50, y + 50, 0.5, 0.02, 25, "Solid", canvasWidth, canvasHeight, Color.BLUE));
            }

            // Timer to update and repaint the particle every 16ms (~60 FPS)
            simulation.timer = new Timer(16, e -> {
            	for(Particle particle : simulation.particles) {
            		particle.update();
            	}
            	simulation.repaint();
            });
            simulation.timer.start();
        });
    }

    @Override
    public void paint(Graphics g) {
        // Clear the screen
        g.clearRect(0, 0, getWidth(), getHeight());

        for (Particle particle : particles) {
            g.setColor(particle.color);
            int particleSize = 5; // Define a size for the particle
            g.fillRect((int) particle.getxPosition(), (int) particle.getyPosition(), particleSize, particleSize);
        }

        // display velocity information for the first particle
        if (!particles.isEmpty()) {
            Particle particle = particles.get(0);
            g.setColor(Color.WHITE);
            String velocityInfo = String.format("xVelocity: %.2f | yVelocity: %.2f | xPosition: %.2f | yPosition: %.2f",
                    particle.getxVelocity(), particle.getyVelocity(), particle.getxPosition(), particle.getyPosition());
            g.drawString(velocityInfo, 10, 20); // Draw velocity info at the top-left of the screen
        }
    }

    @Override
    public void update(Graphics g) {
        // Create an offscreen image for double buffering
        Image offscreenImage = createImage(getWidth(), getHeight());
        Graphics offscreenGraphics = offscreenImage.getGraphics();

        // Clear the offscreen image
        offscreenGraphics.clearRect(0, 0, getWidth(), getHeight());

        // Draw everything on the offscreen image
        paint(offscreenGraphics);

        // Draw the offscreen image to the screen
        g.drawImage(offscreenImage, 0, 0, this);

        // Dispose the offscreen graphics to free up resources
        offscreenGraphics.dispose();
    }
}
