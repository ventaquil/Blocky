import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

public class AreaBlock extends Block {
    private BufferedImage image;
    private static Boolean setIcons = true;

    public AreaBlock(Double x, Double y, Double width, Double height)
    {
        super(x, y, width, height);

        try {
            image = ImageIO.read(AreaBlock.class.getResourceAsStream("/resources/AreaBlock.png"));
        } catch (IOException e) {
            setIcons = false;
        }
    }

    public void paintBlock(Graphics2D g2D)
    {
        if (setIcons) {
            g2D.drawImage(image.getScaledInstance(width.intValue(), height.intValue(), Image.SCALE_DEFAULT), getX().intValue(), getY().intValue(), null);
        } else {
            Rectangle2D rectangle = new Rectangle2D.Double(getX().intValue(), getY().intValue(), width.intValue(), height.intValue());

            g2D.setColor(new Color(100, 0, 143));
            g2D.fill(rectangle);

            g2D.setColor(new Color(0, 0, 0, 0));
            g2D.draw(rectangle);
        }
    }

    public static void setIcons(Boolean status)
    {
        setIcons = status;
    }
}
