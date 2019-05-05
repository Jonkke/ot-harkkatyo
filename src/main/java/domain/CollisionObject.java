/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * Collision object is a special object attached to those game objects that are
 * supposed to collide with other objects or game area bounds.
 *
 * Collision objects can represent either rectangular shapes or circular shapes.
 * The shape that a collision object represents is dictated by the constructor
 * that was used to create a collision object.
 *
 * Collision checks are isolated to this class.
 *
 * @author Jonkke
 */
public class CollisionObject {

    private GameObject hostObj;
    private String type;
    private int width;
    private int height;
    private int radius;

    public CollisionObject(int width, int height, GameObject hostObj) {
        this.hostObj = hostObj;
        this.type = "rectangle";
        this.width = width;
        this.height = height;
    }

    public CollisionObject(int radius, GameObject hostObj) {
        this.hostObj = hostObj;
        this.type = "circle";
        this.radius = radius;
    }

    /**
     * Checks whether this collision object intersects (=collides) with another
     * collision object or not.
     *
     * @param cobj the collision object to test collision against
     * @return boolean value
     */
    public boolean checkCollision(CollisionObject cobj) {
        if ("circle".equals(this.type) && "circle".equals(cobj.type)) {
            return Math.sqrt(Math.pow(cobj.getHostX() - this.getHostX(), 2) + Math.pow(cobj.getHostY() - this.getHostY(), 2)) < this.radius + cobj.radius;
        }
        if ("circle".equals(this.type) && "rectangle".equals(cobj.type)) {
            return this.getHostX() + this.radius >= cobj.getHostX() - cobj.width / 2
                    && this.getHostX() - this.radius <= cobj.getHostX() + cobj.width / 2
                    && this.getHostY() + this.radius >= cobj.getHostY() - cobj.height / 2
                    && this.getHostY() - this.radius <= cobj.getHostY() + cobj.height / 2;
        }
        return false;
    }

    /**
     * This method returns the side that was hit in another object. It is
     * assumed that a collision event has been confirmed with the
     * checkCollision() beforehand
     *
     * @param cobj the object we are colliding with
     * @return the side in the other object we hit, 0=top, 1=right, 2=bottom,
     * 3=left
     */
    public int getSideHit(CollisionObject cobj) {
        double thisLeftX;
        double thisRightX;
        double thisTopY;
        double thisBottomY;

        if ("circle".equals(this.type)) {
            thisLeftX = this.getHostX();
            thisRightX = this.getHostX();
            thisTopY = this.getHostY();
            thisBottomY = this.getHostY();
        } else {
            thisLeftX = this.getHostX() - this.width / 2;
            thisRightX = this.getHostX() + this.width / 2;
            thisTopY = this.getHostY() - this.height / 2;
            thisBottomY = this.getHostY() + this.height / 2;
        }

        double otherLeftX = cobj.getHostX() - cobj.width / 2;
        double otherRightX = cobj.getHostX() + cobj.width / 2;
        double otherTopY = cobj.getHostY() - cobj.height / 2;
        double otherBottomY = cobj.getHostY() + cobj.height / 2;

        double tc = thisBottomY - otherTopY;
        double bc = otherBottomY - thisTopY;
        double rc = otherRightX - thisLeftX;
        double lc = thisRightX - otherLeftX;

        if (tc < bc && tc < rc && tc < lc) {
            return 0;
        } else if (bc < tc && bc < rc && bc < lc) {
            return 2;
        } else if (rc < tc && rc < bc && rc < lc) {
            return 1;
        } else {
            return 3;
        }
    }

    /**
     * Checks whether this collision object is hitting the vertical bounds or
     * not
     *
     * @param xBounds the upper limit of the x bounds (lower always being 0)
     * @return boolean value
     */
    public boolean checkXBoundsCollision(int xBounds) {
        return this.getHostX() + this.radius >= xBounds || this.getHostX() - this.radius <= 0;
    }

    /**
     * Checks whether this collision object is hitting the horizontal bounds or
     * not
     *
     * @param yBounds the upper limit of the y bounds (lower always being 0)
     * @return boolean value
     */
    public boolean checkYBoundsCollision(int yBounds) {
        return this.getHostY() + this.radius >= yBounds || this.getHostY() - this.radius <= 0;
    }

    public double getHostX() {
        return this.hostObj.x;
    }

    public double getHostY() {
        return this.hostObj.y;
    }

}
