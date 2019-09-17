package dev.lukeb.calculatorapp;

/*
    * The Term class is used to store and operate on terms that are added to numberFieldData
 */
public class Term extends ExpressionComponent {

    public String value;

    public Term(String val){
        value = val;
    }

    @Override
    public boolean isTerm(){    return true;    }

    @Override
    public boolean isEmpty(){
        if(this.value.length() == 0 || this.value.equals("-"))
            return true;
        return false;
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

    @Override
    public void append(String toAppend){
        this.value += toAppend;
    }

    public String toString(){
        return value;
    }

    public Double toDouble(){
        return Double.parseDouble(this.value);
    }

    public void negate(){
        if (value.charAt(0) == '-') {
            this.value = this.value.substring(1);
        } else {
            this.value = "-" + this.value;
        }
    }

}
