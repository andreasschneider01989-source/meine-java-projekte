package p1;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MATRIXGAME KERNEL v1.2 [Swarm_Intelligence_v1]
 * EIGENSCHAFTEN:
 * 1. ANTI-ENTROPIE: Objekte stoßen sich bei Überlappung sanft ab (Struktur-Findung).
 * 2. ATTENTION-VECTORS: Zeiger richten sich auf das nächstgelegene Objekt (Informationsfluss).
 * 3. LOGIK-INTERCONNECT: Export/Import für den Austausch mit anderen KIs.
 */
public class MatrixGameCore extends JPanel implements ActionListener {

    private double zoom = 1.0, offsetX = 0, offsetY = 0;
    private double timeScale = 1.0;
    private List<Entity> entities = new ArrayList<>();
    private Point lastMousePt;

    public MatrixGameCore() {
        setBackground(new Color(5, 5, 10));
        setFocusable(true);
        entities.add(new Entity(0, 0, Color.CYAN, "Kernel_Origin"));

        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) zoom *= 1.1; else zoom /= 1.1;
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { 
                lastMousePt = e.getPoint();
                if (SwingUtilities.isRightMouseButton(e)) copyAt(e.getPoint());
                else if (e.getClickCount() == 2) spawnAt(e.getPoint());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (lastMousePt != null) {
                    offsetX += (e.getX() - lastMousePt.x) / zoom;
                    offsetY += (e.getY() - lastMousePt.y) / zoom;
                    lastMousePt = e.getPoint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) exportState();
                if (e.getKeyCode() == KeyEvent.VK_L) importState();
                if (e.getKeyCode() == KeyEvent.VK_SPACE) entities.clear();
                if (e.getKeyCode() == KeyEvent.VK_1) timeScale = 0.2;
                if (e.getKeyCode() == KeyEvent.VK_2) timeScale = 1.0;
                if (e.getKeyCode() == KeyEvent.VK_3) timeScale = 4.0;
            }
        });

        new Timer(16, this).start();
    }

    private double toWorldX(int sx) { return (sx - getWidth()/2.0) / zoom - offsetX; }
    private double toWorldY(int sy) { return (sy - getHeight()/2.0) / zoom - offsetY; }

    private void spawnAt(Point p) {
        entities.add(new Entity(toWorldX(p.x), toWorldY(p.y), Color.GREEN, "Node_" + entities.size()));
    }

    private void copyAt(Point p) {
        double wx = toWorldX(p.x), wy = toWorldY(p.y);
        entities.stream().filter(e -> Math.hypot(e.x - wx, e.y - wy) < 20/zoom).findFirst()
            .ifPresent(e -> entities.add(new Entity(e.x+10, e.y+10, e.color, e.name+"_Copy")));
    }

    private void updatePhysics() {
        // --- SWARM LOGIC & ATTENTION ---
        for (Entity e : entities) {
            Entity closest = null;
            double minDist = Double.MAX_VALUE;

            for (Entity other : entities) {
                if (e == other) continue;
                double d = Math.hypot(e.x - other.x, e.y - other.y);
                
                // 1. Anti-Entropie (Abstoßung wenn zu nah)
                if (d < 30) {
                    double force = (30 - d) * 0.05 * timeScale;
                    double ang = Math.atan2(e.y - other.y, e.x - other.x);
                    e.x += Math.cos(ang) * force;
                    e.y += Math.sin(ang) * force;
                }

                // 2. Suche nächsten Nachbarn für Attention
                if (d < minDist) {
                    minDist = d;
                    closest = other;
                }
            }
            if (closest != null) e.lookAt(closest);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        updatePhysics();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(getWidth() / 2, getHeight() / 2);
        g2.scale(zoom, zoom);
        g2.translate(offsetX, offsetY);

        for (Entity ent : entities) ent.draw(g2);

        // HUD
        g2.setTransform(new java.awt.geom.AffineTransform());
        g2.setColor(Color.WHITE);
        g2.drawString("MATRIXGAME V1.2 - Swarm Intelligence Enabled", 20, 30);
        g2.drawString("LOGIK: Objekte stoßen sich ab und 'beobachten' einander.", 20, 50);
        g2.setColor(Color.YELLOW);
        g2.drawString("[S] Export | [L] Import | Space: Clear | 1-3: Speed", 20, 70);
    }

    private void exportState() {
        String data = entities.stream().map(en -> String.format("ENT;%.1f;%.1f;%d;%s", en.x, en.y, en.color.getRGB(), en.name)).collect(Collectors.joining("|"));
        System.out.println("\nSTATE_EXPORT:" + data);
    }

    private void importState() {
        String input = JOptionPane.showInputDialog(this, "Matrix-String einfügen:");
        if (input != null && input.contains("ENT")) {
            entities.clear();
            for (String part : input.split("\\|")) {
                String[] d = part.split(";");
                entities.add(new Entity(Double.parseDouble(d[1]), Double.parseDouble(d[2]), new Color(Integer.parseInt(d[3])), d[4]));
            }
        }
    }

    @Override public void actionPerformed(ActionEvent e) { repaint(); }

    public static void main(String[] args) {
        JFrame f = new JFrame("Matrixgame Intelligence Kernel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 700);
        f.add(new MatrixGameCore());
        f.setVisible(true);
    }

    static class Entity {
        double x, y, targetAngle = 0, currentAngle = 0;
        Color color; String name;
        Entity(double x, double y, Color c, String n) { this.x = x; this.y = y; this.color = c; this.name = n; }

        void lookAt(Entity other) {
            targetAngle = Math.atan2(other.y - y, other.x - x);
        }

        void draw(Graphics2D g) {
            // Sanfte Drehung des Zeigers (Lern-Effekt)
            double diff = targetAngle - currentAngle;
            while (diff > Math.PI) diff -= 2 * Math.PI;
            while (diff < -Math.PI) diff += 2 * Math.PI;
            currentAngle += diff * 0.1;

            g.setColor(color);
            g.drawOval((int)x - 10, (int)y - 10, 20, 20);
            
            // Der Attention-Vector
            g.setStroke(new BasicStroke(2));
            g.drawLine((int)x, (int)y, (int)(x + Math.cos(currentAngle) * 15), (int)(y + Math.sin(currentAngle) * 15));
            g.setStroke(new BasicStroke(1));
            
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(name, (int)x + 12, (int)y + 4);
        }
    }
}