/*
 * Class that defines the physics required to manipulate the objects
 * will extend the animation actors because some animations will be inserted after
 * Version alpha - 1.5.0
 */
package testes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author MarcoSilva
 */
public class PhysicsActor extends AnimationActors {

    //==== class properties ======
    private Vector2 velocity;
    private Vector2 acceleration;

    //max speed and reducing speed variables below
    private float maximumSpeed;
    private float deceleration;

    //to verify if it's necessary to rotate the texture with the velocity change
    private boolean angleChanged;

    //=== CONSTRUCTOR =======
    public PhysicsActor() {
        velocity = new Vector2();
        acceleration = new Vector2();
        maximumSpeed = 3000;
        deceleration = 0;
        angleChanged = false;
    }

    /**
     * sets the velocity with angle/magnitude which translates roughly in x = magnitude * cos(angle) y = magnitude * sin(angle)
     *
     * @param velocityX
     * @param velocityY
     */
    public void setVelocityXY(float velocityX, float velocityY) {
        velocity.set(velocityX, velocityY);
    }

    /**
     * adds the velocity with angle/magnitude
     *
     * @param velocityX
     * @param velocityY
     */
    public void addVelocity(float velocityX, float velocityY) {
        velocity.add(velocityX, velocityY);
    }

    /**
     * sets the velocity with angle and speed
     *
     * @param angleDegree
     * @param speed
     */
    public void setVelocityAS(float angleDegree, float speed) {
        this.velocity.x = speed * MathUtils.cosDeg(angleDegree);
        this.velocity.y = speed * MathUtils.sinDeg(angleDegree);
    }

    /**
     * acceleration set method
     *
     * @param accelX
     * @param accelY
     */
    public void setAccelerationXY(float accelX, float accelY) {
        acceleration.set(accelX, accelY);
    }

    /**
     * acceleration add method
     *
     * @param accelX
     * @param accelY
     */
    public void addAccelarationXY(float accelX, float accelY) {
        acceleration.add(accelX, accelY);
    }

    /**
     * adjust the actor accel by drifting adding extra accel to the other side
     *
     * @param ang
     * @param amm
     */
    public void addAccelerationAS(float ang, float amm) {
        acceleration.add(amm * MathUtils.cosDeg(ang), amm * MathUtils.sinDeg(ang));
    }

    /**
     * set acceleration with angle and speed
     *
     * @param angleDegree
     * @param speed
     */
    public void setAccelerationAS(float angleDegree, float speed) {
        this.acceleration.x = speed * MathUtils.cosDeg(angleDegree);
        this.acceleration.y = speed * MathUtils.sinDeg(angleDegree);
    }

    /**
     * sets the deceleration value
     *
     * @param deceleration
     */
    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    //===== speed methods =====
    public float getSpeed() {
        return velocity.len();
    }

    public void setSpeed(float speed) {
        velocity.setLength(speed);
    }

    public void setMaxSpeed(float maxSpeed) {
        maximumSpeed = maxSpeed;
    }

    public float getMotionAngle() {
        return MathUtils.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees+90;
    }

    public void setAutoAngle(boolean bool) {
        angleChanged = bool;
    }

    public void accelerateForward(float speed) {
        setAccelerationAS(getRotation(), speed);
    }

    /**
     * changes velocity according to acceleration and deltaTime decrease speed when not accelerating if actual speed is greater than maximumSpeed, keep the speed at that max value change position according to velocity and deltaTime if angle is true, rotates the actor equal to that direction motion
     *
     * @param deltaTime
     */
    @Override
    public void act(float deltaTime) {
        //super act for animation actors
        super.act(deltaTime);

        //apply accel
        velocity.add(acceleration.x * deltaTime, acceleration.y * deltaTime);

        //decreasing vel. when not getting any accel
        if (acceleration.len() < 0.01) {
            float decelAmount = deceleration * deltaTime;
            if (getSpeed() < decelAmount) {
                setSpeed(0);
            } else {
                setSpeed(getSpeed() - decelAmount);
            }
        }

        //no more speed allowed than the maximum OVERDRIVE
        if (getSpeed() > maximumSpeed) {
            setSpeed(maximumSpeed);
        }

        //adding X and Y to the current position of the element
        moveBy(velocity.x * deltaTime, velocity.y * deltaTime);

        //rotate the img here according to the speed being greater than 1
        //and the anglechanged being true
        if (angleChanged && getSpeed() > 0.1) {
            setRotation(getMotionAngle());
        }
    }
    
    /**
     * 
     * @param pa
     */
    public void copy(PhysicsActor pa) {
        super.copy(pa);
        this.velocity = new Vector2(pa.velocity);
        this.acceleration = new Vector2(pa.acceleration);
        this.maximumSpeed = pa.maximumSpeed;
        this.deceleration = pa.deceleration;
        this.angleChanged = pa.angleChanged;
    }
    
    /**
     * returns a clone of the object passed as argument
     * @return 
     */
    @Override
    public PhysicsActor cloned() {
        PhysicsActor oClone = new PhysicsActor();
        oClone.copy(this);        
        return oClone;
    }

}
