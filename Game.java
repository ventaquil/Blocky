import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

import java.lang.Thread;
import java.lang.InterruptedException;

import javax.swing.JFrame;

import java.util.List;
import java.util.ArrayList;

public abstract class Game {
    private static Screen screen;
    private static Block blocky;
    private static List<Integer> activeKeys = new ArrayList<Integer>();
    private static Short AreaPointer = null;
    private static Area[] Areas;

    private static void loadArea()
    {
        if (AreaPointer == null) {
            Areas = new Area[3];

            AreaPointer = 0;
            Areas[AreaPointer] = new Area();
        } else {
            AreaPointer = (new Integer(++AreaPointer % 3)).shortValue();
            Areas[AreaPointer] = new Area();
        }
    }

    private static JFrame prepareFrame()
    {
        JFrame frame = new JFrame("Blocky");

        frame.setSize(300, 300);

        frame.setResizable(false);

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            frame.setIconImage(ImageIO.read(Game.class.getResourceAsStream("/resources/blocky.png")));
        } catch(IOException e){
            System.exit(-1);
        }

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e)
            {
                Game.addActiveKey(e.getKeyCode());

                System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                Game.removeActiveKey(e.getKeyCode());

                System.out.println("Key released code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent wEvt)
            {
                Game.removeActiveKeys();
                // ((JFrame)wEvt.getSource()).dispose();
                System.out.println("Iconified");
            }

            @Override
            public void windowDeactivated(WindowEvent wEvt)
            {
                Game.removeActiveKeys();
                System.out.println("Deactivated");
            }

        });

        frame.getContentPane()
             .add(screen);

        return frame;
    }

    private static void mainLoop()
    {
        Long microtime, seconds = new Long(0), temp;
        Integer fps = 0;

        while (true) {
            microtime = System.currentTimeMillis();

            try {
                Thread.sleep(new Long(1000 / 100));

                // Counting FPS
                temp = new Long(microtime / 1000);
                if (temp > seconds) {
                    seconds = temp;

                    screen.showFPS(fps);
                    fps = 0;
                } else {
                    fps++;
                }

                executeActiveKeysActions();

                screen.repaint();
            } catch(InterruptedException ex) {
                System.out.println("Exception!");
                Thread.currentThread()
                      .interrupt();
            }
        }
    }

    public static void main(String[] args)
    {
        blocky = new Block();

        screen = new Screen(blocky);

        loadArea();

        JFrame frame = prepareFrame();
        frame.setVisible(true);

        mainLoop();
    }

    public static void removeActiveKeys()
    {
        activeKeys.clear();
        getActiveKeys();
    }

    public static Boolean checkActiveKey(Integer keyCode)
    {
        for (int i = 0, j = activeKeys.size(); i < j; i++) {
            if (activeKeys.get(i) == keyCode) {
                return true;
            }
        }

        return false;
    }

    public static void getActiveKeys()
    {
        System.out.println("Active keys:");
        for (int i = 0, j = activeKeys.size(); i < j; i++) {
            System.out.println("Key Code: " + activeKeys.get(i).toString());
        }
    }

    public static void addActiveKey(Integer keyCode)
    {
        if (!checkActiveKey(keyCode)) {
            activeKeys.add(keyCode);
        }
        getActiveKeys();
    }

    public static void removeActiveKey(Integer keyCode)
    {
        activeKeys.remove(keyCode);
        getActiveKeys();
    }

    private static void executeActiveKeysActions()
    {
        for (int i = 0, j = activeKeys.size(); i < j; i++) {
            switch (activeKeys.get(i)) {
                case 27: // ESC
                    System.exit(0);
                    break;
                case 37: // Arrow Left
                    if (blocky.getX()
                              .intValue() > screen.getOffset()) {
                        blocky.decreaseX();
                    }
                    break;
                case 38: // Arrow Up
                    blocky.jump();
                    break;
                case 39: // Arrow Right
                    if ((blocky.getX()
                               .intValue() - screen.getOffset()) > 46) {
                        screen.increaseOffset();
                    }
                    blocky.increaseX();
                    break;
            }
        }
    }
}
