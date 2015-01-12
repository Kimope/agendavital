/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

/**
 *
 * @author ramon
 */
public class UtilidadesNoticia {

    public static String formatearFecha(String _fecha) throws java.text.ParseException {
        String DMA = _fecha.substring(5, 16);
        DMA = DMA.replace(" ", "-");
        if (DMA.contains("Jan")) {
            DMA = DMA.replace("Jan", "01");
        } else if (DMA.contains("Feb")) {
            DMA = DMA.replace("Feb", "02");
        } else if (DMA.contains("Mar")) {
            DMA = DMA.replace("Mar", "03");
        } else if (DMA.contains("Apr")) {
            DMA = DMA.replace("Apr", "04");
        } else if (DMA.contains("May")) {
            DMA = DMA.replace("May", "05");
        } else if (DMA.contains("Jun")) {
            DMA = DMA.replace("Jun", "06");
        } else if (DMA.contains("Jul")) {
            DMA = DMA.replace("Jul", "07");
        } else if (DMA.contains("Aug")) {
            DMA = DMA.replace("Aug", "08");
        } else if (DMA.contains("Sep")) {
            DMA = DMA.replace("Sep", "09");
        } else if (DMA.contains("Oct")) {
            DMA = DMA.replace("Oct", "10");
        } else if (DMA.contains("Nov")) {
            DMA = DMA.replace("Nov", "11");
        } else if (DMA.contains("Dec")) {
            DMA = DMA.replace("Dec", "12");
        }
        return DMA;
    }
}
