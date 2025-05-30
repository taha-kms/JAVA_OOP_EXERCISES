package it.polito.extgol;

import jakarta.persistence.Entity;

@Entity
public class Loner extends Cell {

    public Loner() {
        super();
        this.cellType = CellType.LONER;
    }

    @Override
    public Boolean evolve(int aliveNeighbors) {
        boolean willLive;

        if (this.isAlive) {
            willLive = aliveNeighbors >= 1 && aliveNeighbors <= 3;
        } else {
            willLive = aliveNeighbors == 3;
        }

        if (this.isAlive) {
            lifepoints += willLive ? 1 : -1;
        } else if (willLive) {
            lifepoints = 0;
        }

        return willLive;
    }

}
