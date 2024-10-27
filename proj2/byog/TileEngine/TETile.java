package byog.TileEngine;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;
import byog.Core.RandomUtils;

/**
 * The TETile object is used to represent a single tile in your game. A 2D array of tiles make up a
 * board, and can be drawn to the screen using the TERenderer class.
 *
 * All TETile objects must have a character, textcolor, and background color to be used to represent
 * the tile when drawn to the screen. You can also optionally provide a path to an image file of an
 * appropriate size (16x16) to be drawn in place of the unicode representation. If the image path
 * provided cannot be found, draw will fallback to using the provided character and color
 * representation, so you are free to use image tiles on your own computer.
 *
 * The provided TETile is immutable, i.e. none of its instance variables can change. You are welcome
 * to make your TETile class mutable, if you prefer.
 */

/**
 * TETile 对象用于表示游戏中的单个瓦片。一个二维数组的瓦片构成一个
 * 棋盘，并可以使用 TERenderer 类绘制到屏幕上。
 *
 * 所有 TETile 对象必须具有一个字符、文本颜色和背景颜色，以在绘制到屏幕时表示
 * 瓦片。您还可以选择提供一个适当大小（16x16）的图像文件路径，以在 Unicode 表示
 * 形式的瓦片上绘制。如果提供的图像路径无法找到，绘制将回退到使用提供的字符和颜色
 * 表示，因此您可以在自己的计算机上使用图像瓦片。
 *
 * 提供的 TETile 是不可变的，即其实例变量不能更改。如果您愿意，可以使 TETile 类可变。
 */

public class TETile {
    private final char character; // Do not rename character or the autograder will break.
    private final Color textColor;
    private final Color backgroundColor;
    private final String description;
    private final String filepath;

    /**
     * Full constructor for TETile objects.
     * @param character The character displayed on the screen.
     * @param textColor The color of the character itself.
     * @param backgroundColor The color drawn behind the character.
     * @param description The description of the tile, shown in the GUI on hovering over the tile.
     * @param filepath Full path to image to be used for this tile. Must be correct size (16x16)
     */
    /**
     * TETile 对象的完整构造函数。
     * @param character 在屏幕上显示的字符。
     * @param textColor 字符本身的颜色。
     * @param backgroundColor 在字符后面绘制的颜色。
     * @param description 瓦片的描述，在鼠标悬停在瓦片上时显示。
     * @param filepath 要用于此瓦片的图像的完整路径。必须是正确的大小（16x16）
     */

    public TETile(char character, Color textColor, Color backgroundColor, String description,
                  String filepath) {
        this.character = character;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.filepath = filepath;
    }

    /**
     * Constructor without filepath. In this case, filepath will be null, so when drawing, we
     * will not even try to draw an image, and will instead use the provided character and colors.
     * @param character The character displayed on the screen.
     * @param textColor The color of the character itself.
     * @param backgroundColor The color drawn behind the character.
     * @param description The description of the tile, shown in the GUI on hovering over the tile.
     */
    /**
     * 无图像路径的构造函数。在这种情况下，filepath 将为 null，因此在绘制时，
     * 我们将不会尝试绘制图像，而是使用提供的字符和颜色。
     * @param character 在屏幕上显示的字符。
     * @param textColor 字符本身的颜色。
     * @param backgroundColor 在字符后面绘制的颜色。
     * @param description 瓦片的描述，在鼠标悬停在瓦片上时显示。
     */

    public TETile(char character, Color textColor, Color backgroundColor, String description) {
        this.character = character;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.filepath = null;
    }

    /**
     * Creates a copy of TETile t, except with given textColor.
     * @param t tile to copy
     * @param textColor foreground color for tile copy
     */
    /**
     * 创建一个 TETile t 的副本，但具有给定的文本颜色。
     * @param t 要复制的瓦片
     * @param textColor 瓦片副本的前景色
     */

    public TETile(TETile t, Color textColor) {
        this(t.character, textColor, t.backgroundColor, t.description, t.filepath);
    }


    /**
     * Draws the tile to the screen at location x, y. If a valid filepath is provided,
     * we draw the image located at that filepath to the screen. Otherwise, we fall
     * back to the character and color representation for the tile.
     *
     * Note that the image provided must be of the right size (16x16). It will not be
     * automatically resized or truncated.
     * @param x x coordinate
     * @param y y coordinate
     */
    /**
     * 在位置 x, y 绘制瓦片到屏幕上。如果提供了有效的文件路径，
     * 我们将绘制位于该文件路径的图像到屏幕上。否则，我们将
     * 回退到瓦片的字符和颜色表示。
     *
     * 注意，提供的图像必须是正确的大小（16x16）。它不会
     * 被自动调整大小或截断。
     * @param x x 坐标
     * @param y y 坐标
     */

    public void draw(double x, double y) {
        if (filepath != null) {
            try {
                StdDraw.picture(x + 0.5, y + 0.5, filepath);
                return;
            } catch (IllegalArgumentException e) {
                // Exception happens because the file can't be found. In this case, fail silently
                // and just use the character and background color for the tile.
            }
        }

        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
        StdDraw.setPenColor(textColor);
        StdDraw.text(x + 0.5, y + 0.5, Character.toString(character()));
    }

    /** Character representation of the tile. Used for drawing in text mode.
     * @return character representation
     */
    /** 瓦片的字符表示。用于在文本模式下绘制。
     * @return 字符表示
     */

    public char character() {
        return character;
    }

    /**
     * Description of the tile. Useful for displaying mouseover text or
     * testing that two tiles represent the same type of thing.
     * @return description of the tile
     */
    /**
     * 瓦片的描述。用于显示鼠标悬停文本或
     * 测试两个瓦片是否表示同一类型的东西。
     * @return 瓦片的描述
     */

    public String description() {
        return description;
    }

    /**
     * Creates a copy of the given tile with a slightly different text color. The new
     * color will have a red value that is within dr of the current red value,
     * and likewise with dg and db.
     * @param t the tile to copy
     * @param dr the maximum difference in red value
     * @param dg the maximum difference in green value
     * @param db the maximum difference in blue value
     * @param r the random number generator to use
     */
    /**
     * 创建给定瓦片的副本，文本颜色略有不同。新
     * 颜色的红色值将在当前红色值的范围内变化 dr，
     * 绿色值和蓝色值同理。
     * @param t 要复制的瓦片
     * @param dr 红色值的最大差异
     * @param dg 绿色值的最大差异
     * @param db 蓝色值的最大差异
     * @param r 要使用的随机数生成器
     */

    public static TETile colorVariant(TETile t, int dr, int dg, int db, Random r) {
        Color oldColor = t.textColor;
        int newRed = newColorValue(oldColor.getRed(), dr, r);
        int newGreen = newColorValue(oldColor.getGreen(), dg, r);
        int newBlue = newColorValue(oldColor.getBlue(), db, r);

        Color c = new Color(newRed, newGreen, newBlue);

        return new TETile(t, c);
    }

    private static int newColorValue(int v, int dv, Random r) {
        int rawNewValue = v + RandomUtils.uniform(r, -dv, dv + 1);

        // make sure value doesn't fall outside of the range 0 to 255.
        int newValue = Math.min(255, Math.max(0, rawNewValue));
        return newValue;
    }

    /**
     * Converts the given 2D array to a String. Handy for debugging.
     * Note that since y = 0 is actually the bottom of your world when
     * drawn using the tile rendering engine, this print method has to
     * print in what might seem like backwards order (so that the 0th
     * row gets printed last).
     * @param world the 2D world to print
     * @return string representation of the world
     */
    /**
     * 将给定的二维数组转换为字符串。方便调试。
     * 注意，由于 y = 0 实际上是您的世界底部
     * 当使用瓦片渲染引擎绘制时，因此此打印方法必须
     * 以可能看似相反的顺序打印（以便最后打印第 0 行）。
     * @param world 二维世界以打印
     * @return 世界的字符串表示
     */

    public static String toString(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;
        StringBuilder sb = new StringBuilder();

        for (int y = height - 1; y >= 0; y -= 1) {
            for (int x = 0; x < width; x += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                sb.append(world[x][y].character());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Makes a copy of the given 2D tile array.
     * @param tiles the 2D array to copy
     **/
    /**
     * 复制给定的二维瓦片数组。
     * @param tiles 要复制的二维数组
     **/

    public static TETile[][] copyOf(TETile[][] tiles) {
        if (tiles == null) {
            return null;
        }

        TETile[][] copy = new TETile[tiles.length][];

        int i = 0;
        for (TETile[] column : tiles) {
            copy[i] = Arrays.copyOf(column, column.length);
            i += 1;
        }

        return copy;
    }

    @Override
    /** Provides an equals method that is consistent
     *  with the way that the autograder works.
     */
    /**
     * 提供一个 equals 方法，与自动评分程序的工作方式一致。
     */

    public boolean equals(Object x) {
        if (this == x) {
            return true;
        }
        if (x == null) {
            return false;
        }
        if (this.getClass() != x.getClass()) {
            return false;
        }
        TETile that = (TETile) x;
        return this.character == that.character;
    }

    @Override
    public int hashCode() {
        return this.character;
    }
}
