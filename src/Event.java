final class Event
{
   public Action action;
   public long time;
   public AbstractEntity entity;

   public Event(Action action, long time, AbstractEntity entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }




}

