package io.weblibrary.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.weblibrary.models.LocationLib;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Service
@Data
public class CoronaUpdateDataService {
//	private static String corona_virus_data_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/01-01-2022.csv";
	private static String corona_virus_data_url = "https://raw.githubusercontent.com/chrislopez24/corona-parser/master/cases.csv";
	private List<LocationLib> allRequests = new ArrayList<>();
	
	

	
	//fetching the data
	@PostConstruct
	@Scheduled(cron = "2 * * * * *")
	public void returnVirusData() throws IOException, InterruptedException {
		List<LocationLib> newRequests = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
		          .uri(URI.create(corona_virus_data_url))
		          .build();
		
		//for java 8 (1.8) configuration
//		 URL urlFetch = new URL(corona_virus_data_url);
//
//	        URLConnection connection = urlFetch.openConnection();
//	        InputStreamReader inputStream = new InputStreamReader((InputStream) urlFetch.getContent());
//	        BufferedReader br = new BufferedReader(inputStream);
//	        
//	        String str;
//	        while ((str = br.readLine()) != null) {
//	            System.out.println(str + "\n");
////	        	 System.out.println("Test 1");
//	        }
//	        br.close();
	      
	        
	        HttpResponse<String> httpResponse =  client.send(request, HttpResponse.BodyHandlers.ofString());
	        StringReader csvReader = new StringReader(((HttpResponse<String>) httpResponse).body());
//	        withFirstRecordAsHeader()
	        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
	         
	        for (CSVRecord record : records) {
	        	LocationLib locationLib = new LocationLib();

//	            String country = record.get("Country_Region");
//	            String state = record.get("Province_State");
//	            System.out.println(country);
	        	
	            
	            locationLib.setCountry(record.get("Country/Other"));
	            locationLib.setTotalCases(record.get("Total Cases"));
	            locationLib.setTotalActive(record.get("Active Cases"));

	            
//	            locationLib.setLatestTotal(Integer.parseInt(record.get(record.size()-7))); 
	            
	            System.out.println(locationLib);
	            newRequests.add(locationLib);
	            
	        }
	        this.allRequests = newRequests;
	}

}
