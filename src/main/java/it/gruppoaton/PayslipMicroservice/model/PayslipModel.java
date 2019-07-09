package it.gruppoaton.PayslipMicroservice.model;

import it.gruppoaton.PayslipMicroservice.entities.Employee;

public class PayslipModel {

    private String idPayslip;

    private int month;

    private int year;
    private Employee employee;

    public PayslipModel(){

    }
    public PayslipModel(Employee employee,String idPayslip,int month,int year){

        this.employee = employee;
        this.idPayslip = idPayslip;
        this.month = month;
        this.year = year;
    }

    public String getIdPayslip() {
        return idPayslip;
    }

    public void setIdPayslip(String idPayslip) {
        this.idPayslip = idPayslip;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
