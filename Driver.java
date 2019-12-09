import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.image.*;
import java.awt.geom.AffineTransform;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Driver extends JPanel implements ActionListener, KeyListener,
		MouseListener, MouseMotionListener {
	public static int life = 3;

	int screen_width = 600;
	int screen_height = 800;


	String src = new File("").getAbsolutePath() + "/src/"; // path to image
	Clip hop;
	Ship s = new Ship("Assets/Player.png", 0, 0);
	Background frame_one = new Background("Assets/Background.png");
	Background frame_two = new Background("Assets/Background.png", 0, -screen_height); //+5 because of small gap between backgrounds
	

	//Array holding enemy ships
	Enemy[] enemies = new Enemy[10];


	//mouse variables
	//Mouse's last coordinates before leaving
	int x_leave = 0;
	int y_leave = 0;

	//Mouse's entering coordinates
	int x_enter = 0;
	int y_enter = 0;

	// clip.open(audioInputStream);
	Sequencer sequencer;

	// Background bg;
	int my_variable = 0; // example
	String lost = "";

	public void paint(Graphics g) {

		super.paintComponent(g);

		g.setFont(font);
		g.setColor(Color.RED);
		// g.drawString(("my_variable:") + Integer.toString(my_variable), 0,
		// 870);
		g.setFont(font2);
		g.setColor(Color.CYAN);


		frame_one.paint(g);
		frame_two.paint(g);
		s.paint(g);

		for(int i = 0; i < enemies.length; i++){
			if(enemies[i].isAlive()) {
				enemies[i].paint(g);
			}
		}

		//Check for win situation
		int last_enemy = 800;
		for(int i = 0; i < enemies.length; i++) {
			if(last_enemy >= enemies[i].getY()) {
				last_enemy = enemies[i].getY();
			}
		}
		if(s.getY() <= last_enemy) {
			g.drawString("YOU WON", 400, 400);
			System.exit(-1);
		}

	}

	Font font = new Font("Courier New", 1, 50);
	Font font2 = new Font("Courier New", 1, 30);

	//
	public void update() {

		//Background moving	
		//Moving one pixel down every time update is called
		frame_one.setY(frame_one.getY() + 1);
		frame_two.setY(frame_two.getY() + 1);
		//When frame_one goes off screen, move it to top
		if(frame_one.getY() >= screen_height){
			frame_one.setY(-screen_height);
		}
		//When frame_two goes off screen, move it to top
		if(frame_two.getY() >= screen_height){
			frame_two.setY(-screen_height);
		}

		//Enemies moving
		for(int i = 0; i < enemies.length; i++){
			enemies[i].enemy_move();
		}
		
		//Enemy projectiles collision with ship
		for(int i = 0; i < enemies.length; i++) {
			if(enemies[i].collided(s)) {
				System.exit(-1);
			}
		}
		
		//Ship projectile collision with enemies
		for(int i = 0; i < enemies.length; i++) {
			if(s.collided(enemies[i])) {
				enemies[i].setAlive(false);
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}

	public static void main(String[] arg) {
		
		//START WINDOW
		//Main window
		JFrame start = new JFrame("Galaga Was Alone");
		start.setSize(300, 300);
		start.setResizable(false);
		start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE      );
		
		//Buttons
		//Button 1
		JButton level_one = new JButton("Level 1");
		level_one.addMouseListener(new Listener());
		start.getContentPane().add(level_one, BorderLayout.WEST);		
		
		//Button 2
		JButton level_two = new JButton("Level 2");
		level_two.addMouseListener(new Listener());
		start.getContentPane().add(level_two, BorderLayout.CENTER);
		
		//Button 2
		JButton level_three = new JButton("Level 3");
		level_three.addMouseListener(new Listener());
		start.getContentPane().add(level_three, BorderLayout.EAST);
		
		
		start.setVisible(true);
		
		//Driver d = new Driver();
	}

	public Driver() {
		JFrame f = new JFrame();
		f.setTitle("Galaga Was Alone");
		f.setSize(screen_width, screen_height);
		f.setResizable(false);
		f.addKeyListener(this);
		f.addMouseListener(this);
		f.addMouseMotionListener(this);

		//Instantiating all the enemies
		for(int i = 0; i < enemies.length; i++){
			enemies[i] = new Enemy("Assets/Enemy.png");
			int rand_x = (int) (Math.random() * (screen_width-100 + 1)) + 0;
			int rand_y = (int) (Math.random() * 10000 + 1) + -10000;
			enemies[i].setX(rand_x);
			enemies[i].setY(rand_y);
			enemies[i].setVy(5);
			enemies[i].setProjectileImg("Assets/Orange_Line.png");
		}

		// Obtains the default Sequencer connected to a default device.
		try {
			sequencer = MidiSystem.getSequencer();
			// Opens the device, indicating that it should now acquire any
			// system resources it requires and become operational.
			sequencer.open();

			// create a stream from a file

			InputStream is = new BufferedInputStream(new FileInputStream(
					new File("./src/Assets/Thelazysong.mid").getAbsoluteFile()));

			// Sets the current sequence on which the sequencer operates.
			// The stream must point to MIDI file data.
			sequencer.setSequence(is);

			// Starts playback of the MIDI data in the currently loaded
			// sequence.


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// player.addMouseListener(this);
		// bg = new Background("background.png");
		// do not add to frame, call paint on
		// these objects in paint method

		f.add(this);
		t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyCode());

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		x_enter = e.getX();
		y_enter = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		x_leave = e.getX();
		y_leave = e.getY();
	}

	public void reset() {

	}

	boolean on = false;
	@Override
	public void mousePressed(MouseEvent e) {
		//invoke the shoot() method
		s.fire();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!(((Math.abs(x_enter - x_leave) >= 200) || (Math.abs(y_enter - y_leave) >= 400)))){
			s.setX(arg0.getX() - 50);
			s.setY(arg0.getY() - 75);
		}
	}

}

class Listener implements MouseListener{
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Driver d = new Driver();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}