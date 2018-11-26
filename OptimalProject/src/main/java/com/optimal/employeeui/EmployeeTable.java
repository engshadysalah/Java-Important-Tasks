/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.employeeui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.optimal.dbmodel.Employee;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ENG-GEMY
 */
public class EmployeeTable extends JTable {

    private final String[] columnNames = {"ID", "Full Name", "Phone", "Address", "Birth Date" , "Job Titel " , "Department Titel"  };
    private TableRowSorter<TableModel> sorter;
    ArrayList<Employee> employees;
    public void Table() {    
       
    }

    
      public void setTableFromEmployee(ArrayList<Employee> employees) {
          this.employees=employees;
     
        setModel(new javax.swing.table.DefaultTableModel(
                new Object[employees.size()][columnNames.length],
                columnNames
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        sorter = new TableRowSorter<>(getModel());
        setRowSorter(sorter);
        
//        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
//        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        //sortKeys.add(new RowSorter.SortKey(7, SortOrder.ASCENDING)); 
//        sorter.setSortKeys(sortKeys);
        
        getColumnModel().getColumn(0).setPreferredWidth(50);
         setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK);

                 
                     
                     
                     if (table.getSelectedRow() == row) {
                    
                    c.setBackground(Color.ORANGE);
                    
                    System.out.println("Selected Row id =  " + getSelectedEmployeeId());

                    //Done : to show data of selected row
                    EmployeeFrame.showSelectedRaw(getSelectedEmployeeId());
                    
                    if (table.getSelectedColumn() == column) {
                        c.setBackground(Color.YELLOW);
//                        System.out.println("Column :> value = "+value+"  Selected = "+isSelected+"  Focus = "+hasFocus+"  row = "+row+"   cloumn = "+column);
                     //   System.out.println("name "+table.getValueAt(row, 0));
                       //  System.out.println("name "+table.getValueAt(row, column));


             
                        
                    }
                } else {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }

                return c;
            }
        });
          //registerKeyboardAction(new CopyAction(), KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), JComponent.WHEN_FOCUSED);
          
          
          
        
        
        int i=0;
        for(Employee employee:employees)
        {
            int j=0;
            
             //   private final String[] columnNames = {"", "ID", "Full Name", "User Name", "Created By", "Status",};
          
          setValueAt(employee.getId(),i,j++);
          setValueAt(employee.getFirstName(),i,j++);
          setValueAt(employee.getPhone(),i,j++);
          setValueAt(employee.getAddress(),i,j++);
          setValueAt(employee.getBirthDate(),i,j++);
          setValueAt(employee.getJob().getJobTitle(),i,j++);
          setValueAt(employee.getDepartments().getTitle(),i,j++);
          
          System.out.println("==>" + employee.getJob().getJobTitle());
          
        i++;
        }
        
        
    }
      
      Employee getSelectedEmploy()
      {
      return employees.get(getSelectedRow());
      }

      int getSelectedEmployeeId() {

        return employees.get(getSelectedRow()).getId();

    }
      
}
