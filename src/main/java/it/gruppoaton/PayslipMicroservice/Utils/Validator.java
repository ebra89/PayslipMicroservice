package it.gruppoaton.PayslipMicroservice.Utils;

import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
@Component
public class Validator {

    @Autowired
    private EmployeeService employeeService;

     public static String fiscalCodeValidator(String cf) {
        int i, s, c;
        String cf2;
        int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
                11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
        if( cf.length() == 0 ) return "erroe codice fiscale";
        if( cf.length() != 16 )
            return "La lunghezza del codice fiscale non è;\n"
                    + "corretta: il codice fiscale dovrebbe essere lungo\n"
                    + "esattamente 16 caratteri.";
        cf2 = cf.toUpperCase();
        for( i=0; i<16; i++ ){
            c = cf2.charAt(i);
            if( ! ( c>='0' && c<='9' || c>='A' && c<='Z' ) )
                return "Il codice fiscale contiene dei caratteri non validi:\n"
                        + "i soli caratteri validi sono le lettere e le cifre.";
        }
        s = 0;
        for( i=1; i<=13; i+=2 ){
            c = cf2.charAt(i);
            if( c>='0' && c<='9' )
                s = s + c - '0';
            else
                s = s + c - 'A';
        }
        for( i=0; i<=14; i+=2 ){
            c = cf2.charAt(i);
            if( c>='0' && c<='9' )	 c = c - '0' + 'A';
            s = s + setdisp[c - 'A'];
        }
        if( s%26 + 'A' != cf2.charAt(15) )
            return "Il codice fiscale non &egrave; corretto:\n"
                    + "il codice di controllo non corrisponde.";
        return "ok";
    }

    public boolean PayslipFileNameValidator(String payslipFileName){



        String[] tokens = StringUtils.split(payslipFileName,"_-.");

        // controlla mese e anno
        Integer month=0;
        Integer year=0;
        LinkedList<Integer> p = new LinkedList<>();
        for(String s : tokens){
            try {
                p.add(Integer.parseInt(s));
            }catch (Exception ex){}
        }
        month =p.get(0);
        if (month > 12 && month < 1){System.out.println("errore formato mese "+month); return false;

        }

        year = p.get(1);
        if (year.toString().length()<4 ||year.toString().length()>4){System.out.println("errore formato anno "+year);return false;}

        //controllo codice fiscale
        LinkedList<String> s=new LinkedList<>();
        for(String string : tokens){
                s.add(string);
        }
        String fiscalCode= s.get(s.size()-2);
        if(!(fiscalCodeValidator(fiscalCode).equals("ok"))){
            System.out.println(fiscalCodeValidator(fiscalCode));
            System.out.println(fiscalCode);
            return false;}
        if((employeeService.findByFc(fiscalCode))==null){System.out.println("employee con codice fiscale "+fiscalCode+" inesistente"); return false;}

        if(!(s.getLast().toUpperCase().equals("PDF"))){System.out.println("estensione del file " + s.getLast()); return false;}
    return true;
   }
}
