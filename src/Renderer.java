import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Renderer extends JPanel {
    private List<Body> bodies;

    public Renderer(List<Body> bodies) {
        this.bodies = bodies;
        setPreferredSize(new Dimension(800, 600)); // set window size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // clear canvas
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        // draw each body
        for (Body body : bodies) {
            if (body.shape instanceof Circle) {
                Circle circle = (Circle) body.shape;

                int x = (int) (body.position.x + getWidth() / 2);
                int y = (int) (getHeight() / 2 - body.position.y);

                // draw the circle
                g.setColor(Color.BLUE);
                g.fillOval(x - (int) circle.radius, y - (int) circle.radius,
                        (int) (2 * circle.radius), (int) (2 * circle.radius));
            }
        }
    }
}