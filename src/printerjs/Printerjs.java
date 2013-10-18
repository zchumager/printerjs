package printerjs;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author zchumager
 */
public class Printerjs extends Applet{
   private PrinterJob printerJob;
    private PrintService printService;
    
    public void init(){}
    
    public void print(String filePath, String printerName) throws URISyntaxException{
        //Buscar una impresora por su nombre dentro de los servicios de impresión disponibles
        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(printerName, null));
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet); 
        
        //Guardar el servicio en un atributo de la clase
        this.printService = printServices[0];
        
        //Iniciar un nuevo trabajo de impresión
        this.printerJob = PrinterJob.getPrinterJob();
        //Intentar definirle el servicio de impresión asignado en el atributo de la clase previamente
        try{
            printerJob.setPrintService(this.printService);
        }catch(PrinterException e){
            System.out.println(e.toString());
        }
        
        try {
           //Cargar el documento
           PDDocument pdDocument = PDDocument.load(new File(new URI(filePath)));
           //imprimir el documento
           pdDocument.print(this.printerJob);
        } catch (IOException | URISyntaxException | PrinterException ex) {
            Logger.getLogger(Printerjs.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    //se testea el funcionamiento del applet sobreescribiendo el metodo start del applet
    public void start(){
       Printerjs printerjs = new Printerjs();
       try {
           printerjs.print("file:///C:/Users/zchumager/Documents/asd_cy.pdf", "PDFCreator");
       } catch (URISyntaxException ex) {
           Logger.getLogger(Printerjs.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
