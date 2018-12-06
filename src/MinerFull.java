
import java.util.List;
import processing.core.PImage;
import java.util.Optional;


public class MinerFull extends AbstractAnimated implements MovementEntity{

    public int resourceLimit;

    public MinerFull(String id, Point position,
                  List<PImage> images, int resourceLimit,
                  int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }



    public void transform(WorldModel world,
                              EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = CreateStuff.createMinerNotFull(this.getId(), this.resourceLimit,
                this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveTo(WorldModel world,
                              AbstractEntity target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<AbstractEntity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void executeActivity(WorldModel world,
                                         ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<AbstractEntity> fullTarget = world.findNearest(this.getPosition(),
                Blacksmith.class);

        if (fullTarget.isPresent() &&
                this.moveTo(world, fullTarget.get(), scheduler))
        {
            this.transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent( this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }




    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
            scheduler.scheduleEvent(this,
                    Animation.createAnimationAction(this, 0), this.getAnimationPeriod());

    }


}