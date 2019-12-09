import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

/* class to represent a Ship object in a game */
public class Enemy {

	//attributes
	private int x, y; 		//position
	private boolean alive = true;
	private int width, height; //size
	private Image img; 		//Image for object
	private int vx, vy; 	 //velocities
	private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);
	private int cntr = 0, rate = 200;

	Projectile[] projectiles = new Projectile[10];
	//constructor - takes in filename
	public Enemy(String fileName) {
		x = 200;
		y = 200;
		vx = 0;
		vy = 0;
		width = 172;
		height = 78;

		for(int i = 0; i < projectiles.length; i++){
			projectiles[i] = new Projectile("Assets/Orange_Line.png");
			projectiles[i].setVy(10);
		}

		//do not touch
		img = getImage(fileName);
		updateEnemy();
	}

	//2nd constructor - allows placement (location) of the object
	public Enemy(String fileName, int x_param, int y_param){
		x = x_param;
		y = y_param;
		vx = 0;
		vy = 0;
		width = 172;
		height = 78;

		for(int i = 0; i < projectiles.length; i++){
			projectiles[i] = new Projectile("Assets/Orange_Line.png");
		}

		//do not touch
		//helper functions to handle img drawing
		img = getImage(fileName);
		
		//helper function for location of image
		//if you update x and y, use updateShip()
		updateEnemy();
	}

	public void enemy_move(){
		this.y += this.vy;
		updateEnemy();
	}

	//shoot method that will activate a Projectile in the array
	public void fire(){
		for(int i = 0; i < projectiles.length; i++){
			if(projectiles[i] != null && !projectiles[i].isActive()){
				projectiles[i].setActive(true);

				//move projectiles to location of ship
				projectiles[i].setX(this.x);
				projectiles[i].setY(this.y);

				projectiles[i].setVy(projectiles[i].getVy());
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

		//enemies firing
		cntr++;
		if(cntr%rate == 0){
			this.fire();
		}

	}

	private void updateEnemy() {
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

	//Check for collision with ship
	public boolean collided(Ship s) {
		for(int i = 0; i < projectiles.length; i++) {
			if(projectiles[i].collided_ship(s)) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getX() {
		return x;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public AffineTransform getTx() {
		return tx;
	}

	public void setTx(AffineTransform tx) {
		this.tx = tx;
	}

	public int getCntr() {
		return cntr;
	}

	public void setCntr(int cntr) {
		this.cntr = cntr;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public Projectile[] getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(Projectile[] projectiles) {
		this.projectiles = projectiles;
	}

	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}

	public void setX(int x) {
		this.x = x;
		updateEnemy();
	}

	public void setY(int y) {
		this.y = y;
		updateEnemy();
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