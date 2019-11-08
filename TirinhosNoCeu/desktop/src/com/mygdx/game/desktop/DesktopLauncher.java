package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.mygdx.game.TirinhosGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        TirinhosGame myGame = new TirinhosGame();
        LwjglApplication launcher = new LwjglApplication(myGame);        
    }
}  