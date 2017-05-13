package entities;

//import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPerson = 35;
	private float angleAroundPerson = 0;
	
    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;
    
    private Person person;
    
    public Camera(Person person){
    	this.person = person;
    }
     
    public void move(){
    	calculateZoom();
    	calculatePitch();
    	calculateAngleAroundPerson();
    	float horizontalDistance = calculateHorizontalDistance();
    	float verticalDistance = calculateVerticalDistance();
    	calculateCameraPosition(horizontalDistance, verticalDistance);
    	this.yaw = 180 - (person.getRotY() + angleAroundPerson);
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
	
    private void calculateCameraPosition(float horizDistance, float verticDistance){
    	float theta = person.getRotY() + angleAroundPerson;
    	float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
    	float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
    	position.x = person.getPosition().x - offsetX;
    	position.z = person.getPosition().z - offsetZ;
    	position.y = person.getPosition().y + verticDistance + 4;
    }
    
    private float calculateHorizontalDistance(){
    	return (float) (distanceFromPerson * Math.cos(Math.toRadians(pitch +4)));
    }
    
    private float calculateVerticalDistance(){
    	return (float) (distanceFromPerson * Math.sin(Math.toRadians(pitch +  4)));
    }
    
    private void calculateZoom(){
    	float zoomLevel = Mouse.getDWheel() * 0.03f;
    	distanceFromPerson -= zoomLevel;
        if(distanceFromPerson<5){
            distanceFromPerson = 5;
            }
    }
    
    private void calculatePitch(){
    	if(Mouse.isButtonDown(1)){
    		float pitchChange = Mouse.getDY() * 0.2f;
    		pitch -= pitchChange;
    		 if(pitch < 0){
                 pitch = 0;
             }else if(pitch > 90){
                 pitch = 90;
             }
    	}
    }
    
    private void calculateAngleAroundPerson(){
    	if(Mouse.isButtonDown(0)){
    		float angleChange = Mouse.getDX() * 0.3f;
    		angleAroundPerson -= angleChange; 
    	}
    }
}
