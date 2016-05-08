import java.util.Comparator;

public class Rectangle {
    private int length;
    private int width;
    private int area;

    Rectangle(int length, int width) {
        this.length = length;
        this.width = width;
        this.area = length*width;
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
