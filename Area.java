import java.util.Random;

public class Area {
    private Integer Hash;

    private static Integer randInt(Integer min, Integer max) {
        return new Integer((new Random()).nextInt((max - min) + 1) + min);
    }

    private void randArea()
    {
        Hash = Area.randInt(0, 128);
        System.out.println(Hash.toString() + " randomized");
    }

    public Area()
    {
        randArea();
    }
};
