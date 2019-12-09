import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

/* class to represent a Ship object in a game */
public class Ship {

	//attributes
	private int x, y; 		//position
	private boolean alive;
	private int width, height; //size
	private Image img; 		//Image for object
	private int vx, vy; 	 //velocities
	private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);

	Projectile[] projectiles = new Projectile[10];
	//constructor - takes in filename
	public Ship(String fileName) {
		x = 200;
		y = 200;
		vx = 0;
		vy = 0;
		width = 172;
		height = 78;

		for(int i = 0; i < projectiles.length; i++){
			int speed = (int) ((Math.random() * 10) + 1);
			projectiles[i] = new Projectile("Assets/Purple_line.png");
		}

		//do not touch
		img = getImage(fileName);
		updateShip();
	}

	//2nd constructor - allows placement (location) of the object
	public Ship(String fileName, int x_param, int y_param){
		x = x_param;
		y = y_param;
		vx = 0;
		vy = 0;
		width = 172;
		height = 78;

		for(int i = 0; i < projectiles.length; i++){
			projectiles[i] = new Projectile("Assets/Purple_line.png");
		}

		//do not touch
		//helper functions to handle img drawing
		img = getImage(fileName);

		//helper function for location of image
		//if you update x and y, use updateShip()
		updateShip();
	}

	//shoot method that will activate a Projectile in the array
	public void fire(){
		for(int i = 0; i < projectiles.length; i++){
			if(projectiles[i] != null && !projectiles[i].isActive()){
				projectiles[i].setActive(true);

				//move projectiles to location of ship
				projectiles[i].setX(this.x);
				projectiles[i].setY(this.y);

				projectiles[i].setVy(-10);
				break;
			}
		}
	}

	// draw the affinetransform
	//Ship responsible for drawing projectiles as well
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);

		//drawing active projectiles for ship
		for(int i = 0; i < projectiles.length; i++){
			projectiles[i].paint(g);
		}
	}

	private void updateShip() {
		tx.setToTranslation(x, y);
	}

	// converts image to make it drawable in paint
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Ship.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

	//Set image file for projectiles
	public void setProjectileImg(String path){
		for(int i = 0; i < projectiles.length; i++){
			projectiles[i].setImg(path);
		}
	}
	
	//Check for ship's projectiles' collision with given enemy
	public boolean collided(Enemy e) {
		for(int i = 0; i < projectiles.length; i++) {
			if(projectiles[i].collided_enemy(e) && projectiles[i].isActive()) {
				projectiles[i].setActive(false);
				return true;
			}
		}
		
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		updateShip();
	}

	public void setY(int y) {
		this.y = y;
		updateShip();
	}

	public int getY(){
		return y;
	}

	public void setVx(int vx) {
		vy = 0;
		this.vx = vx;
	}

	public void setVy(int vy) {
		vx = 0;
		this.vy = vy;
	}

}
