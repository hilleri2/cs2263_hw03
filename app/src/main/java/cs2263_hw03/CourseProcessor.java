/**
 * MIT License
 *
 * Copyright (c) 2022 hilleri2
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cs2263_hw03;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class creates a javaFX user interface to accept inputs, create courses
 * based on those inputs and add them to a list, can display the courses
 * either altogether or by department, and can save/load them from a text file.
 * @author Eric Hill
 */
public class CourseProcessor extends Application {
    private ArrayList<Course> courseList = new ArrayList<>();

    /**
     * this method interfaces with the Course class to create a course and add it to courseList
     * @param d - the department name
     * @param na - the class name
     * @param nu - the class number
     * @param c - the number of credits
     * @return the created Course
     * @throws Exception
     */
    private Course addCourse(String d, String na, String nu, String c) throws Exception{
        if (d == "" || na == "" || nu == "" || c == ""){
            throw new Exception();
        }
        return new Course(d,na,nu,c);
    }

    /**
     * this method saves all the courses in the current list to a text file
     * @throws IOException
     */
    private void save() throws IOException {
        Gson gson = new Gson();

        //String json = gson.toJson();
        File savefile = new File("ListofCourses.txt");
        savefile.createNewFile();
        FileWriter writer = new FileWriter(savefile);
        for (var i : courseList){
            String json = gson.toJson(i);
            writer.write(json+"\n");
        }
        writer.close();
    }

    /**
     * this method loads all of the courses from the specific text file
     * @throws FileNotFoundException
     */
    private void load() throws FileNotFoundException {
        File savefile = new File("ListofCourses.txt");
        Scanner scan = new Scanner(savefile);
        courseList.clear();
        while (scan.hasNextLine()){
            Gson gson = new Gson();
            String json = scan.nextLine();
            courseList.add(gson.fromJson(json, Course.class));
        }
    }

    /**
     * this method creates and loads all of the javaFX UI elements onto the screen
     * @param stage1 - holds all of the javaFX Objects and shows them on the screen
     * @throws Exception
     */
    public void start(Stage stage1) throws Exception {
        stage1.setTitle("Course Manager");
        Button save = new Button("Save courses");
        save.setOnAction(value -> {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Button load = new Button("Load courses");
        load.setOnAction(value -> {
            try {
                load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button add = new Button("Add Course");
        Button dispAll = new Button("Display (all)");
        Label tDeptS = new Label("Select department name to display");
        ComboBox<String> cDeptS = new ComboBox<>();
        cDeptS.getItems().addAll(Course.departments);
        Button disp = new Button("Display (dept)");
        Label tDept = new Label("Select department name");
        ComboBox<String> cDept = new ComboBox<>();
        cDept.getItems().addAll(Course.departments);
        Label tName = new Label("Enter course name");
        TextField cName = new TextField();
        Label tNum = new Label("Enter course number");
        TextField cNum = new TextField();
        Label tCred = new Label("Enter course credits");
        TextField cCred = new TextField();

        add.setOnAction(value -> {
            try {
                courseList.add(addCourse(cDept.getValue(), cName.getText(), cNum.getText(), cCred.getText()));
            }
            catch(Exception e){
                Dialog inputProb = new Dialog();
                inputProb.setContentText("please enter fields with valid values");
                inputProb.getDialogPane().getButtonTypes().addAll(new ButtonType("Ok"));
                inputProb.setTitle("Input Error");
                inputProb.show();
            }
        });

        VBox col1 = new VBox(3,tDept,cDept, tName,cName, tNum,cNum, tCred,cCred, add);
        VBox col2 = new VBox(5, save,load);
        VBox col3 = new VBox(5,tDeptS,cDeptS,disp, dispAll);
        VBox courseDisplay = new VBox();
        courseDisplay.getChildren().add(new HBox(5,new Label("Class code"),new Label("Class number"),new Label("Class name"),new Label("Credits")));
        ScrollPane display = new ScrollPane(courseDisplay);
        HBox hboxtop = new HBox(8, col1, col2, col3, display);

        dispAll.setOnAction(value -> {
            //clear display
            courseDisplay.getChildren().clear();
            //add baseline info
            courseDisplay.getChildren().add(new HBox(5,new Label("Class code"),new Label("Class number"),new Label("Class name"),new Label("Credits")));
            for (var i : courseList){
                HBox courseInfo = new HBox(42);
                courseInfo.getChildren().addAll(new Label(i.getCode()),new Label(i.getNum().toString()),new Label(i.getName()),new Label(i.getCred().toString()));
                courseDisplay.getChildren().add(courseInfo);
            }
        });
        disp.setOnAction(value -> {
            //clear display
            courseDisplay.getChildren().clear();
            //add baseline info
            courseDisplay.getChildren().add(new HBox(5,new Label("Class code"),new Label("Class number"),new Label("Class name"),new Label("Credits")));
            for (var i : courseList){
                if (i.getDept().equals(cDeptS.getValue())) {
                    HBox courseInfo = new HBox(42);
                    courseInfo.getChildren().addAll(new Label(i.getCode()), new Label(i.getNum().toString()), new Label(i.getName()), new Label(i.getCred().toString()));
                    courseDisplay.getChildren().add(courseInfo);
                }
            }
        });
        Scene scene1 = new Scene(hboxtop, 800, 500);
        stage1.setScene(scene1);
        stage1.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
