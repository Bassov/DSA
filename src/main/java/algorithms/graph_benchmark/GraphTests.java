package algorithms.graph_benchmark;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 *
 * @author s.protasov
 */
public class GraphTests {

    static long ts = -1;

    public static void tic() {
        ts = System.nanoTime();
    }

    /**
     *
     * @return milliseconds
     */
    public static float tac() {
        return (System.nanoTime() - ts) / 1e6f;
    }

    public static long mem() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //TODO use your graph class
        MyGraph<String, Integer> g;
        int SIZES = 8;
        String[] names = new String[]{"sparce", "low", "medium", "high", "dense"};
        float[] ratios = new float[]{0.03f, 0.25f, 0.5f, 0.75f, 0.97f};

        String[] params = new String[] { "mem", "addedge", "removeedge", "addnode", "removemnode", "areadj", "adjnodes" };

        // stores all resulting information as [matrix][size][methods]
        float[][][] cube = new float[names.length][SIZES][params.length];

        // for each density
        for (int i = 0; i < names.length; i++) {
            // for each size
            System.gc();
            System.out.printf("%s graph [%.2f]:\n", names[i], ratios[i]);

            //TODO: tune your numbers here
            for (int s = 0, size=100;  s < SIZES; s++,size *=2) {
                System.gc();
                System.out.printf("    %d nodes:\n", size);

                //TODO: adjust getGraph method for your class
                g = getGraph(size, ratios[i]);

                // ------------------ MEM ----------------------------------
                float memory =  mem() / 1024;
                cube[i][s][0] = memory;
                System.out.printf("        Mem: %.0f KB\n", memory);
                //-----------------------------------------------------------

                Random rand = new Random();
                int N = 100;
                float time;

                //--------------------- ADD EDGE ----------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here
                    g.insertEdge(g.vertices[from], g.vertices[to], 1);
                }
                time = tac() / N * 10 * 10 * 10 * 10 * 10 * 10;
                cube[i][s][1] = time;
                System.out.printf("        Add edge: %.0f ms\n", time);
                //------------------------------------------------------------

                //--------------------- REMOVE EDGE --------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here
                    g.removeEdge(g.vertices[from], g.vertices[to]);
                }
                time = tac() / N * 10 * 10 * 10 * 10 * 10 * 10;
                cube[i][s][2] = time;
                System.out.printf("        Remove edge: %.0f ms\n", time);
                //------------------------------------------------------------

                //--------------------- ARE ADJACENT ------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here
                    g.areAdjacent(g.vertices[from], g.vertices[to]);
                }
                time = tac() / N * 10 * 10 * 10 * 10 * 10 * 10;
                cube[i][s][5] = time;
                System.out.printf("        Are adjacent: %.0f ms\n", time);
                //------------------------------------------------------------

                //--------------------- ADJACENT NODES -----------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size);
                    //TODO: your code here
                    g.neighbors(g.vertices[from]);
                }
                time = tac() / N * 10 * 10 * 10 * 10 * 10 * 10;
                cube[i][s][6] = time;
                System.out.printf("        Adjacent nodes: %.0f ms\n", time);
                //------------------------------------------------------------

                //--------------------- REMOVE VERTEX ------------------------
                g = getGraph(size, ratios[i]);
                //--------------------- ADD EDGE FOR NEW GRAPH----------------
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here
                    g.insertEdge(g.vertices[from], g.vertices[to], 1);
                }
                //------------------------------------------------------------
                tic();
                for (int x = 0; x < g.vertices.length; x++) {
                    g.removeVertex(g.vertices[x]);
                }
                time = tac() / N * 10 * 10 * 10 * 10 * 10 * 10;
                cube[i][s][4] = time;
                System.out.printf("        Remove vertex: %.0f ms\n", time);
                //------------------------------------------------------------

                //--------------------- ADD VERTEX ---------------------------
                tic();
                for (int x = 0; x < g.vertices.length; x++) {
                    g.insertVertex(g.vertices[x]);
                }
                time = tac() / N * 10 * 10 * 10 * 10 * 10 * 10;
                cube[i][s][3] = time;
                System.out.printf("        Add vertex: %.0f ms\n", time);
                //------------------------------------------------------------
            }
        }
    }

    /**
     *
     * @param nodes number of nodes
     * @param edgeRatio 0..1 of all possible edges
     * @return generated graph
     */
    public static MyGraph<String, Integer> getGraph(int nodes, float edgeRatio) {
        Random rand = new Random();
        String[] ids = new String[nodes];
        for (int i = 0; i < nodes; i++) {
            ids[i] = Integer.toString(new Integer(i).hashCode());
        }
        String[] unique =  new HashSet<>((List<String>)Arrays.asList(ids)).toArray(new String[0]);
        // keeping only unique values
        MyGraph<String, Integer> g = new MyGraph<>(unique);
        if (edgeRatio > 0.5) {
            int total = nodes * (nodes - 1) / 2;
            int count = total;
            // add all nodes
            for (int i = 0; i < nodes; i++) {
                for (int j = i + 1; j < nodes; j++) {
                    //TODO: place your code here
                    g.setEdgeBoth(g.vertices[i], g.vertices[j], 1);
                }
            }

            // remove while more
            while (edgeRatio * total < count) {
                int from = rand.nextInt(nodes), to = rand.nextInt(nodes);
                //TODO: place your code here
                if (from != to) {
                    if (g.removeEdge(g.vertices[from], g.vertices[to]) != null) {
                        count--;
                    }
                }
            }
        } else {
            int total = nodes * (nodes - 1) / 2;
            int count = 0;
            // add while less
            while (edgeRatio * total > count) {
                int from = rand.nextInt(nodes), to = rand.nextInt(nodes);
                //TODO: place your code here
                if (from != to) {
                    if (g.setEdge(g.vertices[from], g.vertices[to], 1) == null) {
                        count++;
                    }
                }
            }
        }
        return g;
    }
}
