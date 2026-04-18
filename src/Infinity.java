import java.util.ArrayList;
import java.util.List;

public class Infinity {
    private List<Body> bodies = new ArrayList<>();
    private Vector2 gravity = new Vector2(0, 9.81);
    private double timestep = 0.016; // timestep 4 60 fps
    private CollisionDetector collisionDetector = new CollisionDetector();
    private double accumulator = 0.0; // FIX: persist accumulator

    // private double accumulator = 0.0;

    // Choose integration method: Euler, Verlet, or Runge-Kutta
    public enum IntegrationMethod {
        EULER,
        VERLET,
        RK4
    }

    private IntegrationMethod integrationMethod = IntegrationMethod.VERLET; // Default to Verlet

    public void step(double dt) {
        // update the accumulator with the passed time (dt)
        if (dt > 0.25) dt = 0.25; // FIX: prevent spiral of death
        accumulator += dt;

        // run physics steps
        while (accumulator >= timestep) {

            // apply forces
            for (Body body : bodies) {
                body.setForce(new Vector2(0, 0)); // FIX: clear forces each frame
                body.setForce(body.force.add(gravity.multiply(body.mass)));
            }

            // Integrate using selected method
            for (Body body : bodies) {
                switch (integrationMethod) {
                    case EULER:
                        body.integrate(timestep);
                        break;
                    case VERLET:
                        body.verletIntegrate(timestep);
                        break;
                    case RK4:
                        body.rk4Integrate(timestep);
                        break;
                }
            }

            // collision detection and resoltion
            collisionDetector.detectCollision(bodies); // Handle collisions

            accumulator -= timestep; // decrease the accumulator by timestep
        }
    }

    public List<Body> getBodies() {return bodies;}

    public void addBody(Body body) {bodies.add(body);}

    public void setIntegrationMethod(IntegrationMethod method) {this.integrationMethod = method;}
}