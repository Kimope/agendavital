package agendavital.vista;

import javafx.scene.control.TextField;

/**
 * @author josesoler
 */
public class FormValidation 
{
    public static boolean textFieldEmpty(TextField i)
    {
        boolean r =true;
        
        if(i.getText().isEmpty())
        {
            r=false;
            i.setStyle("-fx-background-color:#eb7264");
        }
        else
        {
            i.setStyle("-fx-background-color: #FCF0CC");
        }
        return r;
    }
    public static boolean textFieldvacio_onumero(TextField i)
    {
        boolean r =false;
        
        if(i.getText().matches("^[a-zA-Z]*$"))
        {
            r=true;
            i.setStyle("-fx-background-color:#eb7264");
        }
        else
        {
            i.setStyle("-fx-background-color: #FCF0CC");
        }
        return r;
    }
}