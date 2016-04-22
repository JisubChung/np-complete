package packingRectangles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello");
		
		ArrayList<Rectangle> list = new ArrayList<Rectangle>();
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File("E:/JAVA projects/NPComplete/src/packingRectangles/instance.txt"));
			read(scanner, list);
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Please select an option: ");
			System.out.println("1: Find a pretty good solution");
			System.out.println("2: Find an exact solution");
			int option = reader.nextInt(); // Scans the next token of the input as an int.
			switch (option) {
				case 1: System.out.println("Finding a pretty good solution....");
						break;
				case 2: System.out.println("Finding a exact solution....");
						break;
				default: System.out.println("Invalid option");
						break;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("You're missing instance.txt");
		}		
	}
	
	public static void read(Scanner scanner, ArrayList<Rectangle> list) {
		while (scanner.hasNextLine()) {
			Scanner scanLine = new Scanner(scanner.nextLine());
			scanLine.useDelimiter(" ");
			if (scanLine.hasNext()) {
				int height = Integer.parseInt(scanLine.next());
				int width = Integer.parseInt(scanLine.next());
				list.add(new Rectangle(height, width));
			}
			scanLine.close();
		}
	}
	
	public static void listToString(ArrayList<Rectangle> list) {
		while (!list.isEmpty()) {
			System.out.println(list.get(0).getHeight() + " " + list.get(0).getWidth());
			list.remove(0);
		}
	}
}
