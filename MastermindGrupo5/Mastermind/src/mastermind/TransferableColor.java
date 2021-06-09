package mastermind;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * Clase para la gestión de colores del Drag & Drop
 * @author Grupo 5
 */
public class TransferableColor implements Transferable {
    
    private static DataFlavor colorFlavor = new DataFlavor(Integer.class,"Un numero");
    private static DataFlavor[] supportedFlavors = { colorFlavor, DataFlavor.stringFlavor };
    private Integer color;
    
    public TransferableColor(int color) {
        this.color = color;
    }
    
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }
    
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(colorFlavor) || flavor.equals(DataFlavor.stringFlavor))
        return true;
        return false;
    }
    
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(colorFlavor))
            return color;
        else if (flavor.equals(DataFlavor.stringFlavor))
            return color.toString();
        else throw new UnsupportedFlavorException(flavor);
    }
    
    public static DataFlavor getColorFlavor(){
        return colorFlavor;
    }
}