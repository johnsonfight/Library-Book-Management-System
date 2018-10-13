
public class Rank implements Comparable<Rank>  {
	
	String name;
	int value;
	int rank;
	int somthing;

	public Rank(String name, int value) {
		this.name = name;
		this.rank = 0;
		this.value = value;
		this.somthing = 0;
	}
	
	public Rank(String name, int value, int branch) {
		this.name = name;
		this.rank = 0;
		this.value = value;
		this.somthing = branch;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int compareTo(Rank other) {
		if (this.value < other.value)
			return 1;
		else if (this.value > other.value)
			return -1;
		else
			return 0;
	}
}
