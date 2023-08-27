
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.*;
import java.net.*;



public class WorkerThread {

    private static final double R = 6372.8; // Ακτίνα της Γης σε χιλιόμετρα


    private int id;
    private int PORT;


    public WorkerThread( int id,int port  ) {


        this.id=id;

        this.PORT=port;

    }

    public void run3() throws IOException{





        try (// Δημιουργούμε το ServerSocket για να δεχόμαστε συνδέσεις από τον Server
             ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Worker is listening on port " +     PORT    );


            while(true){
                // Αναμένουμε για συνδέσεις από τον Server
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Worker connected to Server");




                Waccept worker = new Waccept(connectionSocket,null,0,0);
                worker.start();


            } // Περιμένουμε να λάβουμε τη λίστα με τα chunks από τον Server






        } catch (IOException|NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }














    }
    class Waccept extends Thread{
        private ArrayList<String> list2;
        private int start;
        private int end;
        private Socket socket;
        private double sdist  = 0;
        private double sele = 0;
        private double stime = 0;



        public Waccept( Socket socket,ArrayList<String> list2, int start, int end) {
            this.socket=socket;
            this.list2 = new ArrayList<String>();
            this.start = start;
            this.end = end;
        }



        public void run(){
            try{
                //PrintWriter out = new PrintWriter(Socket.getOutputStream(),true);
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s2=in1.readLine();
                int s1= Integer.parseInt(s2)-1;



                for(int i=0;i<=s1;i++){

                    list2.add(in1.readLine());
                }
                start=Integer.parseInt(in1.readLine());
                end=Integer.parseInt(in1.readLine());
                System.out.println(end);
                GpxFix(list2,start,end);
                System.out.println("sdist: "+sdist+" sele: "+sele+" stime: "+stime);
                out.println(sdist);
                out.println(sele);
                out.println(stime);


                socket.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
        public static double haversine(double lat1, double lon1, double lat2, double lon2) {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = Math.pow(Math.sin(dLat / 2), 2) +
                    Math.pow(Math.sin(dLon / 2), 2) *
                            Math.cos(lat1) *
                            Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            return R * c;
        }

        public void GpxFix(ArrayList<String> list ,int start,int end){
            String xmlString="";
            String xmlString2 = " ";
            double PrevLat=0.0;
            double prevLon= 0.0;
            double prevele=0.0;
            double lat=0.0;
            double lon =0.0;
            String eleStr = " ";


            for(int i = start;i<=end;i++){

                xmlString=list.get(i);

                if(xmlString.contains("<wpt")){



                    if(i>=6){
                        int latIndex = xmlString.indexOf("lat=") + 5;
                        int latEndIndex = xmlString.indexOf("\"", latIndex);
                        int lonIndex = xmlString.indexOf("lon=") + 5;
                        int lonEndIndex = xmlString.indexOf("\"", lonIndex);
                        lat = Double.parseDouble(xmlString.substring(latIndex, latEndIndex));
                        lon=Double.parseDouble(xmlString.substring(lonIndex,lonEndIndex));

                        xmlString2=list2.get(i-4);
                        latIndex = xmlString2.indexOf("lat=") + 5;
                        lonIndex = xmlString2.indexOf("lon=") + 5;
                        latEndIndex = xmlString2.indexOf("\"", latIndex);
                        lonEndIndex = xmlString2.indexOf("\"", lonIndex);
                        PrevLat = Double.parseDouble(xmlString2.substring(latIndex, latEndIndex));
                        prevLon=Double.parseDouble(xmlString2.substring(lonIndex,lonEndIndex));
                        sdist=sdist+haversine(PrevLat,prevLon,lat,lon);

                    }









                }else if(xmlString.contains("<ele>")){

                    if(i>=7){
                        eleStr = xmlString.substring(xmlString.indexOf("<ele>") + 5, xmlString.indexOf("</ele>"));
                        double ele = Double.parseDouble(eleStr);
                        xmlString2=list2.get(i-4);
                        String PeleStr =xmlString2.substring(xmlString2.indexOf("<ele>") + 5, xmlString2.indexOf("</ele>"));
                        prevele = Double.parseDouble(PeleStr);
                        if(ele>prevele){
                            sele=sele+(ele-prevele);
                        }



                    }







                }else if(xmlString.contains("<time>")){
                    if(i>=8){
                        String time = xmlString.substring(xmlString.indexOf("<time>") +6, xmlString.indexOf("</time>"));
                        xmlString2=list2.get(i-4);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        String preTime =xmlString2.substring(xmlString2.indexOf("<time>") + 6, xmlString2.indexOf("</time>"));
                        LocalDateTime time1 = LocalDateTime.parse(time, formatter);
                        LocalDateTime time2 = LocalDateTime.parse(preTime, formatter);
                        Duration duration = Duration.between(time1, time2);
                        double seconds = duration.getSeconds();
                        double minutes = seconds / 60;
                        double hours = minutes / 60;
                        double totalMinutes = Math.abs(minutes);
                        //System.out.println(minutes);
                        stime = stime+ totalMinutes;

                    }






                }
            }                }
    }}






