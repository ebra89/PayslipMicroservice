package it.gruppoaton.PayslipMicroservice.model;

import it.gruppoaton.PayslipMicroservice.entities.Employee;

public class Email {

    private Employee employee;
    private String subject;
    private String body;

    public Email(Employee employee, String subject, String body) {
        this.employee = employee;
        this.subject = subject;
        this.body = body;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
