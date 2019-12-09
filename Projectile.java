import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;


public class Projectile {
	private boolean active = false;
	private int x, y;
	private boolean alive;
	private int width, height;
	private Image img;
	private int vx, vy = 10;
	private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);

	public Projectile(String fileName) {
		x = 200;
		y = 200;
		vx = 0;
		vx = 0;
		width = 10;
		height = 10;

		//do not touch
		img = getImage(fileName);
		updateProjectile();
	}

	public Projectile(String fileName, int x, int y, int vx, int vy) {
		this(fileName);
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;

		//do not touch
		img = getImage(fileName);
		updateProjectile();
	}

	public void paint(Graphics g) {
		if(this.active){
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(img, tx, null);

			y+=vy;
			updateProjectile();
		}
	}

	private void updateProjectile() {
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
	
	// checks if colliding with given ship
	public boolean collided_ship(Ship s){
		
		//Check if projectile is at least active
		if(!this.active) {
			return false;
		}
		
		Rectangle ship = new Rectangle(s.getX(), s.getY(), 109, 100);
		Rectangle proj = new Rectangle(x + 40, y, 20, 100);
		
		return proj.intersects(ship);
	}
	
	// checks if colliding with given enemy
	public boolean collided_enemy(Enemy e){
		//Check if projectile is at least active
		if(!this.active) {
			return false;
		}
		
		
		Rectangle enemy = new Rectangle(e.getX(), e.getY(), 100, 100);
		Rectangle proj = new Rectangle(x + 40, y, 20, 100);
			
		return proj.intersects(enemy);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		updateProjectile();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		updateProjectile();
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

	public void setImg(String path) {
		this.img = getImage(path);
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public AffineTransform getTx() {
		return tx;
	}

	public void setTx(AffineTransform tx) {
		this.tx = tx;
	}

}