import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.lang.IndexOutOfBoundsException;
import java.util.List;

public class GamePanel extends CustomPanel {
    private static GamePanel instance = null;
    private Integer offset = 0;

    public void executeKeysActions(List<Integer> activeKeys)
    {
        Boolean isStarted = Game.instance()
                                .isStarted();

        for (int i = 0; i < activeKeys.size(); i++) {
            try {
                switch(activeKeys.get(i)) {
                    case 37: // Arrow Left
                        if (isStarted) {
                            PlayerBlock.instance()
                                       .moveLeft();
                        }
                        break;
                    case 38: // Arrow Up
                        if (isStarted) {
                            PlayerBlock.instance()
                                       .jump();
                        }
                        break;
                    case 39: // Arrow Right
                        if (isStarted) {
                            PlayerBlock.instance()
                                       .moveRight();
                        }
                        break;
                    case 81: // q
                        Game.instance()
                            .finish();
                        break;
                }
            } catch (IndexOutOfBoundsException e) { }
        }
    }

    private GamePanel()
    {
        super();

        setMinimumSize(new Dimension(600, 300));

        setMaximumSize(new Dimension(600, 300));

        setPreferredSize(new Dimension(600, 300));
    }

    public Integer getOffset()
    {
        return offset;
    }

    public void increaseOffset()
    {
        offset++;
    }

    public void increaseOffset(Integer x)
    {
        if (x - offset > 60) {
            offset = x - 60;
        }
    }

    public static GamePanel instance()
    {
        if (instance == null) {
            instance = new GamePanel();
        }

        return instance;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;

        // Clear scene
        g2D.clearRect(0, 0, getWidth(), getHeight());

        // Fill background
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        // Draw score
        g2D.setColor(Color.WHITE);

        g2D.setFont(new Font("Arial", Font.BOLD, 14));

        String score = "Your score: " + Game.instance().getScore();

        FontMetrics fm = g2D.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(score, g2D);
        g2D.drawString(score, (getWidth() - (int) r.getWidth()) / 2, (int) r.getHeight());

        g2D.draw(new Line2D.Double(0., 22., getWidth(), 22.));

        // Translate scene
        g2D.translate(-offset, 23);

        Area.instance()
            .paintComponent(g2D);

        PlayerBlock.instance()
                   .paintComponent(g2D);

        g2D.dispose();
    }

    public void restart()
    {
        offset = 0;
    }
};
