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
 * All collision checks are isolated in to this class/object.
 *
 * @author Jonkke
 */
public abstract class CollisionObject {

    private String type;
    private int x;
    private int y;
    private int width;
    private int height;
    private int radius;

    public CollisionObject(int width, int height) {
        this.type = "rectangle";
        this.width = width;
        this.height = height;
    }

    public CollisionObject(int radius) {
        this.type = "circle";
        this.radius = radius;
    }

    public boolean checkCollision(CollisionObject cobj) {
        if (this.type == "circle" && cobj.type == "circle") {
            return Math.sqrt(Math.pow(cobj.x - this.x, 2) + Math.pow(cobj.y - this.y, 2)) < this.radius;
        }
        // the rest...
        return false;
    }

}
