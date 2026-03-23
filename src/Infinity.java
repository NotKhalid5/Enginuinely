import java.util.ArrayList;
import java.util.List;

public class Infinity {
    private List<Body> bodies = new ArrayList<>();
    private Vector2 gravity = new Vector2(0, 9.81);

    private double accumulator = 0.0;
    private double timestep = 0.016; // timestep 4 60 fps

    private CollisionDetector collisionDetector = new CollisionDetector();

    public void step(double dt) {
        // update the accumulator with the passed time (dt)
        accumulator += dt;

        // run physics steps
        while (accumulator >= timestep) {
            // apply forces
            for (Body body : bodies) {
                body.force = body.force.add(gravity.multiply(body.mass));

            }

            for (Body body : bodies) {
                body.integrate(timestep);
            }

            // collision detection and resolution
            collisionDetector.detectCollision(bodies); // Handle collisions

            accumulator -= timestep; // decrease the accumulator by timestep
        }
    }

    public List<Body> getBodies() {return bodies;}

    public void addBody(Body body) {bodies.add(body);}
}
