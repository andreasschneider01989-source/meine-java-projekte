package p1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class k extends JPanel {
    // --- Logik-Variablen ---
    private double zoomFactor = 1.0;
    private double zoomCenterX = 0;
    private double zoomCenterY = 0;
    private final double G = 1500.0; // Gravitationskonstante für die Simulation
    private final double M_SONNE = 5.0; // Masse der Sonne im Zentrum
    private final List<Planet> planeten = new ArrayList<>();

    // --- Planet-Datenstruktur (Das Skelett) ---
    static class Planet {
        String name;
        double x, y, vx, vy;
        Color color;
        int size;

        Planet(String n, double x, double y, double vx, double vy, Color c, int s) {
            this.name = n; this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.color = c; this.size = s;
        }
    }

    public k() {
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(Color.BLACK);

        // --- Initialisierung des Sonnensystems ---
        // Parameter: Name, x-Start, y-Start, vx (Geschwindigkeit y), vy, Farbe, Größe
        planeten.add(new Planet("Merkur", 150, 0, 0, 7.5, Color.GRAY, 8));
        planeten.add(new Planet("Venus", 250, 0, 0, 5.8, Color.ORANGE, 12));
        planeten.add(new Planet("Erde", 400, 0, 0, 4.5, Color.BLUE, 13));
        planeten.add(new Planet("Mars", 600, 0, 0, 3.8, Color.RED, 10));

        // --- Der Game-Loop (60 FPS) ---
        Timer timer = new Timer(16, e -> updatePhysik());
        timer.start();
    }

    private void updatePhysik() {
        double dt = 0.5; // Zeitschritt
        for (Planet p : planeten) {
            double r2 = p.x * p.x + p.y * p.y;
            double r = Math.sqrt(r2);
            
            // Beschleunigung berechnen (a = G * M / r^2)
            double accel = -G * M_SONNE / (r2 * r);
            p.vx += accel * p.x * dt;
            p.vy += accel * p.y * dt;
            
            // Position updaten
            p.x += p.vx * dt;
            p.y += p.vy * dt;
        }
        repaint();
    }

    // --- Zoom & Pan Methoden ---
    public void zoomIn() { zoomFactor *= 1.2; repaint(); }
    public void zoomOut() { zoomFactor /= 1.2; repaint(); }
    public void panLeft() { zoomCenterX += 50 / zoomFactor; repaint(); }
    public void panRight() { zoomCenterX -= 50 / zoomFactor; repaint(); }
    public void panUp() { zoomCenterY += 50 / zoomFactor; repaint(); }
    public void panDown() { zoomCenterY -= 50 / zoomFactor; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sonne zeichnen
        drawObject(g2, 0, 0, 25, Color.YELLOW, "Sonne");

        // Planeten zeichnen
        for (Planet p : planeten) {
            drawObject(g2, p.x, p.y, p.size, p.color, p.name);
        }
    }

    private void drawObject(Graphics2D g2, double worldX, double worldY, int radius, Color c, String name) {
        double sx = (zoomCenterX + worldX) * zoomFactor + getWidth() / 2.0;
        double sy = (zoomCenterY + worldY) * zoomFactor + getHeight() / 2.0;
        double sr = radius * zoomFactor;

        g2.setColor(c);
        g2.fillOval((int)(sx - sr), (int)(sy - sr), (int)(sr * 2), (int)(sr * 2));
        
        if (zoomFactor > 0.5) { // Namen nur bei ausreichendem Zoom anzeigen
            g2.setColor(Color.WHITE);
            g2.drawString(name, (int)sx + 10, (int)sy);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Matrixgame: Realtime Orbit Simulation");
            k p = new k();
            f.add(p);
            
            // Key Bindings (Tastatursteuerung)
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("PLUS"), "zi");
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ADD"), "zi");
            p.getActionMap().put("zi", new AbstractAction() { public void actionPerformed(ActionEvent e) { p.zoomIn(); }});
            
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("MINUS"), "zo");
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SUBTRACT"), "zo");
            p.getActionMap().put("zo", new AbstractAction() { public void actionPerformed(ActionEvent e) { p.zoomOut(); }});
            
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "l");
            p.getActionMap().put("l", new AbstractAction() { public void actionPerformed(ActionEvent e) { p.panLeft(); }});
            
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "r");
            p.getActionMap().put("r", new AbstractAction() { public void actionPerformed(ActionEvent e) { p.panRight(); }});
            
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "u");
            p.getActionMap().put("u", new AbstractAction() { public void actionPerformed(ActionEvent e) { p.panUp(); }});
            
            p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "d");
            p.getActionMap().put("d", new AbstractAction() { public void actionPerformed(ActionEvent e) { p.panDown(); }});

            f.pack();
            f.setLocationRelativeTo(null);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}