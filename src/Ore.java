import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Ore extends AbstractActive{


    public Ore (String id, Point position,
                  List<PImage> images,
                  int actionPeriod)
    {
        super (id, position, images, actionPeriod);

    }

    public void executeActivity(WorldModel world,
                                   ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = CreateStuff.createOreBlob(this.getId() + ParseStuff.BLOB_ID_SUFFIX,
                pos, this.getActionPeriod() / ParseStuff.BLOB_PERIOD_SCALE,
                ParseStuff.BLOB_ANIMATION_MIN +
                        ParseStuff.rand.nextInt(ParseStuff.BLOB_ANIMATION_MAX - ParseStuff.BLOB_ANIMATION_MIN),
                imageStore.getImageList(ParseStuff.BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }




}