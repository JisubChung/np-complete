public class PackingRectangle {
    private int length;
    private int width;
    private boolean[][] packingRectangle;
    private int numberOfRectanglesPacked;

    /**
     * A note: all array calls are supposed to:
     *      packingRectangle[row][column]
     *      column = length (left right)
     *      row = width (up down)
     */

    PackingRectangle(int length, int width) {
        this.length = length;
        this.width = width;
        this.numberOfRectanglesPacked = 0;
        packingRectangle = new boolean[width][length];
        initialize();
    }

    public int howManyRectanglesPacked() {
        return numberOfRectanglesPacked;
    }

    public void packedARectangle() {
        numberOfRectanglesPacked++;
    }

    public void unpackARectangle() {
        numberOfRectanglesPacked--;
    }

    private boolean getCell(int row, int col) {
        return packingRectangle[row][col];
    }

    // this method FLIPS the rectangle for reusability
    public void flipRectangle(int row, int col, Rectangle rectangle) {
        boolean added = true;
        for (int nCol = col; nCol < col + rectangle.getLength(); nCol++) {
            for (int nRow = row; nRow < row + rectangle.getWidth(); nRow++) {
                if(!getCell(nRow, nCol)) {
                    packingRectangle[nRow][nCol] = true;
                } else {
                    packingRectangle[nRow][nCol] = false;
                    added = false;

                }
            }
        }
        if (added) {
            rectangle.addCol(col);
            rectangle.addRow(row);
            packedARectangle();
        } else {
            rectangle.removeRectangleHistory();
            unpackARectangle();
        }
    }

    // make packingRectangle to have false on all cells
    public void initialize() {
        for(int row = 0; row < width; row++) {
            for(int col = 0; col < length; col ++) {
                packingRectangle[row][col] = false;
            }
        }
    }

    public boolean whereWillThisFit(Rectangle rectangle) {
        boolean doesFit = false;
        int rectLt = rectangle.getLength();
        int rectWd = rectangle.getWidth();
        int col = 0, row = 0;

        // This first part finds a location for the rectangle
        for (row = 0; row <= this.width - rectWd && !doesFit; row++) {
            for (col = 0; col <= this.length - rectLt && !doesFit; col++) {
                if (!getCell(row, col)) {
                    doesFit = true;
                    for (int row2 = row; row2 < row + rectWd && doesFit && row2 < this.width; row2++) {
                        if (getCell(row2,col)) {
                            doesFit = false;
                        }
                    }
                    for (int col2 = col; col2 < col + rectLt && doesFit && col2 < this.length; col2++) {
                        if (getCell(row,col2)) {
                            doesFit = false;
                        }
                    }
                }
            }
        }
        if (doesFit) {
            col--;
            row--;
            rectangle.addCol(col);
            rectangle.addRow(row);
        }
        return doesFit;
    }

    public void toStringPack() {
        for(int row = 0; row < width; row++) {
            for (int col = 0; col < length; col++) {
                if(getCell(row, col)) {
                    System.out.print('1');
                } else {
                    System.out.print('0');
                }

            }
            System.out.println();
        }
        System.out.println("Dimensions: " + length + "x" + width);
        System.out.println("Area: " + length * width);
    }
}
