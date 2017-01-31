package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Sprite {

    protected Image image;
    protected ImageView imageView;
    private Pane layer;
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected double w;
    protected double h;
    protected double frameWidth = 20;
    protected double frameHeight = 20;
    int dir = 0;
    protected boolean collision2 = false;

    public Sprite(Pane layer, Image image, double x, double y, double dx, double dy) {

        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);

        this.w = image.getWidth();
        this.h = image.getHeight();

        addToLayer();
    }

    public void setImageView(Image image) {
    	this.imageView = new ImageView(image);
    	this.imageView.relocate(x, y);
    }
    

    public void move() {
        x += dx;
        y += dy;
    }
    
    public void spriteMovement() {
    	x += dx;
    	y += dy;
    }
    
    public boolean collidesWithCoord(Rectangle block, double x, double y) {
		return (block.intersects(x, y, w, h)); 

}

public boolean collidesWith(Rectangle block) {
		return (block.intersects(x, y, frameWidth, frameHeight)); 

}

public boolean collidesWith(Circle dot) {
	return (dot.intersects(x, y, frameWidth, frameHeight)); 

}

public void freeze() {
		x -= dx;
		y -= dy;
	}

    public void updateUI() {
        imageView.relocate(x, y);
    }
    
    public void updateUI(double x, double y) {
    	this.x = x;
    	this.y = y;
        imageView.relocate(x, y);
    }
    


    public boolean collidesWith( Sprite otherSprite) {
        return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);
    }
    
    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }
    
}