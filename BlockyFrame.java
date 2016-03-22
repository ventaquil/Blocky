import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BlockyFrame extends JFrame {
    private static BlockyFrame instance;

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
                Blocky.addActiveKey(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
//                 System.out.println("Key released " + e.getKeyCode());
                Blocky.removeActiveKey(e.getKeyCode());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent wEvt)
            {
                Blocky.removeActiveKeys();
            }

            @Override
            public void windowDeactivated(WindowEvent wEvt)
            {
                Blocky.removeActiveKeys();
            }

        });
    }

    private BlockyFrame(JPanel panel) {
        super("Blocky");

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIcon();

        setListeners();

        getContentPane().add(panel);

        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void create(JPanel panel)
    {
        instance = new BlockyFrame(panel);
    }

    public static void refresh()
    {
        if (instance != null) {
            instance.pack();
        }

        instance.repaint();
    }

    public static void changePaneContent(JPanel panel)
    {
        instance.getContentPane()
                .removeAll();

        instance.getContentPane()
                .add(panel);

        instance.pack();

        instance.revalidate();
        instance.repaint();
    }
}
