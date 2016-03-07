import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private static final Integer WIDTH = 300,
                                 HEIGHT = 150;
    private static GameFrame frame = null;

    private void setIcon()
    {
        try{
            setIconImage(ImageIO.read(GameFrame.class.getResourceAsStream("/resources/blocky.png")));
        } catch(IOException e){
            System.exit(-1);
        }
    }

    private void setListeners()
    {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e)
            {
                Game.addActiveKey(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                Game.removeActiveKey(e.getKeyCode());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent wEvt)
            {
                Game.removeActiveKeys();
            }

            @Override
            public void windowDeactivated(WindowEvent wEvt)
            {
                Game.removeActiveKeys();
            }

        });
    }

    private void addScreen()
    {
        GameScreen.prepare(WIDTH, HEIGHT);

        getContentPane().add(GameScreen.get());
    }

    private GameFrame()
    {
        super("Blocky");

        setResizable(false);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIcon();

        setListeners();

        addScreen();

        pack();

        setVisible(true);
    }

    public static void prepare()
    {
        frame = new GameFrame();
    }

    public static GameFrame get()
    {
        return frame;
    }

    public static void setAreaSize()
    {
        Area.setSize(WIDTH, HEIGHT);
    }

    public static Integer getContentHeight()
    {
        return HEIGHT;
    }
}
