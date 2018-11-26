/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.jobui;

import com.optimal.dbmodel.Job;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author shady
 */
public class JobTable extends JTable {

    private final String[] columnNames = {"ID", "Jop Title", "Description"};
    private TableRowSorter<TableModel> sorter;
    ArrayList<Job> jobs;

    public void Table() {

    }

    public void setTableFromJop(ArrayList<Job> jobs) {
        this.jobs = jobs;

        setModel(new javax.swing.table.DefaultTableModel(
                new Object[jobs.size()][columnNames.length],
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

                    System.out.println("Selected Row id =  " + getSelectedJobId());

                    //Done : to show data of selected row
                    JobFrame.showSelectedRaw(getSelectedJobId());

                    //problem : to delet data of selected row
                    JobFrame.deletSelectedRow(getSelectedJobId());

                    //problem : to update data of selected row
                    JobFrame.updateSelectedRow(getSelectedJobId());

                    
                    if (table.getSelectedColumn() == column) {
                        c.setBackground(Color.YELLOW);

                    }
                } else {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }

                return c;
            }
        });
        //registerKeyboardAction(new CopyAction(), KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), JComponent.WHEN_FOCUSED);

        int i = 0;
        for (Job job : jobs) {
            int j = 0;

            setValueAt(job.getId(), i, j++);
            setValueAt(job.getJobTitle(), i, j++);
            setValueAt(job.getDescription(), i, j++);

            i++;
        }

    }

    Job getSelectedJob() {

        return jobs.get(getSelectedRow());
    }

    int getSelectedJobId() {

        return jobs.get(getSelectedRow()).getId();

    }

}
