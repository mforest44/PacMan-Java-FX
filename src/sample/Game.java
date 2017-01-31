package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Game extends Application {

    Random rnd = new Random();

    Pane playfieldLayer;

    Image playerImage;
    Image enemyImage, enemyImage2, enemyImage3, enemyImage4;
    Image BG_Maze;
    
    MySounds mySounds;
    boolean respawn = false, respawn2 = false, respawn3 = false, respawn4 = false;
    protected ArrayList<Rectangle> r;
    protected Pacman player;
    protected ArrayList<Ghost> enemies = new ArrayList<>();

    ArrayList<Circle> dots;
	ArrayList<Circle> bigDots;
    int score = 0;
    Label scoreLabel;
    Label gameOver;
    boolean collision = false;
    boolean collision2 = false;
    Group root = new Group();
    Scene scene;
    Ghost ghost1,ghost2,ghost3,ghost4;
    ArrayList<Rectangle> lives;
    
    
    @Override
    public void start(Stage primaryStage) {

        playfieldLayer = new Pane();
        gameOver = new Label();
        
        scoreLabel = new Label("Score: " + Integer.toString(score));
        scoreLabel.setLayoutX(350);
        scoreLabel.setLayoutY(25);
        scoreLabel.setScaleX(2);
        scoreLabel.setScaleY(2);
        scoreLabel.setTextFill(Color.YELLOW);
        

        // Modification possible de la taille d'affichage dans Settings
       BG_Maze = new Image ("images/Pac-ManMaze_448x576.png");
       ImageView imageView = new ImageView (BG_Maze);
		playfieldLayer.getChildren().add(imageView);
       root.getChildren().add(playfieldLayer);
       root.getChildren().add(scoreLabel);
       root.getChildren().add(gameOver);
        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setWidth(464);
        primaryStage.setHeight(614);
        primaryStage.show();

        mySounds = new MySounds();
        mySounds.playClip(1);
       
        drawRectangles();
        drawDots();
        loadGame(); 
        updateLoc();
        moveGhosts(ghost1, ghost2,ghost3, ghost4);
      

        // Boucle principale.
        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
				changeSpot();
                player.processInput();
                checkGhosts();
                player.move();
                boxCollide();
                dotCollide();
                checkCollisions();
                player.updateUI();
                drawScore();
                checkGameOver();
            }
        };	
        gameLoop.start();
        
    }

    //Détection collision avec murs
    public void checkGhosts() {
    	for( Rectangle r: r) {
			for( Ghost enemy: enemies) {
				if( enemy.collidesWith(r)) {
					enemy.collision2 = true;
				}
			}
		}
        moveGhostsCollides();
    }

    // Mouvement ghosts lors d'une collision avec un mur
    public void moveGhostsCollides() {
        for( Ghost enemy: enemies) {
            if( enemy.collision2) {
                enemy.freeze();
                enemy.dir = (int)(Math.random()*4);
                enemy.collision2 = false;
            }
        }
    }

    public void changeSpot() {
    	if (player.x >= 424 && (player.y >= 260 && player.y <= 297)) {
    		player.x = 24;
    		player.y = 270;
    	}
    	else if(player.x <= 24 && (player.y >= 260 && player.y <= 297)) {
    		player.x = 424;
    		player.y = 272;
    	}
    }
    
    public void checkGameOver() {
        gameOver = new Label();
        gameOver.setLayoutX(190);
        gameOver.setLayoutY(150);
        gameOver.setScaleX(2);
        gameOver.setScaleY(2);
        gameOver.setTextFill(Color.RED);
        
    	if (dots.size() == 0 && bigDots.size() == 0) {
    		gameOver.setText("WINNER");
    		player.freeze();
    		player.removeFromLayer();
    		for (Ghost ghost : enemies) {
    			ghost.removeFromLayer();
    		}
    		root.getChildren().add(gameOver);
    	}
    }
    
    public void drawRectangles() {
    	
    	lives = new ArrayList<Rectangle>();
    	lives.add(new Rectangle(36,548,24,24));
    	lives.add(new Rectangle(69,547,23,25));
    	for(Rectangle life : lives) {
    		life.setFill(Color.TRANSPARENT);
    	}
    	
    	r = new ArrayList<Rectangle>();

    	r.add(new Rectangle(40, 88, 48, 32)); //1
    	r.add(new Rectangle(120, 88, 64, 32)); //2
    	r.add(new Rectangle(264, 88, 64, 32)); //3
    	r.add(new Rectangle(360, 88, 48, 32)); //4
    	r.add(new Rectangle(40, 152, 48, 16)); //5
    	r.add(new Rectangle(360, 152, 48, 16)); //6
    	r.add(new Rectangle(120, 152, 16, 112)); //7
    	r.add(new Rectangle(168, 152, 112, 16)); //8
    	r.add(new Rectangle(312, 152, 16, 112)); //9
    	r.add(new Rectangle(264, 200, 50, 16)); //10
    	r.add(new Rectangle(134, 200, 50, 16)); //11
    	r.add(new Rectangle(216, 166, 16, 50)); //12

    	r.add(new Rectangle(168, 248, 40, 8)); //13
    	r.add(new Rectangle(240, 248, 40, 8)); //14
    	r.add(new Rectangle(168, 256, 8, 48)); //15
    	r.add(new Rectangle(272, 256, 8, 48)); //16
    	r.add(new Rectangle(168, 304, 112, 8)); //17

    	r.add(new Rectangle(120, 296, 16, 64)); //18
    	r.add(new Rectangle(312, 296, 16, 64)); //19
    	r.add(new Rectangle(168, 344, 112, 16)); //20
    	r.add(new Rectangle(216, 360, 16, 48)); //21
    	r.add(new Rectangle(120, 392, 64, 16)); //22
    	r.add(new Rectangle(264, 392, 64, 16)); //23
    	r.add(new Rectangle(40, 392, 48, 16)); //24
    	r.add(new Rectangle(72, 408, 16, 48)); //25
    	r.add(new Rectangle(360, 392, 48, 16)); //26
    	r.add(new Rectangle(360, 408, 16, 48)); //27
    	r.add(new Rectangle(168, 440, 112, 16)); //28
    	r.add(new Rectangle(216, 456, 16, 48)); //29
    	r.add(new Rectangle(120, 440, 16, 48)); //30
    	r.add(new Rectangle(40, 488, 144, 16)); //31
    	r.add(new Rectangle(312, 440, 16, 48)); //32
    	r.add(new Rectangle(264, 488, 144, 16)); //33

    	r.add(new Rectangle(0, 48, 448, 8)); //34
    	r.add(new Rectangle(216, 56, 16, 64)); //35
    	r.add(new Rectangle(0, 56, 8, 144)); //36
    	r.add(new Rectangle(440, 56, 8, 144)); //37

    	r.add(new Rectangle(0, 200, 88, 64)); //38
    	r.add(new Rectangle(360, 200, 88, 64)); //39
    	r.add(new Rectangle(0, 296, 88, 64)); //40
    	r.add(new Rectangle(360, 296, 88, 64)); //41

    	r.add(new Rectangle(0, 360, 8, 176)); //42
    	r.add(new Rectangle(8, 440, 32, 16)); //43
    	r.add(new Rectangle(408, 440, 32, 16)); //44
    	r.add(new Rectangle(440, 360, 8, 176)); //45
    	r.add(new Rectangle(0, 536, 448, 8)); //46
		for(Rectangle block : r){
			block.setFill(Color.TRANSPARENT);;
			block.setStroke(Color.TRANSPARENT);
		}
		playfieldLayer.getChildren().addAll(r);
		playfieldLayer.getChildren().addAll(lives);
    }

    private void boxCollide() { 
    	collision = false;
    	for ( Rectangle block : r) { 
    		if(player.collidesWith(block)){
    			collision = true;
    			player.freeze();
    		}
    	}
    }
   
    
    Circle temp;
    Circle temp2;
    boolean hollow = false;
  
    //Check si Pacman mange des points
    private void dotCollide() {
    	
    	collision = false;
    	collision2 = false;
    	
    	for (Circle dot : dots) {
    		if(player.collidesWith(dot)) {
    			collision = true;
    			temp = dot;
    			removeDot(dot);
    			score += 100;
    			mySounds.playClip(2);
    		}
    	}
    	for (Circle bigDot : bigDots) {
    		if(player.collidesWith(bigDot)) {
    			collision2 = true;
    			temp2 = bigDot;
    			removeDot(bigDot);
    			score += 500;
    			player.setSpeed(2);
    			hollow = true;
    			new Timer().schedule(
    			        new TimerTask() {
    			            @Override
    			            public void run() {
    			                player.setSpeed(1);
    			                hollow = false;
    			            }
    			        }, 
    			        5000 
    			);
    			mySounds.playClip(2);
    		}
    	}
    	if (collision2 == true) {
    		bigDots.remove(temp2);
    	}
    	if(collision == true) {
            dots.remove(temp);
        }
    }
    
    public void removeDot(Circle dot) {
    	playfieldLayer.getChildren().remove(dot);
    }
			
   
    
    private void loadGame() {
    	playerImage = new Image("Images/pacRIGHT1.png");
        enemyImage  = new Image("Images/PINK1.png" );
        enemyImage2 = new Image("Images/Orange1.png");
        enemyImage3 = new Image("Images/CYAN1.png");
        enemyImage4 = new Image("Images/RED1.png");

        Input input = new Input(scene);

        input.addListeners();

        Image image = playerImage;

        // Placement Pacman
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = 412;

        player = new Pacman(playfieldLayer, image, x, y, 0, 0, 1, input,mySounds);
        

        Image image2 = enemyImage;
        Image image3 = enemyImage2;
        Image image4 = enemyImage3;
        Image image5 = enemyImage4;
        ghost1 = new Ghost( playfieldLayer, image2, x, 280, 0, 0);
        ghost2 = new Ghost(playfieldLayer, image3, x + 20, 280, 0, 0);
        ghost3 = new Ghost(playfieldLayer,image4, x - 20, 280, 0,0);
        ghost4 = new Ghost(playfieldLayer, image5, x, 260, 0,0);
        Animation animatePac = new SpriteAnimation(
        		player.imageView, Duration.millis(10000),player,
				ghost4.imageView, ghost4,
				ghost2.imageView, ghost2,
				ghost1.imageView, ghost1,
				ghost3.imageView, ghost3);
        animatePac.setCycleCount(Animation.INDEFINITE);
        animatePac.play();

        enemies.add(ghost1);
        enemies.add(ghost2);
        enemies.add(ghost3);
        enemies.add(ghost4);

       moveGhosts(ghost1,ghost2,ghost3,ghost4);
       counter[0] = 0;
    }

	
	ArrayList<int []> locations = new ArrayList<int []>();
	ArrayList<int []> locations2 = new ArrayList<int []>();
	ArrayList<int []> locations3 = new ArrayList<int []>();
	ArrayList<int []> locations4 = new ArrayList<int []>();
	
	public void updateLoc() {

	    //ghost1
		locations.add(new int[] {220,235});
		locations.add(new int[] {220,232});
		locations.add(new int[] {224,230});
		for (int i = 0; i < 15; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] - 5, 230});
		}
		for (int i = 0; i < 29; i++) {
			locations.add(new int[] {144, locations.get(locations.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 26; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] - 5, 375});
		}
		for (int i = 0; i < 8; i++) {
			locations.add(new int[] {14, locations.get(locations.size() - 1)[1] + 5});	
		}
		for (int i = 0; i < 6; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] + 5, 415});		
		}
		for (int i = 0; i < 10; i++) {
			locations.add(new int[] {44, locations.get(locations.size() - 1)[1] + 5});	
		}
		for (int i = 0; i < 10; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] + 5, 465});	
		}
		for (int i = 0; i < 80; i++) {
			locations.add(new int[] {94, locations.get(locations.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 20; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] + 5, 65});
		}
		for (int i = 0; i < 13; i++) {
			locations.add(new int[] {194, locations.get(locations.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 44; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] + 5, 130});
		}
		for (int i = 0; i < 10; i++) {
			locations.add(new int[] {414, locations.get(locations.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 16; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] - 5, 180});
		}
		for (int i = 0; i < 48; i++) {
			locations.add(new int[] {334, locations.get(locations.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 18; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] - 5, 420});
		}
		for (int i = 0; i < 10; i++) {
			locations.add(new int[] {244, locations.get(locations.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 10; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] + 5, 370});
		}
		for (int i = 0; i < 28; i++) {
			locations.add(new int[] {289, locations.get(locations.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 10; i++) {
			locations.add(new int[] {locations.get(locations.size() - 1)[0] - 5, 230});
		}


		//ghost2
		locations2.add(new int[] {220,235});
		locations2.add(new int[] {220,232});
		locations2.add(new int[] {224,230});
		for (int i = 0; i < 13; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] + 5, 230});
		}
		for (int i = 0; i < 28; i++) {
			locations2.add(new int[] {289, locations2.get(locations2.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 10; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] - 5, 370});
		}
		for (int i = 0; i < 10; i++) {
			locations2.add(new int[] {239, locations2.get(locations2.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 28; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] - 5, 420});
		}
		for (int i = 0; i < 9; i++) {
			locations2.add(new int[] {99, locations2.get(locations2.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 17; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] - 5, 465});
		}
		for (int i = 0; i < 11; i++) {
			locations2.add(new int[] {14, locations2.get(locations2.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 81; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] + 5, 520});
		}
		for (int i = 0; i < 11; i++) {
			locations2.add(new int[] {419, locations2.get(locations2.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 16; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] - 5, 465});
		}
		for (int i = 0; i < 80; i++) {
			locations2.add(new int[] {339, locations2.get(locations2.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 18; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] - 5, 65});
		}
		for (int i = 0; i < 14; i++) {
			locations2.add(new int[] {249, locations2.get(locations2.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 46; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] - 5, 135});
		}
		for (int i = 0; i < 9; i++) {
			locations2.add(new int[] {19, locations2.get(locations2.size() - 1)[1] + 5});
		}
		for (int i = 0; i <14; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] + 5, 180});
		}
		for (int i = 0; i <18; i++) {
			locations2.add(new int[] {89, locations2.get(locations2.size() - 1)[1] + 5});
		}
		for (int i = 0; i <11; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] + 5, 270});
		}
		for (int i = 0; i < 9; i++) {
			locations2.add(new int[] {144, locations2.get(locations2.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 12; i++) {
			locations2.add(new int[] {locations2.get(locations2.size() - 1)[0] + 5, 230});
		}

		//ghost3
		locations3.add(new int[] {220,232});
		locations3.add(new int[] {224,230});
		for (int i = 0; i < 3; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] + 5, 230});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {239, locations3.get(locations3.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] + 5, 180});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {289, locations3.get(locations3.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 39; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] - 5, 130});
		}
		for (int i = 0; i < 13; i++) {
			locations3.add(new int[] {94, locations3.get(locations3.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 16; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] - 5, 65});
		}
		for (int i = 0; i < 22; i++) {
			locations3.add(new int[] {14, locations3.get(locations3.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 17; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] + 5, 175});
		}
		for (int i = 0; i < 58; i++) {
			locations3.add(new int[] {99, locations3.get(locations3.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 17; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] - 5, 465});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {14, locations3.get(locations3.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 80; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] + 5, 515});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {414, locations3.get(locations3.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 15; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] - 5, 465});
		}
		for (int i = 0; i < 57; i++) {
			locations3.add(new int[] {339, locations3.get(locations3.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 16; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] + 5, 180});
		}
		for (int i = 0; i < 23; i++) {
			locations3.add(new int[] {419, locations3.get(locations3.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 35; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] - 5, 65});
		}
		for (int i = 0; i < 13; i++) {
			locations3.add(new int[] {244, locations3.get(locations3.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 20; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] - 5, 130});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {144, locations3.get(locations3.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] + 5, 180});
		}
		for (int i = 0; i < 10; i++) {
			locations3.add(new int[] {194, locations3.get(locations3.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 7; i++) {
			locations3.add(new int[] {locations3.get(locations3.size() - 1)[0] + 5, 230});
		}

		//ghost4
		locations4.add(new int[] {224,230});
		for (int i = 0; i < 7; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 230});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {189, locations4.get(locations4.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 180});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {139, locations4.get(locations4.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 40; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] + 5, 130});
		}
		for (int i = 0; i < 47; i++) {
			locations4.add(new int[] {339, locations4.get(locations4.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 20; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 365});
		}
		for (int i = 0; i < 11; i++) {
			locations4.add(new int[] {239, locations4.get(locations4.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] + 5, 420});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {289, locations4.get(locations4.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 470});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {239, locations4.get(locations4.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 520});
		}
		for (int i = 0; i < 12; i++) {
			locations4.add(new int[] {189, locations4.get(locations4.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 460});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {139, locations4.get(locations4.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 410});
		}
		for (int i = 0; i < 56; i++) {
			locations4.add(new int[] {89, locations4.get(locations4.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 15; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 130});
		}
		for (int i = 0; i < 13; i++) {
			locations4.add(new int[] {14, locations4.get(locations4.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 36; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] + 5, 65});
		}
		for (int i = 0; i < 13; i++) {
			locations4.add(new int[] {194, locations4.get(locations4.size() - 1)[1] + 5});
		} 
		for (int i = 0; i < 10; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] + 5, 130});
		} 
		for (int i = 0; i < 13; i++) {
			locations4.add(new int[] {244, locations4.get(locations4.size() - 1)[1] - 5});
		} 
		for (int i = 0; i < 19; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] + 5, 65});
		}
		for (int i = 0; i < 43; i++) {
			locations4.add(new int[] {334, locations4.get(locations4.size() - 1)[1] + 5});
		}
		for (int i = 0; i < 8; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 280});
		}
		for (int i = 0; i < 11; i++) {
			locations4.add(new int[] {294, locations4.get(locations4.size() - 1)[1] - 5});
		}
		for (int i = 0; i < 9; i++) {
			locations4.add(new int[] {locations4.get(locations4.size() - 1)[0] - 5, 225});
		}
		
	}
	
	int [] counter = {0};
	int [] counter2 = {0};
	int [] counter3 = {0};
	int [] counter4 = {0};
	
	public void moveGhosts(Ghost ghost, Ghost ghost2, Ghost ghost3, Ghost ghost4) {
	
		
	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.07), ev -> {
		if (respawn) {
			counter[0] = 0;
			respawn = false;
		}
		ghost.updateUI(locations.get(counter[0])[0], locations.get(counter[0])[1]);
		counter[0] = counter[0] + 1;
		if (counter[0] == locations.size()) {
			counter[0] = 0;
			
		}
		
	}));
	timeline.setAutoReverse(true);
	timeline.setCycleCount(Animation.INDEFINITE);

	
	Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(0.07), ev -> {
		if (respawn2) {
			counter2[0] = 0;
			respawn2 = false;
		}
		ghost2.updateUI(locations2.get(counter2[0])[0], locations2.get(counter2[0])[1]);
		counter2[0] = counter2[0] + 1;
		if (counter2[0] == locations2.size()) {
			counter2[0] = 0;
			
		}
		
	}));
	timeline2.setAutoReverse(true);
	timeline2.setCycleCount(Animation.INDEFINITE);
	
	Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(0.07), ev -> {
		if (respawn3) {
			counter3[0] = 0;
			respawn3 = false;
		}
		ghost3.updateUI(locations3.get(counter3[0])[0], locations3.get(counter3[0])[1]);
		counter3[0] = counter3[0] + 1;
		if (counter3[0] == locations3.size()) {
			counter3[0] = 0;
			
		}
		
	}));
	timeline3.setAutoReverse(true);
	timeline3.setCycleCount(Animation.INDEFINITE);
	
	Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(0.07), ev -> {
		if (respawn4) {
			counter4[0] = 0;
			respawn4 = false;
		}
		ghost4.updateUI(locations4.get(counter4[0])[0], locations4.get(counter4[0])[1]);
		counter4[0] = counter4[0] + 1;
		if (counter4[0] == locations4.size()) {
			counter4[0] = 0;
			
		}
		
	}));
	timeline4.setAutoReverse(true);
	timeline4.setCycleCount(Animation.INDEFINITE);
	
	Timeline timeline8 = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
		timeline.play();
		timeline2.play();
		timeline3.play();
		timeline4.play();
		
	}));
	timeline8.play();
	
	}
  

    public void drawScore() { 
    	scoreLabel.setText("Score: " + Integer.toString(score));
    }

    int totalLife = 2; //total lives
   
    int checkIt = -1;
    
    private void checkCollisions() {
        collision = false;
        for( Ghost enemy: enemies) {
        	if (enemy == (enemies.get(0))) {
        		checkIt = 0;
        	}
        	if (enemy == (enemies.get(1))) {
        		checkIt = 1;
        	}
        	if (enemy == (enemies.get(2))) {
        		checkIt = 2;
        	}
        	if (enemy == (enemies.get(3))) {
        		checkIt = 3;
        	}
            if( player.collidesWith(enemy)) {
            	if (!hollow) {
            		respawn = true;
            		respawn2 = true;
            		respawn3 = true;
            		respawn4 = true;
            		 collision = true;
            		 player.freeze();
            		 player.x = 0;
            		 player.y = 0;
                     mySounds.playClip(4);
                     totalLife -= 1;
	                     if (totalLife == -1) {
	                    	 
	                    	 gameOver = new Label();
	                    	 gameOver.setLayoutX(190);
	                    	 gameOver.setLayoutY(150);
	                    	 gameOver.setScaleX(2);
	                    	 gameOver.setScaleY(2);
	                    	 gameOver.setTextFill(Color.RED);
	                    	 gameOver.setText("GAME OVER");
	                    	 player.freeze();
	                    	 player.removeFromLayer();
	                    	 
	                    	 for (Ghost ghost : enemies) {
	                    		 ghost.removeFromLayer();
	                    	 }
	                    	 for (Circle dot : dots) {
	                    		removeDot(dot);
	                    	 }
	                    	 for(Circle bigDot : bigDots) {
	                    		 removeDot(bigDot);
	                    	 }
	                    	 root.getChildren().add(gameOver);
	                    	 break;
	                     }
	                     lives.get(totalLife).setFill(Color.BLACK);
	                     

	                    player.updateUI(225,412);
	                    player.dx = 0;
	                    player.dy = 0;

                     break;
            	}
            	else if (hollow) {
            		collision = true;
            		enemy.updateUI(200,280);
            		if (checkIt == 0) {
            		respawn = true;
            		}
            		if(checkIt == 1) {
            			respawn2 = true;
            		}
            		if (checkIt == 2) {
            			respawn3 = true;
            		}
            		if (checkIt == 3) {
            			respawn4 = true;
            		}
                    mySounds.playClip(3);
            	}
               
            }
        }
        if (collision == true) {

        }
        
     }
    
    public void drawDots() {
    	dots = new ArrayList<Circle>();
    	bigDots = new ArrayList<Circle>();
    	
    	bigDots.add(new Circle(20,75,7,Color.YELLOW));
    	bigDots.add(new Circle(426,75,7,Color.YELLOW)); 
    	bigDots.add(new Circle(20,525,7,Color.YELLOW));
    	bigDots.add(new Circle(425,525,7,Color.YELLOW));
    	

    	dots.add(new Circle(20,95,4,Color.YELLOW));
    	dots.add(new Circle(20,115,4,Color.YELLOW));
    	dots.add(new Circle(20,135,4,Color.YELLOW));
    	dots.add(new Circle(20,155,4,Color.YELLOW));
    	dots.add(new Circle(20,180,4,Color.YELLOW));
    	dots.add(new Circle(20,375,4,Color.YELLOW));
    	dots.add(new Circle(20,395,4,Color.YELLOW));
    	dots.add(new Circle(20,415,4,Color.YELLOW));
    	dots.add(new Circle(20,470,4,Color.YELLOW));
    	dots.add(new Circle(20,490,4,Color.YELLOW));
    	dots.add(new Circle(20,507,4,Color.YELLOW));

    	dots.add(new Circle(40,75,4,Color.YELLOW));
    	dots.add(new Circle(60,75,4,Color.YELLOW));
    	dots.add(new Circle(80,75,4,Color.YELLOW));
    	dots.add(new Circle(100,75,4,Color.YELLOW));
    	dots.add(new Circle(120,75,4,Color.YELLOW));
    	dots.add(new Circle(140,75,4,Color.YELLOW));
    	dots.add(new Circle(160,75,4,Color.YELLOW));
    	dots.add(new Circle(180,75,4,Color.YELLOW));
    	dots.add(new Circle(200,75,4,Color.YELLOW));
    	dots.add(new Circle(200,95,4,Color.YELLOW));
    	dots.add(new Circle(200,115,4,Color.YELLOW));
    	dots.add(new Circle(250,95,4,Color.YELLOW));
    	dots.add(new Circle(250,115,4,Color.YELLOW));
    	dots.add(new Circle(250,75,4,Color.YELLOW));
    	dots.add(new Circle(270,75,4,Color.YELLOW));
    	dots.add(new Circle(290,75,4,Color.YELLOW));
    	dots.add(new Circle(310,75,4,Color.YELLOW));
    	dots.add(new Circle(330,75,4,Color.YELLOW));
    	dots.add(new Circle(350,75,4,Color.YELLOW));
    	dots.add(new Circle(370,75,4,Color.YELLOW));
    	dots.add(new Circle(390,75,4,Color.YELLOW));
    	dots.add(new Circle(410,75,4,Color.YELLOW));
    	dots.add(new Circle(40,75,4,Color.YELLOW));


    	dots.add(new Circle(425,95,4,Color.YELLOW));
    	dots.add(new Circle(425,95,4,Color.YELLOW));
    	dots.add(new Circle(425,115,4,Color.YELLOW));
    	dots.add(new Circle(425,135,4,Color.YELLOW));
    	dots.add(new Circle(425,155,4,Color.YELLOW));
    	dots.add(new Circle(425,175,4,Color.YELLOW));
    	dots.add(new Circle(425,375,4,Color.YELLOW));
    	dots.add(new Circle(425,395,4,Color.YELLOW));
    	dots.add(new Circle(425,470,4,Color.YELLOW));
    	dots.add(new Circle(425,490,4,Color.YELLOW));
    	dots.add(new Circle(425,507,4,Color.YELLOW));
    	
    	dots.add(new Circle(40,135,4,Color.YELLOW));
    	dots.add(new Circle(60,135,4,Color.YELLOW));
    	dots.add(new Circle(80,135,4,Color.YELLOW));
    	dots.add(new Circle(100,135,4,Color.YELLOW));
    	dots.add(new Circle(120,135,4,Color.YELLOW));
    	dots.add(new Circle(140,135,4,Color.YELLOW));
    	dots.add(new Circle(160,135,4,Color.YELLOW));
    	dots.add(new Circle(180,135,4,Color.YELLOW));
    	dots.add(new Circle(200,135,4,Color.YELLOW));
    	dots.add(new Circle(220,135,4,Color.YELLOW));
    	dots.add(new Circle(240,135,4,Color.YELLOW));
    	dots.add(new Circle(260,135,4,Color.YELLOW));
    	dots.add(new Circle(280,135,4,Color.YELLOW));
    	dots.add(new Circle(300,135,4,Color.YELLOW));
    	dots.add(new Circle(320,135,4,Color.YELLOW));
    	dots.add(new Circle(340,135,4,Color.YELLOW));
    	dots.add(new Circle(360,135,4,Color.YELLOW));
    	dots.add(new Circle(380,135,4,Color.YELLOW));
    	dots.add(new Circle(400,135,4,Color.YELLOW));
    	
    	dots.add(new Circle(40,180,4,Color.YELLOW));
    	dots.add(new Circle(60,180,4,Color.YELLOW));
    	dots.add(new Circle(80,180,4,Color.YELLOW));
    	dots.add(new Circle(100,180,4,Color.YELLOW));
    	dots.add(new Circle(100,160,4,Color.YELLOW));
    	dots.add(new Circle(155,185,4,Color.YELLOW));
    	dots.add(new Circle(175,185,4,Color.YELLOW));
    	dots.add(new Circle(195,185,4,Color.YELLOW));
    	dots.add(new Circle(155,160,4,Color.YELLOW));
    	dots.add(new Circle(295,160,4,Color.YELLOW));
    	dots.add(new Circle(255,185,4,Color.YELLOW));
    	dots.add(new Circle(275,185,4,Color.YELLOW));
    	dots.add(new Circle(295,185,4,Color.YELLOW));
    	dots.add(new Circle(340,160,4,Color.YELLOW));
    	
    	dots.add(new Circle(100,95,4,Color.YELLOW));
    	dots.add(new Circle(100,115,4,Color.YELLOW));
    	dots.add(new Circle(100,200,4,Color.YELLOW));
    	dots.add(new Circle(100,220,4,Color.YELLOW));
    	dots.add(new Circle(100,240,4,Color.YELLOW));
    	dots.add(new Circle(100,260,4,Color.YELLOW));
    	dots.add(new Circle(100,280,4,Color.YELLOW));
    	dots.add(new Circle(100,300,4,Color.YELLOW));
    	dots.add(new Circle(100,320,4,Color.YELLOW));
    	dots.add(new Circle(100,340,4,Color.YELLOW));
    	dots.add(new Circle(100,360,4,Color.YELLOW));
    	dots.add(new Circle(100,380,4,Color.YELLOW));
    	dots.add(new Circle(100,400,4,Color.YELLOW));
    	dots.add(new Circle(100,420,4,Color.YELLOW));
    	dots.add(new Circle(100,440,4,Color.YELLOW));
    	dots.add(new Circle(100,460,4,Color.YELLOW));
    	
    	dots.add(new Circle(80,470,4,Color.YELLOW));
    	dots.add(new Circle(60,470,4,Color.YELLOW));
    	dots.add(new Circle(40,470,4,Color.YELLOW));
    	dots.add(new Circle(150,450,4,Color.YELLOW));
    	dots.add(new Circle(150,470,4,Color.YELLOW));
    	dots.add(new Circle(170,470,4,Color.YELLOW));
    	dots.add(new Circle(190,470,4,Color.YELLOW));
    	dots.add(new Circle(300,450,4,Color.YELLOW));
    	dots.add(new Circle(300,470,4,Color.YELLOW));
    	dots.add(new Circle(280,470,4,Color.YELLOW));
    	dots.add(new Circle(260,470,4,Color.YELLOW));
    	dots.add(new Circle(250,490,4,Color.YELLOW));
    	dots.add(new Circle(200,490,4,Color.YELLOW));
    	
    	dots.add(new Circle(40,525,4,Color.YELLOW));
    	dots.add(new Circle(60,525,4,Color.YELLOW));
    	dots.add(new Circle(80,525,4,Color.YELLOW));
    	dots.add(new Circle(100,525,4,Color.YELLOW));
    	dots.add(new Circle(120,525,4,Color.YELLOW));
    	dots.add(new Circle(140,525,4,Color.YELLOW));
    	dots.add(new Circle(160,525,4,Color.YELLOW));
    	dots.add(new Circle(180,525,4,Color.YELLOW));
    	dots.add(new Circle(200,525,4,Color.YELLOW));
    	dots.add(new Circle(220,525,4,Color.YELLOW));
    	dots.add(new Circle(240,525,4,Color.YELLOW));
    	dots.add(new Circle(260,525,4,Color.YELLOW));
    	dots.add(new Circle(280,525,4,Color.YELLOW));
    	dots.add(new Circle(300,525,4,Color.YELLOW));
    	dots.add(new Circle(320,525,4,Color.YELLOW));
    	dots.add(new Circle(340,525,4,Color.YELLOW));
    	dots.add(new Circle(360,525,4,Color.YELLOW));
    	dots.add(new Circle(380,525,4,Color.YELLOW));
    	dots.add(new Circle(400,525,4,Color.YELLOW));
    	
    	dots.add(new Circle(120,425,4,Color.YELLOW));
    	dots.add(new Circle(140,425,4,Color.YELLOW));
    	dots.add(new Circle(160,425,4,Color.YELLOW));
    	dots.add(new Circle(180,425,4,Color.YELLOW));
    	dots.add(new Circle(200,425,4,Color.YELLOW));
    	dots.add(new Circle(240,425,4,Color.YELLOW));
    	dots.add(new Circle(260,425,4,Color.YELLOW));
    	dots.add(new Circle(280,425,4,Color.YELLOW));
    	dots.add(new Circle(300,425,4,Color.YELLOW));
    	dots.add(new Circle(320,425,4,Color.YELLOW));
    	dots.add(new Circle(340,425,4,Color.YELLOW));

    	dots.add(new Circle(390,425,4,Color.YELLOW));
    	dots.add(new Circle(410,425,4,Color.YELLOW));
    	dots.add(new Circle(425,415,4,Color.YELLOW));
    	dots.add(new Circle(390,445,4,Color.YELLOW));
    	
    	dots.add(new Circle(340,95,4,Color.YELLOW));
    	dots.add(new Circle(340,115,4,Color.YELLOW));
    	dots.add(new Circle(340,185,4,Color.YELLOW));
    	dots.add(new Circle(340,205,4,Color.YELLOW));
    	dots.add(new Circle(340,225,4,Color.YELLOW));
    	dots.add(new Circle(340,245,4,Color.YELLOW));
    	dots.add(new Circle(340,265,4,Color.YELLOW));
    	dots.add(new Circle(340,285,4,Color.YELLOW));
    	dots.add(new Circle(340,305,4,Color.YELLOW));
    	dots.add(new Circle(340,325,4,Color.YELLOW));
    	dots.add(new Circle(340,345,4,Color.YELLOW));
    	dots.add(new Circle(340,365,4,Color.YELLOW));
    	dots.add(new Circle(340,385,4,Color.YELLOW));
    	dots.add(new Circle(340,405,4,Color.YELLOW));
    	dots.add(new Circle(340,425,4,Color.YELLOW));
    	dots.add(new Circle(340,445,4,Color.YELLOW));
    	dots.add(new Circle(340,465,4,Color.YELLOW));
    	
    	dots.add(new Circle(360,470,4,Color.YELLOW));
    	dots.add(new Circle(380,470,4,Color.YELLOW));
    	dots.add(new Circle(400,470,4,Color.YELLOW));
    	
    	dots.add(new Circle(40,375,4,Color.YELLOW));
    	dots.add(new Circle(60,375,4,Color.YELLOW));
    	dots.add(new Circle(80,375,4,Color.YELLOW));

    	dots.add(new Circle(40,425,4,Color.YELLOW));
    	dots.add(new Circle(60,425,4,Color.YELLOW));
    	dots.add(new Circle(60,445,4,Color.YELLOW));
    	
    	dots.add(new Circle(405,375,4,Color.YELLOW));
    	dots.add(new Circle(385,375,4,Color.YELLOW));
    	dots.add(new Circle(365,375,4,Color.YELLOW));
    	dots.add(new Circle(310,375,4,Color.YELLOW));
    	dots.add(new Circle(290,375,4,Color.YELLOW));
    	dots.add(new Circle(270,375,4,Color.YELLOW));
    	dots.add(new Circle(250,375,4,Color.YELLOW));
    	dots.add(new Circle(250,395,4,Color.YELLOW));
    	dots.add(new Circle(200,375,4,Color.YELLOW));
    	dots.add(new Circle(200,395,4,Color.YELLOW));
    	dots.add(new Circle(180,375,4,Color.YELLOW));
    	dots.add(new Circle(160,375,4,Color.YELLOW));
    	dots.add(new Circle(140,375,4,Color.YELLOW));
    	dots.add(new Circle(120,375,4,Color.YELLOW));
    	
    	dots.add(new Circle(405,185,4,Color.YELLOW));
    	dots.add(new Circle(385,185,4,Color.YELLOW));
    	dots.add(new Circle(365,185,4,Color.YELLOW));
    	
    	
    	
    	
    	
    	
    	
    	
    	playfieldLayer.getChildren().addAll(dots);
    	playfieldLayer.getChildren().addAll(bigDots);
    	
    	
    }
    

    public static void main(String[] args) {
        launch(args);
    }
    
}