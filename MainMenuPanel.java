import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class MainMenuPanel extends CustomPanel {
    private static MainMenuPanel instance = null;

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

        addButton("Start Game", new MouseListener() {
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

        addButton("Choose Difficult Level", new MouseListener() {
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
                           .changePanel(ChangeDifficultLevelPanel.instance());
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

    public void executeKeysActions(List<Integer> activeKeys)
    {
        for (int i = 0; i < activeKeys.size(); i++) {
            switch(activeKeys.get(i)) {
                case 27: // ESC
                    System.exit(0);
                    break;
            }
        }
    }

    public static MainMenuPanel instance()
    {
        if (instance == null) {
            instance = new MainMenuPanel();
        }

        return instance;
    }

    private MainMenuPanel()
    {
        super();

        setMinimumSize(new Dimension(600, 300));

        setMaximumSize(new Dimension(600, 300));

        setPreferredSize(new Dimension(600, 300));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addButtons();
    }
};
