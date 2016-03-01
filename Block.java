import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import java.lang.Math;

import java.net.URL;

public class Block {
    private Double x = 0.;
    private Double y = 0.;
    private Boolean jumping = false;
    private Double jumpCounter = null;

    public void paint(Graphics2D g2)
    {
        Color oldColor = g2.getColor();

        if (jumping) {
            jumping();
        }

        try {
            BufferedImage image = ImageIO.read(new File("./resources/blocky.png"));
            g2.drawImage(image.getScaledInstance(10, 10, Image.SCALE_DEFAULT), x.intValue(), (new Double(-y + 170.0)).intValue(), null);
        } catch (IOException e) {
            System.exit(-1);
        }
        // g2.setColor(Color.red);
        // Rectangle2D quad = new Rectangle2D.Double(x, -y + 170, 10, 10);
        // g2.fill(quad);

        // g2.draw(quad);

        g2.setColor(oldColor);
    }

    public void increaseX()
    {
        x++;
    }

    public void decreaseX()
    {
        x--;
    }

    public Double getX()
    {
        return x;
    }

    public Double getY()
    {
        return y;
    }

    public void jump()
    {
        if (!jumping) {
            jumping = true;
            jumpCounter = 0.;
        }
    }

    private void jumping()
    {
        y = -Math.pow(jumpCounter - Math.sqrt(30), 2) + 30.1;

        if (y > 0.) {
            jumpCounter += 0.1;
        } else {
            y = 0.;
            jumpCounter = null;
            jumping = false;
        }
    }
};
