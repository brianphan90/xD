import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Quake extends AbstractAnimated{


    public Quake (String id, Point position,
                  List<PImage> images,
                  int actionPeriod, int animationPeriod)
    {
       super (id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world,
                                     ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }


    @Override
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {

            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
            scheduler.scheduleEvent(this,
                    Animation.createAnimationAction(this, ParseStuff.QUAKE_ANIMATION_REPEAT_COUNT),
                    this.getAnimationPeriod());


    }


}