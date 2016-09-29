package algorithms.knapsack;

class Knapsack {

    public int knapsack(int weights[], int values[], int totalWeight) {
        int count = values.length;
        Item[] items = new Item[count];

        for (int i = 0; i < count; i++) {
            items[i] = new Item(weights[i], values[i]);
        }

        int[][] table = new int[count + 1][totalWeight + 1];

        for (int col = 0; col <= totalWeight; col++) {
            table[0][col] = 0;
        }

        for (int row = 0; row <= count; row++) {
            table[row][0] = 0;
        }

        for (int item = 1;item <= count; item++) {
            for (int weight = 1; weight <= totalWeight; weight++) {
                if (items[item - 1].w <= weight) {
                    int old = table[item - 1][weight];
                    int current = items[item - 1].c + table[item - 1][weight - items[item - 1].w];
                    table[item][weight] = Math.max(old, current);
                }
                else {
                    table[item][weight] = table[item - 1][weight];
                }
            }
        }

        return table[count][totalWeight];
    }

    private class Item {
        private int w;
        private int c;

        private Item(int w, int c) {
            this.w = w;
            this.c = c;
        }
    }

}