public class Vector2 {
    public double x, y; // public so these fields are visible everywhere

    public Vector2 () {
        x = 0.0;
        y = 0.0;
    }
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // ================================================================
    // MATH OPERATIONS:
    // ================================================================
    // the following functions return new vectors with according changes to the vector passed in
    public Vector2 add(Vector2 v) {return new Vector2(this.x + v.x, this.y + v.y);}
    public Vector2 subtract(Vector2 v) {return new Vector2(this.x - v.x, this.y - v.y);}
    public Vector2 multiply(double s) {return new Vector2(this.x * s, this.y * s);}
    public Vector2 divide(double s) {return new Vector2(this.x / s, this.y / s);}

    // ================================================================
    // VECTOR OPERATIONS:
    // ================================================================

    public double dot(Vector2 v) {return this.x * v.x + this.y * v.y;}
    // cross product
    public Vector2 normalize() {
        double length = Math.sqrt(this.x * this.x + this.y * this.y);
        return new Vector2(this.x / length, this.y / length);
    }
}
