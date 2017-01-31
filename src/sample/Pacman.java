package sample;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Pacman extends Sprite {

    private Input input;
    private double speed;
    MySounds mySounds;
    boolean timer;
    Animation animation;
    protected int movement;
    
    public Pacman(Pane layer, Image image, double x, double y, double dx, double dy, double speed, Input input, MySounds ms) {

        super(layer, image, x, y,  dx, dy);

        this.speed = speed;
        this.input = input;
        mySounds = ms;
        
        
    }

    public void setSpeed(double speed) {
    	this.speed = speed;
    }

    
    public void processInput() {

        if( input.isMoveUp()) {
        	movement = 1;
        	dx = 0;
            dy = -speed;
            
        } else if( input.isMoveDown()) {
        	movement = 2;
            dy = speed;
            dx = 0;
        }
        
        if( input.isMoveLeft()) {     
        	movement = 3;
            dx = -speed;
            dy = 0;
          
        } else if( input.isMoveRight()) {
        	movement = 4;
            dx = speed;
            dy = 0;
        } 


        
    }
    
    public boolean imageTimer() {
    	new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                timer = true;
		            }
		        }, 
		        50 
		);
    	
    	return timer;
    }

    @Override
    public void move() {
        super.move();
        
        checkBounds();
    }

    private void checkBounds() {

        if( y < 0 ) {
            y = 0;
        } else if( (y+h) > Settings.SCENE_HEIGHT ) {
            y = Settings.SCENE_HEIGHT-h;
        }

        if( x < 0) {
            x = 0;
        } else if( (x+w) > Settings.SCENE_WIDTH ) {
            x = Settings.SCENE_WIDTH-w;
        }
    }

}