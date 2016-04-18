public class Game {
    private static Game instance;
    private static int difficultLevel = 0; // 0 - easy, 1 - normal, 2 - hard
    private Integer score = 0;

    public static void changeDifficultLevel(int level)
    {
        if (level >= 0 && level <= 2) {
            difficultLevel = level;
        }
    }

    private Game()
    {
        PlayerBlock.create();

        Area.create();
    }

    public static void run()
    {
        if (instance != null) {
            restart();
        }

        instance = new Game();
    }

    public static Integer getScore()
    {
        return instance.score;
    }

    public static Boolean ifStarted()
    {
        return instance != null;
    }

    public static void updateScore(Integer newX)
    {
        if (newX > instance.score) {
            instance.score = newX;
        }
    }

    public static void finish()
    {
        Blocky.changeToGameOverMenu();

        restart();
    }

    public static void restart()
    {
        instance = null;
        System.gc();
    }
}
