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
    private Short screenNo = 0, // 0 - menu; 1 - choose difficult level; 2 - game; 3 - end
                  activeMenuNo = 0;

    private GameScreen()
    {
        width = GameScreen.width;
        height = GameScreen.height;

        setBackground(Color.BLACK);

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
        g2D.translate(offset, -23);

        g2D.setColor(Color.WHITE);
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

        FontMetrics fm;
        Rectangle2D r;

        // Clear all scene
        g2D.clearRect(0, 0, getWidth(), getHeight());

        // Fill Background
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        switch (screenNo) {
            case 0:
                Short posN = 2;
                Integer space = (getHeight() - (posN * 14)) / 3,
                        helper;

                g2D.setColor(Color.WHITE);
                g2D.setFont(new Font("Arial", Font.BOLD, 14));
                fm = g2D.getFontMetrics();

                r = fm.getStringBounds("Start", g2D);
                helper = (getWidth() - (int) r.getWidth()) / 2;
                g2D.drawString("Start", helper, space);
                if (activeMenuNo == 0) {
                    g2D.draw(new Line2D.Double(helper - 2, space + 2, helper + (int) r.getWidth() + 2, space + 2));
                }

                r = fm.getStringBounds("Exit", g2D);
                helper = (getWidth() - (int) r.getWidth()) / 2;
                space = 2 * space + 14;
                g2D.drawString("Exit", helper, space);
                if (activeMenuNo == 1) {
                    g2D.draw(new Line2D.Double(helper - 2, space + 2, helper + (int) r.getWidth() + 2, space + 2));
                }
                break;
            case 2:
                g2D.translate(-offset, 23);
                Area area = Area.get();
                if (area != null) {
                    area.paint(g2D);
                }

                PlayerBlock.get()
                           .paint(g2D);

                printOverlay(g2D);
                break;
            case 3:
                g2D.setColor(Color.WHITE);

                String score = "Your final score " + Game.getScore();
                g2D.setFont(new Font("Arial", Font.BOLD, 22));
                fm = g2D.getFontMetrics();
                r = fm.getStringBounds(score, g2D);
                int topOffset = (getHeight() - (int) r.getHeight()) / 2;
                g2D.drawString(score, (getWidth() - (int) r.getWidth()) / 2, topOffset);

                g2D.setFont(new Font("Arial", Font.BOLD, 14));
                fm = g2D.getFontMetrics();
                r = fm.getStringBounds("Press [ENTER] to restart", g2D);
                g2D.drawString("Press [ENTER] to restart", (getWidth() - (int) r.getWidth()) / 2, topOffset + 28);

                r = fm.getStringBounds("or [ESC] to exit", g2D);
                g2D.drawString("or [ESC] to exit", (getWidth() - (int) r.getWidth()) / 2, topOffset + 28 + 14);
                break;
        }

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
        Area.get()
            .removeUseless((++offset).intValue());

        if (offset % 40. == 0.) {
            Area.get()
                .generateNew(offset.intValue());
        }
    }

    public void showEndScreen()
    {
        offset = 0.;
        screenNo = 3;
        activeMenuNo = 0;
    }

    public void restart()
    {
        offset = 0.;
        screenNo = 2;
        activeMenuNo = 0;
    }

    public Short getScreenNo()
    {
        return screenNo;
    }

    public void menuUp()
    {
        activeMenuNo++;

        if (activeMenuNo > 1) {
            activeMenuNo = 0;
        }
    }

    public void menuDown()
    {
        activeMenuNo--;

        if (activeMenuNo < 0) {
            activeMenuNo = 1;
        }
    }

    public void executeActiveMenuElementAction()
    {
        switch (activeMenuNo) {
            case 0:
                screenNo = 2;
                break;
            case 1:
                System.exit(0);
                break;
        }
    }

    public void goToMenu()
    {
        offset = 0.;
        screenNo = 0;
        activeMenuNo = 0;
    }
}
