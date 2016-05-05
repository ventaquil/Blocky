import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NotTouchBlock extends AreaBlock {
    protected static BufferedImage image;

    @Override
    public void collisionEvent(MapObject object)
    {
        Game gameInstance = Game.instance();

        if (gameInstance.isStarted() && (object instanceof PlayerBlock)) {
            gameInstance.finish();
        }
    }

    public NotTouchBlock(Dimension coordinates, Dimension size)
    {
        super(coordinates, size);
    }

    @Override
    public void paintComponent(Graphics2D g2D)
    {
        g2D.drawImage(image.getScaledInstance(width, height, Image.SCALE_DEFAULT), x, y, null);
    }

    public static void setImage()
    {
        try {
            image = ImageIO.read(NotTouchBlock.class.getResourceAsStream("/resources/NotTouchBlock.png"));
        } catch (IOException e) {
            System.exit(-1);
        }
    }
};
