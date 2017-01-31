package sample;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
	
	  private ImageView imageView, imageView2, imageView3, imageView4, imageView5;
	  private int count = 0, count1 = 0, count2 = 0, count3 = 0;
	  Pacman pac;
	  Ghost ghost, ghost2, ghost3, ghost4;
	  boolean dead;
	  
	  public SpriteAnimation(ImageView imageView, Duration duration, Pacman pac, ImageView imageView2, Ghost ghost , 
			  																	 ImageView imageView3, Ghost ghost2, 
			  																	 ImageView imageView4, Ghost ghost3,
			  																	 ImageView imageView5, Ghost ghost4
			  																								)
	  {
		  
		  this.imageView = imageView;
		  this.imageView2 = imageView2;
		  this.imageView3 = imageView3;
		  this.imageView4 = imageView4;
		  this.imageView5 = imageView5;
		  
		  this.ghost = ghost;
		  this.ghost2 = this.ghost2;
		  this.ghost3 = this.ghost3;
		  this.ghost4 = this.ghost4;

		  this.dead = dead;
		  
		  this.pac = pac;
		  setCycleDuration(duration);
		  setInterpolator(Interpolator.LINEAR);
	  }

	@Override
	protected void interpolate(double arg0) {
	
		if (pac.movement == 4) {
			if (count <= 20) {
				imageView.setImage(new Image("Images/pacRIGHT1.png"));
				imageView2.setImage(new Image("Images/RED1.png"));
				imageView3.setImage(new Image("Images/ORANGE1.png"));
				imageView5.setImage(new Image("Images/CYAN1.png"));
				imageView4.setImage(new Image("Images/PINK7.png"));
			}
			else if (count >= 21) {
				imageView.setImage(new Image("Images/pacRIGHT2.png"));
				imageView2.setImage(new Image("Images/RED3.png"));
				imageView3.setImage(new Image("Images/ORANGE3.png"));
				imageView5.setImage(new Image("Images/CYAN3.png"));
				imageView4.setImage(new Image("Images/PINK3.png"));
				if (count >= 50) {
				count = 0;
				imageView2.setImage(new Image("Images/RED4.png"));
				imageView3.setImage(new Image("Images/ORANGE2.png"));
				imageView5.setImage(new Image("Images/CYAN2.png"));
				imageView4.setImage(new Image("Images/PINK2.png"));
				}
			}
			count++;
		}
		else if (pac.movement == 3) {
			if (count1 <= 20) {
				imageView.setImage(new Image("Images/pacLEFT1.png"));
				imageView2.setImage(new Image("Images/RED5.png"));
				imageView3.setImage(new Image("Images/ORANGE4.png"));
				imageView5.setImage(new Image("Images/CYAN4.png"));
				imageView4.setImage(new Image("Images/PINK1.png"));
			}
			else if (count1 >= 21) {
				imageView.setImage(new Image("Images/pacLEFT2.png"));
				imageView2.setImage(new Image("Images/RED6.png"));
				imageView3.setImage(new Image("Images/ORANGE5.png"));
				imageView5.setImage(new Image("Images/CYAN6.png"));
				imageView4.setImage(new Image("Images/PINK5.png"));
				if (count1 >= 50) {
				count1 = 0;
				}
			}
			count1++;
		}
		else if (pac.movement == 2) {
			if (count2 <= 20) {
				imageView.setImage(new Image("Images/pacDOWN1.png"));
				imageView2.setImage(new Image("Images/RED7.png"));
				imageView3.setImage(new Image("Images/ORANGE1.png"));
				imageView5.setImage(new Image("Images/CYAN7.png"));
				imageView4.setImage(new Image("Images/PINK3.png"));
			}
			else if (count2 >= 21) {
				imageView.setImage(new Image("Images/pacDOWN2.png"));
				imageView2.setImage(new Image("Images/RED8.png"));
				imageView3.setImage(new Image("Images/ORANGE3.png"));
				imageView5.setImage(new Image("Images/CYAN8.png"));
				imageView4.setImage(new Image("Images/PINK2.png"));
				if (count2 >= 50) {
					imageView3.setImage(new Image("Images/ORANGE7.png"));
					imageView5.setImage(new Image("Images/CYAN6.png"));
					imageView4.setImage(new Image("Images/PINK4.png"));
				count2 = 0;
				}
			}
			count2++;
		}
		else if (pac.movement == 1) {
			if (count3 <= 20) {
				imageView.setImage(new Image("Images/pacTOP1.png"));
				imageView2.setImage(new Image("Images/RED4.png"));
				imageView3.setImage(new Image("Images/ORANGE8.png"));
				imageView5.setImage(new Image("Images/CYAN1.png"));
				imageView4.setImage(new Image("Images/PINK7.png"));
			}
			else if (count3 >= 21) {
				imageView.setImage(new Image("Images/pacTOP2.png"));
				imageView2.setImage(new Image("Images/RED6.png"));
				imageView3.setImage(new Image("Images/ORANGE3.png"));
				imageView5.setImage(new Image("Images/CYAN3.png"));
				imageView4.setImage(new Image("Images/PINK8.png"));
				if (count3 >= 50) {
					imageView3.setImage(new Image("Images/ORANGE4.png"));
					imageView5.setImage(new Image("Images/CYAN5.png"));
					imageView4.setImage(new Image("Images/PINK3.png"));
				count3 = 0;
				}
			}
			count3++;
		}
		
	
		
		
	
		
	
	}

}
