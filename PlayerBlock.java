import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

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
    }

    public static void start()
    {
        block = new PlayerBlock(startX, startY, startWidth, startHeight);
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
        Color oldColor = g2D.getColor();

        g2D.setColor(Color.red);
        Rectangle2D quad = new Rectangle2D.Double(x, getY(), 10, 10);
        g2D.fill(quad);

        g2D.setColor(new Color(0, 0, 0, 0));
        g2D.draw(quad);

        g2D.setColor(oldColor);
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
                !Area.getActive().check(this)
            ) {
                jumpCounter += 0.1;
            } else {
                Block collisionBlock = Area.getActive().getCollisionBlock(this);

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

            if (!Area.getActive().check(this)) {
                jumpCounter += 0.1;
            } else {
                Block collisionBlock = Area.getActive().getCollisionBlock(this);

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
            if (!Area.getActive().check(this)) {
                fall();
            }
        }
    }

    public void increaseX()
    {
        x++;
        if (Area.getActive().check(this)) {
            x--;

            Block collisionBlock = Area.getActive().getCollisionBlock(this);

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
        if (Area.getActive().check(this)) {
            x++;

            Block collisionBlock = Area.getActive().getCollisionBlock(this);

            if (collisionBlock != null) {
                x = collisionBlock.getX() + collisionBlock.getWidth();
            }
        }

        checkFall();
    }
}
