import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import java.lang.Math;

public class PlayerBlock extends Block {
    private static final Double STARTFALLING = Math.sqrt(30);
    private static PlayerBlock block = null;
    private static Double startX = 0.,
                          startY = 0.,
                          startWidth = 0.,
                          startHeight = 0.;
    private Double x = startX,
                   y = startY,
                   jumpModifier = 0.,
                   jumpCounter = null;
    private Boolean jumping = false,
                    falling = false;
    private BufferedImage image;

    public static void setStart(Double x, Double y, Double width, Double height)
    {
        startX = x;
        startY = y;
        startWidth = width;
        startHeight = height;
    }

    private PlayerBlock(Double x, Double y, Double width, Double height)
    {
        super(x, y, width, height);

        try {
            image = ImageIO.read(new File("./resources/PlayerBlock.png"));
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public static void start()
    {
        block = new PlayerBlock(startX, startY, startWidth, startHeight);
    }

    public static void restart()
    {
        start();
    }

    public static PlayerBlock get()
    {
        return block;
    }

    @Override
    public Double getX()
    {
        return x;
    }

    @Override
    public Double getY()
    {
        return -(y + jumpModifier);
    }

    public void paint(Graphics2D g2D)
    {
        g2D.drawImage(image.getScaledInstance(10, 10, Image.SCALE_DEFAULT), getX().intValue(), getY().intValue(), null);
    }

    public void jump()
    {
        if (!jumping && !falling) {
            jumping = true;
            jumpCounter = 0.001;
        }
    }

    public void fall()
    {
        if (!jumping && !falling) {
            falling = true;
            jumpCounter = 0.001;
        }
    }

    public void jumping()
    {
        if (jumping) {
            jumpModifier = -Math.pow(jumpCounter - Math.sqrt(30), 2) + 30.;

            if (
                (getY() > 0) &&
                (jumpCounter < STARTFALLING) &&
                !Area.get().check(this)
            ) {
                jumpCounter += 0.1;
            } else {
                Block collisionBlock = Area.get().getCollisionBlock(this);

                if (collisionBlock != null) {
                    y = -(collisionBlock.getY() + collisionBlock.getHeight());
                } else {
                    y += jumpModifier;
                }

                jumpCounter = jumpModifier
                            = 0.;

                jumping = false;

                fall();
            }
        }
    }

    public void falling()
    {
        if (falling) {
            jumpModifier = -Math.pow(jumpCounter, 2);

            if (!Area.get().check(this)) {
                jumpCounter += 0.1;
            } else {
                Block collisionBlock = Area.get().getCollisionBlock(this);

                y = -(collisionBlock.getY() - getHeight());

                jumpCounter = jumpModifier
                            = 0.;

                falling = false;
            }
        }
    }

    public void checkFall()
    {
        if (!jumping && !falling) {
            y--;
            if (!Area.get().check(this)) {
                fall();
            }
            y++;
        }
    }

    public void increaseX()
    {
        x++;
        if (Area.get().check(this)) {
            x--;

            Block collisionBlock = Area.get().getCollisionBlock(this);

            if (collisionBlock != null) {
                x = collisionBlock.getX();
            }
        }

        Game.updateScore(x.intValue());

        checkFall();
    }

    public void decreaseX()
    {
        x--;
        if (Area.get().check(this) || (x < GameScreen.get().getOffset())) {
            x++;

            Block collisionBlock = Area.get().getCollisionBlock(this);

            if (collisionBlock != null) {
                x = collisionBlock.getX() + collisionBlock.getWidth();
            }
        }

        checkFall();
    }

    public static Double[] getStartValues()
    {
        Double[] startPoint = new Double[4];
        startPoint[0] = startX;
        startPoint[1] = startY;
        startPoint[2] = startWidth;
        startPoint[3] = startHeight;

        return startPoint;
    }
}
