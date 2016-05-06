package packingRectangles;

import java.util.Comparator;

public class Rectangle {
	private int height;
	private int width;
	private int area;
	
	Rectangle(int height, int width) {
		this.height = height;
		this.width = width;
		this.area = height*width;
	}
	
	public int getHeight() {
		return height;
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
	 * Collections.sort(list, Rectangle.heightComparator);
	 * Collections.sort(list, Rectangle.widthComparator);
	 * Collections.sort(list, Rectangle.areaComparator);
	 */
	public static Comparator<Rectangle> heightComparator = new Comparator<Rectangle>() {         
	    @Override         
	    public int compare(Rectangle rect1, Rectangle rect2) {             
	      return (rect2.getHeight() < rect1.getHeight() ? -1 :                     
	              (rect2.getHeight() == rect1.getHeight() ? 0 : 1));           
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
