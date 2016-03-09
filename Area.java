import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import java.lang.IndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Area {
    private static Integer width = 10,
                           height = 10;
    private static Area area = null;
    private List<AreaBlock> blocks;
    private static final Integer notTouchBlocks = 85;

    public static void setSize(Integer width, Integer height)
    {
        if (width > 0) {
            Area.width = width;
        }
        // @TODO exception

        if (height > 0) {
            Area.height = height;
        }
        // @TODO exception
    }

    private static Integer randInt(Integer min, Integer max) {
        return new Integer((new Random()).nextInt((max - min) + 1) + min);
    }

    private AreaBlock randBlock(Boolean notTouchBlock)
    {
        double x = (double) randInt(0, Area.width),
               y = (double) randInt(0, Area.height);

        if (notTouchBlock) {
            return new NotTouchBlock(x, y, 10., 10.);
        } else {
            return new AreaBlock(x, y, 10., 10.);
        }
    }

    private void randArea(Integer elements)
    {
        Double[] startValues = PlayerBlock.getStartValues();
        Boolean collision, notTouchBlock;
        
        AreaBlock virtual = new AreaBlock(startValues[0], startValues[1], startValues[2], startValues[3]);

        for (int i = 0; i < elements; i++) {
            notTouchBlock = randInt(0, 100) >= notTouchBlocks;

            do {
                // Remove old element if exists
                try {
                    blocks.remove(i);
                } catch (IndexOutOfBoundsException e) { }

                collision = false;

                blocks.add(randBlock(notTouchBlock));

                // Find all conflicted blocks
                for (int j = 0; (j < i) && !collision; j++) {
                    try {
                        collision = blocks.get(j)
                                      .detectCollision(blocks.get(i));
                    } catch (IndexOutOfBoundsException e) {
                        collision = false;
                    }
                }
            } while (virtual.detectCollision(blocks.get(i)) || collision);
        }
    }

    public Area removeUseless(Integer offset)
    {
        offset -= 5;

        try {
            for (int i = 0, j = blocks.size(); i < j; i++){
                if (blocks.get(i).getX() < offset) {
                    blocks.remove(i);
                }
            }
        } catch (IndexOutOfBoundsException e) { }

        return this;
    }

    public Area generateNew(Integer offset)
    {
        Integer elements = randInt((int) (0.01 * Area.width), (int) (0.015 * Area.width)) + randInt((int) (0.01 * Area.height), (int) (0.015 * Area.height));
        double x, y;
        Boolean collision, notTouchBlock;
        AreaBlock block = null;

        for (int i = 0; i < elements; i++) {
            notTouchBlock = randInt(0, 100) >= notTouchBlocks;

            do {
                // Remove old element if exists
                if (block != null) {
                    blocks.remove(block);
                }

                collision = false;
                x = (double) randInt(-20, 20);
                y = (double) randInt(0, Area.height);

                if (notTouchBlock) {
                    block = new NotTouchBlock(x + offset + width, y, 10., 10.);
                } else {
                    block = new AreaBlock(x + offset + width, y, 10., 10.);
                }
                blocks.add(block);

                // Find all conflicted blocks
                for (int j = 0, k = blocks.indexOf(block); (j < k) && !collision; j++) {
                    try {
                        collision = blocks.get(j)
                                          .detectCollision(block);
                    } catch (IndexOutOfBoundsException e) {
                        collision = false;
                    }
                }

                if (!collision) {
                    block = null;
                }
            } while (collision);
        }

        return this;
    }

    private Area()
    {
        Integer elements = randInt((int) (0.08 * Area.width), (int) (0.1 * Area.width)) + randInt((int) (0.08 * Area.height), (int) (0.1 * Area.height));
        blocks = new ArrayList<AreaBlock>(elements);

        randArea(elements);
    }

    public static Area load()
    {
        if (area ==  null) {
            area = new Area();
        }

        return area;
    }

    public void paint(Graphics2D g2D)
    {
        try {
            for (int i = 0, j = blocks.size(); i < j; i++) {
                blocks.get(i)
                      .paint(g2D);
            }
        } catch (IndexOutOfBoundsException e) { }
    }

    public static Area get()
    {
        return area;
    }

    public static void restart()
    {
        area.blocks.clear();
        area = null;
        System.gc();

        area = new Area();
    }

    public Boolean check(Block block)
    {
        Boolean collision = false;

        try {
            for (int i = 0, j = blocks.size(); i < j; i++) {
                collision = blocks.get(i).detectCollision(block);

                if (collision) {
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) { }

        return collision;
    }

    public Block getCollisionBlock(Block block)
    {
        try {
            for (int i = 0, j = blocks.size(); i < j; i++) {
                if (blocks.get(i).detectCollision(block)) {
                    return blocks.get(i);
                }
            }
        } catch (IndexOutOfBoundsException e) { }

        return null;
    }
}
