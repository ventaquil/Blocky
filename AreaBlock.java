import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

public class AreaBlock extends Block {
    private BufferedImage image;

    public AreaBlock(Double x, Double y, Double width, Double height)
    {
        super(x, y, width, height);

        try {
            image = ImageIO.read(AreaBlock.class.getResourceAsStream("/resources/AreaBlock.png"));
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public void paint(Graphics2D g2D)
    {
        g2D.drawImage(image.getScaledInstance(10, 10, Image.SCALE_DEFAULT), getX().intValue(), getY().intValue(), null);
    }
}
