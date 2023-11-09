//******************************************************************************************
// ChangingCircle.java
//
// Josh Radford T00745233, Nov 3, 2023
//
// COMP 1231 Assignment 5 Question 1
// 
// This class implements a JavaFX application that allows the user to change the color of a 
// displayed circle by selecting a color out of three options provided by a set of radio 
// buttons. The application also comes with a slider that controls the size of the circle. 
//******************************************************************************************

package com.assignment5;

import java.util.Objects;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ChangingCircle extends Application 
{
    private RadioButton orangeButton, yellowButton, blueButton;
    private static final Color defaultColor = Color.ORANGE;
    private static final int defaultRadius = 35;
    private static final double scale = 0.12;
    private static final String error_sound = "wrong-answer.mp3";
    private Circle circle;
    private Slider circleSlider;
    private AudioClip clip;

    public void start(Stage primaryStage)
    {
        // Creating the initial group and setting the scene with fixed sizing and background color
        Group root = new Group();
        Scene scene = new Scene(root, 400, 350, Color.LEMONCHIFFON);

        // Creates a new AudioClip to be used as sound when clicked on GUI
        clip = new AudioClip(Objects.requireNonNull(ChangingCircle.class.getResource(error_sound)).toExternalForm());
        // Event handler for processing mouse press to play sound
        scene.setOnMousePressed(this::processMousePress);

        // Setting up the instruction text styles & alignment
        Text instructionText = new Text("Change the circle color using the radio buttons\nChange the scale of the circle between 0-100% using the slider");
        Font instructionFont = Font.font("CASPIAN", FontWeight.BOLD, 12);
        instructionText.setTextAlignment(TextAlignment.CENTER);
        instructionText.setFont(instructionFont);
        FlowPane instructions = new FlowPane(instructionText);
        instructions.setAlignment(Pos.CENTER);
        instructions.setLayoutY(20);
        // Adding the instructions to the root Group
        root.getChildren().add(instructions);


        // Setting up the radio buttons for changing color
        ToggleGroup colorGroup = new ToggleGroup();
        // Creating the orange, yellow, and blue buttons with event handler call
        orangeButton = new RadioButton("Orange");
        orangeButton.setSelected(true);
        orangeButton.setToggleGroup(colorGroup);
        orangeButton.setOnAction(this::processRadioButton);
        yellowButton = new RadioButton("Yellow");
        yellowButton.setToggleGroup(colorGroup);
        yellowButton.setOnAction(this::processRadioButton);
        blueButton = new RadioButton("Blue");
        blueButton.setToggleGroup(colorGroup);
        blueButton.setOnAction(this::processRadioButton);
        // Adding all the bottons together into a vertical box, spacing them vertically
        VBox options = new VBox(orangeButton, yellowButton, blueButton);
        options.setSpacing(10);

        // Creates a new circle and sets the circle defaults (color and radius)
        circle = new Circle();
        circle.setFill(defaultColor);
        circle.setRadius(defaultRadius);

        // Creating a horizontal box that aligns the radio buttons and the circle
        HBox circleLayout = new HBox(options, circle);
        circleLayout.setLayoutX(75);
        circleLayout.setLayoutY(100);
        circleLayout.setSpacing(75);


        // Adding the radio buttons and circle to the root Group
        root.getChildren().add(circleLayout);

        // Creates a new slider and sets its display properties
        circleSlider = new Slider(0, 100, 25);
        // Adding a change listener to manage scaling of the circle
        circleSlider.valueProperty().addListener(this::processResize);
        circleSlider.setShowTickLabels(true);
        circleSlider.setShowTickMarks(true);
        circleSlider.setSnapToTicks(true);
        circleSlider.setOrientation(Orientation.HORIZONTAL);
        circleSlider.setPrefWidth(350);
        circleSlider.setLayoutX(25);
        circleSlider.setLayoutY(220);
        // Adding the circles slider to the root Group
        root.getChildren().add(circleSlider);

         // Setting up the red warning text at the bottom of the GUI
        Text warningText = new Text("Select the radio buttons or slider only.\nYou'll hear a warning sound if the mouse is clicked elsewhere!");
        Font warningFont = Font.font("CASPIAN", 12);
        warningText.setTextAlignment(TextAlignment.CENTER);
        warningText.setFont(warningFont);
        warningText.setFill(Color.RED);
        FlowPane warning = new FlowPane(warningText);
        warning.setAlignment(Pos.CENTER);
        warning.setLayoutY(280);
        // Adding the warning text to the root Group
        root.getChildren().add(warning);

        // Setting up the GUI's title and running it
        primaryStage.setTitle("Changing Circle");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }


    // Event handler method for processing the button color when a radio option is selected
    public void processRadioButton(ActionEvent event)
    {
        if (orangeButton.isSelected())
        {
            circle.setFill(Color.ORANGE);
        }
        else if (yellowButton.isSelected())
        {
            circle.setFill(Color.YELLOW);
        }
        else
        {
            circle.setFill(Color.BLUE);
        }
    }


    // Change listener for resizing the cirle, which constrains the scale of the circle by the global scale varibale
    public void processResize(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
    {
        circle.setRadius(defaultRadius + circleSlider.getValue() * scale);
    }


    // Event handler for playing a sound when the user clicks anywhere on the GUI
    public void processMousePress(MouseEvent event)
    {
        //Plays the previously initialized audio clip
        clip.play();
    }
}