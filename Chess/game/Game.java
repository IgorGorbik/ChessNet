package game;

import chessgui.GameGUI;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Игорь
 */
public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String ip = JOptionPane.showInputDialog("Введите адресс сервера");
        String port = JOptionPane.showInputDialog("Введите порт");
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameGUI(ip, port);
            }
        });

    }
    
}
