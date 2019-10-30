/**
 * Game Project - TirinhosNoCeu
 * Purpose - To validate the Java unit, I chose to create a game
 * with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 1.0.0
 */
package com.mygdx.game;

import GameLevels.GameMainMenu;
import com.badlogic.gdx.Game;

/**
 *
 * @author Marco Silva
 */
public class TirinhosGame extends Game {
    @Override
    public void create() {
        GameMainMenu gm = new GameMainMenu(this);
        setScreen(gm);
    }    
}

/**
 * TODO LIST:
 * - PLAYER MOVEMENT : rotating when left/right keys are pressed, 
 *      slowing down only when down or s is the key being pressed
 * - ASTEROIDS: spawn, movement, destruction.
 * - HP : both for asteroids and for the spaceship
 * - POINTS: for each asteroid some points are awarded
 * - UI - keeping width ratio after changing from menu to game
 * - POWERUPS (?)
 * 
 */
