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
        boolean golSurvives = super.evolve(aliveNeighbors);

        if (this.isAlive) {
            if (!golSurvives) {
                graceCounter++;
                if (graceCounter >= 3) {
                    lifepoints -= 1;
                    graceCounter = 0; // reset after real death
                    return false;
                } else {
                    lifepoints += 1;
                    return true;
                }
            } else {
                graceCounter = 0;
                lifepoints += 1;
                return true;
            }
        } else { // DEAD
            boolean respawn = aliveNeighbors == 3;
            if (respawn) {
                graceCounter = 0;
                lifepoints = 0;
            }
            return respawn;
        }
    }

}
