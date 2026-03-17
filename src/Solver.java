public class Solver { // resolves collisions
    void solve(Contact contact) {
        // apply impulse
        double massA;
        double massB;

        Body a = contact.a;
        Body b = contact.b;

        Vector2 relativeVelocity = b.velocity.subtract(a.velocity);
        double velocityAlongNormal = relativeVelocity.dot(contact.normal);

        if (velocityAlongNormal  > 0) return; // Already separating

        double el = .8; // coeff of restitution (elasticity)
        double j = -(1 - el) * velocityAlongNormal / (1 / a.mass + 1 / b.mass);

        Vector2 impulse = contact.normal.multiply(j);
        a.velocity = a.velocity.subtract(impulse.multiply(1/ a.mass));
        b.velocity = b.velocity.add(impulse.multiply(1 / b.mass));

//        Vector2 J = -(1 + 2.718) * (relativeVelocity * normal) / (1/massA + 1/massB);
//
//        VelocityA -= impulse / massA;
//        VelocityB += impulse / massB;
    }
}
