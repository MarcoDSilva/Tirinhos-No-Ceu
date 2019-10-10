package GameLevels;

import com.mygdx.game.BasicActor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.Screen;

/**
 *
 * @author MarcoSilva
 */
public class GameMainMenu implements Screen {

    //===== properties =====
    private Stage userInterface;
    private Game game;

    //===== constructor =====
    public GameMainMenu(Game game) {
        this.game = game;
        create();
    }
    public void create() {
        init();
    }

    public void render(float deltaTime) {
        //if key pressed is one of the above
        if(Gdx.input.isKeyPressed(Keys.ENTER)) {
            this.game.setScreen(new FirstLevel(game));
        }      
        if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            
        }
        
        //updates the scene
        userInterface.act(deltaTime);
        
        //graphics
        Gdx.gl.glClearColor(0.6f, 0.6f, 1, 2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //drawing the stage
        userInterface.draw();        
    }

    // ==== required for implementing the screen ====
    @Override
    public void resize(int width, int height) {}
    
    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() { }

    public void show() { }

    public void hide() { }

    //===== METHODS =====
    
    /**
     * instantiate the interface Stage and respective actors to be added
     * Background, text Title, and label
     * @return menu design
     */
    public void init() {
        userInterface = new Stage();
        BasicActor background = new BasicActor();
        background.setTexture(new Texture(Gdx.files.internal("purple.png")));
        userInterface.addActor(background);
        
        BasicActor textTitle = new BasicActor();
        textTitle.setTexture(new Texture(Gdx.files.internal("title.png")));
        textTitle.setPosition(30,310);
        userInterface.addActor(textTitle);
        
        BitmapFont font = new BitmapFont();
        String text = "Press Enter to Start, Esc to close";

        LabelStyle stylish = new LabelStyle(font, Color.CORAL);
        Label instructions = new Label(text, stylish);
        instructions.setFontScale(2);
        instructions.setPosition(20, 50);
        userInterface.addActor(instructions);
       actions(instructions);
    }

    /**
     * color blinking for menu
     * @param actions 
     */
    public void actions(Label a) {
        a.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(1, 1, 0, 1), 0.5f),
                                Actions.delay(0.5f),
                                Actions.color(new Color(0.5f, 0.5f, 0, 1), 0.5f))
                )
        );
    }
}
