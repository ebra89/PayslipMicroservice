package it.gruppoaton.PayslipMicroservice.model;

import it.gruppoaton.PayslipMicroservice.Utils.EntityModelConverter;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PayslipConverter implements EntityModelConverter<Payslip, PayslipModel> {

    @Override
    public Payslip fromViewModel(PayslipModel payslipModel) {

        return null;
    }

    @Override
    public List<Payslip> fromViewModelList(List<PayslipModel> payslipModels) {
        return null;
    }

    @Override
    public PayslipModel toViewModel(Payslip payslip) {

        PayslipModel payslipModel =new PayslipModel();
        payslipModel.setEmployee(payslip.getEmployee());
        payslipModel.setYear(payslip.getYear());
        payslipModel.setMonth(payslip.getMonth());
        payslipModel.setIdPayslip(String.valueOf(payslip.getIdPayslip()));
        return payslipModel;
    }

    @Override
    public List<PayslipModel> toViewModelList(List<Payslip> payslips) {
        List<PayslipModel> payslipModels=new ArrayList<>();
        for (Payslip payslip:payslips) {
            PayslipModel payslipModel =new PayslipModel();
            payslipModel.setEmployee(payslip.getEmployee());
            payslipModel.setYear(payslip.getYear());
            payslipModel.setMonth(payslip.getMonth());
            payslipModel.setIdPayslip(String.valueOf(payslip.getIdPayslip()));
            payslipModels.add(payslipModel);
        }
        return payslipModels;
    }
}
