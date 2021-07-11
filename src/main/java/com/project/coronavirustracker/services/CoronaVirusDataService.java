package com.project.coronavirustracker.services;

import ch.qos.logback.core.CoreConstants;
import com.project.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String url =  "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct  //execute this when application starts - When you construct instance of this service, execute this method
    @Scheduled(cron = "* * 1 * * *")  //to update every second (* * * * * *)  , (sec min hour * * *) , to update at 0th hour everyday( * * 1 * * *)
    public void fetchCoronaVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>(); // concurrency - lot of people  are accessing our service and we don't want them to error response while we are busy trying constructing this.
        // So we create a new instance of this and we are done constructing this we are going to populate all stats with newStats. If this takes fraction of seconds,
        // and in that fraction of seconds if somebody is requesting information, he still gets the current information while we are working on this.

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder().uri(URI.create(url)).build(); //created a request using builder pattern
        //HttpRequest request1 = HttpRequest.newBuilder(URI.create(url)).build(); //another way to create httprequest
       HttpResponse httpResponse= client.send(request , HttpResponse.BodyHandlers.ofString());
       //System.out.println(httpResponse.body());

        StringReader csvBodyReader= new StringReader((String) httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int previousCases = Integer.parseInt(record.get(record.size()-2));
            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDiffFromPrevDev(latestCases - previousCases);
            newStats.add(locationStats);

           // System.out.println(locationStats)
//            String state = record.get("Province/State");
//            System.out.println(state);
           // String customerNo = record.get("CustomerNo");
           // String name = record.get("Name");
        }

        this.allStats = newStats;
    }
}
