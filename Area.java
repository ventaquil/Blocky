import java.awt.Dimension;
import java.awt.Graphics2D;
import java.lang.IndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Area {
    private static Area instance = null;
    private Integer width,
                    height;
    private List<AreaBlock> map;
    private Integer lastGenerated;

    private Area() { }

    public MapBlock getCollisionBlock(MapBlock block)
    {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).detectCollision(block)) {
                return map.get(i);
            }
        }

        return null;
    }

    public Integer getHeight()
    {
        return height;
    }

    public static Area instance()
    {
        if (instance == null) {
            instance = new Area();
        }

        return instance;
    }

    public void paintComponent(Graphics2D g2D)
    {
        for (int i = 0, j = map.size(); i < j; i++) {
            try {
                map.get(i)
                   .paintComponent(g2D);
            } catch (IndexOutOfBoundsException e) { }
        }
    }

    public Area randNew()
    {
        Integer blockX = PlayerBlock.instance()
                                    .getX();
        if ((blockX % 50 <= 2) && (lastGenerated < blockX)) {
            lastGenerated = blockX;

            Boolean collision,
                    notTouchBlocks = Game.getLevel() >= Game.NORMAL;

            Integer blocksCount = randomInt(1, 3);

            AreaBlock newBlock = null;

            for (int i = 0; i < blocksCount; i++) {
                do {
                    collision = false;

                    if (notTouchBlocks && (randomInt(0, 20) >= 17)) {
                        newBlock = new NotTouchBlock(new Dimension(randomInt(width + blockX, width + blockX + 50), randomInt(0, height - 20)), new Dimension(20, 20));
                    } else {
                        newBlock = new AreaBlock(new Dimension(randomInt(width + blockX, width + blockX + 50), randomInt(0, height - 20)), new Dimension(20, 20));
                    }

                    for (int j = 0; (j < map.size()) && !collision; j++) {
                        try {
                            collision = map.get(j)
                                           .detectCollision(newBlock);
                        } catch (IndexOutOfBoundsException e) {
                            collision = false;
                        }
                    }

                    if (!collision) {
                        map.add(newBlock);
                    }
                } while (collision);
            }
        }

        return this;
    }

    public Area randObjects()
    {
        if (map != null) {
            return this;
        }

        lastGenerated = 0;

        Boolean collision,
                notTouchBlocks = Game.getLevel() >= Game.NORMAL;

        MapBlock virtualBlock = new MapBlock(new Dimension(0, 0), new Dimension(95, 95));

        Integer blocksCount = randomInt(22, 27);

        map = new ArrayList<AreaBlock>(blocksCount);

        AreaBlock newBlock;

        for (int i = 0; i < blocksCount; i++) {
            do {
                collision = false;

                if (notTouchBlocks && (randomInt(0, 20) >= 17)) {
                    newBlock = new NotTouchBlock(new Dimension(randomInt(0, width), randomInt(0, height - 20)), new Dimension(20, 20));
                } else {
                    newBlock = new AreaBlock(new Dimension(randomInt(0, width), randomInt(0, height - 20)), new Dimension(20, 20));
                }

                for (int j = 0; (j < i) && !collision; j++) {
                    try {
                        collision = map.get(j)
                                       .detectCollision(newBlock);
                    } catch (IndexOutOfBoundsException e) {
                        collision = false;
                    }
                }

                collision = collision || virtualBlock.detectCollision(newBlock);

                if (!collision) {
                    map.add(newBlock);
                }
            } while (collision);
        }

        map.add(new AreaBlock(new Dimension(5, 30), new Dimension(20, 20)));

        AreaBlock.setImage();
        NotTouchBlock.setImage();

        return this;
    }

    private static Integer randomInt(Integer min, Integer max)
    {
        return new Integer((new Random()).nextInt((max - min) + 1) + min);
    }

    public Area removeOld()
    {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)
                    .getX() <= GamePanel.instance()
                                        .getOffset() - 60) {
                map.remove(i);
            }
        }

        return this;
    }

    public Area setSize(Dimension size)
    {
        // @TODO exceptions
        width = size.width;
        height = size.height;

        return this;
    }
};
