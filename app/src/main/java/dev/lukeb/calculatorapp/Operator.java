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

    @Override
    public boolean isOperator(){    return true;    }

    @Override
    public boolean isEmpty(){
        if(this.type == null)
            return true;
        return false;
    }

    boolean isAdd(){
        return  type == OperatorType.ADD;
    }

    boolean isSubtract(){
        return  type == OperatorType.SUBTRACT;
    }

    boolean isMultiply(){
        return  type == OperatorType.MULTIPLY;
    }

    boolean isDivide(){
        return  type == OperatorType.DIVIDE;
    }

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
