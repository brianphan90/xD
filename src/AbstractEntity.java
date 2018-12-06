import processing.core.PImage;
import java.util.List;

abstract class AbstractEntity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    protected AbstractEntity(String id, Point position,
                        List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }


    public String getId () {
        return id;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point point) {
        this.position = point; }
    public PImage getCurrentImage(){

        return images.get(imageIndex);
    }


    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int e) {
        imageIndex = e;
    }


}
