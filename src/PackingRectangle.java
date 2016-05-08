public class PackingRectangle {
    int length;
    int width;
    boolean[][] packingRectangle;
    int[] whereLastAdded = {0, 0};

    /**
     * A note: all array calls are supposed to:
     *      packingRectangle[collumn][row]
     */

    PackingRectangle(int length, int width) {
        this.length = length;
        this.width = width;
        packingRectangle = new boolean[width][length];
        initialize();
    }

    public boolean getCell(int col, int row) {
        return packingRectangle[col][row];
    }

    // this method FLIPS the rectangle for reusability
    public void flipRectangle(int row, int col, Rectangle rectangle) {
        for (int nCol = col; nCol < col + rectangle.getLength(); nCol++) {
            for (int nRow = row; nRow < row + rectangle.getWidth(); nRow++) {
                if(!getCell(nCol,nRow)) {
                    packingRectangle[nCol][nRow] = true;
                } else {
                    packingRectangle[nCol][nRow] = false;
                }
            }
        }
        whereLastAdded[0] = col;
        whereLastAdded[1] = row;
    }

    // make packingRectangle to have false on all cells
    public void initialize() {
        for(int row = 0; row < width; row++) {
            for(int col = 0; col < length; col ++) {
                packingRectangle[col][row] = false;
            }
        }
    }

    public boolean iWillTryToPack(Rectangle rectangle) {
        boolean doesFit = false;
        int rectLt = rectangle.getLength();
        int rectWd = rectangle.getWidth();
        int col = 0, row = 0;

        // This first part finds a location for the rectangle
        for (col = 0; col <= this.length - rectLt && !doesFit; col++) {
            for (row = 0; row <= this.width - rectWd && !doesFit; row++) {
                if (!getCell(row, col)) {
                    doesFit = true;
                    for (int col2 = col; col2 < col + rectLt && doesFit; col2++) {
                        if (getCell(row, col)) {
                            doesFit = false;
                        }
                    }
                }
            }
        }
        if (doesFit) {
            col--;
            row--;
        }

        // This part actually stuffs the rectangle
        if (doesFit) {
            flipRectangle(row, col, rectangle);
        }
        return doesFit;
    }

    public void toStringPack() {
        for(int col = 0; col < width; col++) {
            for (int row = 0; row < length; row++) {
                if(getCell(row, col)) {
                    System.out.print('1');
                } else {
                    System.out.print('0');
                }

            }
            System.out.println();
        }
    }

    public int[] getWhereLastAdded() {
        return whereLastAdded;
    }
}
