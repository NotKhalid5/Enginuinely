import java.util.Vector;

public class Body {
    // initializing private fields;
    public Vector2 position; // position of body n 2D space
    public Vector2 velocity; // velocity of body n units per second
    public Vector2 force; // net force applied to body (Newtons)
    public double mass; // mass of body n kilograms

    // Default no-arg constr.
    public Body () {
        this.position = new Vector2(0,0); // Default position at origin
        this.velocity = new Vector2(0,0); // Default velocity is zero
        this.force = new Vector2(0,0);  // Default force is zero
        this.mass = 1.0;  // Default mass of 1 unit
    }

    // Constructor w/ fields to initialize a Body w/ position, velocity, and mass
    public Body(Vector2 position, Vector2 velocity, double mass) {
        this.position = position; // initialize position w/ provided Vector2
        this.velocity = velocity; // initialize velocity w/ provided Vector2
        this.force = new Vector2(0,0); // initial force is zero
        this.mass = mass; // set mass of the body
    }

    public void integrate(double dt) {
        Vector2 acceleration = force.divide(mass); // F = ma => a = F/m (used vector operations defined in Vector2 class)
        velocity = velocity.add(acceleration.multiply(dt)); // update velocity
        position = position.add(velocity.multiply(dt)); // update position

        // reset net force after each step
        force = new Vector2(0,0);
    }

    public void applyForce(Vector2 force) {this.force = this.force.add(force);} // allows 4 the addition of forces like friction and others
}
