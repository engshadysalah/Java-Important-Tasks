/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.departmentsui;

import com.optimal.dao.DepartmentsDAO;
import com.optimal.dbmodel.Departments;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author shady
 */
public class DepartmentsFrame {

    static JFrame frame;
    static JPanel panel;
    static JLabel lbTitle, lbDescrption, lbShowTitel, lbShowDescrption;
    static JTextField textTitle, textDescrption;
    static JButton save, clear, delet, search, show, update;

    static DepartmentsTable jt;
    static JScrollPane sp;

    public DepartmentsFrame() {

        // setLayout(new GridLayout());
        frame = new JFrame("Departments Frame");
        panel = new JPanel();
        lbTitle = new JLabel("Department Title : ");
        lbDescrption = new JLabel("Department Descrption : ");
//        lbShowTitel = new JLabel();
//        lbShowDescrption = new JLabel();
        textTitle = new JTextField();
        textDescrption = new JTextField();

        save = new JButton("Save");
        delet = new JButton("Delet");
        search = new JButton("Search");
        show = new JButton("Show");
        update = new JButton("Update");
        clear = new JButton("Clear");

        jt = new DepartmentsTable();
        sp = new JScrollPane(jt);

        // panel.setLayout(new MigLayout("debug" , "[]10[]" , "[][][]")); // 2 columns & 3 rows
        panel.setLayout(new MigLayout());
        panel.setBackground(Color.gray);

        panel.add(lbTitle, "sg 1 ,split ");
        panel.add(textTitle, "pushx , growx ,span, wrap");
        panel.add(lbDescrption, " sg 1 , split ");
        panel.add(textDescrption, "pushx , growx ,span, wrap");
        panel.add(save, "split ,pushx , growx");
        panel.add(clear, " ,pushx , growx");
        panel.add(show, " pushx , growx ");
        panel.add(search, "pushx , growx");
        panel.add(update, "pushx , growx");
        panel.add(delet, "growx,wrap");
        panel.add(sp, "span , growx");
//        panel.add(lbShowTitel, " ");
//        panel.add(lbShowDescrption, " ");
        frame.add(panel);

        showDepartmentsTable();

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        delet.setVisible(false);
        update.setVisible(false);
        clear.setVisible(false);

        clear.setBackground(Color.white);
        clear.setForeground(Color.red);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textTitle.setText(null);
                textDescrption.setText(null);

                save.setVisible(true);
                delet.setVisible(false);
                update.setVisible(false);
                clear.setVisible(false);

                showDepartmentsTable();
            }
        });

        save.setBackground(Color.black);
        save.setForeground(Color.red);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textTitle.getText().isEmpty() || textDescrption.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Check Your inputs please !");

                } else {

                    Departments departments = new Departments();
                    departments.setTitle(textTitle.getText());
                    departments.setDescrption(textDescrption.getText());

                    DepartmentsDAO departmentsDAO = new DepartmentsDAO();
                    departmentsDAO.save(departments);

                    showDepartmentsTable();

                    textTitle.setText(null);
                    textDescrption.setText(null);
                }
            }
        });

        show.setBackground(Color.black);
        show.setForeground(Color.red);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showDepartmentsTable();

            }
        });

        search.setBackground(Color.black);
        search.setForeground(Color.red);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textTitle.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Check your Inputs Please !");

                } else {

                    DepartmentsDAO departmentsDAO = new DepartmentsDAO();
                    List<Departments> departmentses = departmentsDAO.getDepartmentByTitel(textTitle.getText());

                    if (departmentses.isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Department isn't Founded");
                    } else {

                        for (Departments departments : departmentses) {

                            textTitle.setText(departments.getTitle());
                            textDescrption.setText(departments.getDescrption());

                        }
                        save.setVisible(false);
                        clear.setVisible(true);
                        delet.setVisible(true);
                        update.setVisible(true);

                        jt.setTableFromDepartmentses((ArrayList<Departments>) departmentses);

                    }
                }
            }
        });

        update.setBackground(Color.green);
        update.setForeground(Color.red);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textTitle.getText().isEmpty() || textDescrption.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Check Your inputs please !");

                } else {

                    DepartmentsDAO departmentsDAO = new DepartmentsDAO();
                    departmentsDAO.updateDepartment(2, textTitle.getText(), textDescrption.getText());

                    showDepartmentsTable();
                    JOptionPane.showMessageDialog(null, "Department is Updated");

                }
            }
        });

        delet.setBackground(Color.green);
        delet.setForeground(Color.red);
        delet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DepartmentsDAO departmentsDAO = new DepartmentsDAO();
                departmentsDAO.deletdDepartment(Integer.parseInt(textTitle.getText()));

                showDepartmentsTable();
             
            }
        });

    }

    private void showDepartmentsTable() {

//        textTitle.setText(null);
//        textDescrption.setText(null);
        DepartmentsDAO departmentsDAO = new DepartmentsDAO();
        List<Departments> departmentses = departmentsDAO.getAllDepartments();
        jt.setTableFromDepartmentses((ArrayList<Departments>) departmentses);
    }

    public static void showSelectedRaw(int selectdRowId) {

        //save.setEnabled(false);
        save.setVisible(false);
        clear.setVisible(true);
        delet.setVisible(true);
        update.setVisible(true);

        DepartmentsDAO departmentsDAO = new DepartmentsDAO();
        List<Departments> departmentses = departmentsDAO.getDepartmentById(selectdRowId);

        //System.out.println("Tittel 1 : " +departmentses.get(0).getTitle());
        for (Departments departments : departmentses) {

            textTitle.setText(departments.getTitle());
            textDescrption.setText(departments.getDescrption());
        }
    }

    public static void deletSelectedRow(int selectdRowId) {

        System.out.println("id" + selectdRowId);

        DepartmentsDAO departmentsDAO = new DepartmentsDAO();
        departmentsDAO.deletdDepartment(selectdRowId);

    }

    public static void updateSelectedRow(int selectdRowId) {

        System.out.println("id" + selectdRowId);

    }

    public static void main(String[] args) {

        new DepartmentsFrame();

    }
}
