//import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

public class Master  {
    private String creator;
  
    public String getCreator() {
        return creator;
    }

    private String results;
    public ArrayList<String> getGpxLines() {
        return gpxLines;
    }






    private ArrayList<String> gpxLines = new ArrayList<String>();
    public ArrayList<Integer> getStarts() {
        return starts;
    }






    private ArrayList<Integer> starts = new ArrayList<Integer>();
    public ArrayList<Integer> getEnds() {
        return ends;
    }






    private ArrayList<Integer> ends = new ArrayList<Integer>();
    
    
    public String getResults() {
        return results;
    }






    private String gpxFile;
    private int numThreads;
    public Master(String gpString,int num){
        this.gpxFile=gpString;
        this.numThreads=num;
    }
    
    
    

    
    public  void run() {
        
       
        int wptCount = 0;
        

       // ArrayList<String> gpxLines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(gpxFile));
            String line;
            while ((line = reader.readLine()) != null) {
                gpxLines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file " + gpxFile);
            System.exit(1);
        }
        // παιρνω τον χρηστη
        
        try (BufferedReader b = new BufferedReader(new FileReader(gpxFile))) {
            String line1;
            while ((line1 = b.readLine()) != null) {
                if(line1.contains("creator=")){
                    int start1index= line1.indexOf("creator=") + 9;
                    int end1index=line1.indexOf("\"",start1index);
                    creator = line1.substring(start1index, end1index);
                    break;
                }
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader b = new BufferedReader(new FileReader(gpxFile))) {
            String line1;
            while ((line1 = b.readLine()) != null) {
                if(line1.contains("<wpt")){
                    wptCount++;
                }
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
         
        int chunk_size = (wptCount*4+3) / numThreads;
		int num_extra_waypoints = (wptCount*4+3) % numThreads;
		int start =0;
		
		for (int i = 0; i < numThreads; i++) {
			int end = start + chunk_size;
			if (i < num_extra_waypoints) {
		     	end=end+1;
				chunk_size=chunk_size-1;
			}
			if(end >= gpxLines.size()){
				end = gpxLines.size();
                starts.add(start);
                ends.add(end);
				break;
				
			}
			if(gpxLines.get(end).contains("<ele>")){
		     	end=end+1;
				
			}
			if(gpxLines.get(end).contains("<time>")){
		     	end=end+1;
			
			}
			
			
			starts.add(start);
            ends.add(end);
           
            start = end+1;
		
		}
        
        
        
        /* 
        double distance = 0.0;
        double sele = 0.0;
        double stime = 0.0;
       
        for (WorkerThread worker : workers) {
            distance += worker.getSdist();
            sele += worker.getSele();
            stime += worker.getStime();
         
        }
        double avgspead = distance/stime;
        
        userStats userStats = new userStats();
        // προσθηκη κλειδια/ τιμες στο hashmap
        UserMetrics user1stats = new UserMetrics(distance,sele,stime,avgspead);
        userStats.addUserMetrics(creator,user1stats);
        results = "The user's statistics are"+ userStats.getUserMetrics(creator).toString();
        System.out.println(results);*/
       
    
    }
    
            
    

   
    

   
}


