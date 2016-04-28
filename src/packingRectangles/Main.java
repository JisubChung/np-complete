package packingRectangles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello");
		
		ArrayList<Rectangle> list = new ArrayList<Rectangle>();
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File("./src/packingRectangles/instance.txt"));
			read(scanner, list);
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int go = 0, option = 0;
			while (go == 0) {
				System.out.println("Please select an option: ");
				System.out.println("1: Find a pretty good solution");
				System.out.println("2: Find an exact solution");
				option = reader.nextInt(); // Scans the next token of the input as an int.
				Date date = new Date();
				long time1, time2;
				switch (option) {
					case 1: System.out.println("Finding a pretty good solution....");
							time1 = date.getTime();
							prettyGoodSolution();
							time2 = date.getTime();
							System.out.println((time2 - time1) + "ms" );
							go = 1;
							break;
					case 2: System.out.println("Finding a exact solution....");
							time1 = date.getTime();
							exactSolution();
							time2 = date.getTime();
							System.out.println((time2 - time1) + "ms" );
							go = 1;
							break;
					default: System.out.println("Invalid option");
							break;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("You're missing instance.txt");
		}		
	}
	
	private static void exactSolution() {
		// TODO Auto-generated method stub
	}

	private static void prettyGoodSolution() {
		// TODO Auto-generated method stub
		
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
