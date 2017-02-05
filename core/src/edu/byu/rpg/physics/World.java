package edu.byu.rpg.physics;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains all of our game objects as lists of collision groups.
 * You can use this class to check for collisions by object type.
 */
public class World {

    /** Defines collision group classes. */
    public static enum Type { SOLID, PLAYER, ENEMY }

    /** Private map of type to collision group. */
    private HashMap<Type, ArrayList<ICollideable>> groups;

    /**
     * Initializes the collision groups map.
     */
    public World() {
        groups = new HashMap<Type, ArrayList<ICollideable>>();
        // create new collision group for each type.
        for (Type type : Type.values()) {
            groups.put(type, new ArrayList<ICollideable>());
        }
    }

    /**
     * Adds a collideable object to the group of the specified type.
     * @param type The type of group you want to add this object to.
     * @param collideable The object to add to the collision group.
     */
    public void add(Type type, ICollideable collideable) {
        ArrayList<ICollideable> group = groups.get(type);
        group.add(collideable);
    }

    /**
     * Removes a collideable object from the group of the specified type.
     * @param type The type of group you want to remove this object from.
     * @param collideable The object to remove from the collision group.
     */
    public void remove(Type type, ICollideable collideable) {
        ArrayList<ICollideable> group = groups.get(type);
        group.remove(collideable);
    }

    /**
     * Checks for collisions against the group of the specified type.
     * @param type The type of the group you want to check for collisions against.
     * @param otherBody The {@link Body} object you want to check against the group.
     * @return The first {@link ICollideable} to overlap <tt>otherBody</tt>, or <tt>null</tt>.
     */
    public ICollideable collide(Type type, Body otherBody) {
        ArrayList<ICollideable> group = groups.get(type);
        for (ICollideable collideable : group) {
            if (collideable.collide(otherBody)) return collideable;
        }
        return null;
    }

    /**
     * Checks for collisions against the group of the specified type.
     * @param type The type of the group you want to check for collisions against.
     * @param otherBody The {@link Body} object you want to check against the group.
     * @return <tt>true</tt> if a collision happened, <tt>false</tt> if no.
     */
    public boolean collideCheck(Type type, Body otherBody) {
        return (collide(type, otherBody) != null);
    }

//    /**
//     * Checks for collisions against all the specified groups.  <strong>Note:</strong> This
//     * function will only return the first <tt>collideable</tt> to intersect <tt>otherBody</tt>,
//     * and will execute collision checking against the groups in the order specified by the
//     * <tt>types</tt> parameter.
//     * @param types An array of types to check against.
//     * @param otherBody The {@link Body} object you want to check against the groups.
//     * @return The first {@link ICollideable} to overlap <tt>otherBody</tt>, or <tt>null</tt>.
//     */
//    public ICollideable collide(Type[] types, Body otherBody) {
//        for (int i = 0; i < types.length; i++) {
//            ICollideable collisionObj = collide(types[i], otherBody);
//            if (collisionObj != null) return collisionObj;
//        }
//        return null;
//    }
}
