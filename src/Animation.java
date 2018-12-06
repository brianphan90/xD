public class Animation implements Action{


    private AbstractAnimated entity;

    private int repeatCount;

    public Animation(AbstractAnimated entity, int repeatCount)
    {

        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.entity,
                    createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    entity.getAnimationPeriod());
        }
    }

    public static Animation createAnimationAction(AbstractAnimated entity, int repeatCount)
    {
        return new Animation(entity, repeatCount);
    }



}