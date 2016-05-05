import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerBlock extends MapBlock {
    private static PlayerBlock instance = null;
    private static BufferedImage image;
    private Boolean jumping = false,
                    falling = false;
    private Integer moveModifier;
    private Double moveCounter;

    public void fall()
    {
        if (!jumping && !falling) {
            falling = true;
            moveCounter = 0.001;
        }
    }

    private void falling()
    {
        if (falling) {
            moveModifier = new Double(Math.pow(moveCounter, 2)).intValue();

            MapBlock collisionBlock = Area.instance()
                                          .getCollisionBlock(this);

            if (collisionBlock == null) {
                moveCounter += 0.3;
            } else {
                y = collisionBlock.getY() - getHeight();

                moveCounter = 0.;
                moveModifier = 0;

                falling = false;
            }
        }
    }

    @Override
    public Integer getY()
    {
        if (moveModifier == null) {
            return y;
        }

        return y + moveModifier;
    }

    public static PlayerBlock instance()
    {
        if (instance == null) {
            setImage();
    
            instance = new PlayerBlock(new Dimension(5, 5), new Dimension(20, 20));
        }

        return instance;
    }

    public void jump()
    {
        if (!jumping && !falling) {
            jumping = true;
            moveCounter = 0.001;
        }
    }

    private void jumping()
    {
        if (jumping) {
            moveModifier = new Double(Math.pow(moveCounter - Math.sqrt(100), 2)).intValue() - 100;

            MapBlock collisionBlock = Area.instance()
                                          .getCollisionBlock(this);

            if (
                (getY() > 0) &&
                (moveCounter < Math.sqrt(100)) &&
                (collisionBlock == null)
            ) {
                moveCounter += 0.3;
            } else {
                if (collisionBlock == null) {
                    y += moveModifier;
                } else {
                    y = collisionBlock.getY() +  collisionBlock.getHeight();
                }

                if (y < 0) {
                    y = 0;
                }

                moveCounter = 0.;
                moveModifier = 0;

                jumping = false;

                fall();
            }
        }
    }

    public void move()
    {
        jumping();
        falling();

        if (getY() > Area.instance().getHeight()) {
            Game.instance()
                .finish();
        }
    }

    public void moveLeft()
    {
        Integer offset = GamePanel.instance()
                                  .getOffset();

        if (x < offset) {
            return;
        }

        x -= 3;

        if (x < offset) {
            x = offset;
        } else {
            MapBlock collisionBlock = Area.instance()
                                        .getCollisionBlock(this);
            if (collisionBlock == null) {
                fall();
            } else {
                x = collisionBlock.getX() + collisionBlock.getWidth();
            }
        }
    }

    public void moveRight()
    {
        x += 3;

        MapBlock collisionBlock = Area.instance()
                                      .getCollisionBlock(this);
        if (collisionBlock == null) {
            fall();
        } else {
            x = collisionBlock.getX() - getWidth();
        }

        Game.instance()
            .updateScore(x);

        GamePanel.instance()
                 .increaseOffset(x);
    }

    @Override
    public void paintComponent(Graphics2D g2D)
    {
        g2D.drawImage(image.getScaledInstance(width, height, Image.SCALE_DEFAULT), getX(), getY(), null);
    }

    private PlayerBlock(Dimension coordinates, Dimension size)
    {
        super(coordinates, size);
    }

    public static void restart()
    {
        instance = new PlayerBlock(new Dimension(5, 5), new Dimension(20, 20));
    }

    private static void setImage()
    {
        try {
            image = ImageIO.read(PlayerBlock.class.getResourceAsStream("/resources/PlayerBlock.png"));
        } catch (IOException e) {
            System.exit(-1);
        }
    }
};
