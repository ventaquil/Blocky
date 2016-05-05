import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BlockyFrame extends JFrame {
    private static BlockyFrame instance = null;
    private List<Integer> activeKeys = new ArrayList<Integer>();

    private void addActiveKey(Integer keyCode)
    {
        if (!checkActiveKey(keyCode)) {
            activeKeys.add(keyCode);
        }
    }

    private BlockyFrame()
    {
        super("Blocky");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIcon();

        setListeners();

        setResizable(false);

        getContentPane().add(MainMenuPanel.instance());

        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void changePanel(JPanel panel)
    {
        Container pane = getContentPane();

        pane.removeAll();

        pane.add(panel);

        refresh();

        System.gc();
    }

    private Boolean checkActiveKey(Integer keyCode)
    {
        for (int i = 0; i < activeKeys.size(); i++) {
            if (activeKeys.get(i) == keyCode) {
                return true;
            }
        }

        return false;
    }

    public BlockyFrame executeKeysActions()
    {
        try {
            CustomPanel customPanel = (CustomPanel) getContentPane().getComponent(0);
            customPanel.executeKeysActions(activeKeys);
        } catch (ArrayIndexOutOfBoundsException e) { }

        return this;
    }

    public static BlockyFrame instance()
    {
        if (instance == null) {
            instance = new BlockyFrame();
        }

        return instance;
    }

    private void removeActiveKey(Integer keyCode)
    {
        activeKeys.remove(keyCode);
    }

    private void removeActiveKeys()
    {
        activeKeys.clear();
    }

    public void refresh()
    {
        invalidate();
        validate();
        repaint();
    }

    private void setIcon()
    {
        try{
            setIconImage(ImageIO.read(BlockyFrame.class.getResourceAsStream("/resources/PlayerBlock.png")));
        } catch(IOException e){ }
    }

    private void setListeners()
    {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e)
            {
                addActiveKey(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                removeActiveKey(e.getKeyCode());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e)
            {
                removeActiveKeys();
            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {
                removeActiveKeys();
            }

        });
    }
};
