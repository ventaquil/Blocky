import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DifficultLevelMenuPanel extends JPanel {
    protected static DifficultLevelMenuPanel instance;

    protected DifficultLevelMenuPanel(Integer width, Integer height)
    {
        setMinimumSize(new Dimension(width, height));

        setMaximumSize(new Dimension(width, height));

        setPreferredSize(new Dimension(width, height));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public static DifficultLevelMenuPanel instance()
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
        instance = new DifficultLevelMenuPanel(width, height);

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Easy", new MouseListener() {
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
                Game.changeDifficultLevel(0);
                Blocky.changeToMainMenu();
            }
        }));

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Normal", new MouseListener() {
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
                Game.changeDifficultLevel(1);
                Blocky.changeToMainMenu();
            }
        }));

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Hard", new MouseListener() {
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
                Game.changeDifficultLevel(2);
                Blocky.changeToMainMenu();
            }
        }));

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Back", new MouseListener() {
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
    }
}
