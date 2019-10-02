package com.frontSAT.fSAT.model;

import java.util.List;

public class DepotMoyen {
    private List<Depot> depots;
    private int moyenne;

    public DepotMoyen(List<Depot> depots, int moyenne) {
        this.depots = depots;
        this.moyenne = moyenne;
    }

    public List<Depot> getDepots() {
        return depots;
    }

    public void setDepots(List<Depot> depots) {
        this.depots = depots;
    }

    public int getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(int moyenne) {
        this.moyenne = moyenne;
    }
}
