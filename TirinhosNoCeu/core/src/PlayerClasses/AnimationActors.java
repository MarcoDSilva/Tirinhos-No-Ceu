/*
*animation class that extends the basic actor one
*hashmaps are used to hold the animations, the first being the key, the 2nd being the animation itself
 * Version alpha - 2.1.0
 */
package PlayerClasses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.HashMap;

/**
 *
 * @author MarcoSilva
 */
public class AnimationActors extends BasicActor {

    // ==== class properties ====
    private float elapsedTime;
    private Animation<TextureRegion> activateAnimation;
    private String activeName;
    private HashMap<String, Animation> animationStorage;

    //=== CONSTRUCTOR ===
    public AnimationActors() {
        super();
        elapsedTime = 0;
        activateAnimation = null;
        activeName = null;
        animationStorage = new HashMap<>();
    }

    /**
     * Here we update the hashmap that has the animations. the key is a string , ie("Active") if the activeAnimation has no texture, aka null, then the first time we use this method we set that key and animation as active
     *
     * @param key
     * @param animation
     */
    public void storeAnimation(String key, Animation animation) {
        animationStorage.put(key, animation);
        if (activateAnimation == null) {
            setActiveAnimation(key);
        }
    }

    /**
     * To store the frame you need to pass the key required to identify the animation and the corresponding texture.
     *
     * @param key
     * @param texture
     */
    public void storeAnimation(String key, Texture texture) {
        TextureRegion region = new TextureRegion(texture);
        TextureRegion[] frames = {region};
        Animation<TextureRegion> anima = new Animation<>(1.0f, frames);
        storeAnimation(key, anima);
    }

    /**
     * Here we define the animation to be played. If the Hashmap doesn't contain the key, it returns that no animation exist and breaks the method If the placeholder (activeName) is equal to the key, it breaks this method right away. Else, activates that animation.
     *
     * @param key
     */
    public void setActiveAnimation(String key) {

        if (!animationStorage.containsKey(key)) {
            System.out.println("No animation with the atribute of: " + key);
            return;
        }

        if (activeName.equals(key)) {
            return; //if it's running, just finish this method
        }

        activeName = key;
        activateAnimation = animationStorage.get(key);
        elapsedTime = 0;

        Texture texture = activateAnimation.getKeyFrame(elapsedTime).getTexture();
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        elapsedTime += deltaTime;
    }

    @Override
    public void draw(Batch batch, float pAlpha) {
        super.draw(batch, pAlpha);
    }
    
    /**
     * 
     * @param p 
     */
    public void copy(AnimationActors p) {
        super.copy(p);
        
        this.elapsedTime = 0;
        this.animationStorage = p.animationStorage;
        this.activeName = p.activeName;
        this.activateAnimation = p.animationStorage.get(this.activeName);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public AnimationActors cloned() {
        AnimationActors aCopia = new AnimationActors();
        aCopia.copy(this);
        return aCopia;
    }

}
