import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Vein extends AbstractActive{

    public Vein (String id, Point position,
                  List<PImage> images,
                  int actionPeriod)
    {
        super (id, position, images, actionPeriod);

    }

    public void executeActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.getPosition());

        if (openPt.isPresent())
        {
            Ore ore = CreateStuff.createOre(ParseStuff.ORE_ID_PREFIX + this.getId(),
                    openPt.get(), ParseStuff.ORE_CORRUPT_MIN +
                            ParseStuff.rand.nextInt(ParseStuff.ORE_CORRUPT_MAX - ParseStuff.ORE_CORRUPT_MIN),
                    imageStore.getImageList(ParseStuff.ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }




}