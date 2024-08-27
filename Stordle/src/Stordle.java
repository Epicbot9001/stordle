import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Stordle {
	private ArrayList<Approx> orange;
	private String[] green;
	private ArrayList<String> gray;
	private ArrayList<String> wordBank;

	public Stordle() {
		orange = new ArrayList<Approx>();
		green = new String[5];
		gray = new ArrayList<String>();
		wordBank = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader("wordle-answers-alphabetical.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordBank.add(line.toUpperCase());
			}
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}

	public void transcribe(String input, String g, String o) {
		boolean one = false, two = false, three = false, four = false, five = false;
		input = input.toUpperCase();
		for (int i = 0; i < g.length(); i++) {
			int a = Integer.parseInt(g.substring(i, i + 1)) - 1;
			if (a == 0) one = true;
			if (a == 1) two = true;
			if (a == 2) three = true;
			if (a == 3) four = true;
			if (a == 4) five = true;
			green[a] = Character.toString(input.charAt(a));
		}

		for(int i = 0; i < o.length(); i++) {
			int a = Integer.parseInt(o.substring(i, i + 1)) - 1;
			orange.add(new Approx(a, Character.toString(input.charAt(a))));
			if (a == 0) one = true;
			if (a == 1) two = true;
			if (a == 2) three = true;
			if (a == 3) four = true;
			if (a == 4) five = true;
		}

		if (!one && repeated(Character.toString(input.charAt(0))) && !duplicate(Character.toString(input.charAt(0)), input, 0)) gray.add(Character.toString(input.charAt(0)));
		if (!two && repeated(Character.toString(input.charAt(1))) && !duplicate(Character.toString(input.charAt(1)), input, 1)) gray.add(Character.toString(input.charAt(1)));
		if (!three && repeated(Character.toString(input.charAt(2))) && !duplicate(Character.toString(input.charAt(2)), input, 2)) gray.add(Character.toString(input.charAt(2)));
		if (!four && repeated(Character.toString(input.charAt(3))) && !duplicate(Character.toString(input.charAt(3)), input, 3)) gray.add(Character.toString(input.charAt(3)));
		if (!five && repeated(Character.toString(input.charAt(4))) && !duplicate(Character.toString(input.charAt(4)), input, 4)) gray.add(Character.toString(input.charAt(4)));

	}

	public boolean repeated(String s) {
		for (int i = 0; i < gray.size(); i++) {
			if (gray.get(i).equals(s)) {
				return false;
			}
		}
		return true;
	}

	public boolean duplicate(String letter, String word, int index) {
		for (int i = 0; i < word.length(); i++) {
			if (i != index) {
				if (letter.equals(Character.toString(word.charAt(i)))) {
					return true;
				}
			}
		}
		return false;
	}

	public void check() {
		for (int i = 0; i < 5; i++) {
			if (green[i] != null) {
				for (int j = 0; j < wordBank.size(); j++) {
					if (!Character.toString(wordBank.get(j).charAt(i)).equals(green[i])) {
						wordBank.remove(j);
						j--;
					}
				}
			}
		}

		for (int i = 0; i < orange.size(); i++) {
			for (int j = 0; j < wordBank.size(); j++) {
				//System.out.println("jb: " + j);
				boolean included = false;
				for (int k = 0; k < 5; k++) {
					if (k == orange.get(i).getIndex()) {
						if (Character.toString(wordBank.get(j).charAt(k)).equals(orange.get(i).getLetter())) {
							wordBank.remove(j);
							j--;
							included = true;;
							break;
						}
					} else if (Character.toString(wordBank.get(j).charAt(k)).equals(orange.get(i).getLetter())) {
						included = true;
					}
				}
				if (included == false) {
					wordBank.remove(j);
					j--;
				}
			}
		}

		for (int i = 0; i < gray.size(); i++) {
			for (int j = 0; j < wordBank.size(); j++) {
				for (int k = 0; k < 5; k++) {
					if (Character.toString(wordBank.get(j).charAt(k)).equals(gray.get(i))) {
						wordBank.remove(j);
						j--;
						break;
					}
				}
			}
		}

		for (int i = 0; i < wordBank.size(); i++) {
			System.out.println(wordBank.get(i));
		}
	}

	public void clear() {
		orange.clear();
		green = new String[5];
		gray.clear();
		try (BufferedReader br = new BufferedReader(new FileReader("wordle-answers-alphabetical.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordBank.add(line);
			}
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		while (true) {
			Stordle s = new Stordle();
			System.out.print("\n---------------------------------------------------------------------------------------\nFirst Word: ");
			Scanner scan1 = new Scanner(System.in);
			String firstWord = scan1.nextLine();
			while (firstWord.length() != 5) {
				System.out.print("You didn't input 5 letters! ヽ(ｏ`皿′ｏ)ﾉ\n\nFirst Word: ");
				firstWord = scan1.nextLine();
			}
			System.out.print("Position of green [1,2,3,4,5]: ");
			Scanner scan2 = new Scanner(System.in);
			String green = scan2.nextLine();
			System.out.print("Position of orange [1,2,3,4,5]: ");
			Scanner scan3 = new Scanner(System.in);
			String orange = scan3.nextLine();
			s.transcribe(firstWord, green, orange);
			s.check();

			System.out.print("\nSecond Word: ");
			String secondWord = scan1.next();
			if (secondWord.equals("e")) {
				s.clear();
				continue;
			}
			while (secondWord.length() != 5) {
				System.out.print("You didn't input 5 letters! ヽ(ｏ`皿′ｏ)ﾉ\n\nSecond Word: ");
				secondWord = scan1.nextLine();
			}
			System.out.print("Position of green [1,2,3,4,5]: ");
			green = scan2.nextLine();
			System.out.print("Position of orange [1,2,3,4,5]: ");
			orange = scan3.nextLine();
			s.transcribe(secondWord, green, orange);
			s.check();

			System.out.print("\nThird Word: ");
			String thirdWord = scan1.next();
			if (thirdWord.equals("e")) {
				s.clear();
				continue;
			}
			while (thirdWord.length() != 5) {
				System.out.print("You didn't input 5 letters! ヽ(ｏ`皿′ｏ)ﾉ\n\nThird Word: ");
				thirdWord = scan1.next();
			}
			System.out.print("Position of green [1,2,3,4,5]: ");
			green = scan2.nextLine();
			System.out.print("Position of orange [1,2,3,4,5]: ");
			orange = scan3.nextLine();
			s.transcribe(thirdWord, green, orange);
			s.check();

			System.out.print("\nFourth Word: ");
			String fourthWord = scan1.next();
			if (fourthWord.equals("e")) {
				s.clear();
				continue;
			}
			while (fourthWord.length() != 5) {
				System.out.print("You didn't input 5 letters! ヽ(ｏ`皿′ｏ)ﾉ\n\nFourth Word: ");
				fourthWord = scan1.nextLine();
			}
			System.out.print("Position of green [1,2,3,4,5]: ");
			green = scan2.nextLine();
			System.out.print("Position of orange [1,2,3,4,5]: ");
			orange = scan3.nextLine();
			s.transcribe(fourthWord, green, orange);
			s.check();

			System.out.print("\nFifth Word: ");
			String fifthWord = scan1.next();
			if (fifthWord.equals("e")) {
				s.clear();
				continue;
			}
			while (fifthWord.length() != 5) {
				System.out.print("You didn't input 5 letters! ヽ(ｏ`皿′ｏ)ﾉ\n\nFifth Word: ");
				fifthWord = scan1.nextLine();
			}
			System.out.print("Position of green [1,2,3,4,5]: ");
			green = scan2.nextLine();
			System.out.print("Position of orange [1,2,3,4,5]: ");
			orange = scan3.nextLine();
			s.transcribe(fifthWord, green, orange);
			s.check();
		}
	}
}
