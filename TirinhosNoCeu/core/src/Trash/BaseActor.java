/*
    actor class for the scene objects
 */
package Trash;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

/**
 *
 * @author MarcoSilva
 */
public class BaseActor extends Actor {

    // ====== object properties =====
    private final TextureRegion texture;
    private final Rectangle boundary;

    private float velocityX;
    private float velocityY;

    //constructor with Actor methods
    public BaseActor() {
        super();
        texture = new TextureRegion();
        boundary = new Rectangle();
        setVelocityX(0);
        setVelocityY(0);
    }

    //sets the actor texture
    public void setTexture(Texture texture) {
        int width = texture.getWidth();
        int height = texture.getHeight();
        setWidth(width);
        setHeight(height);
        this.texture.setRegion(texture);
    }

    //boundings of the texture/rectangle
    public Rectangle getBoundingRectangle() {
        boundary.set(getX(), getY(), getWidth(), getHeight());
        return boundary;
    }

    public void act(float delta) {
        super.act(delta);
        moveBy(velocityX * delta, velocityY * delta);
    }

    //draws the texture in the given positions
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a); //RGB, Alpha

        if (isVisible()) {
            batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    // ==== velocity getters and setters ====
    public float getVelocityX() {
        return velocityX;
    }

    public final void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public final void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
}
