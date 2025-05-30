package it.polito.extgol;

import jakarta.persistence.Entity;

@Entity
public class Highlander extends Cell {

    private int graceCounter = 0;

    public Highlander() {
        super();
        this.cellType = CellType.HIGHLANDER;
    }

    @Override
    public Boolean evolve(int aliveNeighbors) {
        boolean survivesGOL = super.evolve(aliveNeighbors);

        if (!survivesGOL) {
            if (graceCounter < 2) {
                graceCounter++;
                if (this.isAlive)
                    lifepoints += 1;
                return true;
            } else {
                graceCounter = 0;
                if (this.isAlive)
                    lifepoints -= 1;
                return false;
            }
        } else {
            graceCounter = 0;
            if (this.isAlive) {
                lifepoints += 1;
            } else {
                lifepoints = 0; // respawn
            }
            return true;
        }
    }

}
