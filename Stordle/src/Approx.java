public class Approx {
	private int index;
	private String letter;

	public Approx(int index, String letter) {
		this.index = index;
		this.letter = letter;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int i) {
		index = i;
	}

	public String getLetter() {
		return letter;
	}
	public void setLetter(String l) {
		letter = l;
	}

	public String toString() {
		return letter;
	}
}
