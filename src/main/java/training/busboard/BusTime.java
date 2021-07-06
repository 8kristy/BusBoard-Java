package training.busboard;

public class BusTime {

    private String busId;
    private String time;
    private String direction;

    public BusTime(String busId, String time, String direction){
        this.busId = busId;
        this.time = time;
        this.direction = direction;
    }

    public String getBusId() {
        return busId;
    }

    public String getTime() {
        return time;
    }

    public String getDirection(){
        return direction;
    }
    
}
