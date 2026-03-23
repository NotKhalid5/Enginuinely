import java.util.List;

public class CollisionDetector {
    // Method to detect circle-to-circle collision
    public void detectCollision(List<Body> bodies) {
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) { // Only check j > i to avoid duplicate checks
                Body a = bodies.get(i);
                Body b = bodies.get(j);

                // only check collision b/w 2 circles
                if (a.shape instanceof Circle && b.shape instanceof Circle) { // Only check collision between circles
                    detectCircleCollision(a, b);
                }
            }
        }
    }

    // Circle-to-circle collision detection
    private void detectCircleCollision(Body a, Body b) {
        Circle ca = (Circle) a.shape;
        Circle cb = (Circle) b.shape;

        double dx = a.position.x - b.position.x;
        double dy = a.position.y - b.position.y;

        double distance = Math.sqrt(dx * dx + dy * dy); // Distance between the centers using distance formula
        double radiusSum = ca.radius + cb.radius; // Sum of radii

        // Check if the circles are colliding (i.e., distance < sum of radii)
        if (distance < radiusSum) {
            resolveCircleCollision(a, b, distance, radiusSum);
        }
    }

    // Method to resolve circle-to-circle collision
    private void resolveCircleCollision(Body a, Body b, double distance, double radiusSum) {
        //prevent divide by 0
        if (distance == 0) distance = 0.0001;

        // Calculate the collision normal (unit vector from one circle to the other)
        Vector2 normal = new Vector2(
                b.position.x - a.position.x,
                b.position.y - a.position.y
        ).normalize();

        // Calculate the penetration depth (how much the circles overlap)
        double overlap = radiusSum - distance;


        // Move circles apart along the collision normal (Separate bodies)
        double percent = 0.8; // correction strength
        double slop = 0.01;   // ignore tiny overlaps

        double correctionMag = Math.max(overlap - slop, 0) / (1 / a.mass + 1 / b.mass) * percent;

        Vector2 correction = normal.multiply(correctionMag);

        a.position = a.position.subtract(correction.multiply(1 / a.mass));
        b.position = b.position.add(correction.multiply(1 / b.mass));

        // Apply impulses to resolve velocities (simple elastic collision model)
        // Calculate relative velocity in the direction of the normal
        Vector2 relativeVelocity = b.velocity.subtract(a.velocity);
        double velocityAlongNormal = relativeVelocity.dot(normal);

        // Only resolve if the circles are moving towards each other
        if (velocityAlongNormal > 0) return; // No need to resolve if they are separating

        // Coefficient of restitution (elasticity) - 1.0 for perfectly elastic, less for inelastic
        double resititution = 0.8;

        // Calculate impulse scalar
        double impulse = -(1 + resititution) * velocityAlongNormal / (1 / a.mass + 1 / b.mass);

        // Apply the impulse to the velocities
        Vector2 impulseVector = normal.multiply(impulse);
        a.velocity = a.velocity.subtract(impulseVector.multiply(1 / a.mass));
        b.velocity = b.velocity.add(impulseVector.multiply(1 / b.mass));
    }
}