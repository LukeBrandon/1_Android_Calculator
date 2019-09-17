package dev.lukeb.calculatorapp;

enum OperatorType{
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

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
        if(isAdd()){
            return "+";
        } else if (isSubtract()) {
            return "-";
        } else if (isMultiply()) {
            return "*";
        } else if (isDivide()) {
            return "/";
        } else {
            return "?";
        }
    }

    public void append(int toAppend){
        throw new RuntimeException("Cannot append to operations");
    }

}
