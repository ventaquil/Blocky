import java.awt.Dimension;
import java.awt.Graphics2D;

public class MapBlock extends MapObject {
    protected Integer x,
                      y,
                      width,
                      height;

    public Boolean detectCollision(MapBlock object)
    {
        if (
            (
                (
                    (getX() <= object.getX()) &&
                    (object.getX() < getX() + getWidth())
                ) ||
                (
                    (getX() < object.getX() + object.getWidth()) &&
                    (object.getX() + object.getWidth() <= getX() + getWidth())
                )
            ) &&
            (
                (
                    (getY() <= object.getY()) &&
                    (object.getY() < getY() + getHeight())
                ) ||
                (
                    (getY() < object.getY() + object.getHeight()) &&
                    (object.getY() + object.getHeight() <= getY() + getHeight())
                )
            )
            
        ) {
            collisionEvent(object);

            object.collisionEvent(this);

            return true;
        } else {
            return false;
        }
    }

    public Integer getHeight()
    {
        return height;
    }

    public Integer getWidth()
    {
        return width;
    }

    public Integer getX()
    {
        return x;
    }

    public Integer getY()
    {
        return y;
    }

    public MapBlock(Dimension coordinates, Dimension size)
    {
        x = coordinates.width;
        y = coordinates.height;

        // @TODO exceptions
        width = size.width;
        height = size.height;
    }
};
