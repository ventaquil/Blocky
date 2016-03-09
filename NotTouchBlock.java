import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

public class NotTouchBlock extends AreaBlock {
    public NotTouchBlock(Double x, Double y, Double width, Double height)
    {
        super(x, y, width, height);

        try {
            image = ImageIO.read(NotTouchBlock.class.getResourceAsStream("/resources/NotTouchBlock.png"));
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    @Override
    public String getName()
    {
        return "NotTouchBlock";
    }
}
