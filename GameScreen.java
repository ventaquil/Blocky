import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class GameScreen extends JPanel {
    private static Integer width, height, fps = null;
    private static GameScreen screen;
    private Double offset = 0.;

    private GameScreen()
    {
        width = GameScreen.width;
        height = GameScreen.height;

        setBackground(Color.black);

        setMinimumSize(new Dimension(width, height));

        setMaximumSize(new Dimension(width, height));

        setPreferredSize(new Dimension(width, height));
    }

    public static void prepare(Integer width, Integer height)
    {
        GameScreen.width = width;
        GameScreen.height = height + 23;

        screen = new GameScreen();
    }

    public static GameScreen get()
    {
        return screen;
    }

    private void printOverlay(Graphics2D g2D)
    {
        g2D.translate(0, -23);

        g2D.setColor(Color.white);
        g2D.setFont(new Font("Arial", Font.PLAIN, 9));

        // Draw FPS
        if (fps != null) {
            g2D.drawString(GameScreen.fps + "FPS", 2, getHeight() - 2);
        }

        // Draw Blocky (x,y)
        g2D.drawString("x: " + String.format("%.2f", PlayerBlock.get().getX()), 2, 9);
        g2D.drawString("y: " + String.format("%.2f", PlayerBlock.get().getY()), 2, 19);

        // Draw score
        g2D.setFont(new Font("Arial", Font.BOLD, 14));
        String score = "Your score: " + Game.getScore();
        FontMetrics fm = g2D.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(score, g2D);
        g2D.drawString(score, (getWidth() - (int) r.getWidth()) / 2, (int) r.getHeight());

        g2D.draw(new Line2D.Double(0., 23., getWidth(), 23.));
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;

        // Clear all scene
        g2D.clearRect(0, 0, getWidth(), getHeight());

        // Fill Background
        g2D.setColor(Color.black);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        g2D.translate(0, 23);
        Area activeArea = Area.getActive();
        if (activeArea != null) {
            activeArea.paint(g2D);
        }

        PlayerBlock.get()
                   .paint(g2D);

        printOverlay(g2D);

        g2D.dispose();
    }

    public static void updateFps(Integer fps)
    {
        if (fps > 0) {
            GameScreen.fps = fps;
        } else {
            GameScreen.fps = null;
        }
    }

    public Double getOffset()
    {
        return offset;
    }

    public void increaseOffset()
    {
        offset++;
    }
}
