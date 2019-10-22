/*
 * Class that defines the physics required to manipulate the objects
 * will extend the animation actors because some animations will be inserted after
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
     * @param vx
     * @param vy
     */
    public void setVelocityXY(float velocityX, float velocityY) {
        velocity.set(velocityX, velocityY);
    }

    /**
     * adds the velocity with angle/magnitude
     *
     * @param vx
     * @param vy
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
     * @param ax
     * @param ay
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
        return MathUtils.atan2(maximumSpeed, maximumSpeed);
    }
    
    public void setAutoAngle(boolean bool) {
        angleChanged = bool;
    }

    /*
    public void accelerateForward(float speed) {
        setAccelerationAS(rotationOfTextureFromAnimation, speed);
    }*/
    
    /**
     * changes velocity according to acceleration and deltaTime
     * decrease speed when not accelerating
     * if actual speed is greater than maximumSpeed, keep the speed at that max value
     * change position according to velocity and deltaTime
     * if angle is true, rotates the actor equal to that direction motion
     * @param deltaTime 
     */
    public void act(float deltaTime) {
        //super act for animation actors

        //apply accel
        velocity.add(acceleration.x * deltaTime, acceleration.y * deltaTime);

        //decreasing vel. when not speeding
        if (acceleration.len() < 0.01) {
            float decelAmount = deceleration * deltaTime;
            if (getSpeed() < decelAmount) {
                setSpeed(0);
            } else {
                setSpeed(getSpeed() - decelAmount);
            }
        }
        
        //no more speed than the maximum OVERDRIVE
        if (getSpeed() > maximumSpeed) {
            setSpeed(maximumSpeed);
        }
        
        //getting that sweet speed
        //moving the actor from animationactor class here
        //rotate the img here
        
    }
    
}
