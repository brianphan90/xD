
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Goodboi extends AbstractAnimated implements MovementEntity{

    public static final String Goodboi_KEY = "goodboi";
    public static final String Goodboi_ID_SUFFIX = " -- goodboi";
    public static final int Goodboi_PERIOD_SCALE = 4;
    public static final int Goodboi_ANIMATION_MIN = 50;
    public static final int Goodboi_ANIMATION_MAX = 150;

    public Goodboi(String id, Point position,
                  int actionPeriod, int animationPeriod, List<PImage> images){
        super(id, position, images, actionPeriod, animationPeriod);     //follow and delete oreblobs
    }
    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<AbstractEntity> goodboiTarget = world.findNearest(
                getPosition(), OreBlob.class);
        long nextPeriod = getActionPeriod();

        if (goodboiTarget.isPresent())
        {
            Point tgtPos = goodboiTarget.get().getPosition();

            if (moveTo( world, goodboiTarget.get(), scheduler))
            {
                Fire fire = new Fire(tgtPos,
                        imageStore.getImageList( Fire.FIRE_KEY));

                world.addEntity( fire);
                nextPeriod += getActionPeriod();
                fire.scheduleActions( scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent( this,
                new Activity(this, world, imageStore),
                nextPeriod);
    }
    public  boolean moveTo( WorldModel world,
                            AbstractEntity target, EventScheduler scheduler)
    {
        if ((getPosition().adjacent(target.getPosition())))
        {
            world.removeEntity( target);
            scheduler.unscheduleAllEvents( target);
            return true;
        }
        else
        {
            Point nextPos = nextPosition( world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                Optional<AbstractEntity> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents( occupant.get());
                }

                world.moveEntity( this, nextPos);
            }
            return false;
        }
    }




}