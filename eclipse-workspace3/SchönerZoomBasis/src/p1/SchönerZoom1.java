package p1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SchönerZoom1 extends JPanel {
    private double zoomFactor = 0.2;
    private double zoomCenterX = 0;
    private double zoomCenterY = 0;
    private final double G = 12.0; 
    private double timeScale = 1.0;
    private final List<Körper> objekte = new ArrayList<>();
    private Körper target = null;
    private Random random = new Random();

    static class Material {
        String name; Color baseColor; double spezifischeWärme, waermeleitfaehigkeit, albedo, dichte;
        Material(String n, Color c, double sw, double wl, double al, double d) {
            this.name = n; this.baseColor = c; this.spezifischeWärme = sw;
            this.waermeleitfaehigkeit = wl; this.albedo = al; this.dichte = d;
        }
        static Material KERN_EISEN = new Material("Eisenkern", new Color(100, 80, 80), 450, 80, 0.1, 7800);
        static Material GESTEIN = new Material("Gestein", new Color(120, 90, 60), 800, 2.5, 0.25, 3000);
        static Material WASSER = new Material("Wasser", new Color(0, 100, 200), 4200, 0.6, 0.08, 1000);
        static Material EIS = new Material("Eis", new Color(200, 230, 255), 2100, 2.2, 0.6, 920);
        static Material ATMOSPHAERE = new Material("Atmosphäre", new Color(150, 200, 255, 60), 1000, 0.025, 0.3, 1.2);
    }

    static class Schicht {
        Material material; double dicke, masse, temperatur;
        Schicht(Material mat, double dickeRel, double tempStart, double gesamtMassePlanet) {
            this.material = mat; this.dicke = dickeRel;
            this.masse = gesamtMassePlanet * dickeRel * mat.dichte; this.temperatur = tempStart;
        }
    }

    static class Körper {
        String name; double x, y, vx, vy, masse, biomasse = 0;
        List<Schicht> schichten = new ArrayList<>();
        double radius; LinkedList<Point> path = new LinkedList<>();
        boolean gelöscht = false;

        Körper(String n, double x, double y, double vx, double vy, double m) {
            this.name = n; this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.masse = m;
            updateRadius();
        }

        void addSchicht(Material mat, double dickeRel, double tempStart) {
            schichten.add(new Schicht(mat, dickeRel, tempStart, this.masse));
        }

        double getOberflaechentemperatur() {
            if (schichten.isEmpty()) return 3.0;
            return schichten.get(schichten.size() - 1).temperatur;
        }

        void updateRadius() {
            this.radius = Math.pow(this.masse, 1.0/3.0) * 4.5;
            if (this.name.equals("SONNE")) this.radius *= 1.2;
        }

        double calculateHabitability() {
            double temp = getOberflaechentemperatur();
            boolean hatWasser = false;
            for(Schicht s : schichten) if(s.material == Material.WASSER || s.material == Material.ATMOSPHAERE) hatWasser = true;
            if (!hatWasser || temp < 250 || temp > 390) return 0;
            return Math.exp(-Math.pow(temp - 290, 2) / 1200.0);
        }
    }

    public SchönerZoom1() {
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(new Color(0, 0, 10));
        setFocusable(true);

        Körper sonne = new Körper("SONNE", 0, 0, 0, 0, 4000.0);
        sonne.addSchicht(Material.KERN_EISEN, 1.0, 5800);
        objekte.add(sonne);
        
        setupVollständigesSonnensystem();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                double wx = (e.getX() - getWidth()/2.0) / zoomFactor - zoomCenterX;
                double wy = (e.getY() - getHeight()/2.0) / zoomFactor - zoomCenterY;
                double d = Math.sqrt(wx*wx+wy*wy);
                double v = Math.sqrt(G * 4000.0 / d);
                Körper k = new Körper("Asteroid", wx, wy, -wy/d*v, wx/d*v, 10.0);
                k.addSchicht(Material.GESTEIN, 1.0, 200);
                objekte.add(k);
            }
        });

        new Timer(16, e -> updatePhysik()).start();
    }

    private void setupVollständigesSonnensystem() {
        addPlanet("Merkur", 160, 17.5, 0.3, Material.GESTEIN, 440);
        addPlanet("Venus", 280, 13.5, 0.9, Material.GESTEIN, 730);
        Körper erde = addPlanet("Erde", 450, 10.5, 1.5, Material.WASSER, 288);
        erde.biomasse = 60;
        addPlanet("Mars", 680, 8.5, 0.8, Material.GESTEIN, 220);
        addPlanet("Jupiter", 1200, 6.5, 100.0, Material.ATMOSPHAERE, 160);
        addPlanet("Saturn", 2000, 5.0, 80.0, Material.ATMOSPHAERE, 130);
        addPlanet("Uranus", 3000, 4.0, 30.0, Material.EIS, 80);
        addPlanet("Neptun", 4000, 3.5, 30.0, Material.EIS, 70);
        addPlanet("Pluto", 5000, 3.1, 0.1, Material.EIS, 40);
    }

    private Körper addPlanet(String n, double dist, double v, double m, Material oberflaeche, double temp) {
        Körper k = new Körper(n, dist, 0, 0, v, m);
        k.addSchicht(Material.KERN_EISEN, 0.4, temp + 500);
        k.addSchicht(oberflaeche, 0.6, temp);
        objekte.add(k);
        return k;
    }

    private void updatePhysik() {
        if (timeScale <= 0) return; 
        double dtBase = 0.1 * timeScale;
        int substeps = (int)(8 * Math.max(1, timeScale));

        for (int s = 0; s < substeps; s++) {
            double dt = dtBase / substeps;
            for (Körper p : objekte) {
                if (p.gelöscht || p.name.equals("SONNE")) continue;
                
                double dist2 = p.x*p.x + p.y*p.y + 1.0;
                Schicht oberflaeche = p.schichten.get(p.schichten.size()-1);
                oberflaeche.temperatur += (150000.0 / dist2) * (1 - oberflaeche.material.albedo) * dt;
                oberflaeche.temperatur -= (oberflaeche.temperatur * oberflaeche.temperatur * 0.000008) * dt;

                double hab = p.calculateHabitability();
                if (hab > 0.1) p.biomasse = Math.min(100, p.biomasse + hab * 0.15 * dt);
                else p.biomasse = Math.max(0, p.biomasse - 0.4 * dt);
            }
            calculateGravity(dt);
        }
        if (target != null) { zoomCenterX = -target.x; zoomCenterY = -target.y; }
        repaint();
    }

    private void calculateGravity(double dt) {
        for (int i = 0; i < objekte.size(); i++) {
            Körper p1 = objekte.get(i); if (p1.gelöscht) continue;
            for (int j = i + 1; j < objekte.size(); j++) {
                Körper p2 = objekte.get(j); if (p2.gelöscht) continue;
                double dx = p2.x - p1.x, dy = p2.y - p1.y, r2 = dx*dx + dy*dy + 0.1, r = Math.sqrt(r2);
                if (r < (p1.radius + p2.radius) * 0.85) { verschmelze(p1, p2); continue; }
                double acc = (G * p1.masse * p2.masse / r2) / r;
                p1.vx += (acc/p1.masse)*dx*dt; p1.vy += (acc/p1.masse)*dy*dt;
                p2.vx -= (acc/p2.masse)*dx*dt; p2.vy -= (acc/p2.masse)*dy*dt;
            }
            p1.x += p1.vx * dt; p1.y += p1.vy * dt;
        }
        objekte.removeIf(k -> k.gelöscht);
    }

    private void verschmelze(Körper p1, Körper p2) {
        Körper dom = (p1.masse >= p2.masse) ? p1 : p2;
        Körper sub = (p1.masse < p2.masse) ? p1 : p2;
        dom.vx = (p1.masse * p1.vx + p2.masse * p2.vx) / (p1.masse + p2.masse);
        dom.vy = (p1.masse * p1.vy + p2.masse * p2.vy) / (p1.masse + p2.masse);
        dom.masse += sub.masse; dom.biomasse *= 0.05;
        if(!dom.schichten.isEmpty()) dom.schichten.get(dom.schichten.size()-1).temperatur += 500;
        dom.updateRadius();
        sub.gelöscht = true; if (target == sub) target = dom;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Körper p : objekte) {
            double sx = tX(p.x), sy = tY(p.y), sr = Math.max(1, p.radius * zoomFactor);
            g2.setColor(p.schichten.get(p.schichten.size()-1).material.baseColor);
            g2.fillOval((int)(sx-sr), (int)(sy-sr), (int)(sr*2), (int)(sr*2));
            
            if (p.biomasse > 1) {
                g2.setColor(new Color(0, 255, 0, 180));
                g2.fillRect((int)(sx-15), (int)(sy-sr-10), (int)(p.biomasse * 0.3), 4);
            }
            if (zoomFactor > 0.02) {
                g2.setColor(Color.WHITE);
                g2.drawString(p.name, (int)sx+10, (int)sy);
            }
        }
        g2.setColor(Color.CYAN);
        g2.drawString("MATRIXGAME V0.9 - FULL SYSTEM RECOVERED", 20, 30);
        g2.drawString("Pfeiltasten: Kamera | +/-: Zoom | T/G: Zeit", 20, 50);
    }

    private int tX(double x) { return (int)((zoomCenterX + x) * zoomFactor + getWidth()/2.0); }
    private int tY(double y) { return (int)((zoomCenterY + y) * zoomFactor + getHeight()/2.0); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Matrixgame: Full System & Biosphere");
            SchönerZoom1 p = new SchönerZoom1(); f.add(p);
            p.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    double s = 100 / p.zoomFactor;
                    if(e.getKeyCode() == KeyEvent.VK_LEFT) { p.target=null; p.zoomCenterX += s; }
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT) { p.target=null; p.zoomCenterX -= s; }
                    if(e.getKeyCode() == KeyEvent.VK_UP) { p.target=null; p.zoomCenterY += s; }
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) { p.target=null; p.zoomCenterY -= s; }
                    if(e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_ADD) p.zoomFactor *= 1.3;
                    if(e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT) p.zoomFactor /= 1.3;
                    if(e.getKeyCode() == KeyEvent.VK_T) p.timeScale *= 2.0;
                    if(e.getKeyCode() == KeyEvent.VK_G) p.timeScale /= 2.0;
                    if(e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_9) {
                        int idx = e.getKeyCode() - KeyEvent.VK_1;
                        if(idx < p.objekte.size()) p.target = p.objekte.get(idx);
                    }
                    p.repaint();
                }
            });
            f.pack(); f.setLocationRelativeTo(null); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true); p.requestFocusInWindow();
        });
    }
    
}

