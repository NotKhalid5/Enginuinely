import java.util.List;

public class CollisionDetector {
    // Method to detect circle-to-circle collision
    public void detectCollision(List<Body> bodies) {
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) { // Only check j > i to avoid duplicate checks
                Body a = bodies.get(i);
                Body b = bodies.get(j);

                if (a instanceof Circle && b instanceof Circle) { // Only check collision between circles
                    detectCircleCollision((Circle) a, (Circle) b);
                }
            }
        }
    }

    // Circle-to-circle collision detection
    private void detectCircleCollision(Circle a, Circle b) {
        double dx = a.position.x - b.position.x;
        double dy = a.position.y - b.position.y;

        double distance = Math.sqrt(dx * dx + dy * dy); // Distance between the centers
        double radiusSum = a.radius + b.radius; // Sum of radii

        // Check if the circles are colliding (i.e., distance < sum of radii)
        if (distance < radiusSum) {
            resolveCircleCollision(a, b, distance, radiusSum);
        }
    }

    // Method to resolve circle-to-circle collision
    private void resolveCircleCollision(Circle a, Circle b, double distance, double radiusSum) {
        // Calculate the collision normal (unit vector from one circle to the other)
        Vector2 normal = new Vector2(b.position.x - a.position.x, b.position.y - a.position.y).normalize();

        // Calculate the penetration depth (how much the circles overlap)
        double overlap = radiusSum - distance;

        // Move circles apart along the collision normal
        Vector2 separation = normal.multiply(overlap / 2);
        a.position = a.position.subtract(separation);
        b.position = b.position.add(separation);

        // Apply impulses to resolve velocities (simple elastic collision model)
        // Calculate relative velocity in the direction of the normal
        Vector2 relativeVelocity = b.velocity.subtract(a.velocity);
        double velocityAlongNormal = relativeVelocity.dot(normal);

        // Only resolve if the circles are moving towards each other
        if (velocityAlongNormal > 0) return; // No need to resolve if they are separating

        // Coefficient of restitution (elasticity) - 1.0 for perfectly elastic, less for inelastic
        double el = 0.8;

        // Calculate impulse scalar
        double impulse = -(1 + el) * velocityAlongNormal / (1 / a.mass + 1 / b.mass);

        // Apply the impulse to the velocities
        Vector2 impulseVector = normal.multiply(impulse);
        a.velocity = a.velocity.subtract(impulseVector.multiply(1 / a.mass));
        b.velocity = b.velocity.add(impulseVector.multiply(1 / b.mass));
    }
}