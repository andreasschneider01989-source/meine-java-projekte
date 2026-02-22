package s1;

/**
 * Represents a human in the simulation.
 */
public class Human {
    public double networkedKnowledge;
    public int life;
    public double neuronAlignment;
    public int demonizationLevel; // Tracks negative influence

    /**
     * Constructs a Human with the specified networked knowledge and life.
     *
     * @param networkedKnowledge the initial knowledge level
     * @param life the initial life value
     * @param neuronAlignment the neuron's alignment value
     * @param demonizationLevel the starting demonization level
     */
    public Human(double networkedKnowledge, int life, double neuronAlignment, int demonizationLevel) {
        this.networkedKnowledge = networkedKnowledge;
        this.life = life;
        this.neuronAlignment = neuronAlignment;
        this.demonizationLevel = demonizationLevel;
    }

    /**
     * Aligns the neuron's alignment with another human.
     */
    public void align(Human other) {
        double similarity = 1000 - Math.abs(this.neuronAlignment - other.neuronAlignment);
        double adjustment = (this.neuronAlignment - other.neuronAlignment) * Math.random() / 2;
        this.neuronAlignment -= adjustment;
        other.neuronAlignment += adjustment;
        
        if (similarity < 500) {
            this.demonizationLevel += 5;
        } else if (similarity < 700) {
            this.demonizationLevel += 2;
        } else if (similarity > 995) {
            this.demonizationLevel = Math.max(0, this.demonizationLevel - 1);
        }
    }

    /**
     * Exchanges knowledge with another human.
     */
    public void exchangeKnowledge(Human other) {
        double similarity = 1000 - Math.abs(this.neuronAlignment - other.neuronAlignment);
        double commonKnowledge = Math.min(this.networkedKnowledge, other.networkedKnowledge) * Math.random();
        double knowledgeTransfer1 = this.networkedKnowledge - commonKnowledge;
        double knowledgeTransfer2 = other.networkedKnowledge - commonKnowledge;

        if (similarity > 995) {
            this.networkedKnowledge += (knowledgeTransfer2 * Math.random());
            other.networkedKnowledge += (knowledgeTransfer1 * Math.random());
            this.demonizationLevel = Math.max(0, this.demonizationLevel - 1);
        } else if (similarity > 700) {
            this.networkedKnowledge += (knowledgeTransfer2 * Math.random() * Math.random());
            other.networkedKnowledge += (knowledgeTransfer1 * Math.random() * Math.random());
        } else if (similarity > 500) {
            this.networkedKnowledge -= (knowledgeTransfer2 * Math.random() * Math.random());
            other.networkedKnowledge -= (knowledgeTransfer1 * Math.random() * Math.random());
            this.demonizationLevel += 2;
        } else {
            this.networkedKnowledge -= (knowledgeTransfer2 * Math.random());
            other.networkedKnowledge -= (knowledgeTransfer1 * Math.random());
            this.demonizationLevel += 5;
        }

        if (this.networkedKnowledge < 0)
            this.networkedKnowledge = 0;
        if (other.networkedKnowledge < 0)
            other.networkedKnowledge = 0;
    }
}
