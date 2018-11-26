/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dicom.getalldicomtags;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author root
 */
public class JTablDicomTags extends JTable {

    private final String[] columnNames = {"index", "TagName", "TagAddress", "TagVR", "TagValue", "Tag", "NewValue", "ChecK"};
    private TableRowSorter<TableModel> sorter;
    ArrayList<DicomTags> dicomTagses;
    JCheckBox ch;
    public static Hashtable tag;

    public void Table() {

    }

    public void setTableFromDicomTags(ArrayList<DicomTags> dicomTagses) {
        this.dicomTagses = dicomTagses;

        setModel(new javax.swing.table.DefaultTableModel(
                new Object[dicomTagses.size()][columnNames.length],
                columnNames
        ) {

            // for edit
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                //return true; //then edit all
                return columnIndex == 6 || columnIndex == 7;
            }

            // for checkBox
            //https://stackoverflow.com/questions/7391877/how-to-add-checkboxes-to-jtable-swing
            @Override
            public Class<?> getColumnClass(int columnIndex) {

                Class columClass;

                if (columnIndex == 7) {
                    columClass = Boolean.class;
                } else {
                    columClass = String.class;
                }

                return columClass;
            }

        });
        sorter = new TableRowSorter<>(getModel());
        setRowSorter(sorter);

        ch = new JCheckBox();

        getColumnModel().getColumn(0).setPreferredWidth(50);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (getValueAt(row, 6) == null) {

                    //System.out.println("flase");
                   // setValueAt(false, row, 7);

                } else {

                   // setValueAt(true, row, 7);
                   // System.out.println("true");
                }

                if (table.getSelectedRow() == row) {

                    c.setBackground(Color.ORANGE);

                    if (table.getSelectedColumn() == column) {
                        c.setBackground(Color.YELLOW);

                        if (getValueAt(row, 6) == null) {
                        } else {
                            tag = new Hashtable();

                            tag.put(getValueAt(row, 5), getValueAt(row, 6));

                            Enumeration enu;
                            Hashtable ht = tag;

                            enu = ht.keys();

                            String str;
                            while (enu.hasMoreElements()) {
                                str = (String) enu.nextElement();
                                System.out.println(str + ": " + ht.get(str));
                            }
                        }
//                        getValueAt(row, 6);
//                        System.out.println("===========>" + getValueAt(row, 6));
//
//                        DicomTags dicomTags = new DicomTags();
//                        dicomTags.setTagValue(getValueAt(row, 4).toString());
                    }
                } else {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }

                return c;
            }
        });

      
        int i = 0;
        for (DicomTags dicomTags : dicomTagses) {
            int j = 0;
            setValueAt(i, i, j++);
            setValueAt(dicomTags.getTagName(), i, j++);
            setValueAt(dicomTags.getTagAddr(), i, j++);
            setValueAt(dicomTags.getTagVR(), i, j++);
            setValueAt(dicomTags.getTagValue(), i, j++);
            setValueAt(dicomTags.getTag(), i, j++);
            // setValueAt(new Boolean(false), i, j++);

            // System.out.println("==>" + dicomTags.getTagName());
            i++;
        }

    }

    public DicomTags getSelectedDicomTags() {
        return dicomTagses.get((Integer) getValueAt(getSelectedRow(), 0));
    }

    public DicomTags getSelectedDicomTag() {
        return dicomTagses.get(getSelectedRow());
    }

}
