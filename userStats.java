import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


//import javax.swing.UIDefaults.ProxyLazyValue;
public class userStats implements Serializable {
    private static HashMap<String, UserMetrics> userMetricsMap;

    public userStats() {
        userMetricsMap = new HashMap<String, UserMetrics>();
    }

    public  void addUserMetrics(String username, UserMetrics metrics) {
        userMetricsMap.put(username, metrics);
    }

    public UserMetrics getUserMetrics(String username) {
        return userMetricsMap.get(username);
    }
    public boolean containsKey(String username){
        return userMetricsMap.containsKey(username);
    }
    public void updateUserMetrics(String username,UserMetrics metrics){
        userMetricsMap.replace(username, metrics);
    }

    public Set<Map.Entry<String, UserMetrics>> entrySet() {
        return userMetricsMap.entrySet();

    }
}

class UserMetrics {
    private double averageExerciseTime;
    private double averageDistance;
    private double averageElevation;
    private double avgspead;
    

    public UserMetrics( double averageDistance, double averageElevation,double averageExerciseTime,double avgspead) {
        
        this.averageExerciseTime = averageExerciseTime;
        this.averageDistance = averageDistance;
        this.averageElevation = averageElevation;
        this.avgspead = avgspead;
    }

    public double getAverageExerciseTime() {
        return averageExerciseTime;
    }

    public double getAverageDistance() {
        return averageDistance;
    }

    public double getAverageElevation() {
        return averageElevation;
    }
    public double getAvgSpead(){
        return avgspead;
    }
    public String toString() {
        return "Average Distance: " + averageDistance + " km\n" +
               "Average Elevation: " + averageElevation + " meters\n" +
               "Average Exercise Time: " + averageExerciseTime + " minutes\n" +
               "Average Speed: " + avgspead + " km/h";
    }
}


