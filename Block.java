public class Block {
    protected Double x, y, width, height;


    public Block(Double x, Double y, Double width, Double height)
    {
        this.x = x;
        this.y = y;

        if (width > 0.) {
            this.width = width;
        }
        // @TODO exception

        if (height > 0.) {
            this.height = height;
        }
        // @TODO exception
    }

    public Double getX()
    {
        return x;
    }

    public Double getY()
    {
        return y;
    }

    public Double getWidth()
    {
        return width;
    }

    public Double getHeight()
    {
        return height;
    }

    public Boolean detectCollision(Block block)
    {
        return (
            (
                (
                    (x <= block.getX()) &&
                    (block.getX() < (x + width))
                ) ||
                (
                    (x < (block.getX() + block.getWidth())) &&
                    ((block.getX() + block.getWidth()) <= (x + width))
                )
            ) &&
            (
                (
                    (y <= block.getY()) &&
                    (block.getY() < (y + height))
                ) ||
                (
                    (y < (block.getY() + block.getHeight())) &&
                    ((block.getY() + block.getHeight()) <= (y + height))
                )
            )
        );
    }
}
