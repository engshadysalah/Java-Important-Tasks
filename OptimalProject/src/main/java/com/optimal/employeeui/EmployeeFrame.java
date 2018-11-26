/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template i  n the editor.
 */
package com.optimal.employeeui;

import com.optimal.dao.DepartmentsDAO;
import com.optimal.dao.EmployeeDAO;
import com.optimal.dao.JobDAO;
import com.optimal.dbmodel.Departments;
import com.optimal.dbmodel.Employee;
import com.optimal.dbmodel.Job;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JDateChooser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author shady
 */
// founded from 9:5 to 6:30
// 3:40 to 6:00 
public class EmployeeFrame {

    static JFrame frame;
    static JPanel panel;
    static JLabel lbName, lbAddress, lbPhone ,lbBirthDate, lbJobTitle, lbDepartmentTitle;
    static JTextField name, address, phon;
    
    JDateChooser dateChooser ;
    DateFormat df;
    
    static JButton save, clear, show, search, update, delet;
    static EmployeeTable jt;
    static JScrollPane sp;
    static JComboBox jobList;
    static JComboBox jobCBoox;
    static String jobItemscombo;

    static JComboBox departmentList;
    static JComboBox departmentCBox;
    static String departmentItemscombo;

    public EmployeeFrame() {

        frame = new JFrame();
        panel = new JPanel();

        JobDAO job = new JobDAO();

        List<Job> jobTitelList = job.getAllJobs();
        String[] jobItem = new String[jobTitelList.size()];

        int i = 0;
        for (Job jobtitel : jobTitelList) {

            jobItem[i] = jobtitel.getJobTitle();
            i++;
        }

        DepartmentsDAO departmentsDAO = new DepartmentsDAO();

        List<Departments> departmentTitelList = departmentsDAO.getAllDepartments();
        String[] departmentItem = new String[departmentTitelList.size()];

        int x = 0;
        for (Departments departments : departmentTitelList) {

            departmentItem[x] = departments.getTitle();
            x++;
        }

        jobList = new JComboBox(jobItem);
        departmentList = new JComboBox(departmentItem);

        lbName = new JLabel("Name");
        lbAddress = new JLabel("Address");
        lbPhone = new JLabel("Phone");
        lbBirthDate=new JLabel("Birth Date");
        dateChooser = new JDateChooser();
        lbJobTitle = new JLabel("Job Title");
        lbDepartmentTitle = new JLabel("Department Title");
        name = new JTextField();
        address = new JTextField();
        phon = new JTextField();
        save = new JButton("Save");
        clear = new JButton("Clear");
        show = new JButton("Show");
        search = new JButton("Search");
        update = new JButton("Update");
        delet = new JButton("Delete");
        jt = new EmployeeTable();
        sp = new JScrollPane(jt);

        // panel.setLayout(new MigLayout("debug" , "[]10[]" , "[][][]")); // 2 columns & 3 rows
        panel.setLayout(new MigLayout());
        panel.setBackground(Color.gray);

        panel.add(lbName, " split, sg 1 ");
        panel.add(name, "pushx , growx ,wrap,span ");
        panel.add(lbAddress, " split,sg 1 ");
        panel.add(address, "pushx , growx, wrap ,span  ");
        panel.add(lbPhone, "split , sg 1 ");
        panel.add(phon, "wrap , pushx , growx ,span ");
        panel.add(lbBirthDate, " split, sg 1 ");
        panel.add(dateChooser ," wrap  , pushx , growx");
        panel.add(lbJobTitle, "split , sg 1");
        panel.add(jobList, "growx , wrap ");
        panel.add(lbDepartmentTitle, "split , sg 1");
        panel.add(departmentList, "wrap , growx");
        panel.add(save, "split,growx");
        panel.add(clear, "growx");
        panel.add(show, "growx");
        panel.add(search, "growx");
        panel.add(update, "growx");
        panel.add(delet, " growx,  wrap");
        panel.add(sp, "span ,growx");
        frame.add(panel);

        dateChooser.setDateFormatString("yyyy-mm-dd");
         df=new SimpleDateFormat("yyyy-mm-dd");

        showEmployeeTable();

        frame.setTitle("Employees");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        jobList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jobCBoox = (JComboBox) e.getSource();
                jobItemscombo = (String) jobCBoox.getSelectedItem();

            }
        });

        departmentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                departmentCBox = (JComboBox) e.getSource();
                departmentItemscombo = (String) departmentCBox.getSelectedItem();

            }
        });

        clear.setVisible(false);
        delet.setVisible(false);
        update.setVisible(false);

        clear.setBackground(Color.white);
        clear.setForeground(Color.red);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                name.setText(null);
                address.setText(null);
                phon.setText(null);

                save.setVisible(true);
                delet.setVisible(false);
                update.setVisible(false);
                clear.setVisible(false);

                showEmployeeTable();
            }
        });

        save.setBackground(Color.black);
        save.setForeground(Color.red);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (name.getText().isEmpty() || address.getText().isEmpty() || phon.getText().isEmpty() || jobItemscombo == null || departmentItemscombo == null) {

                    JOptionPane.showMessageDialog(null, "Check Your inputs please !");

                } else {

                    
               
                
                    Job job = new Job();
                    JobDAO jdao = new JobDAO();
                    job = jdao.getIdByjobTitel(jobItemscombo);

                    Departments departments = new Departments();
                    DepartmentsDAO ddao = new DepartmentsDAO();
                    departments = ddao.getIdByDepartmentTitel(departmentItemscombo);

                    Employee em = new Employee();
                    em.setFirstName(name.getText());
                    em.setAddress(address.getText());
                    em.setPhone(phon.getText());
                    em.setBirthDate(df.format(dateChooser.getDate()));
                    em.setJob(job);
                    em.setDepartments(departments);

                    EmployeeDAO employeeDAO = new EmployeeDAO();
                    employeeDAO.Save(em);
                    showEmployeeTable();

                    name.setText(null);
                    address.setText(null);
                    phon.setText(null);
                }

            }
        });

        show.setBackground(Color.black);
        show.setForeground(Color.red);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showEmployeeTable();

            }
        });

        search.setBackground(Color.black);
        search.setForeground(Color.red);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String enteredPhone = JOptionPane.showInputDialog(null, "Enter Employee Phone to Search !");

                EmployeeDAO employeeDAO = new EmployeeDAO();
                List<Employee> empList = employeeDAO.getEmployeeByphone(enteredPhone);

                if (empList.isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Employee isn't Founded");

                } else {

                    for (Employee employee : empList) {

                        name.setText(employee.getFirstName());
                        address.setText(employee.getAddress());
                        phon.setText(employee.getPhone());
                        

                        //System.out.println("job titel==>"+ employee.getJob().getJopTitle() );
                    }
                    save.setVisible(false);
                    clear.setVisible(true);
                    delet.setVisible(true);
                    update.setVisible(true);

                    jt.setTableFromEmployee((ArrayList<Employee>) empList);
                }

            }
        });

        update.setBackground(Color.green);
        update.setForeground(Color.red);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Job job = new Job();
                JobDAO jdao = new JobDAO();
                job = jdao.getIdByjobTitel(jobItemscombo);

                Departments departments = new Departments();
                DepartmentsDAO ddao = new DepartmentsDAO();
                departments = ddao.getIdByDepartmentTitel(departmentItemscombo);

                // problem : cann't get id of selected row of table
                EmployeeDAO employeeDAO = new EmployeeDAO();
                //employeeDAO.updateEmployee(90, name.getText(), address.getText(), phon.getText() ,df.format(dateChooser.getDate()), job.getId(), departments.getId());

                showEmployeeTable();
            }
        });

        delet.setBackground(Color.green);
        delet.setForeground(Color.red);
        delet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // problem : cann't get id of selected row of table
                EmployeeDAO employeeDAO = new EmployeeDAO();
                employeeDAO.deletdEmployee(Integer.parseInt(name.getText()));

                showEmployeeTable();

            }
        });

    }

    private void showEmployeeTable() {

//        name.setText(null);
//        address.setText(null);
//        phon.setText(null);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> empList = employeeDAO.getAllEmployees();
        jt.setTableFromEmployee((ArrayList<Employee>) empList);

    }

    public static void showSelectedRaw(int selectdRowId) {

        save.setVisible(false);
        clear.setVisible(true);
        delet.setVisible(true);
        update.setVisible(true);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> empList = employeeDAO.getEmployeeById(selectdRowId);

        //System.out.println("Tittel 1 : " +empList.get(0).getFirstName());
        for (Employee employee : empList) {

            name.setText(employee.getFirstName());
            address.setText(employee.getAddress());
            phon.setText(employee.getPhone());

            //System.out.println("Department titel==>"+ employee.getDepartments().getTitle() );
        }

    }

    public static void main(String[] args) {

        new EmployeeFrame();

    }

}
