package knapsack;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class KnapsackTest {
	ArrayList<Item> input = new ArrayList<>();
	int totalweight;

	@Before
	public void setUp() throws Exception {
		input.add(new Item(1, 1));
		input.add(new Item(6, 2));
		input.add(new Item(18, 5));
		input.add(new Item(22, 6));
		input.add(new Item(28, 7));

		totalweight = 0;
		for (Item item : input) {
			totalweight += item.getWeight();
		}
	}

	@Test
	public void test() {

		for (int weight_limit = 0; weight_limit <= totalweight; weight_limit++) {
			ArrayList<Item> output = new ArrayList<>();

			int max_value = Knapsack.findKnapsackSolution(input, weight_limit, output);
			System.out.println("Max value is:" + max_value + " using weight limit:" + weight_limit);

			System.out.println("Selected items:");
			for (Item item : output) {
				System.out.println(item);
			}
			System.out.println();
		}
	}

}
