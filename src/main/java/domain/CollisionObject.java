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
        return false;
    }
    
    public int getHostX() {
        return this.hostObj.x;
    }
    
    public int getHostY() {
        return this.hostObj.y;
    }

}
