import processing.core.PImage;
import java.util.List;



public abstract class AbstractAnimated extends AbstractActive {
    private int animationPeriod;
    public PathingStrategy strat;
    protected AbstractAnimated(String id, Point position,
                        List<PImage> images, int actionPeriod,
                        int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
        strat = new AStarPathingStrategy();
    }

    public void nextImage() { setImageIndex((getImageIndex() + 1) % getImages().size()); }


    public int getAnimationPeriod()
    {return animationPeriod;}

    @Override
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                Animation.createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public Point nextPosition(WorldModel world,
                              Point destPos)
    {
        List<Point> path = strat.computePath(this.getPosition(), destPos, pt -> world.withinBounds(pt) && !world.isOccupied(pt), (pt1, pt2) -> pt1.adjacent(pt2), PathingStrategy.CARDINAL_NEIGHBORS);
        if (path.size() == 0)    //if the size of your path is 0, return current position as the position you need to go to cuz u can't move anywhere
        {
            return getPosition();
        }



        /*
        Point start --> getPosition()
        , Point end --> destPos

      Predicate<Point> canPassThrough, --> withinBounds and is nOT occupied
      BiPredicate<Point,Point> withinReach,  --> if it's adjacent to the other point (?) pt1.adjacent(p2)
      Function<Point, Stream<Point>> potentialNeighbors  --> pathingstrategy.cardinal_neighbors
         */

        /*
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x,
                    this.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.getPosition();
            }
        }
*/
        else
            {
            return path.get(0);
            }
    }

}