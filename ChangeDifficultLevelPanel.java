import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChangeDifficultLevelPanel extends CustomPanel {
    private static ChangeDifficultLevelPanel instance = null;

    private JButton createButton(String text, MouseListener listener)
    {
        JButton button = new JButton(text);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setFocusable(false);
        button.addMouseListener(listener);

        return button;
    }

    private void addButtons()
    {
        addLabel("<html>Current level <b>" + Game.getLevelString() +
                 "</b></html>");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        buttonPanel.add(Box.createVerticalGlue());

        buttonPanel.add(createButton("Easy", new MouseListener() {
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
                Game.changeLevel(Game.EASY);

                refreshJLabel();

                BlockyFrame.instance()
                           .changePanel(ChangeDifficultLevelPanel.instance());
            }
        }));

        buttonPanel.add(Box.createVerticalGlue());

        buttonPanel.add(createButton("Normal", new MouseListener() {
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
                Game.changeLevel(Game.NORMAL);

                refreshJLabel();

                BlockyFrame.instance()
                           .changePanel(ChangeDifficultLevelPanel.instance());
            }
        }));

        buttonPanel.add(Box.createVerticalGlue());

        buttonPanel.add(createButton("Hard", new MouseListener() {
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
                Game.changeLevel(Game.HARD);

                refreshJLabel();

                BlockyFrame.instance()
                           .changePanel(ChangeDifficultLevelPanel.instance());
            }
        }));

        buttonPanel.add(Box.createVerticalGlue());

        buttonPanel.add(createButton("Back", new MouseListener() {
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
        }));

        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addLabel(String text)
    {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(label, BorderLayout.NORTH);
    }

    private ChangeDifficultLevelPanel()
    {
        super();

        setMinimumSize(new Dimension(600, 300));

        setMaximumSize(new Dimension(600, 300));

        setPreferredSize(new Dimension(600, 300));

        setLayout(new BorderLayout());

        addButtons();
    }

    public void executeKeysActions(List<Integer> activeKeys)
    {
        for (int i = 0; i < activeKeys.size(); i++) {
            switch(activeKeys.get(i)) {
                case 8: // Backspace
                    BlockyFrame.instance()
                               .changePanel(MainMenuPanel.instance());
                    break;
            }
        }
    }

    public static ChangeDifficultLevelPanel instance()
    {
        if (instance == null) {
            instance = new ChangeDifficultLevelPanel();
        }

        return instance;
    }

    private void refreshJLabel()
    {
        JLabel label = (JLabel) getComponent(0);
        label.setText("<html>Current level <b>" + Game.getLevelString() +
                      "</b></html>");
    }
};
