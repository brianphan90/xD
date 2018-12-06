import java.util.List;
import processing.core.PImage;
import java.util.Optional;

public class OreBlob extends AbstractAnimated implements MovementEntity {


    public OreBlob (String id, Point position,
                  List<PImage> images,
                  int actionPeriod, int animationPeriod)
    {
        super (id, position, images, actionPeriod, animationPeriod);
    }


    public boolean moveTo(WorldModel world,
                                 AbstractEntity target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
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
        Optional<AbstractEntity> blobTarget = world.findNearest(
                this.getPosition(), Vein.class);
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (this.moveTo(world, blobTarget.get(), scheduler))
            {
                Quake quake = CreateStuff.createQuake(tgtPos,
                        imageStore.getImageList(ParseStuff.QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    @Override

    public Point nextPosition(WorldModel world,
                              Point destPos) {
        List<Point> path = strat.computePath(this.getPosition(), destPos, pt -> world.withinBounds(pt) && !world.isOccupied(pt), (pt1, pt2) -> pt1.adjacent(pt2), PathingStrategy.CARDINAL_NEIGHBORS);
        if (path.size() == 0)    //if the size of your path is 0, return current position as the position you need to go to cuz u can't move anywhere
        {
            return getPosition();
        }
        else
        {
            return path.get(0);
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