package it.gruppoaton.PayslipMicroservice.Utils;

import it.gruppoaton.PayslipMicroservice.model.Email;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {

    Queue<Email> coda;
    int sizeMax;

    public Buffer(int sizeMax){
        coda = new LinkedList<>();
        this.sizeMax = sizeMax;
    }

    public boolean isEmpty(){
        return coda.isEmpty();
    }

    public boolean isFull(){
        return coda.size() >= sizeMax;
    }

    public synchronized Email getEmail(Email emial){
        while (isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Email email = coda.remove();
        notifyAll();
        return email;
    }

    public synchronized void addEmail(Email email){
        while (isFull()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        coda.add(email);
        notifyAll();
    }
}
