package dev.lukeb.calculatorapp;

public class Term extends ExpressionComponent {

    public String value;

    public Term(String val){
        value = val;
    }

    @Override
    public boolean isTerm(){    return true;    }

    @Override
    public boolean isEmpty(){
        if(this.value.length() == 0)
            return true;
        return false;
    }


    public String toString(){
        return value;
    }

    public Double toDouble(){
        return Double.parseDouble(this.value);
    }

    @Override
    public void append(String toAppend){
        this.value += toAppend;
    }

    public void negate(){
        if (value.charAt(0) == '-') {
            this.value = this.value.substring(1);
        } else {
            this.value = "-" + this.value;
        }
    }

    @Override
    public void backspace(){
        System.out.println("backspace method called");
        this.value = this.value.substring(0, value.length()-1);
    }

    @Override
    public boolean contains(String toCheck){
        if(this.value.contains(toCheck))
            return true;
        return false;
    }

}
