/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.jobui;

import com.optimal.dao.JobDAO;
import com.optimal.dbmodel.Job;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
public class JobFrame {

    static JFrame frame;
    static JPanel panel;
    static JLabel lbTitle, lbDescrption, lbShowTitel, lbShowDescrption;
    static JTextField textTitle, textDescrption;
    static JButton save, clear, delet, search, show, update;
    Icon saveIcon;
    Icon deletIcon;
    Icon searchIcon;
    ImageIcon showIcon;
    ImageIcon updateIcon;

    static JobTable jt;
    static JScrollPane sp;

    public JobFrame() {

        // setLayout(new GridLayout());
        frame = new JFrame("Jop Frame");
        panel = new JPanel();
        lbTitle = new JLabel("Jop Title");
        lbDescrption = new JLabel("Descrption");
        lbShowTitel = new JLabel();
        lbShowDescrption = new JLabel();
        textTitle = new JTextField();
        textDescrption = new JTextField();
//        saveIcon=new ImageIcon(getClass().getResource("img.png"));
//        deletIcon=new ImageIcon(getClass().getResource("Untitled.png"));
//        searchIcon=new ImageIcon(getClass().getResource("Untitled.png"));
//        showIcon=new ImageIcon(getClass().getResource("Untitled.png"));
//        updateIcon=new ImageIcon(getClass().getResource("Untitled.png"));
        save = new JButton("Save", saveIcon);
        clear = new JButton("Clear");
        delet = new JButton("Delet", deletIcon);
        search = new JButton("Search", saveIcon);
        show = new JButton("Show", showIcon);
        update = new JButton("Update", updateIcon);

        jt = new JobTable();
        sp = new JScrollPane(jt);

        // panel.setLayout(new MigLayout("debug" , "[]10[]" , "[][][]")); // 2 columns & 3 rows
        panel.setLayout(new MigLayout());
        panel.setBackground(Color.gray);

        panel.add(lbTitle, "sg 1 , split ");
        panel.add(textTitle, "pushx , growx ,span, wrap");
        panel.add(lbDescrption, " sg 1 ,split ");
        panel.add(textDescrption, "pushx , growx ,span, wrap");
        panel.add(save, "pushx , growx,split");
        panel.add(clear, "pushx , growx");
        panel.add(show, "pushx , growx,  ");
        panel.add(search, "pushx , growx,");
        panel.add(update, "pushx , growx");
        panel.add(delet, "growx,wrap");
        panel.add(sp, "span,pushx , growx");
//        panel.add(lbShowTitel, " ");
//        panel.add(lbShowDescrption, " ");
        frame.add(panel);

        showJobTable();

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

                delet.setVisible(false);
                update.setVisible(false);
                clear.setVisible(false);
                save.setVisible(true);

                showJobTable();
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

                    Job job = new Job();
                    job.setJobTitle(textTitle.getText());
                    job.setDescription(textDescrption.getText());

                    JobDAO jopDAO = new JobDAO();
                    jopDAO.save(job);

                    showJobTable();

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

                showJobTable();

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

                    JobDAO jobDAO = new JobDAO();
                    List<Job> jobList = jobDAO.getjobByJobTitel(textTitle.getText());

                    if (jobList.isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Job isn't Founded");
                    } else {

                        for (Job jobtitel : jobList) {

                            textTitle.setText(jobtitel.getJobTitle());
                            textDescrption.setText(jobtitel.getDescription());

                        }
                        save.setVisible(false);
                        clear.setVisible(true);
                        delet.setVisible(true);
                        update.setVisible(true);

                        jt.setTableFromJop((ArrayList<Job>) jobList);

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

                    JobDAO jobDAO = new JobDAO();
                    // jobDAO.updateJob(...., textTitle.getText(), textDescrption.getText());
                    //jobDAO.updateJob(22, "policeman" , "new Descrption");

                    JOptionPane.showMessageDialog(null, "Job is Updated");

                    showJobTable();

                }
            }
        });

        delet.setBackground(Color.green);
        delet.setForeground(Color.red);
        delet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JobDAO jobDAO = new JobDAO();
                jobDAO.deletdJob(Integer.parseInt(textTitle.getText()));

                showJobTable();
            }
        });

    }

    private void showJobTable() {

//        textTitle.setText(null);
//        textDescrption.setText(null);
        JobDAO jopDAO = new JobDAO();
        List<Job> jopList = jopDAO.getAllJobs();
        jt.setTableFromJop((ArrayList<Job>) jopList);
    }

    public static void showSelectedRaw(int selectdRowId) {

        save.setVisible(false);
        delet.setVisible(true);
        update.setVisible(true);
        clear.setVisible(true);

        JobDAO jobDAO = new JobDAO();
        List<Job> jobList = jobDAO.getJobByJobId(selectdRowId);

        //System.out.println("Tittel 1 : " +jobList.get(0).getJopTitle());
        for (Job jobtitel : jobList) {

            textTitle.setText(jobtitel.getJobTitle());
            textDescrption.setText(jobtitel.getDescription());
        }
    }

    public static void deletSelectedRow(int selectdRowId) {

        System.out.println("id" + selectdRowId);

        JobDAO jobDAO = new JobDAO();
        //  jobDAO.deletdJob(selectdRowId);

    }

    public static void updateSelectedRow(int selectdRowId) {

        System.out.println("id" + selectdRowId);

        JobDAO jobDAO = new JobDAO();
        //jobDAO.updateJob(22, "policeman" , "new Descrption");

    }

    public static void main(String[] args) {

        new JobFrame();

    }
}
