/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 *
 * @author EFA
 */
public class LabelTextGround {

    //===== properties ======
    private float timeElapsed;
    private final Label timeLabel;
    private final BitmapFont font;
    private final String text;
    private final LabelStyle style;

    public LabelTextGround() {
        timeElapsed = 0;
        font = new BitmapFont();
        text = "Time: 0";
        style = new LabelStyle(font, Color.YELLOW);
        timeLabel = new Label(text, style);
        timeLabel.setFontScale(2);
        timeLabel.setPosition(100, 400);
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed += timeElapsed;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public void setText(String text) {
        timeLabel.setText(text);
        
    }
    

}
