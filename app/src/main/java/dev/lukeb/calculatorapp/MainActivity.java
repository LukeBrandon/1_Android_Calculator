package dev.lukeb.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

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
        numberField = (TextView) findViewById(R.id.numberField);
        errorField = (TextView) findViewById(R.id.errorField);
        historyField = (TextView) findViewById(R.id.historyField);
    }

    public void display(){
        String toDisplay = "";
        for(int i = 0; i < numberFieldData.size(); i++){
            toDisplay += numberFieldData.get(i).toString();
        }
        numberField.setText(toDisplay);
    }

    public void displayHistory(ArrayList<ExpressionComponent> list){
        String toDisplay = "";
        for(int i = 0; i < list.size(); i++){
            toDisplay += list.get(i).toString();
        }
        historyField.setText(toDisplay);
    }

    public void buttonNumberClick(View view){
        clearError();

        // The tag on the UI element contains the value
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

        //If previous ExpressionComponent or nothing, then start a new term
        if(lastThing == null || lastThing.isOperator()){
            numberFieldData.add(new Term(num));
        } else {
            numberFieldData.get(numberFieldData.size() - 1).append(num);
        }

        display();
    }

    // Operations
    public void buttonOperationClick(View view){
        clearError();

        String operation = view.getTag().toString();

        // First input should not be an operator
        if(numberFieldData.size() == 0) {
            errorField.setText("Expression should not start with an operator");
            return;
        }

        ExpressionComponent previousComponent = numberFieldData.get(numberFieldData.size() - 1);
        if(!previousComponent.isOperator()) {
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
      *     - if a term, then it does the backspace operation, which delete the last character.
     */
    public void buttonBackClick(View view){
        if(numberFieldData.size() == 0) { return; }
        // Delete the operator or delete the last number in the term
        ExpressionComponent lastComponent = numberFieldData.get(numberFieldData.size() - 1);
        if(lastComponent.isOperator()){
            numberFieldData.remove(numberFieldData.size() - 1);
        } else {
            lastComponent.backspace();
            if(lastComponent.isEmpty())
                numberFieldData.remove(numberFieldData.size() - 1);
        }
        display();
    }

    public void buttonClearClick(View view){
        clearError();
        numberFieldData.clear();
        historyField.setText("");
        display();
    }

    public void buttonPosNegClick(View view){
        clearError();

        if(numberFieldData.size() > 0) {
            ExpressionComponent lastComponent = numberFieldData.get(numberFieldData.size() - 1);
            if (lastComponent.isOperator()) {
                numberFieldData.add(new Term("-"));
            } else {
                Term prev = (Term) lastComponent;
                prev.negate();
            }
        } else {
            numberFieldData.add(new Term("-"));
        }
        display();
    }

    public void buttonEqualsClick(View view){
        clearError();
        if(numberFieldData.size()  > 1){
            double result = Double.parseDouble(numberFieldData.get(0).toString());

            // Starts at 2 because the first term is always a number, then the next 2 are operator and term and continues
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
        }
    }

    public void clearError(){
        this.errorField.setText("");
    }

}
