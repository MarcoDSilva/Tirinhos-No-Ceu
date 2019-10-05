/*
 *MIT OPEN SOURCE
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author MarcoSilva
 */
public class PlayerHPTest extends Sprite {

    private int HP;

    public PlayerHPTest(Texture texture) {
        //gets the constructor of the Sprite class
        super(texture);

        //sets the num of lifes to N
        HP = 3;
    }

    /**
     * returns the HP available
     *
     * @return HP
     */
    public int getHP() {
        return HP;
    }

    /**
     * Sets the HP available
     *
     * @param HP
     */
    public void setHP(int HP) {
        this.HP = HP;
    }

    /**
     * reduces the HP by the damage it took
     *
     * @param amount
     */
    public void damage(int amount) {
        HP -= amount;
    }

    /**
     * heals the HP by the amount
     *
     * @param amount
     */
    public void heal(int amount) {
        HP += amount;
    }

    /**
     * verifies if the player is alive
     * @return 
     */
    public boolean isAlive() {
        return HP > 0;
    }
}
