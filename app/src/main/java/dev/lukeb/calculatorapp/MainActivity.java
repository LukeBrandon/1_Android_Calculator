package dev.lukeb.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView numberField;
    TextView errorField;
    TextView historyField;
    ArrayList<ExpressionComponent> numberFieldData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberFieldData = new ArrayList<>();
        numberField = findViewById(R.id.numberField);
        errorField = findViewById(R.id.errorField);
        historyField = findViewById(R.id.historyField);
    }

    /*
        * Displays what is in numberFieldData in the numberField
        *       - Done every time a change is made to numberFieldData
     */
    public void display(){
        String toDisplay = "";
        for(int i = 0; i < numberFieldData.size(); i++){
            toDisplay += numberFieldData.get(i).toString();
        }
        numberField.setText(toDisplay);
    }

    /*
        * Displays the expression into the historyField
        *       - Done when '=' is pressed
     */
    public void displayHistory(ArrayList<ExpressionComponent> list){
        String toDisplay = "";
        for(int i = 0; i < list.size(); i++){
            toDisplay += list.get(i).toString();
        }
        historyField.setText(toDisplay);
    }

    /*
        * Handles number button clicks
        *       - Reads the value of the button from the tag on the UI element
        *       - If the last thing in the numberFieldData is a Term, then append to the Term
        *       - If the last thing in the numberFieldData is an Operator, then start a new Term with value from tag
     */
    public void buttonNumberClick(View view){
        clearError();

        String num = view.getTag().toString();

        ExpressionComponent lastThing;

        if(numberFieldData.size() > 0) {
            lastThing = numberFieldData.get(numberFieldData.size() - 1);
        } else {
            lastThing = null;
        }

        if(lastThing != null && lastThing.isTerm() && num.equals(".") && lastThing.contains(".")) {
            errorField.setText("Cannot have two decimals in a term.");
            return;
        }

        if(lastThing == null || lastThing.isOperator()){
                numberFieldData.add(new Term(num));
        } else {
            numberFieldData.get(numberFieldData.size() - 1).append(num);
        }

        display();
    }

    /*
        * This method handles all the button clicks of the operators
        *       - Reads the operator off of the tag on the UI element
        *       - Create operator objects and adds them to the numberFieldData depending on which button in pressed
        *           - Does not create operator if the last ExpressionComponent is another operator or a negative term with no value
     */
    public void buttonOperationClick(View view){
        clearError();

        String operation = view.getTag().toString();

        // First input should not be an operator
        if(numberFieldData.size() == 0) {
            errorField.setText("Expression should not start with an operator");
            return;
        }

        ExpressionComponent previousComponent = numberFieldData.get(numberFieldData.size() - 1);
        if(!previousComponent.isOperator() && !previousComponent.isEmpty() ) {
            if (operation.equals("plus")) {
                numberFieldData.add(new Operator(OperatorType.ADD));
            } else if (operation.equals("subtract")) {
                numberFieldData.add(new Operator(OperatorType.SUBTRACT));
            } else if (operation.equals("multiply")) {
                numberFieldData.add(new Operator(OperatorType.MULTIPLY));
            } else if (operation.equals("divide")) {
                numberFieldData.add(new Operator(OperatorType.DIVIDE));
            } else {
                throw new RuntimeException("Unsupported Operation on buttonOperationClick Callback");
            }
        } else {
            errorField.setText("Cannot put an operator after another operator");
        }
        display();
    }

    /*
      * If there is not any terms in the data then done do anything
      * Checks if the previous component is an operator or term:
      *     - if operator, it is deleted.
      *     - if a term, then it does the backspace operation, which delete the last character or if
      *            its empty and not negative, then it is deleted (should not delete negative empty value).
     */
    public void buttonBackClick(View view){
        if(numberFieldData.size() == 0) { return; }
        // Delete the operator or delete the last number in the term
        ExpressionComponent lastComponent = numberFieldData.get(numberFieldData.size() - 1);
        if(lastComponent.isOperator()){
            numberFieldData.remove(numberFieldData.size() - 1);
        } else {
            Term termLastComponent = (Term) lastComponent;
            termLastComponent.backspace();
            if(termLastComponent.isEmpty() && termLastComponent.negative == false)
                numberFieldData.remove(numberFieldData.size() - 1);
        }
        display();
    }

    /*
        * Clears all of the fields on the screen
     */
    public void buttonClearClick(View view){
        clearError();
        numberFieldData.clear();
        historyField.setText("");
        display();
    }

    /*
        * Handles the negative button
        *       - If last ExpressionComponent is a term, then add new term that is negative
        *       - If last ExpressionComponent is an operate, then start a new term with "-"
     */
    public void buttonPosNegClick(View view){
        clearError();

        if(numberFieldData.size() > 0) {
            ExpressionComponent lastComponent = numberFieldData.get(numberFieldData.size() - 1);
            if (lastComponent.isOperator()) {
                numberFieldData.add(new Term().withNegative(true));
            } else {
                Term prev = (Term) lastComponent;
                prev.negate();
            }
        } else {
            numberFieldData.add(new Term().withNegative(true));
        }
        display();
    }

    /*
        * Handles the equal button
        *       - First ExpressionComponent always a Term, initialize result to first Component
        *       - If error converting the Term to a double then don't do operation and throw error
        *       - Loops through numberFieldData by 2 because every other ExpressionComponent will be a Term then Operator
        *           - Use i-1 as the operator b/c will always be an operator
        *           - Use i as the Term b/c will always be the term and do the operation on the result
     */
    public void buttonEqualsClick(View view){
        clearError();
        if(numberFieldData.size()  > 1){
            Term firstTerm = (Term) numberFieldData.get(0);
            ExpressionComponent lastComponent = numberFieldData.get(numberFieldData.size() - 1);
            double result = firstTerm.toDouble();

            if(lastComponent.isOperator()){
                errorField.setText("Expressions should not end with an Operator");
                return;
            }

            for(int i = 2; i < numberFieldData.size(); i+=2) {
                Operator op = (Operator) numberFieldData.get(i -1);
                Term term = (Term) numberFieldData.get(i);

                Double doubleTerm;
                try {
                    doubleTerm = term.toDouble();
                } catch(Exception e) {
                    errorField.setText("Invalid input");
                    return;
                }
                if (op.isAdd()) {
                    result += doubleTerm;
                } else if (op.isSubtract()) {
                    result -= doubleTerm;
                } else if (op.isMultiply()) {
                    result *= doubleTerm;
                } else if (op.isDivide()) {
                    result /= doubleTerm;
                } else {
                    errorField.setText("Error evaluating the solution, was your input valid?");
                    throw new RuntimeException("Error evaluating the solution");
                }
            }

        displayHistory(numberFieldData);
        numberFieldData.clear();

        DecimalFormat df = new DecimalFormat("#.##########");
        numberFieldData.add(new Term(df.format(result)));

        display();
        } else {
            errorField.setText("There is nothing to evaluate there...");
        }
    }

    /*
        * Clears the error field on the UI
     */
    public void clearError(){
        this.errorField.setText("");
    }

}
