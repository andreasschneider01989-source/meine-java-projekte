package s1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for the collective consciousness simulator.
 * 
 * In dieser Version sind auf der Erde die Eltern (Position 1 und 2)
 * so initialisiert, dass ihre neuronale Ausrichtung vom Spieler (Position 0)
 * bereits ähnlich ist.
 */
public class Main {
    // The Earth world is initialized once – the player is always the human at index 0.
    private World earthWorld;//Die speziell initialisierte Erde, bei der der Spieler (Index 0) immer als Ausgangspunkt verwendet wird.
    private Human player; //Referenz auf den Spieler (Mensch an Position 0).
    
    // currentWorld represents the world currently being simulated.
    private World currentWorld; //Aktuell aktive Welt, in der simuliert wird.
    // Map to store all created worlds so that their state is preserved.
    private Map<WorldType, World> worldMap = new HashMap<>(); //Eine Map, um bereits erstellte Welten zu speichern – so bleibt der Zustand erhalten, wenn man zwischen den Welten wechselt.
    
    private Scanner scanner = new Scanner(System.in);  //Zum Einlesen der Benutzereingaben.

    public static void main(String[] args) {
        Main main = new Main();
        main.initEarth();
        main.chooseWorld();
        main.simulate();
    }

    /**
     * Initializes Earth with the player at position 0.
     * Auf der Erde symbolisiert der Spieler (Index 0) den Start mit Wissen 0,
     * während Vater (Index 1) und Mutter (Index 2) von Anfang an eine ähnliche
     * neuronale Ausrichtung besitzen.
     * Erstellt die Welt "Earth" mit 9 Menschen.
		Setzt den Spieler (Index 0) mit einem Wissen von 0, während andere standardmäßig ein höheres Wissensniveau haben (hier 8).
		Setzt für die Eltern (Index 1 und 2) die neuronale Ausrichtung auf denselben Wert wie beim Spieler.
     */
    public void initEarth() {
        // Create Earth world with 9 humans; default knowledge level for non-player humans is 8.
        earthWorld = new World(WorldType.EARTH, 9, 8);
        // Set the player to the human at position 0 and override player's knowledge to 0.
        player = earthWorld.humans[0];
        player.networkedKnowledge = 1;
        
        // Set parent's neuron alignment similar to player's.
        double commonAlignment = player.neuronAlignment; // Use player's alignment as reference.
        earthWorld.humans[1].neuronAlignment = commonAlignment + 10*Math.random();
        earthWorld.humans[2].neuronAlignment = commonAlignment + 10*Math.random();
        
        worldMap.put(WorldType.EARTH, earthWorld);
        System.out.println("Player (Earth, Position 0) initialized: " +
                "Knowledge=" + player.networkedKnowledge +
                ", Neuron Alignment=" + player.neuronAlignment +
                ", Life=" + player.life +
                ", Demonization=" + player.demonizationLevel);
        System.out.println("Player's father (Earth, Position 1) initialized: " +
                "Neuron Alignment=" + earthWorld.humans[1].neuronAlignment);
        System.out.println("Player's mother (Earth, Position 2) initialized: " +
                "Neuron Alignment=" + earthWorld.humans[2].neuronAlignment);
    }

    /**
     * Displays a menu to choose the world to simulate.
     * If a world has been selected before, its state is preserved.
     */
    public void chooseWorld() {
        System.out.println("\nPlease select a world:");
        System.out.println("1: Our Earth (Internet) [Earth] (9 humans, default level 8 for others, player starts at 0)");
        System.out.println("2: Dome World (13 humans, level 8)");
        System.out.println("3: World with Alternative Physics (12 humans, level 9)");
        System.out.println("4: God Plant (2000 humans, level 4)");
        System.out.println("5: Oracle World (500 humans, level 2)");
        System.out.println("6: World of Billiard Rats (100 humans, level 1)");
        System.out.println("7: World of a Billion People (1000 humans, level 4)");
        System.out.println("8: Life on the Asteroid Belt (1 human, level 2)");
        System.out.println("9: Dogscape (4 humans, level 4)");
        System.out.println("10: Zombie World (100 humans, level 1)");
        System.out.println("11: Ice Worlds (50000 humans, level 2)");

        int selection = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        WorldType selectedType = null;
        switch (selection) {
            case 1: selectedType = WorldType.EARTH; break;
            case 2: selectedType = WorldType.DOME_WORLD; break;
            case 3: selectedType = WorldType.ALTERNATIVE_PHYSICS; break;
            case 4: selectedType = WorldType.GOD_PLANT; break;
            case 5: selectedType = WorldType.ORACLE_WORLD; break;
            case 6: selectedType = WorldType.BILLIARD_RATS; break;
            case 7: selectedType = WorldType.BILLION_PEOPLE; break;
            case 8: selectedType = WorldType.ASTEROID_BELT; break;
            case 9: selectedType = WorldType.DOGSCAPE; break;
            case 10: selectedType = WorldType.ZOMBIE_WORLD; break;
            case 11: selectedType = WorldType.ICE_WORLDS; break;
            default:
                System.out.println("Invalid selection. Loading default world (Earth).");
                selectedType = WorldType.EARTH;
                break;
        }

        if (worldMap.containsKey(selectedType)) {
            currentWorld = worldMap.get(selectedType);
        } else {
            switch (selectedType) {
                case DOME_WORLD: currentWorld = new World(WorldType.DOME_WORLD, 13, 8); break;
                case ALTERNATIVE_PHYSICS: currentWorld = new World(WorldType.ALTERNATIVE_PHYSICS, 12, 9); break;
                case GOD_PLANT: currentWorld = new World(WorldType.GOD_PLANT, 2000, 4); break;
                case ORACLE_WORLD: currentWorld = new World(WorldType.ORACLE_WORLD, 500, 2); break;
                case BILLIARD_RATS: currentWorld = new World(WorldType.BILLIARD_RATS, 100, 1); break;
                case BILLION_PEOPLE: currentWorld = new World(WorldType.BILLION_PEOPLE, 1000, 4); break;
                case ASTEROID_BELT: currentWorld = new World(WorldType.ASTEROID_BELT, 1, 2); break;
                case DOGSCAPE: currentWorld = new World(WorldType.DOGSCAPE, 4, 4); break;
                case ZOMBIE_WORLD: currentWorld = new World(WorldType.ZOMBIE_WORLD, 100, 1); break;
                case ICE_WORLDS: currentWorld = new World(WorldType.ICE_WORLDS, 50000, 2); break;
                case EARTH: currentWorld = earthWorld; break;
                default: currentWorld = earthWorld; break;
            }
            worldMap.put(selectedType, currentWorld);
        }
        System.out.println("World " + currentWorld.type + " has been selected.");
    }

    /**
     * Main simulation loop.
     */
    public void simulate() {
        while (true) {
            System.out.println("\nCurrent World: " + currentWorld.type);
            System.out.println("Player (Earth, Position 0): Knowledge=" + player.networkedKnowledge +
                    ", Neuron Alignment=" + player.neuronAlignment +
                    ", Life=" + player.life +
                    ", Demonization=" + player.demonizationLevel);
            System.out.println("Select an action:");
            if (currentWorld.type == WorldType.EARTH) {
                System.out.println("1: Align neurons with another player");
                System.out.println("2: Exchange knowledge with another player");
                System.out.println("3: Continue simulation (apply world effects)");
                System.out.println("4: Switch world");
                System.out.println("5: Heal Earth");
                System.out.println("6: Diagnose demonization");
                System.out.println("7: Meditate to reduce demonization");
                System.out.println("8: Exit");
            } else {
                System.out.println("1: Align neurons with another player");
                System.out.println("2: Exchange knowledge with another player");
                System.out.println("3: Continue simulation (apply world effects)");
                System.out.println("4: Switch world");
                System.out.println("5: Diagnose demonization");
                System.out.println("6: Meditate to reduce demonization");
                System.out.println("7: Exit");
            }

            int selection = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (currentWorld.type == WorldType.EARTH) {
                switch (selection) {
                    case 1:
                        performPlayerInteraction(true);
                        break;
                    case 2:
                        performPlayerInteraction(false);
                        break;
                    case 3:
                        System.out.println("Continuing simulation...");
                        currentWorld.applyWorldEffects();
                        break;
                    case 4:
                        System.out.println("Switching world:");
                        chooseWorld();
                        break;
                    case 5:
                        healEarth();
                        break;
                    case 6:
                        diagnoseDemonization();
                        break;
                    case 7:
                        meditate();
                        break;
                    case 8:
                        System.out.println("Exiting simulation.");
                        return;
                    default:
                        System.out.println("Invalid selection.");
                        break;
                }
            } else {
                switch (selection) {
                    case 1:
                        performPlayerInteraction(true);
                        break;
                    case 2:
                        performPlayerInteraction(false);
                        break;
                    case 3:
                        System.out.println("Continuing simulation...");
                        currentWorld.applyWorldEffects();
                        break;
                    case 4:
                        System.out.println("Switching world:");
                        chooseWorld();
                        break;
                    case 5:
                        diagnoseDemonization();
                        break;
                    case 6:
                        meditate();
                        break;
                    case 7:
                        System.out.println("Exiting simulation.");
                        return;
                    default:
                        System.out.println("Invalid selection.");
                        break;
                }
            }
        }
    }
    
    /**
     * Helper method to perform either alignment or knowledge exchange between the player and a chosen target.
     */
    private void performPlayerInteraction(boolean isAlignment) {
        if (player.demonizationLevel > 15) {
            System.out.println("Your demonization level is too high to interact with others. Please meditate to reduce negative influences.");
            return;
        }
        String action = isAlignment ? "align" : "exchange knowledge";
        System.out.println("With which player (index) do you want to " + action + "?");
        int targetIndex = scanner.nextInt();
        scanner.nextLine();
        if (currentWorld == earthWorld && targetIndex == 0) {
            System.out.println("You cannot interact with yourself. Please choose another player.");
        } else if (targetIndex >= 0 && targetIndex < currentWorld.humansCount) {
            if (isAlignment) {
                double oldAlignmentPlayer = player.neuronAlignment;
                double oldAlignmentTarget = currentWorld.humans[targetIndex].neuronAlignment;
                player.align(currentWorld.humans[targetIndex]);
                System.out.println("Alignment performed.");
                System.out.println("Player (Earth, Position 0): " + oldAlignmentPlayer + " -> " + player.neuronAlignment);
                System.out.println("Target player: " + oldAlignmentTarget + " -> " + currentWorld.humans[targetIndex].neuronAlignment);
            } else {
                double oldKnowledgePlayer = player.networkedKnowledge;
                double oldKnowledgeTarget = currentWorld.humans[targetIndex].networkedKnowledge;
                player.exchangeKnowledge(currentWorld.humans[targetIndex]);
                System.out.println("Knowledge exchange performed.");
                System.out.println("Player (Earth, Position 0): " + oldKnowledgePlayer + " -> " + player.networkedKnowledge);
                System.out.println("Target player: " + oldKnowledgeTarget + " -> " + currentWorld.humans[targetIndex].networkedKnowledge);
            }
            simulateTargetSubInteraction(targetIndex);
            if (currentWorld.type == WorldType.EARTH) {
                checkEarthDemonizationScenarios();
            }
        } else {
            System.out.println("Invalid player index.");
        }
    }
    
    /**
     * Simulates a subsequent interaction for the target human.
     */
    private void simulateTargetSubInteraction(int targetIndex) {
        Human target = currentWorld.humans[targetIndex];
        int oldDemon = target.demonizationLevel;
        int otherIndex = targetIndex;
        if (currentWorld.humansCount > 1) {
            do {
                otherIndex = (int)(Math.random() * currentWorld.humansCount);
            } while(otherIndex == targetIndex);
        }
        Human other = currentWorld.humans[otherIndex];
        if (Math.random() < 0.5) {
            double oldTargetAlignment = target.neuronAlignment;
            double oldOtherAlignment = other.neuronAlignment;
            target.align(other);
            System.out.println("Target (index " + targetIndex + ") aligned with human (index " + otherIndex + ").");
            System.out.println("Target alignment: " + oldTargetAlignment + " -> " + target.neuronAlignment);
            System.out.println("Other alignment: " + oldOtherAlignment + " -> " + other.neuronAlignment);
        } else {
            double oldTargetKnowledge = target.networkedKnowledge;
            double oldOtherKnowledge = other.networkedKnowledge;
            target.exchangeKnowledge(other);
            System.out.println("Target (index " + targetIndex + ") exchanged knowledge with human (index " + otherIndex + ").");
            System.out.println("Target knowledge: " + oldTargetKnowledge + " -> " + target.networkedKnowledge);
            System.out.println("Other knowledge: " + oldOtherKnowledge + " -> " + other.networkedKnowledge);
        }
        int newDemon = target.demonizationLevel;
        int diff = newDemon - oldDemon;
        if (diff > 0) {
            System.out.println("Due to the subsequent interaction, target's demonization increased by " + diff + ".");
            System.out.println("Player is penalized by the same amount.");
            player.demonizationLevel += diff;
        }
    }
    
    /**
     * When in Earth, allows the player to perform an intervention to heal childhood traumas.
     * This method now skips interaction with the player itself so that the player's knowledge remains unchanged.
     */
    private void healEarth() {
        if (currentWorld.type != WorldType.EARTH) {
            System.out.println("Heal Earth action is only available in the Earth world.");
            return;
        }
        System.out.println("Initiating intervention on Earth to heal childhood traumas...");
        // Skip index 0 (the player) so that player's knowledge is not increased.
        for (int i = 1; i < currentWorld.humansCount; i++) {
            double oldKnowledge = currentWorld.humans[i].networkedKnowledge;
            double oldKnowledgePlayer = player.networkedKnowledge;
            currentWorld.humans[i].exchangeKnowledge(player);
            
            System.out.println("Interaction with human " + i + ": Knowledge " + oldKnowledge + " -> " + currentWorld.humans[i].networkedKnowledge);
            currentWorld.humans[i].networkedKnowledge=oldKnowledge;
            player.networkedKnowledge=oldKnowledgePlayer;
        }
        double total = 0;
        // Calculate average knowledge for non-player inhabitants.
        for (int i = 1; i < currentWorld.humansCount; i++) {
            total += currentWorld.humans[i].networkedKnowledge;
        }
        double avg = total / (currentWorld.humansCount - 1);
        if (avg > currentWorld.knowledgeLevel * 1.2) {
            int reduction = Math.min(10, player.demonizationLevel);
            player.demonizationLevel -= reduction;
            System.out.println("Intervention successful! Your early traumas are healing. Demonization reduced by " + reduction + " points.");
        } else {
            System.out.println("Intervention not yet successful. Continue to engage in positive interactions to overcome childhood difficulties.");
        }
    }
    
    /**
     * Checks the player's demonization level in the Earth world and outputs scenario messages.
     */
    private void checkEarthDemonizationScenarios() {
        if (currentWorld.type != WorldType.EARTH) return;
        int d = player.demonizationLevel;
        if (d > 0 && d < 5) {
            System.out.println("Your early interactions indicate a challenging childhood.");
        } else if (d >= 5 && d < 15) {
            System.out.println("Your struggles are beginning to affect your academic performance and social interactions.");
        } else if (d >= 15 && d < 30) {
            System.out.println("You are experiencing significant challenges in school, university, and work. Relationships are suffering.");
        } else if (d >= 30) {
            System.out.println("Your unresolved childhood trauma is severely affecting every aspect of your life. Immediate intervention is necessary.");
        }
    }
    
    /**
     * Diagnoses the current demonization level of the player and provides suggestions.
     */
    public void diagnoseDemonization() {
        System.out.println("\n--- Demonization Diagnosis ---");
        System.out.println("Your current demonization level is: " + player.demonizationLevel);
        if (player.demonizationLevel < 5) {
            System.out.println("You are in a positive state. Keep up the positive energy exchange.");
        } else if (player.demonizationLevel < 15) {
            System.out.println("Your demonization level is moderate. Consider engaging in more positive interactions.");
        } else {
            System.out.println("Your demonization level is high. Negative influences are strongly affecting you.");
            System.out.println("To reduce demonization, focus on meditation, peaceful interactions, and counteracting negative influences.");
            System.out.println("Examine your interactions to identify potential triggers for these negative energies.");
        }
        System.out.println("--- End of Diagnosis ---\n");
    }
    
    /**
     * Simulates a meditative state to reduce the player's demonization level.
     */
    public void meditate() {
        System.out.println("Meditating to reduce negative energy...");
        int oldDemonization = player.demonizationLevel;
        player.demonizationLevel = Math.max(0, player.demonizationLevel - 10);
        System.out.println("Meditation complete. Your demonization level changed from " + oldDemonization + " to " + player.demonizationLevel + ".");
    }
}
