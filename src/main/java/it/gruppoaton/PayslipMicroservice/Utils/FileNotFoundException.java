package it.gruppoaton.PayslipMicroservice.Utils;

public class FileNotFoundException extends Exception {

    public FileNotFoundException(){
        super("Eccezione!!!");
    }

    @Override
    public String toString() {

        return getMessage()+"File non trovato !!!";
    }
}
