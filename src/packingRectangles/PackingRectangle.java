package packingRectangles;

public class PackingRectangle {
	int height;
	int width;
	boolean[][] packingRectangle;

	PackingRectangle(int height, int width) {
		this.height = height;
		this.width = width;
		packingRectangle = new boolean[width][height];
		initialize();
	}
	
	public boolean getCell(int row, int col) {
		return packingRectangle[row][col];
	}
	
	private void flipCell(int row, int col) {
		if(!getCell(row,col)) {
			packingRectangle[row][col] = true;
		} else {
			packingRectangle[row][col] = false;
		}
	}
	
	// make packingRectangle to have false on all cells
	public void initialize() {
		for(int row = 0; row < width; row++) {
			for(int col = 0; col < height; col ++) {
				packingRectangle[row][col] = false;
			}
		}
	}
	
	public boolean tryFit(Rectangle rectangle) {
		boolean doesFit = false;
		int rectHt = rectangle.getHeight();
		int rectWd = rectangle.getWidth();
		int col = 0, row = 0;
		
		// This first part finds a location for the rectangle
		for (col = 0; col <= this.height - rectHt && !doesFit; col++) {
			for (row = 0; row <= this.width - rectWd && !doesFit; row++) {
				if (!getCell(row, col)) {
					doesFit = true;
					for (int col2 = col; col2 < col + rectHt && doesFit; col2++) {
						if (getCell(row, col)) {
							doesFit = false;
						}
					}
				}
			}
		}
		col--;
		row--;
		
		// This part actually stuffs the rectangle
		if (doesFit) {
			for (int nCol = col; nCol < col + rectHt; nCol++) {
				for (int nRow = row; nRow < row + rectWd; nRow++) {
					flipCell(nRow, nCol);
				}
			}
		}
		return doesFit;
	}
	
	public void toStringPack() {
		for(int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				if(getCell(row, col)) {
					System.out.print('1');
				} else {
					System.out.print('0');
				}
				
			}
			System.out.println();
		}
	}
}
