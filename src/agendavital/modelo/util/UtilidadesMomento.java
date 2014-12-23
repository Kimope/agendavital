/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author ramon
 */
public class UtilidadesMomento {
    
    @SuppressWarnings("empty-statement")
    public static void copyFile(File sourceFile, File destFile) throws IOException {
    if(!destFile.exists()) {
        destFile.createNewFile();
    }
 
    FileChannel origen = null;
    FileChannel destino = null;
    try {
        origen = new FileInputStream(sourceFile).getChannel();
        destino = new FileOutputStream(destFile).getChannel();
 
        long count = 0;
        long size = origen.size();             
        while((count += destino.transferFrom(origen, count, size-count))<size);
    }
    finally {
        if(origen != null) {
            origen.close();
        }
        if(destino != null) {
            destino.close();
        }
    }
}
    
}
