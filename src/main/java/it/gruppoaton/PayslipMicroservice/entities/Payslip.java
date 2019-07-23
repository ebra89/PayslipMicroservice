package it.gruppoaton.PayslipMicroservice.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Payslip {

    @Id
    @GeneratedValue
    private int idPayslip;

    @Lob
    private byte[] payslipPdf;

    private int month;

    private int year;

    private int version;

    private String type;

    private String typeFile;

    @ManyToOne
    private Employee employee;



    public Payslip() {
    }

    public Payslip(byte[] payslipPdf, int month, int year, Employee employee, int version, String type) {
        this.payslipPdf = payslipPdf;
        this.month = month;
        this.year = year;
        this.employee = employee;
        this.version = version;
        this.type = type;
        this.typeFile = ".pdf";


    }

    public Payslip(int idPayslip, byte[] payslipPdf, int month, int year, int version, String type, String typeFile, Employee employee) {
        this.idPayslip = idPayslip;
        this.payslipPdf = payslipPdf;
        this.month = month;
        this.year = year;
        this.version = version;
        this.type = type;
        this.typeFile = typeFile;
        this.employee = employee;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
