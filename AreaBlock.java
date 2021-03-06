import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AreaBlock extends MapBlock {
    protected static BufferedImage image;

    public AreaBlock(Dimension coordinates, Dimension size)
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
            image = ImageIO.read(AreaBlock.class.getResourceAsStream("/resources/AreaBlock.png"));
        } catch (IOException e) {
            System.exit(-1);
        }
    }
};
