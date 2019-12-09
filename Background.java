import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;


public class Background {

	private Image img; //image path
	private int x; //x coordinate
	private int y; //y coordinate
	private int width = 600;
	private int height = 800;
	private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);

	public Background(String fileName) {
		x = 0;
		y = 0;

		//do not touch
		img = getImage(fileName);
		updateBackground();
	}

	public Background(String fileName, int x, int y) {
		this.x = x;
		this.y = y;

		//do not touch
		img = getImage(fileName);
		updateBackground();
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

	private void updateBackground() {
		tx.setToTranslation(x, y);
	}



	// draw the affinetransform
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		updateBackground();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		updateBackground();
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

	public AffineTransform getTx() {
		return tx;
	}

	public void setTx(AffineTransform tx) {
		this.tx = tx;
	}


}
