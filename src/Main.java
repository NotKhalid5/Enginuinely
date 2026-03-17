public class Main {
    public static void main(String [] args) {
        // System.out.println("Testing testing");

        // Body Creation Test Bench:
        // Create a Body object with specific position, velocity, and mass
        Vector2 position = new Vector2(0, 10);   // Body starts at position (0, 10)
        Vector2 velocity = new Vector2(2, 0);    // Body has a velocity of 2 units in the x-direction
        double mass = 5.0;                       // The mass of the body is 5 kg

        Body body = new Body(position, velocity, mass);

        // Apply a force (e.g., gravity or any other external force)
        Vector2 gravity = new Vector2(0, -9.81);  // Force due to gravity (in m/s^2)
        body.applyForce(gravity.multiply(body.mass));  // Apply the force

        // Integrate the body over a small time step (e.g., 1 second)
        double dt = 1.0; // Time step of 1 second
        body.integrate(dt);

        // Print the body's new position and velocity after integration
        System.out.println("New Position: (" + body.position.x + ", " + body.position.y + ")");
        System.out.println("New Velocity: (" + body.velocity.x + ", " + body.velocity.y + ")");



        // Initialize simulation:
        Infinity infinity = new Infinity();

        Body body1 = new Body();
        body1.position = new Vector2(0, 10);
        body1.velocity = new Vector2(0, 0);
        body1.mass = 1.0;
        body1.radius = 1.0;

        infinity.addBody(body1);

        double lastTime = System.nanoTime();
        while (true) {
            double now = System.nanoTime();
            double dt = (now - lastTime) / 1e9; // Time elapsed in seconds
            lastTime = now;

            // Update simulation w/ dt
            infinity.step(dt);

            try {
                Thread.sleep(16);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
