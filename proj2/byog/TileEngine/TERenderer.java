package byog.TileEngine;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;

/**
 * Utility class for rendering tiles. You do not need to modify this file. You're welcome
 * to, but be careful. We strongly recommend getting everything else working before
 * messing with this renderer, unless you're trying to do something fancy like
 * allowing scrolling of the screen or tracking the player or something similar.
 */
/**
 * 用于渲染瓦片的实用程序类。您不需要修改此文件。您可以修改，
 * 但请小心。我们强烈建议您在尝试修改渲染器之前确保其他所有
 * 功能正常，除非您打算做一些花哨的事情，比如允许屏幕滚动或跟踪
 * 玩家或类似的事情。
 */
public class TERenderer {
    private static final int TILE_SIZE = 16;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;

    /**
     * Same functionality as the other initialization method. The only difference is that the xOff
     * and yOff parameters will change where the renderFrame method starts drawing. For example,
     * if you select w = 60, h = 30, xOff = 3, yOff = 4 and then call renderFrame with a
     * TETile[50][25] array, the renderer will leave 3 tiles blank on the left, 7 tiles blank
     * on the right, 4 tiles blank on the bottom, and 1 tile blank on the top.
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    /**
     * 与其他初始化方法的功能相同。唯一的区别是 xOff 和 yOff
     * 参数会改变 renderFrame 方法开始绘制的位置。例如，如果选择
     * w = 60，h = 30，xOff = 3，yOff = 4，然后调用带有 TETile[50][25]
     * 数组的 renderFrame，渲染器将在左侧留出 3 个空瓦片，在右侧留出
     * 7 个空瓦片，在底部留出 4 个空瓦片，在顶部留出 1 个空瓦片。
     * @param w 窗口的宽度（以瓦片为单位）
     * @param h 窗口的高度（以瓦片为单位）
     */
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);      
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * Initializes StdDraw parameters and launches the StdDraw window. w and h are the
     * width and height of the world in number of tiles. If the TETile[][] array that you
     * pass to renderFrame is smaller than this, then extra blank space will be left
     * on the right and top edges of the frame. For example, if you select w = 60 and
     * h = 30, this method will create a 60 tile wide by 30 tile tall window. If
     * you then subsequently call renderFrame with a TETile[50][25] array, it will
     * leave 10 tiles blank on the right side and 5 tiles blank on the top side. If
     * you want to leave extra space on the left or bottom instead, use the other
     * initializatiom method.
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    /**
     * 初始化 StdDraw 参数并启动 StdDraw 窗口。w 和 h 是
     * 世界的宽度和高度（以瓦片为单位）。如果您传递给 renderFrame
     * 的 TETile[][] 数组小于这个，那么在右边和顶部边缘会留出额外的
     * 空间。例如，如果选择 w = 60 和 h = 30， 此方法将创建一个 60
     * 瓦片宽和 30 瓦片高的窗口。如果您随后调用带有 TETile[50][25]
     * 数组的 renderFrame，那么在右侧将留出 10 个瓷砖空白，顶部将留出
     * 5 个瓷砖空白。如果您想在左侧或底部留出额外空间，请使用
     * 其他初始化方法。
     * @param w 窗口的宽度（以瓦片为单位）
     * @param h 窗口的高度（以瓦片为单位）
     */
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * Takes in a 2d array of TETile objects and renders the 2d array to the screen, starting from
     * xOffset and yOffset.
     *
     * If the array is an NxM array, then the element displayed at positions would be as follows,
     * given in units of tiles.
     *
     *              positions   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     *                     
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     *                    ...    ......  |  ......  |  ......  | .... | ......
     *               startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     *               startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     *                 startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     *
     * By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
     * empty space in different places to leave room for other information, such as a GUI.
     * This method assumes that the xScale and yScale have been set such that the max x
     * value is the width of the screen in tiles, and the max y value is the height of
     * the screen in tiles.
     * @param world the 2D TETile[][] array to render
     */
    /**
     * 接收一个 2D TETile 对象数组并将其渲染到屏幕上，从
     * xOffset 和 yOffset 开始。
     *
     * 如果数组是一个 N x M 数组，则在位置上显示的元素如下所示，
     * 按瓦片单位给出。
     *
     *              位置       xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     *
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     *                    ...    ......  |  ......  |  ......  | .... | ......
     *               startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     *               startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     *                 startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     *
     * 通过改变 xOffset、yOffset 和初始化时屏幕的大小，您可以在不同位置留出
     * 空白，以便为其他信息留出空间，例如 GUI。此方法假定 xScale 和 yScale
     * 已设置，使得最大 x 值为瓦片宽度，最大 y 值为瓦片高度。
     * @param world 要渲染的 2D TETile[][] 数组
     */
    public void renderFrame(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
        StdDraw.show();
    }
}
