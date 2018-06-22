package knapsack;

public class Item {
	int value;
	int weight;

	public Item(int value, int weight) {
		this.value = value;
		this.weight = weight;
	}

	public int getValue() {
		return value;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "(value:" + value + ",weight:" + weight + ")";
	}
}
