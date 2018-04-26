package me.callum.hengine.math;

public class Vector2 {

    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x=0;
        this.y=0;
    }

    public void clone(Vector2 vector) {
        this.x=vector.x;
        this.y=vector.y;
    }

    public void add(Vector2 vector) {
        x += vector.x;
        y += vector.y;
    }

    public void sub(Vector2 vector) {
        x -= vector.x;
        y -= vector.y;
    }

    public void mul(float scale) {
        x *= scale;
        y *= scale;
    }

    public void mul(Vector2 scale) {
        x*=scale.x;
        y*=scale.y;
    }
    public void normalize() {
        float magnitude = magnitude();
        x = x / magnitude;
        y = y / magnitude;
    }

    public float dot(Vector2 vector) {
        return (x*vector.x)+(y*vector.y);
    }

    public Vector2 normalized() {
        Vector2 newVector = zero();

        float magnitude = magnitude();

        newVector.x = x / magnitude;
        newVector.y = y / magnitude;

        return newVector;
    }

    public static Vector2 zero() {
        return new Vector2();
    }

    public float magnitude() {
       return (float)Math.sqrt((x*x)+(y*y));
    }

    public float squareMagnitude() {
        return (x*x)+(y*y);
    }

    public float distance(Vector2 other) {
        float x = this.x - other.x;
        float y = this.y - other.y;
        return (float)Math.sqrt((x*x)+(y*y));
    }

    public float squareDistance(Vector2 other) {
        float x = this.x - other.x;
        float y = this.y - other.y;
        return (x*x)+(y*y);
    }

    public Vector2 copy() {
        return new Vector2(x,y);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int intX() {
        return (int) x;
    }

    public int intY() {
        return (int) y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
