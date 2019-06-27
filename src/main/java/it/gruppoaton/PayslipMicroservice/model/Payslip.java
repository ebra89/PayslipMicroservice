package it.gruppoaton.PayslipMicroservice.model;

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payslip {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private String id;
	@Column
	private String fiscalCode;
	@Column
	private int month;
	@Column
	private int year;
	@Column
	private byte[] payslipPdf;
	
	public Payslip() {
		super();
	}

	public Payslip(String id, String fiscalCode, int month, int year, byte[] payslipPdf) {
		super();
		this.id = id;
		this.fiscalCode = fiscalCode;
		this.month = month;
		this.year = year;
		this.payslipPdf = payslipPdf;
	}

	@Override
	public String toString() {
		return "Payslip [id=" + id + ", fiscalCode=" + fiscalCode + ", month=" + month + ", year=" + year
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fiscalCode == null) ? 0 : fiscalCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + month;
		result = prime * result + Arrays.hashCode(payslipPdf);
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payslip other = (Payslip) obj;
		if (fiscalCode == null) {
			if (other.fiscalCode != null)
				return false;
		} else if (!fiscalCode.equals(other.fiscalCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (month != other.month)
			return false;
		if (!Arrays.equals(payslipPdf, other.payslipPdf))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	

}
