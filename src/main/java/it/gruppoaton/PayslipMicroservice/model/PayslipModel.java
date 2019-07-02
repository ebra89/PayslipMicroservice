package it.gruppoaton.PayslipMicroservice.model;

import java.util.Arrays;


public class PayslipModel {
	

	private String id;
	private String fiscalCode;
	private int month;
	private int year;
	private byte[] payslipPdf;
	
	public PayslipModel() {
		super();
	}

	public PayslipModel(String id, String fiscalCode, int month, int year, byte[] payslipPdf) {
		super();
		this.id = id;
		this.fiscalCode = fiscalCode;
		this.month = month;
		this.year = year;
		this.payslipPdf = payslipPdf;
	}

	@Override
	public String toString() {
		return "PayslipModel [id=" + id + ", fiscalCode=" + fiscalCode + ", month=" + month + ", year=" + year
				+ ", payslipPdf=" + Arrays.toString(payslipPdf) + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
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

	public byte[] getPayslipPdf() {
		return payslipPdf;
	}

	public void setPayslipPdf(byte[] payslipPdf) {
		this.payslipPdf = payslipPdf;
	}


}
