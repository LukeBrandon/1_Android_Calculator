package dev.lukeb.calculatorapp;

import java.text.DecimalFormat;

/*
    * The Term class is used to store and operate on terms that are added to numberFieldData
 */
public class Term extends ExpressionComponent {

    public String value;
    public boolean negative;

    /*
        * Constructor that initializes the value of the Term
        *       - Should not be able to have term of value "." because it causes Double.parseDouble() to break in the toString() method
     */
    public Term(String val){
        if(val.equals("."))
            this.value = "0.";
        else
            this.value = val;
    }

    /*
        * Default constructor, initializes a term to empty string
     */
    public Term(){ this.value = "";}

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
        return (this.value.isEmpty());
    }

    /*
        * Performs a backspace
        *   - If length of value is 0 and is negative, then set negative to false, this effectively
        *          will backspace the negative sign that shows up when toString()
        *   - If value is not empty Resets the value of the Term to be the previous value of the
        *          Term without the last character
     */
    @Override
    public void backspace(){
        if(this.negative && this.value.isEmpty())
            this.negative = false;
        if(!this.value.isEmpty())
            this.value = this.value.substring(0, value.length()-1);
    }

    /*
        * Returns if the Term contains a character or not
        * Return: Boolean
     */
    @Override
    public boolean contains(String toCheck){
        return(this.value.contains(toCheck));
    }

    /*
        * Appends a character onto the Term
     */
    @Override
    public void append(String toAppend){
        this.value += toAppend;
    }

    /*
        * Sets the negative member variable
        * Return: this (this allows the withNegative() method to be called inline)
     */
    public Term withNegative(boolean neg){
        this.negative = neg;
        return this;
    }

    /*
        * Returns the string value of the Term
        * Number is displayed in scientific notation if needed
        * Return: String, either in scientific notation or not
     */
    public String toString(){

        if(!this.value.isEmpty() && (Double.parseDouble(this.value) > 10000000.0 || (Double.parseDouble(this.value) < 0.000001 && Double.parseDouble(this.value) > 0.0))){
            DecimalFormat scientific = new DecimalFormat("0.#######E0");

            if(this.negative){
                return "-" + scientific.format(this.toDouble());
            } else {
                return scientific.format(this.toDouble());
            }
        }

        if(this.negative)
            return "-" + value;
        else
            return value;
    }

    /*
        * Returns the double value of the Term
        * Return: Double
     */
    public Double toDouble(){
        if(this.negative)
            return Double.parseDouble("-" + this.value);
        else
            return Double.parseDouble(this.value);
    }

    /*
        * Toggles the negative property of the Term
        *   - If negative, makes positive
        *   - If positive, makes negative
     */
    public void negate(){
        if (this.negative)
            this.negative = false;
        else
            this.negative = true;
    }

}
