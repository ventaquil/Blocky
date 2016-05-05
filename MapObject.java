import java.awt.Graphics2D;

public abstract class MapObject {
    public Boolean detectCollision(MapObject object)
    {
        return false;
    }

    public void collisionEvent(MapObject object) { }

    public void paintComponent(Graphics2D g2D) { }
};
