/**
 * Created by Chris on 12/17/2015.
 * This class is used to connect to the main class and operate it. As of now, the date this was created,
 * all it is used for it to choose which card wins the round.
 *
 * UPDATE: 2/4/16
 * THIS CLASS IS NOW OBSOLETE
 * Due to the fact that we are getting rid of the sockets and using a text file to control the game instead,
 * this class will not be used. Also such a damn shame.
 */

import java.io.*;
import java.net.*;


public class Client {

    public static void main(String[] args) throws IOException{
        if(args.length != 2){
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String output;
        String input;

        System.out.println("Attempting to connect to host to host...");
        try(

                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ){
            System.out.println("Connected to " + hostName);
            while(true){
                if((input = in.readLine()) != null && input.equals("enter text")){
                    if((output = stdIn.readLine()) != null)
                        out.println(output);
                }
                else if(input != null)
                    System.out.println(input);


            }
        } catch (UnknownHostException e){
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e){
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}