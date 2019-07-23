package it.gruppoaton.PayslipMicroservice.Utils;

import it.gruppoaton.PayslipMicroservice.model.Email;

import java.util.LinkedList;
import java.util.Queue;

public class BufferEmail {

    private Queue<Email> coda;
    private int sizeMax ;
    private long delay;

    public BufferEmail(int sizeMax, long delay){
        coda = new LinkedList<>();
        this.sizeMax =sizeMax;
        this.delay=delay;
    }


    public Boolean isEmpty(){
        return coda.isEmpty();

    }

    public Boolean isFull(){
       return coda.size()>=sizeMax;
    }

    public synchronized Email  getEmail(){
        while (isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("errore!!!" + e.getMessage());
            }
        }
        System.out.println(" dimensione coda dal metodo get mail "+coda.size());
        for (Email e: coda) {
            System.out.println("emial nel buffer "+e.getBody());
        }
        Email email = coda.remove();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
        return email;
    }

    public synchronized void putEmail(Email email){
        System.out.println("dimensione coda dal metodo putEmail(): " + coda.size());
        while (isFull()){
            try {

                wait();
            } catch (InterruptedException e) {
                System.err.println("errore!!!");
            }
        }
        coda.add(email);
        notifyAll();
    }
}
