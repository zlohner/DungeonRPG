package edu.byu.rpg.entities.enemies.AI.Attacks;

import edu.byu.rpg.entities.enemies.offense.WeaponType;
import edu.byu.rpg.entities.enemies.offense.weapons.EnemyBulletWeapon;
import edu.byu.rpg.entities.enemies.offense.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/14/2017.
 */
public class ThreeBulletAttackAI implements AttackAI {

    private float attackSpeed = 2.0f;
    private float attackDamage = 2.0f;
    private WeaponType weaponType = WeaponType.BULLET;
    private EnemyBulletWeapon weapon;
    private float attackClock;
    private final float attackDistance = 200.0f;


    public ThreeBulletAttackAI(){
        attackClock = attackSpeed;
    }


    @Override
    public void scale(float scaleAttackDamage, float scaleAttackSpeed, float scaleAttackVelocity) {
        attackSpeed = attackSpeed / scaleAttackSpeed;
        attackDamage = attackDamage * scaleAttackDamage;
        weapon.scale(scaleAttackVelocity);

    }

    @Override
    public void attack(Body enemyBody, World world, float delta) {
        if(attackClock < 0 && world.DistanceToPlayer(enemyBody) <  attackDistance){
            executeAttack(enemyBody, world);
            attackClock = attackSpeed;
        } else {
            attackClock -= delta;
        }

    }
    public void executeAttack(Body enemyBody, World world){
        if (weapon == null) return;

        // get bullet direction and influence by player velocity
        float middleXDir = world.xDistanceToPlayer(enemyBody);
        float middleYDir = world.yDistanceToPlayer(enemyBody);
        float rightXDir = world.xDistanceToPlayer(enemyBody);
        float rightYDir = world.yDistanceToPlayer(enemyBody);
        float leftXDir = world.xDistanceToPlayer(enemyBody);
        float leftYDir = world.yDistanceToPlayer(enemyBody);

        // get center of hitbox
        float x = enemyBody.getCenterX();
        float y = enemyBody.position.y + enemyBody.size.y;

        // set up XDir and YDir for the right and left bullets
        if(middleXDir > 0){
            rightYDir+= Math.abs(0.5f * middleXDir);
            leftYDir-=Math.abs(0.5f * middleXDir);
        } else {
            rightYDir-=Math.abs(0.5f * middleXDir);
            leftYDir+=Math.abs(0.5f * middleXDir);
        }
        if(middleYDir > 0){
            rightXDir+=Math.abs(0.5f * middleYDir);
            leftXDir-=Math.abs(0.5f * middleYDir);
        } else {
            rightXDir-=Math.abs(0.5f * middleYDir);
            leftXDir+=Math.abs(0.5f * middleYDir);
        }
        //fire middle bullet
        weapon.fire(x, y, middleXDir, middleYDir);
        //fire right bullet
        weapon.fire(x, y, rightXDir, rightYDir);
        //fire left bullet
        weapon.fire(x, y, leftXDir, leftYDir);
    }


    @Override
    public float getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Override
    public void setWeapon(EnemyWeapon weapon) {
        this.weapon = (EnemyBulletWeapon)weapon;
    }
}
