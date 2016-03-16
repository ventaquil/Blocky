import java.lang.Thread;
import java.lang.IndexOutOfBoundsException;
import java.lang.InterruptedException;

import java.util.List;
import java.util.ArrayList;

public abstract class Game {
    private static List<Integer> activeKeys = new ArrayList<Integer>();
    private static Integer score = 0;

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

    private static void restart()
    {
        Area.restart();

        PlayerBlock.get().restart();

        GameScreen.get().restart();

        score = 0;
    }

    private static Boolean checkGame()
    {
        return GameScreen.get().getScreenNo() == 2;
    }

    private static Boolean checkEndScreen()
    {
        return GameScreen.get().getScreenNo() == 3;
    }

    private static Boolean checkEnd()
    {
        return (PlayerBlock.get().getY().intValue() >= GameFrame.get().getContentHeight()) ||
                PlayerBlock.checkEnd();
    }

    private static Boolean checkMenu()
    {
        return GameScreen.get().getScreenNo() == 0;
    }

    private static void executeActiveKeysActions()
    {
        for (int i = 0, j = activeKeys.size(); i < j; i++) {
            try {
                switch (activeKeys.get(i)) {
                    case 10: // ENTER
                        if (checkMenu()) {
                            GameScreen.get().executeActiveMenuElementAction();
                        } else if (checkEndScreen()) {
                            restart();
                        }
                        break;
                    case 27: // ESC
                        if (checkEndScreen()) {
                            GameScreen.get().goToMenu();
                        }
                        break;
                    case 37: // Arrow Left
                        if (checkGame()) {
                            PlayerBlock.get()
                                       .decreaseX();
                        }
                        break;
                    case 38: // Arrow Up
                        if (checkMenu()) {
                            GameScreen.get().menuUp();
                        } else if (checkGame()) {
                            PlayerBlock.get()
                                       .jump();
                        }
                        break;
                    case 39: // Arrow Right
                        if (checkGame()) {
                            PlayerBlock.get()
                                       .increaseX();

                            if ((PlayerBlock.get().getX() - GameScreen.get().getOffset()) > 46) {
                                GameScreen.get().increaseOffset();
                            }
                        }
                        break;
                    case 40: // Arrow Down
                        if (checkMenu()) {
                            GameScreen.get().menuDown();
                        } else if (checkGame()) {
                            PlayerBlock.get()
                                       .checkFall();
                        }
                        break;
                    case 82: // r
                        if (checkGame()) {
                            restart();
                        }
                        break;
                }
            } catch(IndexOutOfBoundsException e) { }
        }
    }

    private static void loop()
    {
        Long microtime, seconds = new Long(0), temp;
        Integer fps = 0;

        while (true) {
            microtime = System.currentTimeMillis();

            try {
                if (checkMenu()) {
                    Thread.sleep(new Long(100)); // Wait 100ms
                } else {
                    Thread.sleep(new Long(10)); // Wait 10ms
                }

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
                if (!checkEndScreen() && checkEnd() && !checkMenu()) {
                    GameScreen.get()
                              .showEndScreen();
                } else if (checkGame()) {
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

        Area.load();

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
