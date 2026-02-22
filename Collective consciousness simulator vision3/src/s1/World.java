package s1;

/**
 * Represents a world in the simulation.
 */
public class World {
    public int humansCount;
    public Human[] humans;
    public double knowledgeLevelAlt;
    public double knowledgeLevel;
    public WorldType type;
    public boolean rescued; // Only relevant for Dogscape

    /**
     * Constructs a World with the given type, number of humans, and knowledge level.
     *
     * @param type the type of the world
     * @param humansCount the number of humans in the world
     * @param knowledgeLevel the base knowledge level for inhabitants
     */
    public World(WorldType type, int humansCount, int knowledgeLevel) {
        this.type = type;
        this.humansCount = humansCount;
        knowledgeLevelAlt = knowledgeLevel;
        this.knowledgeLevel = knowledgeLevel;
        this.humans = new Human[humansCount];
        if (type == WorldType.DOGSCAPE) {
            this.rescued = false;
        }
        for (int i = 0; i < humansCount; i++) {
            int life = 20;
            double networkedKnowledge = knowledgeLevel + (int)(Math.random() * knowledgeLevel);
            if (type == WorldType.GOD_PLANT) {
                life = 10;
            }
            humans[i] = new Human(networkedKnowledge, life, Math.random() * 1000, 0);
        }
    }

    /**
     * Applies world-specific effects during a simulation step.
     */
    
    public void newNoledgelevel() {
    	double noledgeSum=0;
    	for(int i=0; i<humans.length; i++) {
    		noledgeSum=noledgeSum+humans[i].networkedKnowledge;
    	}
    	double nolageEver=noledgeSum/humans.length;
    	double nolageReal=noledgeSum/4*3;
    	knowledgeLevel=nolageReal;
    }
    
    public void applyWorldEffects() {
        switch (type) {
            case ORACLE_WORLD:
                System.out.println("The dark angel acts in the Oracle World, attempting to hinder the player.");
                if (humansCount > 0) {
                    humans[0].networkedKnowledge *= 0.95;
                }
                break;
            case DOGSCAPE:
                if (!rescued) {
                    System.out.println("Dogscape is in chaos: the ecosystem is collapsing and packs of dogs attack waking humans.");
                    int index = (int)(Math.random() * humansCount);
                    humans[index].life -= 1;
                } else {
                    System.out.println("Dogscape has been rescued: the ecosystem stabilizes and aggressive attacks subside.");
                    for (int i = 0; i < humansCount; i++) {
                        humans[i].life += 1;
                    }
                }
                break;
            case ZOMBIE_WORLD:
                System.out.println("In Zombie World, flesh-eaters wander around.");
                int index = (int)(Math.random() * humansCount);
                humans[index].networkedKnowledge *= 0.9;
                break;
            case ICE_WORLDS:
                System.out.println("The icy conditions of the Ice Worlds hinder survival.");
                index = (int)(Math.random() * humansCount);
                humans[index].life -= 2;
                break;
            default:
                System.out.println("No special effects in world " + type + ".");
                break;
        }
    }
}
