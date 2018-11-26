/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.teacherui;

import com.optimal.dao.TeacherDAO;
import com.optimal.dbmodel.Teacher;
import com.optimal.teachermodel.TeacherModel;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author root
 */
public class TeacherFrame {

    JFrame frame;
    JPanel panel;

    JLabel nameLabel;
    JTextField nameTextField;

    JLabel buttomLabelRadio;
    JRadioButton maleRadioButton;
    JRadioButton femalRadioButton;
    ButtonGroup genderButtonGroup;
    private String genderVaue;

    JLabel birthDateLabel;
    JDateChooser dateChooser;
    SimpleDateFormat dateFormat;

    JLabel addressLable;
    JComboBox counteryComboBox;
    JComboBox cityComboBox;
    JTextField streetText;
    private String counteryList[] = {"Egypt", "USA", "Syria"};

    private String egyptCountery[] = {"ALx", "cairo", "Menofia"};
    private String usaCountery[] = {"usa1", "usa2", "usa3"};
    private String syriacountery[] = {"syriy1", "syriy2", "syriy3"};

    JLabel habitLable;
    JList habitList;
    JScrollPane habitListSP;
    private String habits[] = {"Facebook", "Video Games", "Eating Dairy", "Going To The Club", "Watching Reality Television ", "Drinking Tea "};

    JFileChooser chooser;

    JButton selectImage;
    String imagePath;

    JButton saveData;
    JButton updateData;
    JButton deletData;
    JButton search;
    JButton displayAllTeacher;

    JButton cleare;

    TeacherDataTable jt;
    JScrollPane sp;

    JLabel displayImgLable;

    ArrayList<TeacherModel> empList = new ArrayList<TeacherModel>();

    Teacher teacher;

    JPanel p;

    public TeacherFrame() {

        frame = new JFrame();
        panel = new JPanel(new MigLayout());

        nameLabel = new JLabel("Name : ");
        nameTextField = new JTextField();

        buttomLabelRadio = new JLabel("Gender : ");
        maleRadioButton = new JRadioButton("Male");
        femalRadioButton = new JRadioButton("Femal");
        genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femalRadioButton);

        birthDateLabel = new JLabel("BirthDate : ");
        dateChooser = new JDateChooser();
        //df = new SimpleDateFormat("yyyy-mm-dd");
        dateChooser.setDateFormatString("yyyy-mm-dd");

        addressLable = new JLabel("Address");
        counteryComboBox = new JComboBox(counteryList);
        cityComboBox = new JComboBox();
        streetText = new JTextField();

        habitLable = new JLabel("Habits : ");
        habitList = new JList(habits);
        habitListSP = new JScrollPane();
        habitListSP.getViewport().add(habitList, null);

        displayImgLable = displayImgLable = new JLabel();

        selectImage = new JButton("Select Image");
        saveData = new JButton("Save Data");
        updateData = new JButton("Update");
        deletData = new JButton("Delete");
        search = new JButton("Search");
        displayAllTeacher = new JButton("Display");

        cleare = new JButton("Clear");

        jt = new TeacherDataTable(this);
        sp = new JScrollPane(jt);

        panel.add(nameLabel, "sg 1 , split ");
        panel.add(nameTextField, "wrap , span , pushx ,growx");

        panel.add(buttomLabelRadio, "sg 1 , split ");
        panel.add(maleRadioButton);
        panel.add(femalRadioButton, "wrap");

        panel.add(birthDateLabel, "sg 1 , split ");
        panel.add(dateChooser, "span 2 , wrap  , pushx ,growx");

        panel.add(addressLable, "sg 1 , split ");
        panel.add(counteryComboBox, "sg 1 ");
        panel.add(cityComboBox, "span 2 ,sg 1");
        panel.add(streetText, "wrap , sg 1 , pushx ,growx");

        //panel.add(habitLable);
        panel.add(habitLable, "sg 1 , split ");
        panel.add(habitListSP, "wrap , pushx ,growx");

        panel.add(displayImgLable, "wrap");

        panel.add(selectImage, "split, pushx ,growx");

        panel.add(saveData, "split, pushx ");
        panel.add(updateData, "split, pushx ,growx");
        panel.add(deletData, "split, pushx ,growx");
        panel.add(search, "split, pushx ,growx");
        panel.add(displayAllTeacher, "split, pushx ,growx");

        panel.add(cleare, "wrap, pushx ,growx");

        panel.add(sp, "span ,growx");

        frame.add(panel);

        showTeacherTable();

        frame.setTitle("Teachers");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        counteryComboBox.setSelectedItem(null);
        counteryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (counteryComboBox.getSelectedIndex() == 0) {

                    cityComboBox.removeAllItems();
                    for (int i = 0; i < egyptCountery.length; i++) {
                        cityComboBox.addItem(egyptCountery[i]);
                    }

                } else if (counteryComboBox.getSelectedIndex() == 1) {
                    cityComboBox.removeAllItems();
                    for (int i = 0; i < usaCountery.length; i++) {
                        cityComboBox.addItem(usaCountery[i]);
                    }

                } else if (counteryComboBox.getSelectedIndex() == 2) {
                    cityComboBox.removeAllItems();
                    for (int i = 0; i < syriacountery.length; i++) {
                        cityComboBox.addItem(syriacountery[i]);
                    }

                }

            }
        });

        selectImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                imagePath = getImagePath();
                displayImgLable.setIcon(new ImageIcon(imagePath));

            }
        });

        updateData.setVisible(false);
        deletData.setVisible(false);
        displayAllTeacher.setVisible(false);
        cleare.setVisible(false);

        saveData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (maleRadioButton.isSelected()) {
                    genderVaue = "Male";
                } else if (femalRadioButton.isSelected()) {
                    genderVaue = "Femal";
                }

                teacher = new Teacher();

                teacher.setName(nameTextField.getText());
                teacher.setGender(genderVaue);
                teacher.setBirthdate(dateChooser.getDate());

                System.out.println("ddddddddddddddattttttttte : " + dateChooser.getDate());

                teacher.setCountery((String) counteryComboBox.getSelectedItem());
                teacher.setCity((String) cityComboBox.getSelectedItem());
                teacher.setStreet(streetText.getText());

                StringBuilder habitsBulider = new StringBuilder();
                for (Object object : habitList.getSelectedValuesList()) {

                    habitsBulider.append((String) object + " , ");
                }
                System.out.println("h" + habitsBulider);

                teacher.setHabits(habitsBulider.toString());

                //teacherDAO.setSelectedhabitsIndex(habitList.getSelectedIndices());
                teacher.setImagePath(imagePath);

                if (dateChooser.getDate().equals(null)) {
                    JOptionPane.showMessageDialog(null, "insert Birthdate Please !");
                } else {
                    TeacherDAO teacherDAO = new TeacherDAO();
                    teacherDAO.saveTeacher(teacher);
                }
                displayImgLable.setIcon(new ImageIcon(imagePath));

                showTeacherTable();

                //  System.out.println("habits: "+ habitList.getSelectedValuesList());
            }
        });

        updateData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (maleRadioButton.isSelected()) {
                    genderVaue = "Male";
                } else if (femalRadioButton.isSelected()) {
                    genderVaue = "Femal";
                }

                
                
                Teacher teacherUpdated = new Teacher();
                
                teacherUpdated.setId(jt.getSelectedTeacher().getId());

                teacherUpdated.setName(nameTextField.getText());
                teacherUpdated.setGender(genderVaue);

                teacherUpdated.setBirthdate(dateChooser.getDate());

                System.out.println("ddddddddddddddattttttttte : " + dateChooser.getDate());

                teacherUpdated.setCountery((String) counteryComboBox.getSelectedItem());
                teacherUpdated.setCity((String) cityComboBox.getSelectedItem());
                teacherUpdated.setStreet(streetText.getText());

                StringBuilder habitsBulider = new StringBuilder();
                for (Object object : habitList.getSelectedValuesList()) {

                    habitsBulider.append((String) object + " , ");
                }
                System.out.println("h" + habitsBulider);

                teacherUpdated.setHabits(habitsBulider.toString());

                //teacherDAO.setSelectedhabitsIndex(habitList.getSelectedIndices());
                teacherUpdated.setImagePath(imagePath);

                TeacherDAO teacherDAO = new TeacherDAO();
                teacherDAO.updateTeacherObject(teacherUpdated);

                displayImgLable.setIcon(new ImageIcon(imagePath));

                showTeacherTable();

                /* teacherDAO.updateTeacher(jt.getSelectedTeacher().getId(), nameTextField.getText(), genderVaue, dateChooser.getDate(),
                        (String) counteryComboBox.getSelectedItem(), (String) cityComboBox.getSelectedItem(),
                        streetText.getText(), habitsBulider.toString(), imagePath);
                 */
                displayImgLable.setIcon(new ImageIcon(imagePath));

                showTeacherTable();

            }
        });

        deletData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TeacherDAO teacherDAO = new TeacherDAO();
                teacherDAO.deletdTeacher(jt.getSelectedTeacher().getId());
                showTeacherTable();
                clearData();
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TeacherDAO teacherDAO = new TeacherDAO();
                List<Teacher> searchTeacher = teacherDAO.searchTeacher(Integer.parseInt(nameTextField.getText()));
                jt.setTableFromTeacher((ArrayList<Teacher>) searchTeacher);

                displayAllTeacher.setVisible(true);

                //  showTeacherTable();
            }
        });

        displayAllTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showTeacherTable();
                displayAllTeacher.setVisible(false);
            }
        });

        cleare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clearData();
                System.out.println("Cler Data");
                saveData.setVisible(true);

            }
        });
    }

    public String getImagePath() {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("/home/shady/Desktop"));
        chooser.setDialogTitle("Select Image");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            System.out.println("Image Path " + chooser.getSelectedFile().getAbsolutePath());
            return chooser.getSelectedFile().getAbsolutePath();

        }
        return null;

    }

    private void showTeacherTable() {

        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        jt.setTableFromTeacher((ArrayList<Teacher>) teachers);
    }

    public void showTeacherSelectedRaw() {

        nameTextField.setText(jt.getSelectedTeacher().getName());

        if (jt.getSelectedTeacher().getGender().equals("Male")) {
            maleRadioButton.setSelected(true);
            femalRadioButton.setSelected(false);

        } else {
            femalRadioButton.setSelected(true);
            maleRadioButton.setSelected(false);
        }

        counteryComboBox.setSelectedItem(jt.getSelectedTeacher().getCountery());

        cityComboBox.setSelectedItem(jt.getSelectedTeacher().getCity());

        streetText.setText(jt.getSelectedTeacher().getStreet());

        // habitList.setSelectedIndices(jt.getSelectedTeacher().getSelectedhabitsIndex());
        dateChooser.setDate(jt.getSelectedTeacher().getBirthdate());

        displayImgLable.setIcon(new ImageIcon(jt.getSelectedTeacher().getImagePath()));

        saveData.setVisible(false);

        //   search.setVisible(false);
        updateData.setVisible(true);
        deletData.setVisible(true);
        cleare.setVisible(true);

//        panel.add(p);
//        panel.repaint();
    }

    public void clearData() {

        nameTextField.setText("");

        genderButtonGroup.clearSelection();

        dateChooser.setDate(null);
        counteryComboBox.setSelectedItem(null);
        cityComboBox.setSelectedItem(null);
        streetText.setText("");
        habitList.clearSelection();

        displayImgLable.setIcon(null);

        deletData.setVisible(false);
        updateData.setVisible(false);
        // **IMPORTANT** to call revalidate() to cause JLabel to resize and be repainted.
        // displayImgLable.revalidate();
    }

    public static void main(String[] args) {
        new TeacherFrame();
    }

}
