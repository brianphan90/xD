public class Activity implements Action {


    private AbstractActive entity;
    private WorldModel world;
    private ImageStore imageStore;


    public Activity(AbstractActive entity, WorldModel world,
                  ImageStore imageStore)
    {

        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;

    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.executeActivity(this.world,
                        this.imageStore, scheduler);

    }

    public static Activity createActivityAction(AbstractActive entity, WorldModel world,
                                       ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }

}