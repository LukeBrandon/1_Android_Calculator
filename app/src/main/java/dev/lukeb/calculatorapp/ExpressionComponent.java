package dev.lukeb.calculatorapp;

/*
    * The ExpressionComponent class is an abstract class that Term and Operator both inherit form
    *       - Includes methods that both Terms and Operators need and their default values
 */
public abstract class ExpressionComponent {

    public abstract String toString();

    public boolean isTerm(){   return false;   }

    public boolean isOperator(){    return false;   }

    public boolean isEmpty(){   return true;    }

    public boolean contains(String toCheck){  return false;   }

    public void append(String toAppend){ throw new RuntimeException("Cannot append to operations"); }

    public void backspace(){  throw new RuntimeException("Backspace is not supported on this object"); }

}
