package BasisEditorenMatrixgame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SchönerZoom2FormScaleUnitsDraw extends JPanel {

    // --- Instanzvariablen (NICHT static) ---
    private int FRAME_WIDTH = 1920;
    private int FRAME_HEIGHT = 1080;
    private double zoomFactor = 1.0;
    private double zoomCenterX = 0;
    private double zoomCenterY = 0;
    private static final double ZOOM_STEP = 1.2;
    private double feinheit = 70;
    private int pause = 100;
    private double PAN_STEP;
    
    // Wichtig: unitList als Instanzvariable
    private List<KreisObjekt> unitList = new ArrayList<>();

    // --- Konstruktor ---
    public SchönerZoom2FormScaleUnitsDraw() {
        initializeSettings();
        initializeUnits();
        setupPanel();
    }

    private void initializeSettings() {
        PAN_STEP = feinheit * zoomFactor;
    }

    private void initializeUnits() {
        // Units hier hinzufügen - nicht in main!
    	// public Unit(size,  red,  green,  blue,  posX, double posY, double posZ,double vx, double vy, double vz)
        for(int i=0; i<10000; i++) {
    	unitList.add(new KreisObjekt());}
        
        
//        unitList.add(new Unit(22, 285, 33, 232, 55, 99, 151, -21, 4, 1));
//        unitList.add(new Unit(20, 55, 143, 22, 55, 7659, 1231));
//        unitList.add(new Unit(23, 145, 113, 232, 595, 99, 151));}
    }

    private void setupPanel() {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setBackground(Color.BLACK);
    }

    // --- Game Loop als Instanzmethode ---
    public void startGameLoop() {
        Thread gameThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(pause);
                    updateGameState();
                    repaint(); // Kann jetzt aufgerufen werden, da Instanzmethode
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        gameThread.setDaemon(true); // Wird beendet wenn Fenster geschlossen
        gameThread.start();
    }

    private void updateGameState() {
        for (KreisObjekt unit : unitList) {
            unit.move(1);
        }
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

    // --- Zoom-Methoden ---
    public void zoomIn() {
        zoomFactor *= ZOOM_STEP;
        updatePanStep();
        repaint();
    }

    public void zoomOut() {
        zoomFactor /= ZOOM_STEP;
        updatePanStep();
        repaint();
    }

    private void updatePanStep() {
        PAN_STEP = feinheit / zoomFactor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (KreisObjekt current : unitList) {
            double radius = Math.abs(current.size) * zoomFactor;
            Color color = new Color(
                (int) Math.abs(current.red % 255), 
                (int) Math.abs(current.green % 255),
                (int) Math.abs(current.blue % 255)
            );
            
            double sx = (zoomCenterX + current.posX) * zoomFactor + getWidth() / 2.0;
            double sy = (zoomCenterY + current.posY) * zoomFactor + getHeight() / 2.0;
            
            g2.setColor(color);
            g2.fillOval((int) (sx - radius), (int) (sy - radius), 
                        (int) (radius * 2), (int) (radius * 2));
        }
    }

    // --- MAIN METHODE - Nur Einstiegspunkt ---
    public static void main(String[] args) {
        // Best Practice: main nur für Swing-invokeLater und Objekterstellung
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // 1. Hauptpanel erstellen
        SchönerZoom2FormScaleUnitsDraw simPanel = new SchönerZoom2FormScaleUnitsDraw();
        
        // 2. Frame erstellen und konfigurieren
        JFrame frame = new JFrame("N-Body Sonnensystem-Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(simPanel, BorderLayout.CENTER);
        
        // 3. Steuerelemente hinzufügen
        frame.add(createControlPanel(simPanel), BorderLayout.NORTH);
        
        // 4. Frame anzeigen
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // 5. Game Loop starten
        simPanel.startGameLoop();
    }

    private static JPanel createControlPanel(SchönerZoom2FormScaleUnitsDraw simPanel) {
        JPanel control = new JPanel();
        
        JButton zoomIn = new JButton("Zoom In");
        JButton zoomOut = new JButton("Zoom Out");
        JButton panLeft = new JButton("← Links");
        JButton panRight = new JButton("Rechts →");
        JButton panUp = new JButton("↑ Hoch");
        JButton panDown = new JButton("↓ Runter");

        zoomIn.addActionListener(e -> simPanel.zoomIn());
        zoomOut.addActionListener(e -> simPanel.zoomOut());
        panLeft.addActionListener(e -> simPanel.panLeft());
        panRight.addActionListener(e -> simPanel.panRight());
        panUp.addActionListener(e -> simPanel.panUp());
        panDown.addActionListener(e -> simPanel.panDown());

        control.add(zoomIn);
        control.add(zoomOut);
        control.add(panLeft);
        control.add(panRight);
        control.add(panUp);
        control.add(panDown);
        
        return control;
    }
}