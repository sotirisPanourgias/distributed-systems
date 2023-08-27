import java.io.*;
import java.net.*;
import java.util.*;
public class Client {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost"; // το όνομα του server που θέλουμε να συνδεθούμε
        int portNumber = 5555; // ο αριθμός θύρας που ακούει ο server
        String fileName; // το όνομα του αρχείου που θα δώσει ο χρήστης
        int numWorkers; // ο αριθμός των workers που θα δώσει ο χρήστης


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file name: ");
        fileName = scanner.nextLine();
        System.out.print("Enter number of workers: ");
        numWorkers = scanner.nextInt();
        System.out.println(fileName);
        System.out.println(numWorkers);
        scanner.close();


        try{
            Socket clientSocket = new Socket(hostName, portNumber); // συνδέονται με τον server
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Στέλνουμε το όνομα του αρχείου και τον αριθμό των workers στον server
            out.println(fileName);
            out.flush();
            out.println(numWorkers);
            out.flush();

            
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error connecting to server");
           return;
        }
    }
}

    

