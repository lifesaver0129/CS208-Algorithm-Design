package knapsack;

import java.util.ArrayList;

public class Knapsack {
	public static int findKnapsackSolution(ArrayList<Item> input, int capacity, ArrayList<Item> output) {
		int n = input.size();
		int[][] M = new int[n + 1][capacity + 1];
		int[][] P = new int[n + 1][capacity + 1];

		for (int i = 0; i <= capacity; i++) {
			M[0][i] = 0;
		}

		for (int j = 1; j <= n; j++) {
			for (int i = 0; i <= capacity; i++) {
				int current_weight = input.get(j - 1).weight;
				int current_value = input.get(j - 1).value;

				if (current_weight > i) {
					M[j][i] = M[j - 1][i];
					P[j][i] = i;
				} else {
					int value = current_value + M[j - 1][i - current_weight];
					if (value > M[j - 1][i]) {
						M[j][i] = value;
						P[j][i] = i - current_weight;
					} else {
						M[j][i] = M[j - 1][i];
						P[j][i] = i;
					}
				}
			}
		}

		int i = capacity;
		for (int j = n; j > 0; j--) {
			if (P[j][i] != i) {
				output.add(input.get(j - 1));
				i = P[j][i];
			}
		}

		return M[n][capacity];
	}
}
