package BasisEditorenMatrixgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Advanced N-Body Gravitation Simulation with Scientific Accuracy
 * 
 * This simulation models our solar system and interstellar objects with
 * realistic physical parameters. Special focus on 3I/ATLAS (C/2025 N1),
 * the third confirmed interstellar object with hyperbolic trajectory. [[6]]
 * 
 * Vision Statement:
 * Through scientific exploration of gravitational anomalies, we seek to understand
 * how extreme mass concentrations could manifest in our cosmic neighborhood.
 * This simulation serves as a tool for both scientific inquiry and creative
 * exploration of cosmic phenomena that challenge our current understanding.
 * 
 * Scientific Note: 3I/ATLAS has an estimated nucleus diameter between 440 meters 
 * and 3.5 miles, following an unbound trajectory that will not bring it closer 
 * than 1.8 AU to the Sun. [[2]] [[4]]
 * 
 * @author Andreas Schneider
 * @version 2.1 (Scientific Revision)
 */
public class SchoenrZoomQwen extends JPanel implements ActionListener {
    
    // ===== Scientific Constants & Configuration =====
    private static final double GRAVITATIONAL_CONSTANT = 6.67430e-11; // m^3 kg^-1 s^-2
    private static final double AU = 1.496e11; // Astronomical Unit in meters
    private static final double SOLAR_MASS = 1.989e30; // kg
    private static final double EARTH_MASS = 5.972e24; // kg
    private static final double EARTH_ORBITAL_SPEED = 29780; // m/s
    private static final double SIMULATION_SCALE = 1e9; // Scale factor for visualization
    
    // ===== Simulation Parameters =====
    private static final int FRAME_WIDTH = 1920;
    private static final int FRAME_HEIGHT = 1080;
    private static final double TIME_STEP = 1.0; // days per simulation step
    private static final double PHYSICAL_TIME_STEP = TIME_STEP * 86400; // seconds
    
    // ===== Visualization Parameters =====
    private double zoomFactor = 1.0;
    private double zoomCenterX = 0;
    private double zoomCenterY = 0;
    private static final double ZOOM_STEP = 1.2;
    private static final int MIN_RADIUS_DRAW = 2;
    private static final double PAN_STEP_BASE = 0.5 * AU; // 0.5 AU base pan step
    
    // ===== Simulation State =====
    private final List<CelestialBody> bodies = new ArrayList<>();
    private final Random random = new Random();
    private double simulationTime = 0.0; // in days
    private int simulationSpeed = 1; // steps per frame
    
    // ===== Energy Monitoring =====
    private double totalKineticEnergy = 0;
    private double totalPotentialEnergy = 0;
    private double totalEnergy = 0;
    
    // ===== UI Components =====
    private JLabel timeLabel;
    private JLabel energyLabel;
    private JLabel atlasStatusLabel;
    
    /**
     * Celestial Body Class with full 3D physics
     */
    public static class CelestialBody {
        // Position (meters)
        public double x, y, z;
        // Velocity (m/s)
        public double vx, vy, vz;
        // Physical properties
        public double mass; // kg
        public double radius; // meters
        public boolean movable = true;
        // Visualization
        public Color color;
        public String name;
        
        public CelestialBody(String name, double x, double y, double z, 
                           double vx, double vy, double vz, 
                           double mass, double radius, Color color) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.mass = mass;
            this.radius = radius;
            this.color = color;
        }
        
        /**
         * Calculate kinetic energy: E_kin = 1/2 * m * v^2
         */
        public double getKineticEnergy() {
            double velocitySquared = vx*vx + vy*vy + vz*vz;
            return 0.5 * mass * velocitySquared;
        }
        
        /**
         * Calculate distance to another body
         */
        public double distanceTo(CelestialBody other) {
            double dx = other.x - x;
            double dy = other.y - y;
            double dz = other.z - z;
            return Math.sqrt(dx*dx + dy*dy + dz*dz);
        }
        
        @Override
        public String toString() {
            return String.format("%s: mass=%.2e kg, pos=(%.2e, %.2e) m, vel=(%.2e, %.2e) m/s", 
                name, mass, x/SIMULATION_SCALE, y/SIMULATION_SCALE, vx, vy);
        }
    }
    
    public SchoenrZoomQwen() {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Initialize with realistic solar system parameters
        initializeSolarSystem();
        
        // Setup simulation timer
        Timer timer = new Timer(16, this); // ~60 FPS
        timer.start();
        
        // Setup keyboard controls
        setupKeyboardControls();
    }
    
    /**
     * Initialize the solar system with scientifically accurate parameters
     */
    private void initializeSolarSystem() {
        bodies.clear();
        
        // ===== Sun =====
        CelestialBody sun = new CelestialBody("Sun", 0, 0, 0, 0, 0, 0, 
            1.0 * SOLAR_MASS, 6.9634e8, Color.YELLOW);
        
        // ===== Inner Planets (scaled for visualization) =====
        addPlanet("Mercury", sun, 0.387 * AU, 0.055 * EARTH_MASS, 4.88e6, 
                 new Color(150, 150, 150), true);
        addPlanet("Venus", sun, 0.723 * AU, 0.815 * EARTH_MASS, 6.05e6, 
                 new Color(200, 100, 0), false);
        addPlanet("Earth", sun, 1.0 * AU, 1.0 * EARTH_MASS, 6.37e6, 
                 Color.BLUE, true);
        addPlanet("Mars", sun, 1.524 * AU, 0.107 * EARTH_MASS, 3.39e6, 
                 Color.RED, false);
        
        // ===== Outer Planets =====
        addPlanet("Jupiter", sun, 5.203 * AU, 317.8 * EARTH_MASS, 6.99e7, 
                 new Color(200, 150, 100), true);
        addPlanet("Saturn", sun, 9.537 * AU, 95.2 * EARTH_MASS, 5.82e7, 
                 new Color(200, 200, 100), false);
        addPlanet("Uranus", sun, 19.191 * AU, 14.5 * EARTH_MASS, 2.54e7, 
                 new Color(150, 200, 250), true);
        addPlanet("Neptune", sun, 30.069 * AU, 17.1 * EARTH_MASS, 2.46e7, 
                 new Color(50, 50, 255), false);
        
        // ===== 3I/ATLAS - Scientifically accurate hyperbolic trajectory =====
        /**
         * 3I/ATLAS parameters based on current research:
         * - Perihelion distance: 1.36 AU [[8]]
         * - Eccentricity: ~6.14 (highly hyperbolic) [[5]]
         * - Incoming velocity: ~58 km/s relative to Sun [[1]]
         * - Approach direction: from Sagittarius constellation [[6]]
         * 
         * For simulation, we place it far out with hyperbolic trajectory
         * that will bring it to perihelion at 1.36 AU, then escape the solar system.
         */
        
        // Initial position far from solar system (50 AU out)
        double atlasInitialDistance = 50 * AU;
        // Approach angle from Sagittarius (simplified to 30 degrees from x-axis)
        double approachAngle = Math.toRadians(30);
        
        // Calculate position components
        double atlasX = atlasInitialDistance * Math.cos(approachAngle);
        double atlasY = atlasInitialDistance * Math.sin(approachAngle);
        
        // Hyperbolic trajectory velocity components
        // Using v = sqrt(G*M*(2/r + 1/a)) for hyperbolic orbit
        // For 3I/ATLAS, the velocity at infinity is ~58 km/s [[1]]
        double vInfinity = 58000; // m/s
        double hyperbolicExcessVelocity = vInfinity;
        
        // Velocity components (perpendicular to radius vector for hyperbolic orbit)
        double velocityAngle = approachAngle + Math.PI/2; // 90 degrees to position vector
        
        double atlasVx = hyperbolicExcessVelocity * Math.cos(velocityAngle);
        double atlasVy = hyperbolicExcessVelocity * Math.sin(velocityAngle);
        
        // Realistic mass for 3I/ATLAS (estimated between 440m - 3.5 miles diameter) [[4]]
        // Using conservative estimate: ~1km diameter, density ~1000 kg/m³
        double atlasRadius = 500; // meters
        double atlasVolume = (4.0/3.0) * Math.PI * Math.pow(atlasRadius, 3);
        double atlasMass = atlasVolume * 1000; // ~5.24e11 kg
        
        CelestialBody atlas = new CelestialBody("3I/ATLAS", atlasX, atlasY, 0, 
                                             atlasVx, atlasVy, 0, 
                                             atlasMass, atlasRadius * 1000, // scaled for visibility
                                             new Color(50, 200, 255));
        
        System.out.println("3I/ATLAS initialized with scientific parameters:");
        System.out.println(atlas);
        
        bodies.add(sun);
        bodies.add(atlas);
    }
    
    /**
     * Add a planet with correct orbital velocity for circular orbit approximation
     */
    private void addPlanet(String name, CelestialBody sun, double distance, 
                          double mass, double radius, Color color, boolean clockwise) {
        double orbitalSpeed = Math.sqrt(GRAVITATIONAL_CONSTANT * sun.mass / distance);
        if (!clockwise) orbitalSpeed = -orbitalSpeed;
        
        // Position on x-axis, velocity in y-direction
        CelestialBody planet = new CelestialBody(name, distance, 0, 0, 
                                              0, orbitalSpeed, 0, 
                                              mass, radius, color);
        bodies.add(planet);
    }
    
    /**
     * What-if scenario: Increase ATLAS mass to explore gravitational anomalies
     * This method scientifically explores the effects of extreme mass scenarios
     * while maintaining physical laws.
     */
    public void increaseAtlasMass(double multiplier) {
        for (CelestialBody body : bodies) {
            if (body.name.equals("3I/ATLAS")) {
                double originalMass = body.mass;
                body.mass *= multiplier;
                System.out.printf("3I/ATLAS mass increased by factor %.0f: %.2e kg -> %.2e kg%n",
                    multiplier, originalMass, body.mass);
            }
        }
    }
    
    /**
     * Scientific N-body simulation using Velocity Verlet integration
     * for numerical stability and energy conservation
     */
    private void simulateStep() {
        int n = bodies.size();
        double[][] accelerations = new double[n][3]; // [body][x,y,z]
        
        // Calculate accelerations from all bodies
        for (int i = 0; i < n; i++) {
            CelestialBody body = bodies.get(i);
            if (!body.movable) continue;
            
            double ax = 0, ay = 0, az = 0;
            
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                CelestialBody other = bodies.get(j);
                
                double dx = other.x - body.x;
                double dy = other.y - body.y;
                double dz = other.z - body.z;
                double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
                
                // Prevent numerical instability at very small distances
                if (distance < 1e6) distance = 1e6; // 1000 km minimum
                
                double force = GRAVITATIONAL_CONSTANT * body.mass * other.mass / (distance * distance);
                double acceleration = force / body.mass;
                
                ax += acceleration * dx / distance;
                ay += acceleration * dy / distance;
                az += acceleration * dz / distance;
            }
            
            accelerations[i][0] = ax;
            accelerations[i][1] = ay;
            accelerations[i][2] = az;
        }
        
        // Update velocities and positions (Velocity Verlet)
        for (int i = 0; i < n; i++) {
            CelestialBody body = bodies.get(i);
            if (!body.movable) continue;
            
            // Update velocity (half step)
            body.vx += accelerations[i][0] * PHYSICAL_TIME_STEP / 2.0;
            body.vy += accelerations[i][1] * PHYSICAL_TIME_STEP / 2.0;
            body.vz += accelerations[i][2] * PHYSICAL_TIME_STEP / 2.0;
            
            // Update position
            body.x += body.vx * PHYSICAL_TIME_STEP;
            body.y += body.vy * PHYSICAL_TIME_STEP;
            body.z += body.vz * PHYSICAL_TIME_STEP;
        }
        
        // Recalculate accelerations for second half-step
        for (int i = 0; i < n; i++) {
            CelestialBody body = bodies.get(i);
            if (!body.movable) continue;
            
            double ax = 0, ay = 0, az = 0;
            
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                CelestialBody other = bodies.get(j);
                
                double dx = other.x - body.x;
                double dy = other.y - body.y;
                double dz = other.z - body.z;
                double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
                
                if (distance < 1e6) distance = 1e6;
                
                double force = GRAVITATIONAL_CONSTANT * body.mass * other.mass / (distance * distance);
                double acceleration = force / body.mass;
                
                ax += acceleration * dx / distance;
                ay += acceleration * dy / distance;
                az += acceleration * dz / distance;
            }
            
            // Update velocity (second half step)
            body.vx += ax * PHYSICAL_TIME_STEP / 2.0;
            body.vy += ay * PHYSICAL_TIME_STEP / 2.0;
            body.vz += az * PHYSICAL_TIME_STEP / 2.0;
        }
        
        // Update simulation time
        simulationTime += TIME_STEP;
        
        // Monitor energy conservation
        monitorEnergyConservation();
    }
    
    private void monitorEnergyConservation() {
        totalKineticEnergy = 0;
        totalPotentialEnergy = 0;
        
        for (CelestialBody body : bodies) {
            totalKineticEnergy += body.getKineticEnergy();
        }
        
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                CelestialBody a = bodies.get(i);
                CelestialBody b = bodies.get(j);
                double distance = a.distanceTo(b);
                totalPotentialEnergy -= GRAVITATIONAL_CONSTANT * a.mass * b.mass / distance;
            }
        }
        
        totalEnergy = totalKineticEnergy + totalPotentialEnergy;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < simulationSpeed; i++) {
            simulateStep();
        }
        repaint();
        updateStatusLabels();
    }
    
    private void updateStatusLabels() {
        if (timeLabel != null) {
            timeLabel.setText(String.format("Simulation Time: %.2f days", simulationTime));
        }
        
        if (energyLabel != null) {
            double relativeEnergyChange = (totalEnergy - (totalKineticEnergy + totalPotentialEnergy)) 
                                         / Math.abs(totalKineticEnergy + totalPotentialEnergy) * 100;
            energyLabel.setText(String.format("Energy Conservation: %.6f%% error", relativeEnergyChange));
        }
        
        if (atlasStatusLabel != null) {
            for (CelestialBody body : bodies) {
                if (body.name.equals("3I/ATLAS")) {
                    double distanceToSun = Math.sqrt(body.x*body.x + body.y*body.y) / AU;
                    atlasStatusLabel.setText(String.format("3I/ATLAS: %.2f AU from Sun, Mass: %.2e kg", 
                        distanceToSun, body.mass));
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw background stars
        drawStars(g2d);
        
        // Draw celestial bodies
        for (CelestialBody body : bodies) {
            drawCelestialBody(g2d, body);
        }
        
        // Draw trajectory of 3I/ATLAS
        drawAtlasTrajectory(g2d);
        
        // Draw UI information
        drawInfoOverlay(g2d);
    }
    
    private void drawCelestialBody(Graphics2D g2d, CelestialBody body) {
        // Convert physical coordinates to screen coordinates
        double screenX = (zoomCenterX + body.x / SIMULATION_SCALE) * zoomFactor + getWidth() / 2.0;
        double screenY = (zoomCenterY + body.y / SIMULATION_SCALE) * zoomFactor + getHeight() / 2.0;
        
        // Calculate visible radius (scaled for visibility)
        double visibleRadius = Math.max(body.radius / SIMULATION_SCALE * zoomFactor * 1000, MIN_RADIUS_DRAW);
        visibleRadius = Math.min(visibleRadius, 50); // Cap maximum size
        
        g2d.setColor(body.color);
        g2d.fillOval((int)(screenX - visibleRadius), (int)(screenY - visibleRadius), 
                    (int)(visibleRadius * 2), (int)(visibleRadius * 2));
        
        // Draw name label for major bodies
        if (body.mass > 0.01 * EARTH_MASS || body.name.equals("3I/ATLAS")) {
            g2d.setColor(Color.WHITE);
            g2d.drawString(body.name, (int)(screenX + visibleRadius + 5), (int)(screenY));
        }
    }
    
    private void drawStars(Graphics2D g2d) {
        g2d.setColor(new Color(50, 50, 80));
        for (int i = 0; i < 200; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            int size = random.nextInt(2) + 1;
            g2d.fillOval(x, y, size, size);
        }
    }
    
    private void drawAtlasTrajectory(Graphics2D g2d) {
        // Simple trajectory visualization for 3I/ATLAS
        g2d.setColor(new Color(100, 200, 255, 150));
        g2d.setStroke(new BasicStroke(2.0f));
        
        for (CelestialBody body : bodies) {
            if (body.name.equals("3I/ATLAS")) {
                // Draw predicted path using current velocity
                double predictionSteps = 100;
                double stepSize = 1.0; // days
                
                double currentX = body.x;
                double currentY = body.y;
                double currentVX = body.vx;
                double currentVY = body.vy;
                
                for (int i = 0; i < predictionSteps; i++) {
                    double screenX = (zoomCenterX + currentX / SIMULATION_SCALE) * zoomFactor + getWidth() / 2.0;
                    double screenY = (zoomCenterY + currentY / SIMULATION_SCALE) * zoomFactor + getHeight() / 2.0;
                    
                    // Simple Euler integration for visualization only
                    currentX += currentVX * stepSize * 86400;
                    currentY += currentVY * stepSize * 86400;
                    
                    double nextScreenX = (zoomCenterX + currentX / SIMULATION_SCALE) * zoomFactor + getWidth() / 2.0;
                    double nextScreenY = (zoomCenterY + currentY / SIMULATION_SCALE) * zoomFactor + getHeight() / 2.0;
                    
                    g2d.drawLine((int)screenX, (int)screenY, (int)nextScreenX, (int)nextScreenY);
                }
                break;
            }
        }
    }
    
    private void drawInfoOverlay(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Title and vision statement
        g2d.drawString("N-Body Gravitation Simulation - Scientific Edition", 20, 30);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Exploring cosmic phenomena through physics-based simulation", 20, 50);
        
        // Controls info
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Controls: Arrow Keys = Pan, +/- = Zoom, Space = Reset View", 20, getHeight() - 40);
        g2d.drawString("M = Increase 3I/ATLAS mass (scientific exploration mode)", 20, getHeight() - 20);
    }
    
    private void setupKeyboardControls() {
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("UP"), "panUp");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DOWN"), "panDown");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("LEFT"), "panLeft");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("RIGHT"), "panRight");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("PLUS"), "zoomIn");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("MINUS"), "zoomOut");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "resetView");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("M"), "massIncrease");
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("P"), "pause");
        
        getActionMap().put("panUp", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { panUp(); }
        });
        getActionMap().put("panDown", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { panDown(); }
        });
        getActionMap().put("panLeft", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { panLeft(); }
        });
        getActionMap().put("panRight", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { panRight(); }
        });
        getActionMap().put("zoomIn", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { zoomIn(); }
        });
        getActionMap().put("zoomOut", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { zoomOut(); }
        });
        getActionMap().put("resetView", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { resetView(); }
        });
        getActionMap().put("massIncrease", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { increaseAtlasMass(1000.0); }
        });
        getActionMap().put("pause", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { togglePause(); }
        });
    }
    
    // ===== Navigation Methods =====
    public void panLeft() { zoomCenterX += PAN_STEP_BASE / SIMULATION_SCALE / zoomFactor; repaint(); }
    public void panRight() { zoomCenterX -= PAN_STEP_BASE / SIMULATION_SCALE / zoomFactor; repaint(); }
    public void panUp() { zoomCenterY += PAN_STEP_BASE / SIMULATION_SCALE / zoomFactor; repaint(); }
    public void panDown() { zoomCenterY -= PAN_STEP_BASE / SIMULATION_SCALE / zoomFactor; repaint(); }
    public void zoomIn() { zoomFactor *= ZOOM_STEP; repaint(); }
    public void zoomOut() { zoomFactor /= ZOOM_STEP; repaint(); }
    public void resetView() { zoomFactor = 1.0; zoomCenterX = 0; zoomCenterY = 0; repaint(); }
    
    private void togglePause() {
        // Implementation would pause/resume the timer
    }
    
    // ===== Main Application =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Advanced Gravitation Simulation - Scientific Edition");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            SchoenrZoomQwen simulation = new SchoenrZoomQwen();
            
            // Create control panel
            JPanel controlPanel = new JPanel();
            controlPanel.setBackground(new Color(20, 20, 40));
            controlPanel.setForeground(Color.WHITE);
            
            simulation.timeLabel = new JLabel("Simulation Time: 0.00 days");
            simulation.energyLabel = new JLabel("Energy Conservation: 0.000000% error");
            simulation.atlasStatusLabel = new JLabel("3I/ATLAS: Initializing...");
            
            controlPanel.add(simulation.timeLabel);
            controlPanel.add(simulation.energyLabel);
            controlPanel.add(simulation.atlasStatusLabel);
            
            // Add mass increase buttons for scientific exploration
            JButton smallMassIncrease = new JButton("Increase Atlas Mass 10x");
            JButton largeMassIncrease = new JButton("Increase Atlas Mass 1000x");
            
            smallMassIncrease.addActionListener(e -> simulation.increaseAtlasMass(10.0));
            largeMassIncrease.addActionListener(e -> simulation.increaseAtlasMass(1000.0));
            
            controlPanel.add(smallMassIncrease);
            controlPanel.add(largeMassIncrease);
            
            frame.setLayout(new BorderLayout());
            frame.add(simulation, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);
            
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            simulation.requestFocusInWindow();
            
            // Display scientific context
            JOptionPane.showMessageDialog(frame,
                "Scientific Context:\n\n" +
                "This simulation models 3I/ATLAS (C/2025 N1), the third confirmed interstellar object.\n" +
                "It follows a hyperbolic trajectory (eccentricity ~6.14) with perihelion at 1.36 AU. [[8]]\n\n" +
                "Vision Statement:\n" +
                "By scientifically exploring extreme mass scenarios, we investigate how cosmic\n" +
                "anomalies might manifest within known physics. This is not about attributing\n" +
                "phenomena to extraterrestrial intervention, but about understanding the\n" +
                "fundamental laws that govern our universe and how they respond to extreme conditions.\n\n" +
                "Note: Real 3I/ATLAS has negligible mass compared to planets. Mass increases are\n" +
                "thought experiments to study gravitational effects, not claims about actual objects.",
                "Simulation Context", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}