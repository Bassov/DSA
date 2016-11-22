package algorithms.shortest_path_dijkstra;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        LHD lhd = new LHD("russia.txt");
        lhd.calculateFromFile("input.txt", "output.txt");
    }

}
