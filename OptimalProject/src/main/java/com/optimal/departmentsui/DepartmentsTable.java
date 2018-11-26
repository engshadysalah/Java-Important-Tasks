/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.departmentsui;

import com.optimal.dbmodel.Departments;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultEditorKit.CopyAction;

/**
 *
 * @author shady
 */
public class DepartmentsTable extends JTable {

    private final String[] columnNames = {"ID", "Department Title", "Description"};
    private TableRowSorter<TableModel> sorter;
    ArrayList<Departments> departments;

    public void Table() {

    }

    public void setTableFromDepartmentses(ArrayList<Departments> department) {
        this.departments = department;

        setModel(new javax.swing.table.DefaultTableModel(
                new Object[department.size()][columnNames.length],
                columnNames
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        sorter = new TableRowSorter<>(getModel());
        setRowSorter(sorter);

        getColumnModel().getColumn(0).setPreferredWidth(50);
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK);

                if (table.getSelectedRow() == row) {

                    c.setBackground(Color.ORANGE);
//                  System.out.println("name "+table.getValueAt(row, 0));

                    //System.out.println("Selected Row id =  " + getSelectedDpartments().getId());

                    //Done : to show data of selected row
                    DepartmentsFrame.showSelectedRaw(getSelectedDpartments().getId());

                    //problem : to delet data of selected row

                    //problem : to update data of selected row

                    
                    if (table.getSelectedColumn() == column) {
                        c.setBackground(Color.YELLOW);

                    }
                } else {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }

                return c;
            }
        });
       // registerKeyboardAction(new CopyAction(), KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), JComponent.WHEN_FOCUSED);

        int i = 0;
        for (Departments departments : departments) {
            int j = 0;

            setValueAt(departments.getId(), i, j++);
            setValueAt(departments.getTitle(), i, j++);
            setValueAt(departments.getDescrption(), i, j++);

            i++;
        }

    }

    Departments getSelectedDpartments() {

        return departments.get(getSelectedRow());
    }

    int getSelectedDepartmentsId() {

        return departments.get(getSelectedRow()).getId();

    }
    
    

}

