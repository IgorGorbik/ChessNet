package chessserver;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Игорь
 */
public class ChessServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String port = JOptionPane.showInputDialog("Задайте номер порта");
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChessServerGUI(port);
            }
        });
        
    }

}
