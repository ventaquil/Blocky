import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
        GameScreen.height = height;

        screen = new GameScreen();
    }

    public static GameScreen get()
    {
        return screen;
    }

    private void printOverlay(Graphics2D g2D)
    {
        g2D.translate(0, 0);

        g2D.setColor(Color.white);

        // Show FPS
        if (fps != null) {
            g2D.setFont(new Font("Arial", Font.PLAIN, 9));
            g2D.drawString(GameScreen.fps + "FPS", 2, GameFrame.get().getContentHeight() - 2);
        }

        // Show Blocky (x,y)
        g2D.setFont(new Font("Arial", Font.PLAIN, 9));
        g2D.drawString("x: " + PlayerBlock.get().getX(), 2, 9);
        g2D.drawString("y: " + PlayerBlock.get().getY(), 2, 19);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;

        // Clear all scene
        g2D.clearRect(0, 0, getWidth(), getHeight());

        // Fill Background
        g2D.setColor(Color.black);
        g2D.fillRect(0, 0, getWidth(), getHeight());

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
