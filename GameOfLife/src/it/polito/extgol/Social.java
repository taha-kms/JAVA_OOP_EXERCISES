package it.polito.extgol;

import jakarta.persistence.Entity;

@Entity
public class Social extends Cell {

    public Social() {
        super();
        this.cellType = CellType.SOCIAL;
    }

    @Override
    public Boolean evolve(int aliveNeighbors) {
        boolean willLive;

        if (this.isAlive) {
            willLive = aliveNeighbors >= 2 && aliveNeighbors <= 8;
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
