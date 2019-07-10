package it.gruppoaton.PayslipMicroservice.Utils;

import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

public class Validator {

    @Autowired
    private EmployeeService employeeService;

     public String fiscalCodeValidator(String cf) {
        int i, s, c;
        String cf2;
        int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
                11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
        if( cf.length() == 0 ) return "";
        if( cf.length() != 16 )
            return "La lunghezza del codice fiscale non &egrave;\n"
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
        return "";
    }

    public boolean PayslipFileNameValidator(String payslipFileName){



        String[] tokens = StringUtils.split(payslipFileName,"_-.");

        // controlla mese e anno
        int n=0;
        int month=0;
        int year=0;
        LinkedList<Integer> p=new LinkedList<>();
        for(String s : tokens){
            try {
                p.add(Integer.parseInt(s));
            }catch (Exception ex){
                System.out.println(ex.getStackTrace());
            }
            n++;
        }
        month =p.get(0);
        year = p.get(1);


        //controllo codice fiscale
        LinkedList<String> s=new LinkedList<>();
        for(String string : tokens){
                s.add(string);
        }
        if(!fiscalCodeValidator(s.getLast()).isEmpty()){return false;}

    return true;


    }
}
