package GUI;

import java.awt.Event;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

public final class PopMenu extends JPopupMenu{

Action copy;
Action cut;     
Action paste;       

    public PopMenu() {
        createActions();
        this.add(copy);
        this.add(cut);
        this.add(paste);
    }
    
    public void createActions(){
        copy = new DefaultEditorKit.CopyAction();
        paste = new DefaultEditorKit.PasteAction();
        cut = new DefaultEditorKit.CutAction();
        
        copy.putValue(Action.NAME, "Copiar");
        copy.putValue(Action.ACCELERATOR_KEY,KeyStroke.getAWTKeyStroke('C', Event.CTRL_MASK)); 
        copy.putValue(Action.SMALL_ICON, new javax.swing.ImageIcon(getClass().getResource("/img/copy.png")));
        copy.putValue(Action.SHORT_DESCRIPTION, "Copia el texto seleccionado al portapapeles");

        cut.putValue(Action.NAME, "Cortar");
        cut.putValue(Action.ACCELERATOR_KEY,KeyStroke.getAWTKeyStroke('X', Event.CTRL_MASK)); 
        cut.putValue(Action.SMALL_ICON, new javax.swing.ImageIcon(getClass().getResource("/img/cut.png")));
        cut.putValue(Action.SHORT_DESCRIPTION, "Corta el texto seleccionado al portapapeles");

        paste.putValue(Action.NAME, "Pegar");
        paste.putValue(Action.ACCELERATOR_KEY,KeyStroke.getAWTKeyStroke('V', Event.CTRL_MASK)); 
        paste.putValue(Action.SMALL_ICON, new javax.swing.ImageIcon(getClass().getResource("/img/paste.png")));
        paste.putValue(Action.SHORT_DESCRIPTION, "pegar texto del portapapeles");
    }
}
