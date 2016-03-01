import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Screen extends JPanel {
    private Block block;
    private Integer FPS = null;
    private Integer Offset = 0;

    public Screen(Block blocky)
    {
        setBackground(Color.black);

        setMinimumSize(new Dimension(300, 300));

        setMaximumSize(new Dimension(300, 300));

        setPreferredSize(new Dimension(300, 300));

        block = blocky;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear all scene
        g2.clearRect(0, 0, getWidth(), getHeight());

        // Fill Background
        g2.setColor(Color.black);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (FPS != null) {
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 9));
            g2.drawString(FPS.toString() + "FPS", 2, 270);
        }

        // Translate scene
        g2.translate(-Offset, 0);

        g2.setColor(Color.blue);
        g2.draw(new Line2D.Double(1, 1, 100, 80));

        block.paint(g2);

        g2.dispose();
    }

    public void moveForward()
    {
        
    }

    public void showFPS(Integer fps)
    {
        if (fps >= 0) {
            FPS = fps;
        }
    }

    public Integer getOffset()
    {
        return Offset;
    }

    public void increaseOffset()
    {
        Offset++;
    }
};
