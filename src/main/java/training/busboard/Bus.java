package training.busboard;

public class Bus{
    private int timeToStation;
    private String id;
    private String direction;

    public double getTimeToStation() {
        return timeToStation;
    }

    public String getId() {
        return id;
    }

    public int getIntegerTime(){
        return (int)Math.round(timeToStation);
    }

    public String getDirection(){
        return direction;
    }

    public Bus(int timeToStation, String id, String direction) {
        this.timeToStation = timeToStation / 60;
        this.id = id;
        this.direction = direction;
    }

}