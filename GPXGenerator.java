import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.lang.Math;
public class GPXGenerator {
    private String name;

    public GPXGenerator(String name) {
        this.name = name;
    }

    public File generateGPXFile() {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime currentTime = LocalDateTime.now();
        File gpxFile = new File(name + ".gpx");
        try {
            FileWriter writer = new FileWriter(gpxFile);
            writer.write("<?xml version=\"1.0\"?>\n");
            writer.write("<gpx version=\"1.1\" creator=\"" + name + "\">\n");
            double startLat = random.nextDouble() * 90;
            double startLon = random.nextDouble() * 180;
            for (int i = 0; i < random.nextInt(40)+6; i++) {
                double lat = startLat +( random.nextDouble()/1000 );
                double lon = startLon + (random.nextDouble()/1000) ;
                double elevation = random.nextDouble() * 10;
                String timeString = currentTime.format(formatter);


                writer.write("<wpt lat=\"" + lat + "\" lon=\"" + lon + "\">\n");
                writer.write("    <ele>" + elevation + "</ele>\n");
                writer.write("    <time>" + timeString + "</time>\n");
                writer.write("</wpt>\n");
                currentTime = currentTime.plusSeconds(random.nextInt(60));
                //currentTime = currentTime.plusMinutes(1);
            }
            writer.write("</gpx>");
            writer.close();
            System.out.println("GPX file generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the GPX file.");
            e.printStackTrace();
        }
        return gpxFile;
    }
}
