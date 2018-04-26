package me.callum.hengine.gfx;

import java.awt.image.BufferedImage;

/**
 * A more advanced SpriteSheet which buffers sub-images of a tilesheet.
 *
 * @Author Hydrogen
 * @Version April 2018
 */
public class TileSheet extends SpriteSheet {

    private int columns, rows;

    private BufferedImage[] images;

    public TileSheet(BufferedImage image) {
        super(image);
    }

    /**
     * Buffers the specified sub images into a buffer for later use.
     *
     * @param columns the amount of columns to extract
     * @param rows the amount of rows to extract
     * @param tileSizeX the width of each tile
     * @param tileSizeY the height of each tile
     * @param tileSpacingX the amount of horizontal space between tiles
     * @param tileSpacingY the amount of vertical space between tiles
     */
    public void loadSubImages(int columns, int rows, int tileSizeX, int tileSizeY, int tileSpacingX, int tileSpacingY) {
        this.columns=columns;
        this.rows = rows;

        images = new BufferedImage[columns*rows];

        int xPlot,yPlot;

        for(int x = 0; x  < columns; x++) {
            xPlot = x * (tileSizeX + tileSpacingX);
            for(int y = 0; y < rows; y++) {
                yPlot = y * (tileSizeY + tileSpacingY);
                images[y*columns+x] = getImage().getSubimage(xPlot, yPlot, tileSizeX, tileSizeY);
            }
        }
    }

    /**
     * Returns the tile at the specific column and row in the from the internal buffer.
     * The buffer is zero-based.
     *
     * @param x the column to index image from
     * @param y the row to index the image from
     * @return the image at the xth column and the yth row
     */
    public BufferedImage getImage(int x, int y) {
        return images[y*columns+x];
    }

    /**
     * Returns the tile at the specified index.
     *
     * @param id the index to retrieve the image from.
     *           If you have a 5x5 sheet loaded in, to get the third column of the second row,
     *           you'd do (5 * 2) + 3 - 1. This equates to (row * columns) + column - 1
     * @return
     */
    public BufferedImage getImage(int id) {
        return images[id];
    }
}
