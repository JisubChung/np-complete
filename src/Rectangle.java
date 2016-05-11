import java.util.Comparator;
import java.util.Stack;

public class Rectangle {
    private int length;
    private int width;
    private int area;
    private Stack<Integer> colHistory = new Stack();
    private Stack<Integer> rowHistory = new Stack();
    private boolean isUsed;

    Rectangle(int length, int width) {
        this.length = length;
        this.width = width;
        this.area = length*width;
        this.isUsed = false;
    }

    public boolean isUsed() {
        return this.isUsed;
    }

    public void useThisRectangle() {
        if (isUsed) {
            System.out.println("you're trying to use an already used rectangle");
        }
        else {
            isUsed = true;
        }
    }

    public void dontUseThisRectangle() {
        if (!isUsed) {
            System.out.println("You haven't used this rectangle");
        } else {
            isUsed = false;
        }
    }

    public int getLength() {
        return length;
    }
    public int getWidth() {
        return width;
    }
    public int getArea() {
        return area;
    }

    public int getAddedCol() {
        return colHistory.peek();
    }

    public void addCol(int addedCol) {
        colHistory.push(addedCol);
    }

    public int getAddedRow() {
        return rowHistory.peek();
    }

    public void addRow(int addedRow) {
        rowHistory.push(addedRow);
    }

    public void removeRectangleHistory() {
        rowHistory.pop();
        colHistory.pop();
    }

    /**
     * All sorts are >
     * Usage Guide:
     * Collections.sort(list, Rectangle.lengthComparator);
     * Collections.sort(list, Rectangle.widthComparator);
     * Collections.sort(list, Rectangle.areaComparator);
     */
    public static Comparator<Rectangle> lengthComparator = new Comparator<Rectangle>() {
        @Override
        public int compare(Rectangle rect1, Rectangle rect2) {
            return (rect2.getLength() < rect1.getLength() ? -1 :
                    (rect2.getLength() == rect1.getLength() ? 0 : 1));
        }
    };

    public static Comparator<Rectangle> widthComparator = new Comparator<Rectangle>() {
        @Override
        public int compare(Rectangle rect1, Rectangle rect2) {
            return (rect2.getWidth() < rect1.getWidth() ? -1 :
                    (rect2.getWidth() == rect1.getWidth() ? 0 : 1));
        }
    };

    public static Comparator<Rectangle> areaComparator = new Comparator<Rectangle>() {
        @Override
        public int compare(Rectangle rect1, Rectangle rect2) {
            return (rect2.getArea() < rect1.getArea() ? -1 :
                    (rect2.getArea() == rect1.getArea() ? 0 : 1));
        }
    };
}
