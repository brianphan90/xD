import processing.core.PImage;
import java.util.List;

public abstract class AbstractActive extends AbstractEntity{

    private int actionPeriod;

    protected AbstractActive (String id, Point position,
            List<PImage> images, int actionPeriod)
    {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());

    }

        //for miner full, miner not full, ore, ore blob, vein, quake
}