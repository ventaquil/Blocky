import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.IndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Area {
    private static Area instance;
    private List<AreaBlock> blocks;
    private static Integer areaWidth,
                           areaHeight,
                           blockWidth,
                           blockHeight;

    public static Integer getWidth()
    {
        return areaWidth;
    }

    public static Integer getHeight()
    {
        return areaHeight;
    }

    public static void setSize(Integer width, Integer height)
    {
        if (width > 0) {
            areaWidth = width;
        }
        // @TODO exception

        if (height > 0) {
            areaHeight = height;
        }
        // @TODO exception
    }

    public static void setBlockSize(Integer width, Integer height)
    {
        if (width > 0) {
            blockWidth = width;
        }
        // @TODO exception

        if (height > 0) {
            blockHeight = height;
        }
        // @TODO exception
    }

    private static Integer randInt(Integer min, Integer max) {
        return new Integer((new Random()).nextInt((max - min) + 1) + min);
    }

    private AreaBlock randBlock()//Boolean notTouchBlock)
    {
        double x = (double) randInt(0, areaWidth),
               y = (double) randInt(0, areaHeight);

//         if (notTouchBlock) {
//             return new NotTouchBlock(x, y, blockWidth, blockHeight);
//         } else {
            return new AreaBlock(x, y, new Double(blockWidth), new Double(blockHeight));
//         }
    }

    private void generateArea()
    {
        Boolean collision, notTouchBlock;

        AreaBlock virtual = new AreaBlock(0., 0., blockWidth * 3., blockHeight * 3.);

        Integer areaBlocks = randInt(20, 30);
        blocks = new ArrayList<AreaBlock>(areaBlocks);

        for (int i = 0; i < areaBlocks; i++) {
            //notTouchBlock = randInt(0, 100) >= notTouchBlocks;

            do {
                // Remove old element if exists
                try {
                    blocks.remove(i);
                } catch (IndexOutOfBoundsException e) { }

                collision = false;

                blocks.add(randBlock());//notTouchBlock));

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

    public static void removeUseless(Integer offset)
    {
        offset -= 12;

        try {
            for (int i = 0, j = instance.blocks.size(); i < j; i++){
                if (instance.blocks.get(i).getX() < offset) {
                    instance.blocks.remove(i);
                }
            }
        } catch (IndexOutOfBoundsException e) { }
    }

    public static void generateNew(Integer offset)
    {
        Integer elements = randInt(2, 3);
        Double x, y;
        Boolean collision, notTouchBlock;
        AreaBlock block = null;

        for (int i = 0; i < elements; i++) {
//             notTouchBlock = randInt(0, 100) >= notTouchBlocks;

            do {
                // Remove old element if exists
                if (block != null) {
                    instance.blocks.remove(block);
                }

                collision = false;
                x = (double) randInt(-20, 20);
                y = (double) randInt(0, areaHeight);

//                 if (notTouchBlock) {
//                     block = new NotTouchBlock(x + offset + areaWidth, y, 20., 20.);
//                 } else {
                    block = new AreaBlock(x + offset + areaWidth, y, 20., 20.);
//                 }
                instance.blocks.add(block);

                // Find all conflicted blocks
                for (int j = 0, k = instance.blocks.indexOf(block); (j < k) && !collision; j++) {
                    try {
                        collision = instance.blocks.get(j)
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
    }

    private Area()
    {
        generateArea();
    }

    public static void create()
    {
        instance = new Area();
    }

    public static Boolean check(Block block)
    {
        Boolean collision = false;

        try {
            for (int i = 0, j = instance.blocks.size(); i < j; i++) {
                collision = instance.blocks
                                    .get(i)
                                    .detectCollision(block);

                if (collision) {
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) { }

        return collision;
    }

    public static Block getCollisionBlock(Block block)
    {
        try {
            for (int i = 0, j = instance.blocks.size(); i < j; i++) {
                if (instance.blocks.get(i).detectCollision(block)) {
                    return instance.blocks.get(i);
                }
            }
        } catch (IndexOutOfBoundsException e) { }

        return null;
    }

    public static void paint(Graphics2D g2D)
    {
        try {
            for (int i = 0, j = instance.blocks.size(); i < j; i++) {
                instance.blocks
                        .get(i)
                        .paintBlock(g2D);
            }
        } catch (IndexOutOfBoundsException e) { }
    }
}
