public class Circle extends Shape{
    double radius;

    @Override
    boolean checkCollision(Shape other) {
        if (other instanceof Circle) {
            Circle otherCircle = (Circle) other; // explicity cast other to Circle

            double dx = this.position.x - this.position.x;
            double dy = this.position.y - this.position.y;

            double dist = Math.sqrt(dx*dx + dy*dy);

            if (dist < this.radius + otherCircle.radius) {
                resolveCollision(this, otherCircle); // resolve collision
                return true;
            }
        }
        return false; // if no collision
    }

    // handle and resolve collision between two circles
    private void resolveCollision(Circle a, Circle b) {
        // Calculate the collision normal (unit vector from a to b)
        Vector2 normal = new Vector2(b.position.x - a.position.y, b.position,y - a.position.y).normalize();

        // Calculate penetration depth (how much circles overlap)
        double distance = Math.sqrt(Math.pow(b.position.x - a.position.x, 2) + Math.pow(b.position.y - a.position.y, 2));
        double overlap = (a.radius + b.radius) - distance;

        // Move circles apart based on overlap
        Vector2 separation = normal.multiply(overlap / 2);
        a.position = a.position.subtract(separation);
        b.position = b.position.add(separation);

        // Apply impulses to resolve velocities (simple elastic collision model)
        Vector2 relativeVelocity = b.velocity.subtract(a.velocity);
        double velocityAlongNormal = relativeVelocity.dot(normal);

        if (velocityAlongNormal > 0) return; // already separating

        // coeff of restitution (elasticity)
        double el = 0.8;

        // scalar impulse calculation
        double impulse = -(1 + el) * velocityAlongNormal / (1 / a.mass + 1 / b.mass);

        // Apply the impulse to the velocities
        Vector2 impulseVector = normal.multiply(impulse);
        a.velocity = a.velocity.subtract(impulseVector.multiply(1 / a.mass));
        b.velocity = b.velocity.add(impulseVector.multiply(1 / b.mass));
    }
}
