import java.lang.Thread;
import java.lang.IndexOutOfBoundsException;
import java.lang.InterruptedException;

public abstract class Blocky {
    private static BlockyFrame frame;

    private static void loop()
    {
        while(true) {
            try {
                Thread.sleep(20); // Wait 20ms

                Game.instance()
                    .loopAction();
            } catch (InterruptedException e) {
                Thread.currentThread()
                      .interrupt();
            }

            BlockyFrame.instance()
                       .executeKeysActions()
                       .refresh();
        }
    }

    public static void main(String[] arguments)
    {
        frame = BlockyFrame.instance();

        loop();
    }
};
