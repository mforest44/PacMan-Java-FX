package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ghost extends Sprite {

    public Ghost(Pane layer, Image image, double x, double y, double dx, double dy ) {
        super(layer, image, x, y, dx, dy);
    }
    
    public void move() {
   	   	
    	switch (dir){
    	case 0: dy = -1;
    			dx=0;
    			break;
    	case 1 :dy = 1;
    			dx=0;
    	break;
    	case 2 :dx = 1;
    			dy=0;
    	break;
    	case 3 :dx = -1;
    			dy=0;
    	break;
    	}
    	
    	super.move();

}
}