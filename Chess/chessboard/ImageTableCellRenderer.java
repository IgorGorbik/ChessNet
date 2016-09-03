package chessboard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Игорь
 */
    public class ImageTableCellRenderer extends DefaultTableCellRenderer { 
        
        Font font = new Font("Dialog", Font.PLAIN, 25);
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) { 

            JLabel label = (JLabel)super.getTableCellRendererComponent(table,  
                                   value, true, hasFocus, row, column); 
            
            label.setHorizontalAlignment(SwingConstants.CENTER);
            
            if(column == 0 || column == 9) {
                label.setFont(label.getFont());
            } else {
                label.setFont(font);
            }
            
            int r = Integer.parseInt(value.toString());
            
            if(column == 0 || column == 9) {
            
            } else {
                if(r > 0 & r < 7) {
                    label.setForeground(Color.white);
                } else if(r > 6 & r <= 12) {
                    label.setForeground(Color.darkGray);
                }   
            }
            
            if(r == 0) {
                label.setText("");
            } else {
                label.setText("" + (char)(9811 + r));
            }
            
            if(column == 0 || column == 9){
                label.setText("" + (row+1));
                label.setBackground(Color.lightGray);
                return label; 
            } else {
                if((column%2)  == 0 ) {
                        if(row%2 == 0) {
                        label.setBackground(new Color(184,134,11));
                        }
                        else {
                        label.setBackground(new Color(123, 104, 238));
                        }
                } else {
                     if(row%2 != 0) {
                        label.setBackground(new Color(184,134,11));
                     }
                    else {
                        label.setBackground(new Color(123, 104, 238));
                    }
                }               
            }
            return label; 
        } 
    } 

