/**
 * Game Project - TirinhosNoCeu
 * Purpose - To validate the Java unit, I chose to create a game
 * with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 0.8.0
 */
package com.mygdx.game;

import GameLevels.FirstLevel;
import com.badlogic.gdx.Game;

/**
 *
 * @author Marco Silva
 */
public class TirinhosGame extends Game {
    @Override
    public void create() {
        FirstLevel fl = new FirstLevel(this);
        setScreen(fl);
    }
}
