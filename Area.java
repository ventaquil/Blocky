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

    private AreaBlock randBlock()
    {
        double x = (double) randInt(0, Area.width),
               y = (double) randInt(0, Area.height);

        return new AreaBlock(x, y, 10., 10.);
    }

    private void randArea(Integer elements)
    {
        Double[] startValues = PlayerBlock.getStartValues();
        Boolean collision;
        
        AreaBlock virtual = new AreaBlock(startValues[0], startValues[1], startValues[2], startValues[3]);

        for (int i = 0; i < elements; i++) {
            do {
                // Remove old element if exists
                try {
                    blocks.remove(i);
                } catch (IndexOutOfBoundsException e) { }

                collision = false;

                blocks.add(randBlock());

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

        for (int i = 0, j = blocks.size(); i < j; i++){
            if (blocks.get(i).getX() < offset) {
                blocks.remove(i);
            }
        }

        return this;
    }

    public Area generateNew(Integer offset)
    {
        Integer elements = randInt((int) (0.01 * Area.width), (int) (0.015 * Area.width)) + randInt((int) (0.01 * Area.height), (int) (0.015 * Area.height));
        double x, y;
 
        int size = blocks.size();

        for (int i = 0; i < elements; i++) {
            x = (double) randInt(-20, 20);
            y = (double) randInt(0, Area.height);

            blocks.add(new AreaBlock(x + offset + width, y, 10., 10.));
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
        Color oldColor = g2D.getColor();

        Rectangle2D quad;
        for (int i = 0, j = blocks.size(); i < j; i++) {
            quad = new Rectangle2D.Double(blocks.get(i).getX(), blocks.get(i).getY(), blocks.get(i).getWidth(), blocks.get(i).getHeight());
            g2D.setColor(Color.GREEN);
            g2D.fill(quad);

            g2D.setColor(new Color(0, 0, 0, 0));
            g2D.draw(quad);
        }

        g2D.setColor(oldColor);
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

        for (int i = 0, j = blocks.size(); i < j; i++) {
            collision = blocks.get(i).detectCollision(block);

            if (collision) {
                break;
            }
        }

        return collision;
    }

    public Block getCollisionBlock(Block block)
    {
        for (int i = 0, j = blocks.size(); i < j; i++) {
            if (blocks.get(i).detectCollision(block)) {
                return blocks.get(i);
            }
        }

        return null;
    }
}
