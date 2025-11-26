package BasisEditorenMatrixgame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter; // Not used in provided code, but kept for completeness
import java.io.IOException; // Not used in provided code, but kept for completeness
import java.io.PrintWriter; // Not used in provided code, but kept for completeness
import java.util.ArrayList;
import java.util.List;
public class SchönerZoom2FormScale extends JPanel {

// --- Simulationskonstanten ---
	int FRAME_WIDTH = 1920;
	int FRAME_HEIGHT = 1080;

// --- Zoom-Variablen ---
	private double zoomFactor = 1.0;
	double zoomCenterX = 0;
	double zoomCenterY = 0;
	static final double ZOOM_STEP = 1.2;
	double feinheit = 70;
	double PAN_STEP = feinheit * zoomFactor; // Verschiebung pro Button-Klick

// --- Konstruktor --- init1

	public SchönerZoom2FormScale() {
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(Color.BLACK);
	}

// --- Pan-Methoden ---

	public void panLeft() {
		zoomCenterX += PAN_STEP;
		repaint();
	}

	public void panRight() {
		zoomCenterX -= PAN_STEP;
		repaint();
	}

	public void panUp() {
		zoomCenterY += PAN_STEP;
		repaint();
	}

	public void panDown() {
		zoomCenterY -= PAN_STEP;
		repaint();
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());

// Form 1 zeichen roter Kreis
		double radius = 10;
		Color color = new Color(255, 0, 0);
		double posX = 5;
		double posY = 100;
// Berücksichtige Pan und Zoom bei der Positionierung
		radius=radius*zoomFactor;
		double sx = (zoomCenterX + posX) * zoomFactor + getWidth() / 2.0;
		double sy = (zoomCenterY + posY) * zoomFactor + getHeight() / 2.0;
		g2.setColor(color);
		g2.fillOval((int) (sx - radius), (int) (sy - radius), (int) (radius * 2), (int) (radius * 2));

// Form 2 zeichen - Lila Quadrat
		radius = 17;
		color = new Color(255, 0, 255);
		posX = 50;
		posY = 100;
// Berücksichtige Pan und Zoom bei der Positionierung
		radius=radius*zoomFactor;
		sx = (zoomCenterX + posX) * zoomFactor + getWidth() / 2.0;
		sy = (zoomCenterY + posY) * zoomFactor + getHeight() / 2.0;
		g2.setColor(color);
		g2.fillRect((int) (sx - radius), (int) (sy - radius), (int) (radius * 2), (int) (radius * 2));
	
	
	// Form 3 zeichen - Blaues Oval
			radius = 66;
			double radius2=33;
			color = new Color(0, 0, 255);
			posX = 5000;
			posY = -100;
	// Berücksichtige Pan und Zoom bei der Positionierung
			radius=radius*zoomFactor;
			radius2=radius2*zoomFactor;
			sx = (zoomCenterX + posX) * zoomFactor + getWidth() / 2.0;
			sy = (zoomCenterY + posY) * zoomFactor + getHeight() / 2.0;
			g2.setColor(color);
			g2.fillOval((int) (sx - radius), (int) (sy - radius2), (int) (radius * 2), (int) (radius2 * 2));
		}

// Zoom-Funktionen

	public void zoomIn() {
		zoomFactor *= ZOOM_STEP;
		PAN_STEP = feinheit / zoomFactor;
		repaint();
	}

	public void zoomOut() {
		zoomFactor /= ZOOM_STEP;
		PAN_STEP = feinheit / zoomFactor;
		repaint();
	}

// --- Hauptmethode mit allen Buttons ---

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("N-Body Sonnensystem-Simulation (QDBV-Modul)");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			SchönerZoom2FormScale simPanel = new SchönerZoom2FormScale();
			frame.setLayout(new BorderLayout());
			frame.add(simPanel, BorderLayout.CENTER);

// 1. Alle Buttons erstellen
			JButton zoomIn = new JButton("Zoom In");
			JButton zoomOut = new JButton("Zoom Out");
			JButton speedUp = new JButton("Speed Up");
			JButton speedDown = new JButton("Speed Down");
			JButton panLeft = new JButton("← Links");
			JButton panRight = new JButton("Rechts →");
			JButton panUp = new JButton("↑ Hoch");
			JButton panDown = new JButton("↓ Runter");

// 2. ActionListener zuweisen
			zoomIn.addActionListener(e -> simPanel.zoomIn());
			zoomOut.addActionListener(e -> simPanel.zoomOut());
			panLeft.addActionListener(e -> simPanel.panLeft());
			panRight.addActionListener(e -> simPanel.panRight());
			panUp.addActionListener(e -> simPanel.panUp());
			panDown.addActionListener(e -> simPanel.panDown());

// 3. Buttons und Textfeld zum Control-Panel hinzufügen
			JPanel control = new JPanel();
			control.add(zoomIn);
			control.add(zoomOut);
			control.add(speedUp);
			control.add(speedDown);
			control.add(panLeft);
			control.add(panRight);
			control.add(panUp);
			control.add(panDown);
			frame.add(control, BorderLayout.NORTH);
			frame.setSize(1920, 1080); // Höherer Rahmen für Buttons
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}