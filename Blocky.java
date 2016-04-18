import java.lang.Thread;
import java.lang.IndexOutOfBoundsException;
import java.lang.InterruptedException;

import java.util.List;
import java.util.ArrayList;

abstract public class Blocky {
    private static List<Integer> activeKeys = new ArrayList<Integer>();

    private static Boolean checkActiveKey(Integer keyCode)
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

    public static void removeActiveKeys()
    {
        activeKeys.clear();
    }

    private static void executeActiveKeysActions()
    {
        for (int i = 0, j = activeKeys.size(); i < j; i++) {
            try {
                switch (activeKeys.get(i)) {
                    case 10: // ENTER

                        break;
                    case 27: // ESC

                        break;
                    case 37: // Arrow Left
                        if (Game.ifStarted()) {
                            PlayerBlock.decreaseX();
                        }
                        break;
                    case 38: // Arrow Up
                        if (Game.ifStarted()) {
                            PlayerBlock.jump();
                        }
                        break;
                    case 39: // Arrow Right
                        if (Game.ifStarted()) {
                            PlayerBlock.increaseX();
                        }
                        break;
                    case 40: // Arrow Down

                        break;
                    case 82: // r

                        break;
                }
            } catch(IndexOutOfBoundsException e) { }
        }
    }

    private static void loop()
    {
        while (true) {
            if (Game.ifStarted()) {
                try {
                    Thread.sleep(12); // Wait 12ms

                    PlayerBlock.move();
                } catch (InterruptedException e) {
                    Thread.currentThread()
                          .interrupt();
                }
            }

            executeActiveKeysActions();

            BlockyFrame.refresh();
        }
    }

    public static void main(String[] args)
    {
        MainMenuPanel.create(600, 300);

        BlockyFrame.create(MainMenuPanel.instance());

        DifficultLevelMenuPanel.create(600, 300);
        GamePanel.create(600, 300);

        PlayerBlock.setBlockSize(20, 20);
        PlayerBlock.setStartPosition(1., 1.);
        Area.setBlockSize(20, 20);
        Area.setSize(600, 300);

        loop();
    }

    public static void changeToMainMenu()
    {
        BlockyFrame.changePaneContent(MainMenuPanel.instance());
    }

    public static void changeToDifficultLevelMenu()
    {
        BlockyFrame.changePaneContent(DifficultLevelMenuPanel.instance());
    }

    public static void changeToGame()
    {
        BlockyFrame.changePaneContent(GamePanel.instance());
    }

    public static void changeToGameOverMenu()
    {
        BlockyFrame.changePaneContent(GameOverMenuPanel.instance());
    }
}
