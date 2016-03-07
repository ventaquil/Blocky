import java.lang.Thread;
import java.lang.IndexOutOfBoundsException;
import java.lang.InterruptedException;

import java.util.List;
import java.util.ArrayList;

public abstract class Game {
    private static List<Integer> activeKeys = new ArrayList<Integer>();
    private static Integer score = 0;
    private static Area[] areas;
    private static Short areaPointer = 0;

    public static void removeActiveKeys()
    {
        activeKeys.clear();
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

    public static void addActiveKey(Integer keyCode)
    {
        if (!checkActiveKey(keyCode)) {
            activeKeys.add(keyCode);
        }
    }

    public static void removeActiveKey(Integer keyCode)
    {
        activeKeys.remove(keyCode);
    }

    private static void generateAreas()
    {
        areas = new Area[3];
        areaPointer = 1;

        for (int i = 0; i < 3; i++) {
            areas[i] = Area.load();
        }
        areas[0].setActive();
    }

    private static void restart()
    {
        generateAreas();

        PlayerBlock.get().restart();

        GameScreen.get().restart();

        score = 0;
    }

    private static Boolean checkEnd()
    {
        return PlayerBlock.get().getY().intValue() >= GameFrame.get().getContentHeight();
    }

    private static void executeActiveKeysActions()
    {
        for (int i = 0, j = activeKeys.size(); i < j; i++) {
            try {
                switch (activeKeys.get(i)) {
                    case 10: // ENTER
                        if (checkEnd()) {
                            restart();
                        }
                        break;
                    case 27: // ESC
                        System.exit(0);
                        break;
                    case 37: // Arrow Left
                        if (!checkEnd()) {
                            PlayerBlock.get()
                                       .decreaseX();
                        }
                        break;
                    case 38: // Arrow Up
                        if (!checkEnd()) {
                            PlayerBlock.get()
                                       .jump();
                        }
                        break;
                    case 39: // Arrow Right
                        if (!checkEnd()) {
                            if ((PlayerBlock.get().getX() - GameScreen.get().getOffset()) > 46) {
                                GameScreen.get().increaseOffset();
                            }

                            PlayerBlock.get()
                                       .increaseX();
                        }
                        break;
                    case 40: // Arrow Down
                        if (!checkEnd()) {
                            PlayerBlock.get()
                                       .checkFall();
                        }
                        break;
                    case 82: // r
                        if (!checkEnd()) {
                            restart();
                        }
                        break;
                }
            } catch(IndexOutOfBoundsException e) {
                activeKeys.remove(i);
            }
        }
    }

    private static void loop()
    {
        Long microtime, seconds = new Long(0), temp;
        Integer fps = 0;

        while (true) {
            microtime = System.currentTimeMillis();

            try {
                Thread.sleep(new Long(1000 / 100));

                // Count FPS
                temp = new Long(microtime / 1000);
                if (temp > seconds) {
                    seconds = temp;

                    GameScreen.updateFps(fps);
                    fps = 0;
                } else {
                    fps++;
                }

                // Execute all active keys actions
                executeActiveKeysActions();

                // Check end condiction
                if (checkEnd()) {
                    GameScreen.get()
                              .showEndScreen();
                } else {
                    // Block move actions
                    PlayerBlock.get()
                               .jumping();
                    PlayerBlock.get()
                               .falling();
                }

                // Repaint screen
                GameScreen.get()
                          .repaint();
            } catch(InterruptedException ex) {
                Thread.currentThread()
                      .interrupt();
            }
        }
    }

    public static void run()
    {
        GameFrame.setAreaSize();

        PlayerBlock.setStart(0., 0., 10., 10.);

        generateAreas();

        PlayerBlock.start();

        GameFrame.prepare();

        loop();
    }

    public static void updateScore(Integer newScore)
    {
        if (newScore > score) {
            score = newScore;
        }
    }

    public static Integer getScore()
    {
        return score;
    }
}
