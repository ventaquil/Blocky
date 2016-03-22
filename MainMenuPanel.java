import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
    protected static MainMenuPanel instance;

    protected MainMenuPanel(Integer width, Integer height)
    {
        setMinimumSize(new Dimension(width, height));

        setMaximumSize(new Dimension(width, height));

        setPreferredSize(new Dimension(width, height));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public static MainMenuPanel instance()
    {
        return instance;
    }

    private static JButton addButton(String text) {
        JButton button;

        button = new JButton(text);
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

    public static void create(Integer width, Integer height)
    {
        instance = new MainMenuPanel(width, height);

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Start Game", new MouseListener() {
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

        instance.add(addButton("Choose Difficult Level", new MouseListener() {
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
                Blocky.changeToDifficultLevelMenu();
            }
        }));

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Options", new MouseListener() {
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
                Blocky.changeToOptionsMenu();
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
