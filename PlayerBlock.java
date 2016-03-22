import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

import java.lang.Math;

public class PlayerBlock extends Block {
    private static PlayerBlock instance;
    private static Integer blockWidth,
                           blockHeight;
    private static Double blockX = 0.,
                          blockY = 0.;
    private BufferedImage image;
    private static Boolean setIcons = true;
    private Boolean jumping = false,
                    falling = false;
    private Double moveCounter,
                   moveModifier;

    @Override
    public Double getY()
    {
        if (moveModifier == null) {
            return y;
        }

        return y + moveModifier;
    }

    public static void setBlockSize(Integer width, Integer height)
    {
        blockWidth = width;
        blockHeight = height;
    }

    public static void setStartPosition(Double x, Double y)
    {
        blockX = x;
        blockY = y;
    }

    private PlayerBlock(Double x, Double y, Double width, Double height)
    {
        super(x, y, width, height);

        try {
            image = ImageIO.read(PlayerBlock.class.getResourceAsStream("/resources/PlayerBlock.png"));
        } catch (IOException e) {
            setIcons = false;
        }
    }

    public static void create()
    {
        instance = new PlayerBlock(blockX, blockY, new Double(blockWidth), new Double(blockHeight));
    }

    private void paintBlock(Graphics2D g2D)
    {
        if (setIcons) {
            g2D.drawImage(image.getScaledInstance(width.intValue(), height.intValue(), Image.SCALE_DEFAULT), getX().intValue(), getY().intValue(), null);
        } else {
            Rectangle2D rectangle = new Rectangle2D.Double(getX().intValue(), getY().intValue(), width.intValue(), height.intValue());

            g2D.setColor(new Color(255, 109, 0));
            g2D.fill(rectangle);

            g2D.setColor(new Color(0, 0, 0, 0));
            g2D.draw(rectangle);
        }
    }

    public static void paint(Graphics2D g2D)
    {
        instance.paintBlock(g2D);
    }

    public static void jump()
    {
        if (!instance.jumping && !instance.falling) {
            instance.jumping = true;
            instance.moveCounter = 0.001;
        }
    }

    public static void fall()
    {
        if (!instance.jumping && !instance.falling) {
            instance.falling = true;
            instance.moveCounter = 0.001;
        }
    }

    private void jumping()
    {
        if (jumping) {
            moveModifier = Math.pow(moveCounter - Math.sqrt(100), 2) - 100.;

            if (
                (getY() > 0.) &&
                (moveCounter < Math.sqrt(100)) &&
                !Area.check(this)
            ) {
                moveCounter += 0.2;
            } else {
                Block collisionBlock = Area.getCollisionBlock(this);

                if (collisionBlock == null) {
                    y += moveModifier;
                } else {
                    y = collisionBlock.getY() +  collisionBlock.getHeight();
                }

                moveCounter = moveModifier
                            = null;

                jumping = false;

                fall();
            }
        }
    }

    private void falling()
    {
        if (falling) {
            moveModifier = Math.pow(moveCounter, 2);

            if (!Area.check(this)) {
                moveCounter += 0.2;
            } else {
                Block collisionBlock = Area.getCollisionBlock(this);

                y = collisionBlock.getY() - getHeight();

                moveCounter = moveModifier
                            = 0.;

//                 if (collisionBlock instanceof NotTouchBlock) {
//                     Game.finish();
//                 }

                falling = false;
            }

            if (getY() > Area.getHeight()) {
                Game.finish();
            }
        }
    }

    private void checkFall()
    {
        if (!jumping && !falling) {
            y--;

            if (!Area.check(this)) {
                fall();
            }

            y++;
        }
    }

    public static void move()
    {
        if (instance != null) {
            instance.jumping();
            instance.falling();
        }
    }

    public static void increaseX()
    {
        if (instance != null) {
            instance.x += 1.7;

            if (Area.check(instance)) {
                instance.x -= 1.7;
            } else {
                Game.updateScore(instance.x.intValue());

                GamePanel.increaseOffset(instance.x);
            }

            instance.checkFall();
        }
    }

    public static void decreaseX()
    {
        if (instance != null) {
            instance.x -= 1.7;

            if (Area.check(instance) || (GamePanel.getOffset() >= instance.x.intValue())) {
                instance.x += 1.7;
            }

            instance.checkFall();
        }
    }

    public static void setIcons(Boolean status)
    {
        setIcons = status;
    }
}
