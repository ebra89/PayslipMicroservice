package it.gruppoaton.PayslipMicroservice.entities;

public class Payslip {

    private int idPayslip;
    private Employee employee;
    private byte payslipPdf;
    private int month;
    private int year;

    public Payslip() {
    }

    public Payslip(int idPayslip, Employee employee, byte payslipPdf, int month, int year) {
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

    public byte getPayslipPdf() {
        return payslipPdf;
    }

    public void setPayslipPdf(byte payslipPdf) {
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
