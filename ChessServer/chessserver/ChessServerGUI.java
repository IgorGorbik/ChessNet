package chessserver;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Игорь
 */
public class ChessServerGUI implements Observer {
    
    private String port;
    
    private JLabel labelPort;
    private JLabel labelCount;
    
    public ChessServerGUI(String port) {
        
        this.port = port;
    
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout(5,5));
        pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel l = new JLabel("Сервер запущен");
        
        labelPort = new JLabel("Порт: " + port);
        
        labelCount = new JLabel("Количество подключений: 0");
        
        pan.add(BorderLayout.NORTH, l);
        pan.add(BorderLayout.CENTER, labelPort);
        pan.add(BorderLayout.SOUTH, labelCount);
        
        f.add(pan);
        
        new Thread(new StartServer(this, port)).start();
        
        f.pack();
        
        f.setVisible(true);

    }

    @Override
    public void update(Observable o, Object arg) {
        labelCount.setText("Количество подключений: " + arg);
    }

}
