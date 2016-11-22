package algorithms.optimizationMST;

public class Weight {

    double dist;
    double time;
    double cost;

    public Weight(String params) {
        String[] p = params.split(":");
        this.dist = Double.parseDouble(p[0]);
        this.time = Double.parseDouble(p[1]);
        this.cost = Double.parseDouble(p[2]);
    }

}