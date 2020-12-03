

package erd.parser;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;


public class CreateTable extends JPanel{
private boolean DEBUG = false;
    Object[][] out;
    
    public CreateTable(Object[][] out) {
        super(new GridLayout(1,0));
        this.out=out;
        MyTableModel my=new MyTableModel();
        JTable table = new JTable(my);
        
        TableColumn column=new TableColumn();
        column=table.getColumnModel().getColumn(1);
        JComboBox combo=new JComboBox();
        
        combo.addItem("VARCHAR");
        combo.addItem("CHAR");
        combo.addItem("INT");
        combo.addItem("DATE");
        
        column.setCellEditor(new DefaultCellEditor(combo));
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }
    class MyTableModel extends AbstractTableModel{
        String[] columnNames = {"NAME", "TYPE", "LENGTH", "NO NULL?", "PRIMARY KEY?", "DESCRIPTION"};
         public int getColumnCount() {
            return this.columnNames.length;
        }

        public int getRowCount() {
            return out.length;
        }

        public String getColumnName(int col) {
            return this.columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return out[row][col];
        }
         
        @Override
    public Class<?> getColumnClass(int c){
        Class clazz=String.class;
        switch(c){
            case 0:
                clazz=String.class;
                break;
            case 1:
                clazz=String.class;
                break;
            case 2:
                clazz=Integer.class;
                break;
            case 3:
                clazz=Boolean.class;
                break;
            case 4:
                clazz=Boolean.class;
                break;
            case 5:
                clazz=String.class;
                break;
            default:
                break;
        }
        return clazz;
    }
        
        
        public boolean isCellEditable(int row, int col) {
            
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }
        
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            out[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }
        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + out[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
    private static void createAndShowGUI(Object[][] salida,String name) {
        
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        CreateTable newContentPane = new CreateTable(salida);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

     
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void main(Object[][] out,String name) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(out,name);
            }
        });
    }
}