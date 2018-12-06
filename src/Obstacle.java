import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Obstacle extends AbstractEntity{


    public Obstacle(String id, Point position,
                  List<PImage> images)
    {
        super(id, position, images);
    }

}
