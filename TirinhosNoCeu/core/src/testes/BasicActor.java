/*
 *  actor class for the scene objects
 *  used for collisions
 * Version alpha - 1.8.0
 */
package testes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import java.util.ArrayList;

/**
 *
 * @author MarcoSilva
 */
public class BasicActor extends Actor {

    // ====== object properties =====
    private TextureRegion texture;
    private Polygon boundariesOfPolygon;
    private ArrayList<? extends BasicActor> typeOfList;

    //constructor with Actor methods
    public BasicActor() {
        super();
        texture = new TextureRegion();
        boundariesOfPolygon = null;
        typeOfList = null;
    }

    /**
     * sets the actor texture
     *
     * @param texture
     */
    public void setTexture(Texture texture) {
        int width = texture.getWidth();
        int height = texture.getHeight();
        setWidth(width);
        setHeight(height);
        this.texture.setRegion(texture);
    }

    /**
     * makes the actor act at the stage
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    /**
     * draws the texture at the given position
     *
     * @param batch
     * @param alpha
     */
    @Override
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a); //RGB, Alpha

        if (isVisible()) {
            batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    /**
     * sets the boundaries of the polygon, shaping it like a rectangle coordinates of the rectangle are: top left (0,height) , top right (width,height) bottom left (0,0), bottom right (width,0) the float vertices takes care of the values and then we apply these to the origin of our polygon
     */
    public void setRectangleBoundary() {
        float width = getWidth();
        float height = getHeight();
        float[] vertices = {0, 0, width, 0, width, height, 0, height};
        boundariesOfPolygon = new Polygon(vertices);
        boundariesOfPolygon.setOrigin(getOriginX(), getOriginY());
    }

    /**
     * initializes a polygon with the shape of an ellipse To calculate the coordinates some trigonometric functions are required. we use sine and cosine to make this ellipse (or a circle if needed)
     *
     */
    public void setEllipseBoundary() {
        int numberOfVertices = 8;
        float width = getWidth();
        float height = getHeight();
        float[] vertices = new float[2 * numberOfVertices];

        /**
         * radians are used inside a loop to calculate the spaces for the vertices equally through the ellipse. These are calculated with an interval of [0,6.28] pi which is roughly 360 degrees
         *
         */
        float radians;

        for (int i = 0; i < numberOfVertices; i++) {
            radians = i * 6.28f / numberOfVertices;

            vertices[2 * i] = width / 2 * MathUtils.cos(radians) + width / 2;
            vertices[2 * i + 1] = height / 2 * MathUtils.sin(radians) + height / 2;
        }

        boundariesOfPolygon = new Polygon(vertices);
        boundariesOfPolygon.setOrigin(getOriginX(), getOriginY());
    }

    /**
     * returns the polygon with the respective positions
     *
     * @return
     */
    public Polygon getBoundingPolygon() {
        boundariesOfPolygon.setPosition(getX(), getY());
        boundariesOfPolygon.setRotation(getRotation());
        return boundariesOfPolygon;
    }

    /**
     * Verifies if 2 polygons overlap with each other. if objOverlap is true , the overlapping object is not solid. if it's false, we can consider the object solid, like a wall for example. then it translates the actor until they stop colliding/overlap with each other
     *
     * @param extraActor
     * @param objOverlap
     * @return
     */
    public boolean overlap(BasicActor extraActor, boolean objOverlap) {
        Polygon actualActor = this.getBoundingPolygon();
        Polygon otherActor = extraActor.getBoundingPolygon();

        //if there is no overlap
        if (!actualActor.getBoundingRectangle().overlaps(otherActor.getBoundingRectangle())) {
            return false;
        }

        /**
         * checks if the polygons defined by the vertex arrays overlap. If they do we get a Minimum TVector (translate) that indicates the min. magnitude required to push the polygon out of that collision
         */
        Intersector.MinimumTranslationVector translate = new Intersector.MinimumTranslationVector();
        boolean overlapingActors = Intersector.overlapConvexPolygons(actualActor, otherActor, translate);

        if (overlapingActors && objOverlap) {
            this.moveBy(translate.normal.x * translate.depth, translate.normal.y * translate.depth);
        }

        float differenceAtDepth = 0.5f;
        return (overlapingActors && (translate.depth > differenceAtDepth));
    }

    /**
     * used to copy the data from the actor to another actor
     *
     * @param original
     */
    public void copy(BasicActor original) {
        this.texture.setTexture(original.texture.getTexture());

        //this.texture = this.texture;
        //if the passed actor has information, copies it into the actual object
        if (this.boundariesOfPolygon != null) {
            this.boundariesOfPolygon = new Polygon(original.boundariesOfPolygon.getVertices());
            this.boundariesOfPolygon.setOrigin(original.getOriginX(), original.getOriginY());
        }

        //updates all positions, color and visibility info of the object copied
        this.setPosition(original.getX(), original.getY());
        this.setOriginX(original.getOriginX());
        this.setOriginY(original.getOriginY());

        this.setWidth(original.getWidth());
        this.setHeight(original.getHeight());

        this.setColor(original.getColor());
        this.setVisible(original.isVisible());
    }

    /**
     * reference to the type of list the actor was added to.
     *
     * @param typeOf
     */
    public void setTypeOfList(ArrayList<? extends BasicActor> typeOf) {
        typeOfList = typeOf;
    }

    /**
     * Clones the actual object
     *
     * @return BasicActor
     */
    public BasicActor cloned() {
        BasicActor theCopy = new BasicActor();
        theCopy.copy(this);
        return theCopy;
    }

    /**
     * removes the element from the stage
     */
    public void destroy() {
        remove();
        if (typeOfList != null) {
            typeOfList.remove(this);
        }

    }

    /**
     * centers the object to the origin of the texture/position
     *
     * @param t
     */
    public void centerOrigin(BasicActor t) {
        this.setPosition(t.getX() + t.getOriginX() - this.getOriginX(),
                t.getY() + t.getOriginY() - this.getOriginY());
    }

}
