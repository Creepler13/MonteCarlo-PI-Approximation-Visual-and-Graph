package pi;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
*
*
* Graph work in progress
*	uses JBackgroundPanel by JustAnotherJavaProgrammer
*
*/

/**
 * Class to estimate PI with the MonteCarlo method with visual and non visual
 * process
 * 
 * 
 * @author Creepler13
 */
public class MonteCarlo {

	private int points = 0;
	private int pointsDone = 0;
	private int pointsInCircle = 0;

	/**
	 *
	 * Initialize the process with how many points will be randomizingly placed on
	 * the Square. not using the will result in starting the Simulations and
	 * Calculations with 0 points
	 * 
	 * @param i
	 *            number of points
	 */

	public void init(int i) {
		points = i;
	}

	public void doVisual(boolean bool) {
		doVisual = bool;
	}

	private Random rnd = new Random();

	public double calculate() {

		if (doVisual) {
			initV();
		}

		for (int i = 0; i < points; i++) {
			double x = rnd.nextDouble() * 100;
			double y = rnd.nextDouble() * 100;
			pointsDone++;
			// double dist = (x*x+y*y);
			double dist = Math.sqrt(Math.pow(x - (100 / 2), 2) + Math.pow(y - (100 / 2), 2));
			boolean boolTemp = false;
			if (dist > 50) {
				boolTemp = true;
			} else {
				pointsInCircle++;
				boolTemp = false;
			}
			if (doVisual) {
				if (pointsInCircle != 0 || (pointsDone - pointsInCircle) != 0) {
					piA = ((double) pointsInCircle / (double) pointsDone) * 4;
				}
				update(x * 1, y * 1, boolTemp);
				try {
					Thread.sleep(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		return piA * 4;
	}

	private boolean doVisual = false;
	private int scale = 5;
	private int h = 201;
	private int w = 201;
	private int timeout = 0;
	private int graphDraw = 1000;
	private int graphDrawCounter = 0;
	private ArrayList<Double> list = new ArrayList<>();
	private boolean doGraph = true;

	/**
	 * 
	 * Set the Timeout between each point in the visualCalculate
	 * 
	 * @param ms
	 *            Integer time in ms
	 */
	public void setTimeout(int ms) {
		timeout = ms;
	}

	/**
	 * 
	 * Set the Scale of the visualCalculate window
	 * 
	 * @param Scale
	 *            Integer default : 5 (size 1000x1000 pixels)
	 */
	public void setScale(int Scale) {
		scale = Scale;
	}

	/**
	 * 
	 * Set the number of points between each graph updates in the visualCalculate
	 * Important each graph point is saved in a array.
	 * 
	 * @param pointsPerUpdate
	 *            Integer number of points between each graph updates
	 */
	public void setPointsPerGraphUpdate(int pointsPerUpdate) {
		graphDraw = pointsPerUpdate;
	}

	/**
	 * 
	 * Activate or deactivate the Graph in the visualCalculate window
	 * 
	 * @param bool
	 *            boolean Default : True
	 */

	public void doGraph(boolean bool) {
		doGraph = bool;
	}

	/**
	 * 
	 * Uses a Visual representation of the MonteCarlo method to estimate PI with
	 * Graph if not Disabled
	 * 
	 * @return returns the estimated PI after randomizing all the points
	 */

	private JBackgroundPanel panel = null;
	private Graphics g = null;
	private BufferedImage i = null;
	private JFrame frame = null;

	public void initV() {
		frame = new JFrame("Visual MonteCarlo PI Ann�herung");
		frame.setVisible(true);
		frame.setBounds(0, 0, w * scale, h * scale);

		panel = new JBackgroundPanel();
		i = new BufferedImage(w * scale, h * scale, BufferedImage.TYPE_INT_ARGB);

		g = i.createGraphics();
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.fillRect(0, 0, w * scale, h * scale);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 100 * scale, 100 * scale);
		g.drawOval(0, 0, 100 * scale, 100 * scale);

		panel.setBackground(i);
		frame.add(panel);
	}

	private double piA = 0;

	public void update(double xd, double yd, boolean inorout) {

		int x = (int) (xd * scale);
		int y = (int) (yd * scale);
		if (inorout) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		g.drawRect(x, y, 1, 1);

		g.setColor(Color.WHITE);
		g.fillRect(100 * scale + 1, 0, 100 * scale, 100 * scale);
		g.setColor(Color.BLACK);
		g.drawString("Points: " + pointsDone, 100 * scale + 10 * scale, 10 * scale);
		g.drawString("Points left: " + (points - pointsDone), 100 * scale + 10 * scale, 20 * scale);
		g.drawString("Points in Circle: " + pointsInCircle, 100 * scale + 10 * scale, 30 * scale);
		g.drawString("Points outside Circle: " + (pointsDone + 1 - pointsInCircle), 100 * scale + 10 * scale,
				40 * scale);
		g.drawString("Current estimate: " + piA, 100 * scale + 10 * scale, 50 * scale);

		if (doGraph) {
			graphDrawCounter++;
			if (graphDrawCounter == graphDraw) {
				list.add(piA);
				graphDrawCounter = 0;

				int lineLegnth = w * scale / list.size();
				int oldY = h * scale;
				int oldX = 0;
				g.setColor(Color.WHITE);
				g.fillRect(0, 101 * scale, w * scale, 100 * scale);
				g.setColor(Color.BLACK);
				g.drawLine(0, (int) (h * scale - (Math.PI * 10)) - 50 * scale, (int) (w * scale),
						(int) (h * scale - (Math.PI * 10)) - 50 * scale);
				g.setColor(Color.BLUE);
				for (int k = 0; k < list.size(); k++) {

					g.drawLine(oldX, oldY - 50 * scale, (oldX + lineLegnth),
							(int) (h * scale - (list.get(k) * 10)) - 50 * scale);

					oldX = (oldX + lineLegnth);
					oldY = (int) (h * scale - (list.get(k) * 10));
				}

			}
		}

		panel.setBackground(i);
	}

	/**
	 * 
	 * @author JustAnotherJavaProgrammer
	 *
	 */

	class JBackgroundPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private Image background = null;
		public static final int FULL_IMAGE = 0;
		public static final int FILL_PANEL = 1;
		public static final int DISTORT_IMAGE = 2;
		private int backgroundType = FULL_IMAGE;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (background != null) {
				drawImage(background, g);
			}
		}

		public void keyPressed(int keyCode) {
		}

		public void keyReleased(int keyCode) {
		}

		private void drawImage(Image image, Graphics g) {
			double scale;
			switch (backgroundType) {
			case 0:
				scale = (double) (this.getHeight()) / (double) (image.getHeight(null));
				if (image.getWidth(null) * scale > getWidth()) {
					scale = (double) (this.getWidth()) / (double) (image.getWidth(null));
				}

				g.drawImage(image, (int) ((getWidth() - (image.getWidth(null) * scale)) / 2),
						(int) ((getHeight() - (image.getHeight(null) * scale)) / 2),
						(int) (image.getWidth(null) * scale), (int) (image.getHeight(null) * scale), getBackground(),
						null);
				break;
			case 1:
				scale = (double) (this.getHeight()) / (double) (image.getHeight(null));
				if (image.getWidth(null) * scale < getWidth()) {
					scale = (double) (this.getWidth()) / (double) (image.getWidth(null));
				}
				g.drawImage(image, (int) ((getWidth() - (image.getWidth(null) * scale)) / 2),
						(int) ((getHeight() - (image.getHeight(null) * scale)) / 2),
						(int) (image.getWidth(null) * scale), (int) (image.getHeight(null) * scale), getBackground(),
						null);
				break;
			default:
				g.drawImage(image, 0, 0, getWidth(), getHeight(), getBackground(), null);
				break;
			}
		}

		public void setBackground(Image backgroundImage) {
			background = backgroundImage;
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					repaint();
				}
			});
		}

		public Image getBackgroundImage() {
			return background;
		}

		public void setBackgroundType(int backgroundType) {
			if (this.backgroundType != backgroundType) {
				this.backgroundType = backgroundType;
				repaint();
			}
		}

	}

}
