package chessgui;

import chessboard.ImageTableCellRenderer;
import chessboard.TableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Игорь
 */
public class GameGUI implements Runnable {

    JFrame f;
    
    int a = 0;
    
    Socket socket;
    BufferedReader br;
    PrintWriter writer;
    
    JTable t1;
    
    String msg;
    
    String ip;
    String port;
    
    public GameGUI(String ip, String port) {

        if(!"".equals(ip)) {
            this.ip = ip;
        } else {
            this.ip = "127.0.0.1";
        }
        this.port = port;

        f =new JFrame("Chess");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        
        t1 = new JTable(new TableModel());
        t1.setCellSelectionEnabled(false);
        t1.setRowHeight(35);
        t1.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int row = t1.rowAtPoint(point);
                int column = t1.columnAtPoint(point);
                a = (int) t1.getModel().getValueAt(row, column);
                //t1.getModel().setValueAt(0, row, column);
                msg = "" + row + column + "0";
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = e.getPoint();
                int row = t1.rowAtPoint(point);
                int column = t1.columnAtPoint(point);
                //t1.getModel().setValueAt(a, row, column);
                msg = msg + row + column + a;
                writer.println(msg);
                writer.flush();
                msg = null;
            }
            
        });

        TableColumnModel columnModel = t1.getColumnModel();
        t1.getTableHeader().setBackground(Color.lightGray);
        t1.getTableHeader().setReorderingAllowed(false);
        t1.setDefaultRenderer(Object.class, new ImageTableCellRenderer());
        
        Enumeration e = columnModel.getColumns();
        while ( e.hasMoreElements() ) {
            TableColumn column = (TableColumn)e.nextElement();
            if(column.getHeaderValue() == "" ) {
                column.setMinWidth(25);
                column.setMaxWidth(25);
            } else {
                column.setMinWidth(35);
                column.setMaxWidth(35);
            }
        }
        
        JTable t2 = new JTable(new TableModel());
        t2.setCellSelectionEnabled(false);
        t2.getTableHeader().setReorderingAllowed(false);
        t2.setRowHeight(30);
        
        TableColumnModel columnModel2 = t2.getColumnModel();
        t2.getTableHeader().setBackground(Color.lightGray);

        Enumeration e2 = columnModel2.getColumns();
        while ( e2.hasMoreElements() ) {
            TableColumn column = (TableColumn)e2.nextElement();
            if(column.getHeaderValue() == "" ) {
                column.setMinWidth(25);
                column.setMaxWidth(25);
            } else {
                column.setMinWidth(35);
                column.setMaxWidth(35);
            }
        }

        createSocket();
        
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.add(BorderLayout.CENTER, t1);
        pan.add(BorderLayout.NORTH, t1.getTableHeader());
        pan.add(BorderLayout.SOUTH, t2.getTableHeader());
        
        f.add(pan);

        f.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/2 - f.getWidth()/2, dim.height/2 - f.getHeight()/2);
                  
        Thread t = new Thread(this);
        t.start();

        f.setVisible(true);
    
    }

    @Override
    public void run() {
        String str;
            try { 
                while((str = br.readLine()) != null){

                    int row = Integer.valueOf("" + str.charAt(0));

                    int column = Integer.valueOf("" + str.charAt(1));

                    t1.getModel().setValueAt(0, row, column);
                    
                    int row1 = Integer.valueOf("" + str.charAt(3));

                    int column1 = Integer.valueOf("" + str.charAt(4));

                    int v = Integer.valueOf(str.substring(5));

                    t1.getModel().setValueAt(v, row1, column1);
                }
            } catch (IOException ex) {
                Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            }
    }

    private void createSocket() {

        try {
            socket = new Socket(ip, Integer.valueOf(port));
        } catch (IOException ex) {
            Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(f, "Неверно заданы параметры");
            System.exit(0);
        }
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

}
