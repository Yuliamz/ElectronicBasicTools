package GUI;

import java.awt.Graphics;

public final class JPanelAbout extends javax.swing.JPanel {

    public JPanelAbout() {
    } 
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/img/About.png")).getImage(), 0, 0, null);
    }
}
