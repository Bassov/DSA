package algorithms.optimizationMST;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        LHD lhd = new LHD("LHD/russia.txt");
        lhd.calculateFromFile("LHD/input.txt", "LHD/output.txt");
    }

}
