public class Circle extends Shape{
    double radius;

    public Circle(double radius) {this.radius = radius;}

    @Override
    boolean checkCollision(Shape other) {return false;}
}
