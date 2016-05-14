import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * FOR ALL CASES:
 *      Length: left to right
 *      Width : top to bottom
 */

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");

        ArrayList<Rectangle> list = new ArrayList<>();

        Scanner scanner;
        try {
            scanner = new Scanner(new File("./src/instance.txt"));
            read(scanner, list);
            Scanner reader = new Scanner(System.in);

            boolean go = true;
            int option;
            while (go) {
                System.out.println("Please select an option: ");
                System.out.println("1: Find a pretty good solution");
                System.out.println("2: Find an exact solution");
                System.out.println("3: Find both");
                option = reader.nextInt();
                ArrayList<PackingRectangle> solution;
                PackingRectangle pack;
                long time1, time2;
                int length, width, yes;
                switch (option) {
                    case 1:
                        System.out.println("Finding polynomial solutions...");
                        time1 = System.currentTimeMillis();
                        solution = prettyGoodSolution(list);
                        time2 = System.currentTimeMillis();
                        length = solution.size() * solution.get(0).getLength();
                        width = solution.get(0).getWidth();
                        System.out.println("Polynomial Solution:");
                        System.out.println("Duration: " + (time2 - time1) + "ms");
                        System.out.println("Dimensions: " + length + "x" + width);
                        System.out.println("Area: " + length*width);
                        go = false;

                        System.out.println("Would you like a print of the result? (1 for yes)");
                        yes = reader.nextInt();
                        if( yes == 1) {
                            printPolySolution(solution);
                        }
                        break;
                    case 2:
                        System.out.println("Finding a exact solution....");
                        time1 = System.currentTimeMillis();
                        pack = exactSolution(list);
                        time2 = System.currentTimeMillis();
                        length = pack.getLength();
                        width = pack.getWidth();
                        System.out.println("Exact Solution:");
                        System.out.println("Duration: " + (time2 - time1) + "ms");
                        System.out.println("Dimensions: " + length + "x" + width);
                        System.out.println("Area: " + length * width);
                        go = false;

                        System.out.println("Would you like a print of the result? (1 for yes)");
                        yes = reader.nextInt();
                        if( yes == 1) {
                            pack.toStringPack();
                        }
                        break;
                    case 3:
                        System.out.println("Finding both solutions...");
                        time1 = System.currentTimeMillis();
                        solution = prettyGoodSolution(list);
                        time2 = System.currentTimeMillis();
                        length = solution.size() * solution.get(0).getLength();
                        width = solution.get(0).getWidth();
                        System.out.println("Polynomial Solution:");
                        System.out.println("Duration: " + (time2 - time1) + "ms");
                        System.out.println("Dimensions: " + length + "x" + width);
                        System.out.println("Area: " + length*width);

                        System.out.println("=========");

                        time1 = System.currentTimeMillis();
                        pack = exactSolution(list);
                        time2 = System.currentTimeMillis();
                        length = pack.getLength();
                        width = pack.getWidth();
                        System.out.println("Exact Solution:");
                        System.out.println("Duration: " + (time2 - time1) + "ms");
                        System.out.println("Dimensions: " + length + "x" + width);
                        System.out.println("Area: " + length * width);
                        go = false;

                        System.out.println("Would you like a print of the result? (1 for yes)");
                        yes = reader.nextInt();
                        if( yes == 1) {
                            System.out.println("---polynomial---");
                            printPolySolution(solution);
                            System.out.println("---exact---");
                            pack.toStringPack();
                        }
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("You're missing instance.txt");
        }
        System.out.println("done!");
    }

    private static void read(Scanner scanner, ArrayList<Rectangle> list) {
        while (scanner.hasNextLine()) {
            Scanner scanLine = new Scanner(scanner.nextLine());
            scanLine.useDelimiter(" ");
            if (scanLine.hasNext()) {
                int length = Integer.parseInt(scanLine.next());
                int width = Integer.parseInt(scanLine.next());
                list.add(new Rectangle(length, width));
            }
            scanLine.close();
        }
    }

    /**
     * Solves for the exact solution of the PackingProblem
     * <p>
     * The general idea for this is to start with the minimum area
     * generated by the sum of the areas of the rectangles. When
     * it is realized that a progression will not work, then abort
     * and expand the smaller side (and shrink the larger side).
     * This continues until all possible dimensions are attempted.
     * If an answer has yet to be found, then increase the area.
     * <p>
     * This process is going to be done over the following steps:
     * 1. Find the minimum side (minSide, length or width)
     * 2. Find the minimum area (minArea)
     * 3. Try a packing rectangle of minArea with minSide
     * 4. Try to pack rectangles
     * 5. If this fails, lengthen minSide and return to step 3
     * 6. If minSide becomes too large then increase minArea
     * * ie: when minArea/minSide >= minOtherSide
     * 7. The first successful rectangle packing is the minimum
     */
    private static PackingRectangle exactSolution(ArrayList<Rectangle> list) {

        // First: Find minSide
        int minLength = findMinLength(list).getLength();
        int minWidth = findMinWidth(list).getWidth();
        boolean usingLength = false;
        if (minLength <= minWidth) {
            usingLength = true;
        }

        // Second: Find minArea
        int currentArea = findMinArea(list);

        // Third: Figure packing parameters
        int length = minLength-1, width = minWidth-1;

        // Fourth: Try a packing
        PackingRectangle pack;
        Collections.sort(list, Rectangle.areaComparator);

        do {
            if (usingLength) {
                do {
                    length++;
                } while (currentArea % length != 0 && length < currentArea/2);
                width = currentArea/length;
            } else {
                do {
                    width++;
                } while (currentArea % width != 0 && width < currentArea/2);
                length = currentArea/width;
            }
            // Sixth: Try new Area
            if (length > currentArea/minLength || width > currentArea/minWidth) {
                currentArea++;
                length = minLength-1;
                width = minWidth-1;
            }
            // Fifth: Try new parameters
            pack = new PackingRectangle(length, width);
        } while (!someonePackThis(pack, list));
        return pack;
    }

    private static Rectangle findMinLength(ArrayList<Rectangle> list) {
        //just initialize it to first length
        Rectangle rectangle = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (rectangle.getLength() < list.get(i).getLength()) {
                rectangle = list.get(i);
            }
        }
        return rectangle;
    }

    private static Rectangle findMinWidth(ArrayList<Rectangle> list) {
        //just initialize it to first length
        Rectangle rectangle = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (rectangle.getWidth() < list.get(i).getWidth()) {
                rectangle = list.get(i);
            }
        }
        return rectangle;
    }

    private static int findMinArea(ArrayList<Rectangle> list) {
        int minArea = 0;
        for (Rectangle rectangle: list) {
            minArea += rectangle.getArea();
        }
        return minArea;
    }

    /**
     * attempts to fit all rectangles into pack (length x width)
     * This works as follows:
     * 1. Create pack
     * 2. Sort rectangles by area
     * 3. Recursively try to fit rectangles into pack
     *
     * This first method is the driver
     */
    private static boolean someonePackThis(PackingRectangle pack, ArrayList<Rectangle> list) {
        boolean doesFit = false;
        if (list.isEmpty()) {
            doesFit = true;
        } else {
            for (int i = 0; i < list.size() && !doesFit; i++) {
                doesFit = someonePackThis(pack, list, i);
            }
        }
        return doesFit;
    }

    private static boolean someonePackThis(PackingRectangle pack, ArrayList<Rectangle> list, int index) {
        boolean doesFit = false;

        // Terminate recursions upon reaching last element, note this doesn't always conclude recursion
        if (pack.howManyRectanglesPacked() == list.size()-1) {
            int i = 0;
            while (i < list.size() && list.get(i).isUsed()) {
                i++;
            }
            list.get(i).useThisRectangle();
            doesFit = pack.whereWillThisFit(list.get(i));
            if (!doesFit) {
                list.get(i).dontUseThisRectangle();
            } else {
                pack.flipRectangle(list.get(i).getAddedRow(), list.get(i).getAddedCol(), list.get(i));
            }
        } else {
            int i = index, k = 0;
            while(k < list.size() && !doesFit && !list.get(i).isUsed() && pack.howManyRectanglesPacked() < list.size() && pack.whereWillThisFit(list.get(i))) {

                // identify the rectangle to be packed
                list.get(i).useThisRectangle();

                // pack the rectangle
                pack.flipRectangle(list.get(i).getAddedRow(), list.get(i).getAddedCol(), list.get(i));

                // recursively pack another rectangle
                doesFit = someonePackThis(pack, list, ((i+1)%list.size()));

                // if the recursion doesn't give a packed rectangle then remove this rectangle
                if (!doesFit) {
                    list.get(i).dontUseThisRectangle();
                    pack.flipRectangle(list.get(i).getAddedRow(), list.get(i).getAddedCol(), list.get(i));
                }
                i++;
                k++;
                if (i >= list.size()) {
                    i = i%list.size();
                }
            }
        }
        return doesFit;
    }

    /**
     * The general plan for this is a sort of First-Fit bin packing algorithm. We'll first need to create a "bin" which
     * will be defined as the longest length/width. Thus each bin will be a packing rectangle. Then we'll simply pack
     * each of these "bins". As all of the bins will be of the same dimensions we can simply add up their areas to find
     * the area of the greater packing rectangle. This is a 2-approximation.
     *
     * This process is going to be done over the following steps:
     *      1. Create bin dimensions
     *      2. Begin packing bins
     *          a. If a rectangle will fit in a bin, then stuff it
     *          b. else put it in a new bin
     */
    private static ArrayList<PackingRectangle> prettyGoodSolution(ArrayList<Rectangle> rectList) {

        // First: Find dimensions
        int length, width;
        Rectangle rectangleLength = findMinLength(rectList);
        Rectangle rectangleWidth = findMinWidth(rectList);

        if (rectangleLength == rectangleWidth) {

            // There is the special case where the bin can be the exact size of a rectangle
            length = rectangleLength.getLength();
            width = rectangleWidth.getWidth();
        } else {

            // Otherwise we need to account for a bad case scenario
            length = rectangleLength.getLength();
            length += rectangleWidth.getLength();
            width = rectangleWidth.getWidth();
            width += rectangleLength.getWidth();
        }

        // Second: Pack Bins
        ArrayList<PackingRectangle> listOfBins = new ArrayList<>();

        for (int rectIndex = 0; rectIndex < rectList.size(); rectIndex++) {
            boolean added = false;

            // Try to fit it in a pre-existing mini-packingRectangle
            for (int binIndex = 0; binIndex < listOfBins.size(); binIndex++) {
                if (listOfBins.get(binIndex).whereWillThisFit(rectList.get(rectIndex))) {
                    listOfBins.get(binIndex).flipRectangle(rectList.get(rectIndex).getAddedRow(), rectList.get(rectIndex).getAddedCol(), rectList.get(rectIndex));
                    added = true;
                }
            }

            // Couldn't add it to a pre-existing mini-packingRectangle, so create a new one
            if (!added) {
                listOfBins.add(new PackingRectangle(length, width));
                listOfBins.get(listOfBins.size()-1).whereWillThisFit(rectList.get(rectIndex));
                listOfBins.get(listOfBins.size()-1).flipRectangle(rectList.get(rectIndex).getAddedRow(), rectList.get(rectIndex).getAddedCol(), rectList.get(rectIndex));
            }
        }

        return listOfBins;
    }

    private static void printPolySolution(ArrayList<PackingRectangle> list) {
        for(int k = 0; k < list.get(0).getWidth(); k++) {
            for(int i = 0; i < list.get(0).getLength()*list.size(); i++) {
                if(i%list.get(0).getLength() == 0 && i != 0) {
                    System.out.print("|");
                }
                if(list.get(i/list.get(0).getLength()).getCell(k, i%(list.get(0).getLength()))) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }
}