import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverMenuPanel extends JPanel {
    protected static GameOverMenuPanel instance = null;

    protected GameOverMenuPanel(Integer width, Integer height)
    {
        setMinimumSize(new Dimension(width, height));

        setMaximumSize(new Dimension(width, height));

        setPreferredSize(new Dimension(width, height));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    protected static void createIfNull()
    {
        if (instance == null) {
            create(600, 300);
        }
    }

    public static GameOverMenuPanel instance()
    {
        createIfNull();

        return instance;
    }

    private static JButton addButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private static JButton addButton(String text, MouseListener listener) {
        JButton button = addButton(text);

        if (listener != null) {
            button.addMouseListener(listener);
        }

        return button;
    }

    private static JLabel addLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    public static void clear()
    {
        instance = null;
    }

    public static void create(Integer width, Integer height)
    {
        instance = new GameOverMenuPanel(width, height);

        instance.add(Box.createVerticalGlue());

        instance.add(addLabel("Your Score " + Game.getScore()));

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Retry", new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseClicked(MouseEvent e) {
                GamePanel.restart();
                Game.run();
                Blocky.changeToGame();

                GameOverMenuPanel.clear();
            }
        }));

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Main Menu", new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseClicked(MouseEvent e) {
                Blocky.changeToMainMenu();
            }
        }));

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Exit", new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        }));

        instance.add(Box.createVerticalGlue());
    }
}
