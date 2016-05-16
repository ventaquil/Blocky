import java.awt.Dimension;
import java.util.concurrent.Semaphore;

public class Game {
    private static Game instance = null;
    public static final int EASY = 0,
                            NORMAL = 1,
                            HARD = 2;
    private static int difficultLevel = NORMAL;
    private Boolean started = false,
                    ended = false;
    private Integer score = 0;
    private Area area;
    private static Semaphore areaSemaphore = new Semaphore(1, true);

    public static void changeLevel(int level)
    {
        if ((level == EASY) || (level == NORMAL) || (level == HARD)) {
            difficultLevel = level;

            Area.instance()
                .setSize(new Dimension(650, 277))
                .randObjects();
        } else {
            // @TODO exception
        }
    }

    public void finish()
    {
        ended = true;

        area = null;

        areaSemaphore.release();

        BlockyFrame.instance()
                   .changePanel(GameOverPanel.instance());
    }

    private Game()
    {
        if (areaSemaphore.tryAcquire()) {
            Area.instance()
                .setSize(new Dimension(650, 277))
                .randObjects();
        }
    }

    public static Integer getLevel()
    {
        return difficultLevel;
    }

    public static String getLevelString()
    {
        switch (difficultLevel) {
            case EASY:
                return "EASY";
            case NORMAL:
                return "NORMAL";
            case HARD:
                return "HARD";
            default:
                return "UNDEFINED";
        }
    }

    public Integer getScore()
    {
        return score;
    }

    public static Game instance()
    {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public Boolean isEnded()
    {
        return !isStarted();
    }

    public Boolean isStarted()
    {
        return started && !ended;
    }

    public void loopAction()
    {
        if (isStarted()) {
            PlayerBlock.instance()
                       .move();

            Area.instance()
                .removeOld()
                .randNew();

            if (difficultLevel == HARD) {
                GamePanel panel = GamePanel.instance();

                panel.increaseOffset();

                PlayerBlock playerBlock = PlayerBlock.instance();

                if (playerBlock.getX() + playerBlock.getWidth() <= panel.getOffset()) {
                    finish();
                }
            }
        }
    }

    public static void restart()
    {
        instance = new Game();

        PlayerBlock.restart();

        GamePanel.instance()
                 .restart();
    }

    public void start()
    {
        if (ended) {
            restart();

            System.gc();

            instance.start();
        } else {
            PlayerBlock.instance()
                       .fall();
            started = true;
        }
    }

    public void updateScore(Integer newScore)
    {
        if (newScore > score) {
            score = newScore;
        }
    }
};
