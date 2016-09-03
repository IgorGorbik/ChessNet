package chessboard;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Игорь
 */
public class TableModel extends DefaultTableModel {
    
    private int [] a0 = {-1,9,11,10,8,7,10,11,9,-1};
    private int [] a1 = {-1,12,12,12,12,12,12,12,12,-1};
    
    private int [] a2 = {-1,0,0,0,0,0,0,0,0,-1};
    private int [] a3 = {-1,0,0,0,0,0,0,0,0,-1};
    private int [] a4 = {-1,0,0,0,0,0,0,0,0,-1};
    private int [] a5 = {-1,0,0,0,0,0,0,0,0,-1};
    
    private int [] a6 = {-1,6,6,6,6,6,6,6,6,-1};
    private int [] a7 = {-1,3,5,4,2,1,4,5,3,-1};
    
    private int [][] a = { a0, a1, a2, a3, a4, a5, a6, a7 };

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public int getRowCount() {
        return 8;
    }
    
    @Override
    public String getColumnName(int column) {
        if(column == 0 || column == 9) {
            return "";
        } else {
            char c = (char) (96 + column);
            return "" + c;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        if(column == 0 || column == 9) {
            return row + 1;
        } else  {
            return a[row][column];
        } 
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column) {
        a[row][column] = (int) aValue;
        fireTableDataChanged();
    }

}
