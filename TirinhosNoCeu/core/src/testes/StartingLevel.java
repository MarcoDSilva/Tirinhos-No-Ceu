/*
 * Game Project - TirinhosNoCeu with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 1.5.0
 */
package testes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

/**
 *
 * @author EFA
 */
public class StartingLevel extends CommonScreen {

    //=== level elements ===  
    private BasicActor background;
    private BasicActor win;
    private PhysicsActor spaceShip;
    private PhysicsActor meteor;

    //=== game size ===
    private final float mapWidth = 800;
    private final float mapHeight = 800;

    //=== label for score ===
    private LabelTextGround labels;

    // ===== CONSTRUCTOR =====
    public StartingLevel(Game game) {
        super(game);
    }

    /**
     * where all the objects that make part of this level are created
     */
    @Override
    public void create() {
        initActor();
        initTextures();
        initPositions();
        initActors();
    }

    @Override
    public void update(float deltaTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // =================== ALL THE METHODS ARE BELOW THIS LINE ===================
    
    /**
     * all the actors that need to be instantiated are here for a better code reading
     */
    private void initActor() {
        background = new BasicActor();
        win = new BasicActor();
        spaceShip = new PhysicsActor();
        meteor = new PhysicsActor();
        labels = new LabelTextGround();
    }

    
    /**
     * all the textures that need to be instantiated are here
     */
    private void initTextures() {
        background.setTexture(new Texture(Gdx.files.internal("blueBackground.png")));
        win.setTexture(new Texture(Gdx.files.internal("")));
        spaceShip.setTexture(new Texture(Gdx.files.internal("")));
        meteor.setTexture(new Texture(Gdx.files.internal("")));
    }

    /**
     * all the textures that need to be instantiated are here
     */
    private void initPositions() {
        background.setPosition(0, 0);
        spaceShip.setPosition(mapWidth / 2, mapHeight / 2);
        meteor.setPosition(mapWidth / 10, mapHeight / 10);
        win.setPosition(0, 0);
    }

    /**
     * all the actors that need to be added to the mainStage are here
     */
    private void initActors() {
        mainStage.addActor(background);
        mainStage.addActor(win);
        mainStage.addActor(spaceShip);
        mainStage.addActor(meteor);
    }
}
