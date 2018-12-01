import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private TextField readOut;
    private Double x, y, z;
    private String flag;
    private GridPane myGrid;
    private ArrayList<Button> opButtons, numButtons;
    private Button clear;
    boolean decimaled;

    @Override
    public void start(Stage primaryStage) {
        decimaled = false;


        //make window
        primaryStage.setTitle("SimpleCalculator2.0");
        primaryStage.setResizable(false);
        primaryStage.setMaxWidth(250);
        primaryStage.setMaxHeight(250);
        // layOut type
        myGrid = new GridPane();
        myGrid.setPadding(new Insets(5));
        myGrid.setHgap(10);
        myGrid.setVgap(10);
        myGrid.setAlignment(Pos.CENTER);
        myGrid.setGridLinesVisible(false);
        myGrid.setPrefSize(245, 245);
        myGrid.setMaxSize(245, 245);
        // now for children......
        readOut = new TextField();
        readOut.setEditable(false);
        readOut.setPromptText("SimpleCalc 2.0");
        myGrid.add(readOut, 0, 0);
        myGrid.setColumnSpan(readOut, 4);


//start making nodes (in loop if possible)

        // loop For Numbers
        numButtons = new ArrayList<>();
        for (Integer number = 0; number < 10; number++) {
            //String buttonName = number.toString();
            Button myButton = new Button(number.toString());
            myButton.setAlignment(Pos.CENTER);
            myButton.setPrefSize(35, 35);
            myButton.setOnAction(click -> {
                readOut.appendText(myButton.getText());
            });
            numButtons.add(myButton);
        }

// Operation Buttons
        opButtons = new ArrayList<>();
        String names = "0 . = - + / x"; // To form loop.
        String[] buttonNames = names.split(" ");
        for (int i = 0; i < 7; i++) {
            Button myButton = new Button(buttonNames[i]);
            myButton.setAlignment(Pos.CENTER);
            myButton.setPrefSize(35, 35);
            opButtons.add(myButton);
            if (i > 2) {
                int res = i;
                myButton.setOnAction(click -> {
                    operator(res);
                });
            }

        }

        opButtons.get(1).setOnAction(click -> {
            readOut.appendText(opButtons.get(1).getText());
            opButtons.get(1).setDisable(true);
        });

        opButtons.get(0).setOnAction(click -> {
            readOut.appendText(opButtons.get(0).getText());
        });


        opButtons.get(2).setOnAction(click -> { // for multiple presses of the equals button consider flag and loop process.
            equals();
        });

        for (int i = 1; i < 10; i++) { // ninth element is nine
            int row = ((i - 1) / 3); // WHY division by three?  // discuss placement
            int col = (i - 1) % 3; // WHY operand three?
            myGrid.add(numButtons.get(i), col, 3 - row);
        }

        for (int a = 0; a < 7; a++) {
            if (a < 4) {
                //int x = a;
                int y = 4;
                myGrid.add(opButtons.get(a), a, y);
            } else if (a > 3) {
                int x = 3;
                int y = a - 4;
                myGrid.add(opButtons.get(a), x, 3 - y);
            }
        }

        //keyboard action..........
        clear = new Button("C");
        myGrid.add(clear, 0, 5);
        clear.setPrefSize(70, 35);
        myGrid.setColumnSpan(clear, 4);
        clear.setOnAction(click -> {
                    readOut.clear();
                    opButtons.get(1).setDisable(false);
                }
        );

        // myGrid.setOnKeyPressed(key-> System.out.println(key.getCode()));

        myGrid.setOnKeyPressed(onPress -> {
            String qux = onPress.getText();
            for (int i = 0; i < qux.length(); i++) {
                if (!Character.isDigit(qux.charAt(i)) && (qux.compareTo("delete") != 0 || qux.compareTo(".") != 0 || qux.compareTo("BACK_SPACE") != 0)) {
                    onPress.consume();
                }
            }
            for (Integer i = 0; i < 10; i++) {
                if (onPress.getText().compareTo(numButtons.get(i).getText()) == 0) {
                    Integer x = Integer.valueOf(onPress.getText());
                    Button res = numButtons.get(x);
                    res.arm();
                    readOut.appendText(onPress.getText());
                }
            }
            if (onPress.getText().compareTo("0") == 0) {
                opButtons.get(0).arm();
            } else if (onPress.getCode().toString().compareTo("DELETE") == 0) {
                readOut.clear();
                opButtons.get(1).setDisable(false);
                clear.arm();
                decimaled = false;
            } else if (onPress.getText().compareTo(".") == 0) {// 0 . = - + / x
                    if(!decimaled){
                        readOut.appendText(".");
                        opButtons.get(1).setDisable(true);
                        decimaled = true;
                    }
                    if(decimaled = true){
                        onPress.consume();
                    }

            } else if (onPress.getText().compareTo("=") == 0 && onPress.getText().compareTo("+") != 0) {
                equals();
                decimaled = true;
            } else if (onPress.getCode().toString().compareTo("BACK_SPACE") == 0) {
                readOut.deletePreviousChar();
            }
            switch (qux) {
                case "-":
                    operator(3);
                    break;
                case "+":
                    operator(4);
                    break;
                case "/":
                    operator(5);
                    break;
                case "*":
                    operator(6);
                    break;
            }
        });

        myGrid.setOnKeyReleased(offPress -> {
            String qux = offPress.getText();
            for (int i = 0; i < qux.length(); i++) {
                if (!Character.isDigit(qux.charAt(i)) && qux.compareTo("delete") != 0) {
                    offPress.consume();
                }
            }
            for (Integer i = 0; i < 10; i++) {
                if (offPress.getText().compareTo(numButtons.get(i).getText()) == 0) {
                    Integer x = Integer.valueOf(offPress.getText());
                    Button res = numButtons.get(x);
                    res.disarm();
                }
            }
            for (Integer i = 0; i < 7; i++) {
                if (offPress.getText().compareTo("0") == 0) {
                    opButtons.get(0).disarm();
                } else if (offPress.getCode().toString().compareTo("DELETE") == 0) {
                    readOut.clear();
                    clear.disarm();
                }
            }
        });
        //and to make a scene....
        Scene myScene = new Scene(myGrid);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void equals() {
        y = y.valueOf(readOut.getText());// The switch of the Phoenix
        switch (flag) {
            case "subtraction":
                z = x - y;
                break;
            case "addition":
                z = x + y;
                break;
            case "division":
                z = x / y;
                break;
            case "multiplication":
                z = x * y;
                break;
            default:
                readOut.setText(readOut.getText());
        }
        readOut.setText(z.toString());
    }

    public void operator(int i) {
        x = x.valueOf(readOut.getText());
        readOut.clear();
        opButtons.get(1).setDisable(false);
        decimaled = false;
        switch (i) {
            case 3:
                flag = "subtraction";
                break;
            case 4:
                flag = "addition";
                break;
            case 5:
                flag = "division";
                break;
            case 6:
                flag = "multiplication";
                break;
            default:
                readOut.setText("oops");
        }
    }
}

o
