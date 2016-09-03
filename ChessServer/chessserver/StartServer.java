package chessserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Игорь
 */
public class StartServer extends Observable implements Runnable {
    
    private ArrayList<PrintWriter> socketList = new ArrayList<>();
    
    private int count = 0;
    
    private String port;
    
    public StartServer(Observer o, String port) {
        
        this.addObserver(o);
        
        this.port = port;
        
    }
    
    @Override
    public void run() {
        
        try {
            
            ServerSocket server = new ServerSocket(Integer.valueOf(port));
            
            while(true) {
                
                Socket inputSocket = server.accept();
                PrintWriter a1 = new PrintWriter(inputSocket.getOutputStream());
                socketList.add(a1);
                
                Thread t = new Thread(new SocketHandler(inputSocket));
                t.start();
                stateChanged(++count);
  
            }
        } catch (IOException ex) {
            Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
     
    }
    
    class SocketHandler implements Runnable {
        
        Socket socket1;
        BufferedReader socketReader;
        InputStreamReader inputStream;

        SocketHandler(Socket socket){
            
            socket1 = socket;
            
            try {
                inputStream = new InputStreamReader(socket1.getInputStream());
                socketReader = new BufferedReader(inputStream);
            } catch (IOException ex) {
                Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            } 

        }    

        @Override
        public void run() {
            
            String str;
            
            try { 
                while((str = socketReader.readLine()) != null){
                    informPlayers(str);
                }
            } catch (IOException ex) {        
                Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            }
            
        }
            
    }
    
    private void informPlayers(String f) {
            
        Iterator i = socketList.iterator();
        
        while(i.hasNext()) {
            
            PrintWriter r = (PrintWriter) i.next();
            r.println(f);
            r.flush();
            
        }
            
    }
    
    private void stateChanged(int countSocket) {
        setChanged();
        notifyObservers(countSocket);
    }
    
}

