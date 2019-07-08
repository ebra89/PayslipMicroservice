package it.gruppoaton.PayslipMicroservice.entities;

import javax.persistence.*;

@Entity
public class Payslip {

    @Id
    @GeneratedValue
    private int idPayslip;

    @Lob
    private byte[] payslipPdf;

    private int month;

    private int year;
    private String typeFile;

    @ManyToOne
    private Employee employee;



    public Payslip() {
    }

    public Payslip(byte[] payslipPdf, int month, int year, Employee employee) {
        this.payslipPdf = payslipPdf;
        this.month = month;
        this.year = year;
        this.employee = employee;
        this.typeFile = "pdf";
    }


    public int getIdPayslip() {
        return idPayslip;
    }

    public void setIdPayslip(int idPayslip) {
        this.idPayslip = idPayslip;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public byte[] getPayslipPdf() {
        return payslipPdf;
    }

    public void setPayslipPdf(byte[] payslipPdf) {
        this.payslipPdf = payslipPdf;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }
}
