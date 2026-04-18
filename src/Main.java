import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // create bodies 4 sim
        // 1st body
        Body body1 = new Body();
        body1.position = new Vector2(0,0);
        body1.velocity = new Vector2(200,0);
        body1.mass = 1.0;
        body1.shape = new Circle(20.0);

        // 2nd body
        Body body2 = new Body();
        body2.position = new Vector2(200,0);
        body2.velocity = new Vector2(-200,0);
        body2.mass = 1.0;
        body2.shape = new Circle(20.0);

        body1.initializeVerlet(0.016);
        body2.initializeVerlet(0.016);

        // Add bodies 2 sim
        Infinity infinity = new Infinity();
        infinity.addBody(body1);
        infinity.addBody(body2);


        // Create and  set up JFrame Window
        JFrame frame = new JFrame("Physics Simulation");
        Renderer renderer = new Renderer(infinity.getBodies());
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.getContentPane().add(renderer);
        frame.pack();
        frame.setVisible(true);

        // Set integration method
        infinity.setIntegrationMethod(Infinity.IntegrationMethod.VERLET); // or RK4, EULER

        double lastTime = System.nanoTime();

        // simulation loop
        while(true) {
            double now = System.nanoTime();
            double dt = (now - lastTime) / 1e9; // time step n secs
            lastTime = now;

            infinity.step(dt); // step sim forward

//            // Debug output
//            System.out.println("Body1: (" + body1.position.x + ", " + body1.position.y + ")");
//            System.out.println("Body2: (" + body2.position.x + ", " + body2.position.y + ")");
//            i just talked to Nadia, and we talked about Audre Lorde

            renderer.repaint(); // repaint window to reflect changes

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
