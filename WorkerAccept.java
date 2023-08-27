import java.io.*;
import java.net.*;
import java.util.*;

public class WorkerAccept extends Thread {
    private Socket Socket;
    private Result result;

    public WorkerAccept(Socket Socket){
        this.Socket = Socket;
    }
    public void run(){
        try{
            //PrintWriter out = new PrintWriter(Socket.getOutputStream(),true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
            String s2=in1.readLine();
            int s1= Integer.parseInt(s2)-1;
            String line;
            ArrayList<String> list2 = new ArrayList<String>();
            for(int i=0;i<=s1;i++){
                line=in1.readLine();
                list2.add(line);
            }
            int start=Integer.parseInt(in1.readLine());
            int end=Integer.parseInt(in1.readLine());
            result = new Result(list2,start,end);
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public Result getResult(){
        return result;
    }
    public static class Result{
        private int start;
        private int end;
        private ArrayList<String> list2;
        public Result (ArrayList<String> list2,int start,int end){
            this.list2=list2;
            this.start = start;
            this.end = end;
        }
        public ArrayList<String> getList(){
            return list2;
        }
        public int getStart(){
            return start;
        }
        public int getEnd(){
            return end;
        }
    }         
}
