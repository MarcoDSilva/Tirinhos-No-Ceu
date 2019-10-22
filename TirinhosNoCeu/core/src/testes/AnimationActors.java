/*

 */
package testes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.HashMap;

/**
 *
 * @author MarcoSilva
 */
public class AnimationActors extends BasicActor{
    private float elapsedTime;
    private Animation activateAnimation;
    private String activeName;
    private HashMap<String, Animation> animationStorage;

    public AnimationActors() {
        super();
        elapsedTime = 0;
        activateAnimation = null;
        animationStorage = new HashMap<String, Animation>();
    }
    
    

}
