import java.util.Vector;

public class Body {
    // initializing private fields;
    public Vector2 position; // position of body n 2D space
    public Vector2 velocity; // velocity of body n units per second
    public Vector2 force; // net force applied to body (Newtons)
    public double mass; // mass of body n kilograms
    public Shape shape; // allows us 2 attach the geometry of the body and determine handling

    // Store previous position for Verlet integration
    private Vector2 previousPosition;

    // Default no-arg constr.
    public Body () {
        this.position = new Vector2(0,0); // Default position at origin
        this.velocity = new Vector2(0,0); // Default velocity is zero
        this.force = new Vector2(0,0);  // Default force is zero
        this.mass = 1.0;  // Default mass of 1 unit
        this.previousPosition = new Vector2(position.x, position.y);
    }

    public void integrate(double timestep) {
        // update velocity and position using euler method of integration
        velocity = velocity.add(force.multiply(1 / mass).multiply(timestep)); // v = v + a * dt
        position = position.add(velocity.multiply(timestep)); // x = x + v * dt
    }

    // Verlet Integration
    public void verletIntegrate(double timestep) {
        // new position = 2 * current position - previous position + acceleration * timestep^2
        Vector2 acceleration = force.divide(mass); // F = ma => a = F/m
        Vector2 newPosition = position.multiply(2).subtract(previousPosition).add(acceleration.multiply(timestep * timestep));

        // update the previous position
        previousPosition = new Vector2(position.x, position.y);

        // update current position
        position = newPosition;

        // update velocity (based on change in position)
        velocity = position.subtract(previousPosition).divide(timestep); // v = (x_new - x_old) / dt
    }

    // Runge-Kutta Integration
    public void rk4Integrate(double timestep) {
        // compute forces
        Vector2 acceleration = force.divide(mass);

        // Calculate the four intermediate steps (k1-k4)
        Vector2 k1v = velocity;
        Vector2 k1x = acceleration;

        Vector2 k2v = velocity.add(k1x.multiply(0.5).multiply(timestep));
        Vector2 k2x = force.add(k1v.multiply(0.5).multiply(timestep)).multiply(1 / mass);

        Vector2 k3v = velocity.add(k2x.multiply(0.5).multiply(timestep));
        Vector2 k3x = force.add(k2v.multiply(0.5).multiply(timestep)).multiply(1 / mass);

        Vector2 k4v = velocity.add(k3x.multiply(timestep));
        Vector2 k4x = force.add(k3v.multiply(timestep)).multiply(1 / mass);

        // Update position and velocity using the RK4 method
        position = position.add(k1v.add(k2v.multiply(2)).add(k3v.multiply(2)).add(k4v).multiply(timestep / 6));
        velocity = velocity.add(k1x.add(k2x.multiply(2)).add(k3x.multiply(2)).add(k4x).multiply(timestep / 6));
    }

    // Setter for force
    public void setForce(Vector2 force) {
        this.force = force;
    }

    // Constructor w/ fields to initialize a Body w/ position, velocity, and mass
    public Body(Vector2 position, Vector2 velocity, double mass, Shape shape) {
        this.position = position; // initialize position w/ provided Vector2
        this.velocity = velocity; // initialize velocity w/ provided Vector2
        this.force = new Vector2(0,0); // initial force is zero
        this.mass = mass; // set mass of the body
        this.shape = shape; // set the geometry
    }

    public void applyForce(Vector2 force) {this.force = this.force.add(force);} // allows 4 the addition of forces like friction and others

    public void initializeVerlet(double timestep) {this.previousPosition = position.subtract(velocity.multiply(timestep));}
}
