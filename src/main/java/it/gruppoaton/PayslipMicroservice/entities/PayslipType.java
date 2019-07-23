package it.gruppoaton.PayslipMicroservice.entities;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PayslipType {
    @Id
    @GeneratedValue
    private int typeId;
    private String nome;
    @ManyToOne
    private Payslip payslip;

    public PayslipType() {
    }

    public PayslipType(int typeId, String nome) {
        this.typeId = typeId;
        this.nome = nome;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

