package it.gruppoaton.PayslipMicroservice.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Payslip {

    @Id
    @GeneratedValue
    private int idPayslip;

    @Lob
    private byte[] payslipPdf;
    @NotEmpty
    private int month;
    @NotEmpty
    private int year;

    @ManyToOne
    private Employee employee;



    public Payslip() {
    }

    public Payslip(byte[] payslipPdf, int month, int year, Employee employee) {
        this.payslipPdf = payslipPdf;
        this.month = month;
        this.year = year;
        this.employee = employee;
    }

    public Payslip(int idPayslip, Employee employee, byte[] payslipPdf, int month, int year) {
        this.idPayslip = idPayslip;
        this.employee = employee;
        this.payslipPdf = payslipPdf;
        this.month = month;
        this.year = year;
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
}
