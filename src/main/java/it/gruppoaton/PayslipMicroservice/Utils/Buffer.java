package it.gruppoaton.PayslipMicroservice.Utils;

import it.gruppoaton.PayslipMicroservice.model.Email;

import java.util.LinkedList;

public class Buffer {

    LinkedList<Email> coda= new LinkedList<>();
    int sizeMax;

    public Buffer(int sizeMax) {
        this.sizeMax = sizeMax;
    }

    public boolean isFull(){

        return coda.size()>=sizeMax;
    }

    public boolean isEmpty(){
        return  coda.isEmpty();
    }

    public synchronized Email takeEmail(){

        while(isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Email email = coda.removeFirst();
        notifyAll();
        return email;

    }
    public synchronized void putEmail(Email email){
        while(isFull()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        coda.addLast(email);
        notifyAll();
    }
}
