package dev.lukeb.calculatorapp;

/*
    * OperatorType defines the different types of Operators that are supported by the calculator
 */
enum OperatorType{
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

/*
    * The Operator class is used to store and manipulate operators that are added to the numberFieldData
 */
public class Operator extends ExpressionComponent{
    OperatorType type;

    public Operator(OperatorType operatorType){
        type = operatorType;
    }

    /*
        * Overrides the isOperator() method from ExpressionComponent that is false by default
     */
    @Override
    public boolean isOperator(){    return true;    }

    /*
        * Evaluates if the Operator object is empty or not
        * Return: Boolean
     */
    @Override
    public boolean isEmpty(){
        if(this.type == null)
            return true;
        return false;
    }

    /*
        * The following 'is' methods return if the Operator is of that type or not
        * Return: Boolean
     */
    public boolean isAdd(){
        return  type == OperatorType.ADD;
    }

    public boolean isSubtract(){
        return  type == OperatorType.SUBTRACT;
    }

    public boolean isMultiply(){
        return  type == OperatorType.MULTIPLY;
    }

    public boolean isDivide(){
        return  type == OperatorType.DIVIDE;
    }

    /*
        * Returns the String value of each operator
        * Return: String
     */
    public String toString(){
        if(this.isAdd()){
            return "+";
        } else if (this.isSubtract()) {
            return "-";
        } else if (this.isMultiply()) {
            return "*";
        } else if (this.isDivide()) {
            return "/";
        } else {
            return "?";
        }
    }

}
