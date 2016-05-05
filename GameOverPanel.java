import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GameOverPanel extends CustomPanel {
    private static GameOverPanel instance = null;

    private void addButton(String text, MouseListener listener)
    {
        JButton button = new JButton(text);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setFocusable(false);
        button.addMouseListener(listener);

        add(button);
    }

    private void addButtons()
    {
        add(Box.createVerticalGlue());

        addLabel("Your Score " + Game.instance()
                                     .getScore());

        add(Box.createVerticalGlue());

        addButton("Retry", new MouseListener() {
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
                Game.instance()
                    .start();

                BlockyFrame.instance()
                           .changePanel(GamePanel.instance());
            }
        });

        add(Box.createVerticalGlue());

        addButton("Main Menu", new MouseListener() {
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
                BlockyFrame.instance()
                           .changePanel(MainMenuPanel.instance());
            }
        });

        add(Box.createVerticalGlue());

        addButton("Exit", new MouseListener() {
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
        });

        add(Box.createVerticalGlue());
    }

    private void addLabel(String text)
    {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        add(label);
    }

    public void executeKeysActions(List<Integer> activeKeys)
    {
        for (int i = 0; i < activeKeys.size(); i++) {
            switch(activeKeys.get(i)) {
                case 8: // Backspace
                    BlockyFrame.instance()
                               .changePanel(MainMenuPanel.instance());
                    break;
                case 27: // ESC
                    System.exit(0);
                    break;
            }
        }
    }

    private GameOverPanel()
    {
        super();

        setMinimumSize(new Dimension(600, 300));

        setMaximumSize(new Dimension(600, 300));

        setPreferredSize(new Dimension(600, 300));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addButtons();
    }

    public static GameOverPanel instance()
    {
        if (instance == null) {
            instance = new GameOverPanel();
        }

        instance.refreshJLabel();

        return instance;
    }

    private void refreshJLabel()
    {
        JLabel label = (JLabel) getComponent(1);
        label.setText("Your Score " + Game.instance()
                                          .getScore());
    }
};

