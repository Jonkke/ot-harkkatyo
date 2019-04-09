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
 * All collision checks are isolated to this class.
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
        if (this.type == "circle" && cobj.type == "circle") {
            return Math.sqrt(Math.pow(cobj.getHostX() - this.getHostX(), 2) + Math.pow(cobj.getHostY() - this.getHostY(), 2)) < this.radius + cobj.radius;
        }
        if (this.type == "circle" && cobj.type == "rectangle") {
            return this.getHostX() + this.radius >= cobj.getHostX() - cobj.width / 2
                    && this.getHostX() - this.radius <= cobj.getHostX() + cobj.width / 2
                    && this.getHostY() + this.radius >= cobj.getHostY() - cobj.height / 2
                    && this.getHostY() - this.radius <= cobj.getHostY() + cobj.height / 2;
        }
        // TODO: prevent objets from getting stuck inside each other
        // TODO: work out a way to determine the side of collision when a circle hits a rectangle
        return false;
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

    public int getHostX() {
        return this.hostObj.x;
    }

    public int getHostY() {
        return this.hostObj.y;
    }

}
