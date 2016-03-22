import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    protected static GamePanel instance;
    protected Integer offset = 0;

    protected GamePanel(Integer width, Integer height)
    {
        setMinimumSize(new Dimension(width, height));

        setMaximumSize(new Dimension(width, height));

        setPreferredSize(new Dimension(width, height));
    }

    public static GamePanel instance()
    {
        return instance;
    }

    public static void create(Integer width, Integer height)
    {
        instance = new GamePanel(width, height);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;

        // Clear all scene
        g2D.clearRect(0, 0, getWidth(), getHeight());

        // Fill Background
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        g2D.setColor(Color.WHITE);

        // Draw score
        g2D.setFont(new Font("Arial", Font.BOLD, 14));
        String score = "Your score: " + Game.getScore();
        FontMetrics fm = g2D.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(score, g2D);
        g2D.drawString(score, (getWidth() - (int) r.getWidth()) / 2, (int) r.getHeight());

        g2D.draw(new Line2D.Double(0., 21., getWidth(), 21.));

        // Translate scene
        g2D.translate(-offset, 23);

        PlayerBlock.paint(g2D);
        Area.paint(g2D);

        g2D.dispose();
    }

    public static Integer getOffset()
    {
        return instance.offset;
    }

    public static void increaseOffset(Double blockX)
    {
        increaseOffset(blockX.intValue());
    }

    public static void increaseOffset(Integer blockX)
    {
        if ((blockX - instance.offset) > 46) {
            instance.offset = blockX - 46;

            Area.removeUseless(instance.offset);

            if (instance.offset % 40 == 0) {
                Area.generateNew(instance.offset);
            }
        }
    }

    public static void restart()
    {
        instance.offset = 0;
        Game.restart();
    }
}
