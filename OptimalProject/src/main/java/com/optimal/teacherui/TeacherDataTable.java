/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.teacherui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.optimal.dbmodel.Teacher;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ENG-GEMY
 */
public class TeacherDataTable extends JTable {

    private final String[] columnNames = {"Id", "Name", "Gender", "BirthDate", "Countery", "City", "Street", "Habits"};
    private TableRowSorter<TableModel> sorter;
    ArrayList<Teacher> teachers;
    TeacherFrame frm;

    public TeacherDataTable(TeacherFrame frm) {
        this.frm = frm;
    }

    public void setTableFromTeacher(ArrayList<Teacher> teachers) {
        this.teachers = teachers;

        setModel(new javax.swing.table.DefaultTableModel(
                new Object[teachers.size()][columnNames.length],
                columnNames
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        sorter = new TableRowSorter<>(getModel());
        setRowSorter(sorter);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    // your valueChanged overridden method 
                    System.out.println("dddddddddddddddddone");
                    frm.showTeacherSelectedRaw();

                }
            }
        });
        getColumnModel().getColumn(0).setPreferredWidth(50);
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK);

                if (isSelected) {

                    // JOptionPane.showMessageDialog(null, "");
                }

                if (table.getSelectedRow() == row) {

                    c.setBackground(Color.ORANGE);

                    //  frm.showTeacherSelectedRaw();
                    if (table.getSelectedColumn() == column) {
                        c.setBackground(Color.YELLOW);
                    }
                } else {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }

                return c;
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        int i = 0;
        for (Teacher teacher : teachers) {
            int j = 0;

            setValueAt(teacher.getId(), i, j++);
            setValueAt(teacher.getName(), i, j++);
            setValueAt(teacher.getGender(), i, j++);
            setValueAt(dateFormat.format(teacher.getBirthdate()), i, j++);
            setValueAt(teacher.getCountery(), i, j++);
            setValueAt(teacher.getCity(), i, j++);
            setValueAt(teacher.getStreet(), i, j++);
            setValueAt(teacher.getHabits(), i, j++);

            i++;
        }
    }

    Teacher getSelectedTeacher() {
       
        return teachers.get(getSelectedRow());
     //   return teachers.get((Integer) getValueAt(getSelectedRow(), 0));
    }

}
