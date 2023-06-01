//  Description: CoursePane is the Main Pane(HBox) responsibile for holding all the information displayed to the user
//               There are 3 subpanes within, leftPane, rightPane and centerPane, all of which are borderpanes.
//               Within leftPane and centerPane, are two other subpanes, both of which are gridpanes.
//               The subpane in rightPane is a VBox.

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class CoursePane extends HBox {
    // GUI components
    private ArrayList<Course> courseList;
    private VBox checkboxContainer;

    // Panes
    private BorderPane leftPane;
    private BorderPane centerPane;
    private BorderPane rightPane;

    // leftPane and rightPane elements
    private Label subjectLabel;
    private Label courseLabel;
    private Label instructorLabel;
    private Label labelLeftBottom;
    private Label labelRightBottom;

    private TextField subjectTextField;
    private TextField instructorTextField;

    private ComboBox<String> subjects;

    private GridPane leftSubPane;
    private GridPane centerCenterPane;

    private ArrayList<CheckBox> checkBoxArr;

    // centerPane elements
    private Button addButton;
    private Button dropButton;

    // constructor
    public CoursePane() {
        this.courseList = new ArrayList<Course>();

        // Panes
        this.leftPane = new BorderPane();
        this.centerPane = new BorderPane();
        this.rightPane = new BorderPane();

        // SubPane of the center of Left Pane
        this.leftSubPane = new GridPane();

        // SubPane of the center of center pane
        this.centerCenterPane = new GridPane();

        // Labels
        this.subjectLabel = new Label("Subject");
        this.courseLabel = new Label("Course Num");
        this.instructorLabel = new Label("Instructor");
        this.labelLeftBottom = new Label("No course entered");
        this.labelRightBottom = new Label("Total course enrolled: 0");

        // Text Fields
        this.subjectTextField = new TextField();
        this.instructorTextField = new TextField();

        this.subjects = new ComboBox<String>();

        // Buttons
        this.addButton = new Button(" Add => ");
        this.dropButton = new Button(" Drop <= ");

        this.checkBoxArr = new ArrayList<CheckBox>();

        // Checkbox container
        this.checkboxContainer = new VBox(5);

        Label labelLeft = new Label("Add Course(s)");
        labelLeft.setTextFill(Color.BLUE);
        labelLeft.setFont(Font.font(null, 14));
        labelLeft.setPadding(new Insets(10, 10, 0, 10));

        Label labelRight = new Label("Course(s) Enrolled");
        labelRight.setTextFill(Color.BLUE);
        labelRight.setFont(Font.font(null, 14));
        labelRight.setPadding(new Insets(10, 10, 0, 10));

        // Borders
        leftPane.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        rightPane.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // ComboBox
        subjects.getItems().addAll("ACC", "AME", "BME", "CHM", "DAT", "EEE", "CSE");
        subjects.setValue("CSE"); // Default value


        // setting up layout of leftPane
        leftPane.setTop(labelLeft);
        leftPane.setCenter(leftSubPane);
        leftPane.setBottom(labelLeftBottom);
        leftPane.setPrefWidth(280);

        labelLeftBottom.setPadding(new Insets(10, 10, 10, 10));

        // SubPane
        leftSubPane.add(subjectLabel, 0, 0);
        leftSubPane.add(courseLabel, 0, 1);
        leftSubPane.add(instructorLabel, 0, 2);
        leftSubPane.add(subjects, 1, 0);
        leftSubPane.add(subjectTextField, 1, 1);
        leftSubPane.add(instructorTextField, 1, 2);

        leftSubPane.setAlignment(Pos.CENTER_LEFT);
        leftSubPane.setHgap(10);
        leftSubPane.setVgap(20);
        leftSubPane.setPadding(new Insets(0, 10, 0, 10));

        //setting up layout of centerPane
        centerPane.setCenter(centerCenterPane);

        centerCenterPane.setAlignment(Pos.CENTER);
        centerCenterPane.add(addButton, 1, 1);
        centerCenterPane.add(dropButton, 1, 2);
        centerCenterPane.setPadding(new Insets(10, 10, 10, 10));
        centerCenterPane.setVgap(15);

        //Makes buttons same size
        addButton.setMinWidth(70);
        dropButton.setMinWidth(70);

        // setting up layout of rightPane
        labelRightBottom.setPadding(new Insets(10, 10, 10, 10));

        rightPane.setTop(labelRight);
        rightPane.setLeft(checkboxContainer);
        rightPane.setBottom(labelRightBottom);

        rightPane.setPrefWidth(220);

        // adding all the panes to an HBox
        this.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().addAll(leftPane, centerPane, rightPane);

        // linking buttons to buttonhandlers
        addButton.setOnAction(new ButtonHandler());
        dropButton.setOnAction(new ButtonHandler());

    } 

    // ButtonHandler to set up what happens when a button is clicked
    private class ButtonHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            try {
                // Add Button
                addButton.setOnMouseClicked(event -> {
                    try {
                        boolean isDuplicate = false;

                        // checks if both textFields are filled
                        if (!subjectTextField.getText().trim().isEmpty()
                                && !instructorTextField.getText().trim().isEmpty()) {
                            
                            // if input is not an integer, catch block runs
                            int number = Integer.parseInt(subjectTextField.getText().trim());
                            String instructor = instructorTextField.getText().trim();

                            // checks for duplicates
                            for (int i = 0; i < courseList.size(); ++i) {
                                if (number == courseList.get(i).getCourseNum()
                                        && subjects.getValue() == courseList.get(i).getSubject()) {
                                    isDuplicate = true;
                                }
                            }

                            if (!isDuplicate) {
                                Course courseToAdd = new Course(subjects.getValue(), number, instructor);

                                CheckBox courseCheckBox = new CheckBox(courseToAdd.toString());
                                courseCheckBox.setPadding(new Insets(0, 0, 0, 10));
                                courseCheckBox.setIndeterminate(false);
                                checkboxContainer.getChildren().addAll(courseCheckBox);
                                courseList.add(courseToAdd);
                                checkBoxArr.add(courseCheckBox);

                                labelLeftBottom.setText("Course added successfully");
                                labelLeftBottom.setTextFill(Color.BLACK);

                                labelRightBottom.setText("Total course enrolled: " + courseList.size());

                                // Resets text fields
                                subjectTextField.setText("");
                                instructorTextField.setText("");

                            }

                            // Course is duplicate
                            else {
                                labelLeftBottom.setText("Duplicated Course - Not added");
                                labelLeftBottom.setTextFill(Color.RED);
                            }
                        }

                        // Checking for empty text fields
                        else if (subjectTextField.getText().trim().isEmpty()
                                || instructorTextField.getText().trim().isEmpty()) {

                            labelLeftBottom.setText("At least one text field is empty. Fill all fields!");
                            labelLeftBottom.setTextFill(Color.RED);
                        }
                    }

                    // if User enters invalid input in course num
                    catch (NumberFormatException ex) {
                        labelLeftBottom.setText("Error! Course number must be an integer");
                        labelLeftBottom.setTextFill(Color.RED);
                    }

                    catch (Exception exception) {
                    }

                });

                // Drop Button
                dropButton.setOnMouseClicked(event -> {
                    try {
                        for (CheckBox i : checkBoxArr) {
                            if (i.isSelected()) {
                                checkboxContainer.getChildren().remove(i);

                                // removing the course from courselist
                                for (Course j : courseList) {
                                    if (i.getText().equals(j.toString())) {
                                        courseList.remove(j);
                                        break;
                                    }
                                }

                                // updates total courses enrolled
                                labelRightBottom.setText("Total course enrolled: " + courseList.size());
                            }
                        }
                    }

                    // catches any exception
                    catch (Exception ex) {

                    }
                });

            } 

            catch (NumberFormatException ex) {
                labelLeftBottom.setText("Error! Course number must be an integer");
                labelLeftBottom.setTextFill(Color.RED);

            }

            catch (Exception exception) {

            }
        } 
    } 
} 