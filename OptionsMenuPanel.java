import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionsMenuPanel extends JPanel {
    protected static OptionsMenuPanel instance;
    protected static Boolean setIcons = true;

    protected OptionsMenuPanel(Integer width, Integer height)
    {
        setMinimumSize(new Dimension(width, height));

        setMaximumSize(new Dimension(width, height));

        setPreferredSize(new Dimension(width, height));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public static OptionsMenuPanel instance()
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

    private static JLabel addLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    public static void create(Integer width, Integer height)
    {
        instance = new OptionsMenuPanel(width, height);

        instance.add(Box.createVerticalGlue());

        instance.add(addLabel("Enable Graphics"));

        instance.add(Box.createVerticalGlue());

        ButtonGroup group = new ButtonGroup();

        JRadioButton button;

        button = new JRadioButton("Yes", true);
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                OptionsMenuPanel.setIcons = (e.getStateChange() == 1);
            }
        });
        button.setFocusable(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        group.add(button);
        instance.add(button);

        button = new JRadioButton("No", false);
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                OptionsMenuPanel.setIcons = (e.getStateChange() == 0);
            }
        });
        button.setFocusable(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        group.add(button);
        instance.add(button);

        instance.add(Box.createVerticalGlue());

        instance.add(addButton("Save", new MouseListener() {
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
                Game.setIcons(setIcons);
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
