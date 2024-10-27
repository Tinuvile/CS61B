package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

// import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * 计算大小为 s 的六边形第 i 行的宽度。
     * @param s 六边形的大小。
     * @param i 行号，其中 i = 0 是底行。
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }

        return s + 2 * effectiveI;
    }

    /**
     * 计算假设底行的 x 坐标为零的六边形第 i 行左边最左边瓦片的相对 x 坐标。
     * 例如，如果 s = 3，且 i = 2，则此函数返回 -2，因为从底部往上第 2 行的开始
     * 在起始位置左侧 2 个单位，例如：
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2，从六边形底部开始，起始位置在左侧 2 个单位
     *  xxxxxx
     *   xxxx
     *
     * @param s 六边形的大小
     * @param i 六边形的行号，其中 i = 0 是底部
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /** 添加一行相同的瓦片。
     * @param world 要绘制的世界
     * @param p 行的最左边位置
     * @param width 要绘制的瓦片宽度
     * @param t 要绘制的瓦片
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * 向世界中添加一个六边形。
     * @param world 要绘制的世界
     * @param p 六边形的左下坐标
     * @param s 六边形的大小
     * @param t 要绘制的瓦片
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("六边形的大小必须至少为 2。");
        }

        // 六边形有 2*s 行。此代码从底行开始迭代，
        // 我们称之为行 0。
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);
        }
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }
}
