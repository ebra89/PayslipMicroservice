package it.gruppoaton.PayslipMicroservice.Utils;

public class NumberFormatException extends Exception {

    public NumberFormatException(){
        super("Eccezione!!!");
    }

    @Override
    public String toString() {
        return getMessage()+"Nome del file non conferme !!!";
    }
}
