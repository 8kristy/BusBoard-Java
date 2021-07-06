package training.busboard.web;

import java.util.ArrayList;

import training.busboard.BusStop;

public class BusInfo {
    private final String postcode;
    private ArrayList<BusStop> stops;

    public BusInfo(String postcode) {
        this.postcode = postcode;
        stops = new ArrayList<>();
    }

    public void addBusStop(BusStop stop) {
        this.stops.add(stop);
    }
    public ArrayList<BusStop> getStops() {
        return stops;
    }

    public String getPostcode() {
        return postcode;
    }

}
