import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import java.util.Random;

public class Area {
    private static Integer width = 10,
                           height = 10;
    private static Area active = null;
    private AreaBlock[] blocks;

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

    private void randBlock(Integer i)
    {
        double x, y;
        x = (double) randInt(0, Area.width);
        y = (double) randInt(0, Area.height);
        // System.out.println("x: " + x + "; y: " + y);
        blocks[i] = new AreaBlock(x, y, 10., 10.);
    }

    private void randArea(Integer elements)
    {
        for (int i = 0; i < elements; i++) {
            randBlock(i);
        }
    }

    private Area()
    {
        Integer elements = randInt((int) (0.1 * Area.width), (int) (0.15 * Area.width)) + randInt((int) (0.1 * Area.height), (int) (0.15 * Area.height));
        blocks = new AreaBlock[elements];

        randArea(elements);
    }

    public void setActive()
    {
        Area.active = this;
    }

    public static Area load()
    {
        Area area = new Area();

        if (Area.active ==  null) {
            area.setActive();
        }

        return area;
    }

    public void paint(Graphics2D g2D)
    {
        Color oldColor = g2D.getColor();

        Rectangle2D quad;
        for (int i = 0, j = blocks.length; i < j; i++) {
            quad = new Rectangle2D.Double(blocks[i].getX(), blocks[i].getY(), blocks[i].getWidth(), blocks[i].getHeight());
            g2D.setColor(Color.green);
            g2D.fill(quad);

            g2D.setColor(new Color(0, 0, 0, 0));
            g2D.draw(quad);
        }

        g2D.setColor(oldColor);
    }

    public static Area getActive()
    {
        return Area.active;
    }

    public Boolean check(Block block)
    {
        Boolean collision = false;

        for (int i = 0, j = blocks.length; i < j; i++) {
            collision = blocks[i].detectCollision(block);

            if (collision) {
                System.out.println("Collision at block " + i + " => " + blocks[i].getX() + " x " + blocks[i].getY());
                break;
            }
        }

        return collision;
    }

    public Block getCollisionBlock(Block block)
    {
        for (int i = 0, j = blocks.length; i < j; i++) {
            if (blocks[i].detectCollision(block)) {
                return blocks[i];
            }
        }

        return null;
    }
}
