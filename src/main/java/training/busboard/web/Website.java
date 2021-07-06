package training.busboard.web;

import training.busboard.*;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@EnableAutoConfiguration
public class Website {

    @RequestMapping("/")
    ModelAndView home() {
        return new ModelAndView("index");
    }
    
    @RequestMapping("/busInfo")
    ModelAndView busInfo(@RequestParam("postcode") String postcode) {
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        BusInfo info = null;
        String postcodeData = null;
        try{
            postcodeData = getApiResponse("http://api.postcodes.io/postcodes/"+ postcode, client);
        }
        catch (Exception e){
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("error", "Invalid postcode");
            return mv;
        }
            
        try{        
            Coordinate coordinate = JsonParser.extractCoordinatesFromJson(postcodeData);
            String responseForBs = getApiResponse("https://api.tfl.gov.uk/StopPoint//?lat=" + coordinate.getlatitude() +
                                                    "&lon=" + coordinate.getlongitude() +
                                                    "&stopTypes=NaptanPublicBusCoachTram", client);
            ArrayList<BusStop> stops = JsonParser.extractBusStopsFromJson(responseForBs);
            if (stops.size() == 0){
                ModelAndView mv = new ModelAndView("index");
                mv.addObject("error", "No nearby bus stops found :("); 
                return mv;
            }
            info = new BusInfo(postcode);
            for (BusStop stop : stops){
                info.addBusStop(stop);
            }
        }
        catch (Exception e){
            System.out.println("An error has occured.");
            e.printStackTrace();
        }


        return new ModelAndView("info", "busInfo", info);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Website.class, args);
    }
    
    public static String getApiResponse(String url, Client client) throws Exception {
        return client.target(url)
        .request(MediaType.APPLICATION_JSON)
        .get(String.class);
    }

}