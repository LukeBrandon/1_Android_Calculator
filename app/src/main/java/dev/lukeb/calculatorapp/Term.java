package dev.lukeb.calculatorapp;

/*
    * The Term class is used to store and operate on terms that are added to numberFieldData
 */
public class Term extends ExpressionComponent {

    public String value;

    public Term(String val){
        value = val;
    }

    /*
        * Overrides the isTerm() method on ExpressionComponent that returns false by default
     */
    @Override
    public boolean isTerm(){    return true;    }

    /*
        * Returns if the value of the Term object is empty or not (just a negative is empty)
        * Return: Boolean
     */
    @Override
    public boolean isEmpty(){
        if(this.value.length() == 0 || this.value.equals("-"))
            return true;
        return false;
    }

    /*
        * Performs a backspace
        *   - Resets the value of the Term to be the previous value of the Term without the last character
     */
    @Override
    public void backspace(){
        System.out.println("backspace method called");
        this.value = this.value.substring(0, value.length()-1);
    }

    /*
        * Returns if the Term contains a character or not
        * Return: Boolean
     */
    @Override
    public boolean contains(String toCheck){
        if(this.value.contains(toCheck))
            return true;
        return false;
    }

    /*
        * Appends a character onto the Term
     */
    @Override
    public void append(String toAppend){
        this.value += toAppend;
    }

    /*
        * Returns the string value of the Term
        * Return: String
     */
    public String toString(){
        return value;
    }

    /*
        * Returns the double value of the Term
        * Return: Double
     */
    public Double toDouble(){
        return Double.parseDouble(this.value);
    }

    /*
        * Toggles the negative property of the Term
        *   - If negative, makes positive
        *   - If positive, makes negative
     */
    public void negate(){
        if (value.charAt(0) == '-') {
            this.value = this.value.substring(1);
        } else {
            this.value = "-" + this.value;
        }
    }

}
