import processing.core.PImage;

import java.util.List;

final class Fire extends AbstractAnimated {



    public static final String FIRE_ID = "fire";
    public static final int FIRE_ACTION_PERIOD = 1100;
    public static final int FIRE_ANIMATION_PERIOD = 100;
    public static final int FIRE_ANIMATION_REPEAT_COUNT = 10;
    public static final String FIRE_KEY = "fire";



    public Fire(Point position, List<PImage> images) {
        super(FIRE_ID, position, images, FIRE_ACTION_PERIOD, FIRE_ANIMATION_PERIOD);


    }



    public  void scheduleActions(EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent( this,
                new Activity(this, world, imageStore),
                getActionPeriod());
        scheduler.scheduleEvent( this,
                new Animation(this, FIRE_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
    }



    public  void executeActivity( WorldModel world,
                                  ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents( this);
        world.removeEntity( this);
    }
}
