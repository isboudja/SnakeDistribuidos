import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
public class PanelJuego  extends JPanel {
        public static final int Ancho = 400;
        public static final int Alto = 400;
        private List<Ellipse2D.Double> Partess;
        private Fruta fru;

        public PanelJuego() {
            setPreferredSize(new Dimension(Ancho, Alto));
            setBackground(Color.BLACK);
            Defecto();
        }
    public void setFruta(Fruta f) {
        this.fru = f;
    }
    public Fruta getFruta() {
        return fru;
    }
    public void setCuerpoSnake(List<Ellipse2D.Double> snakeParts) {
        this.Partess = snakeParts;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.fillOval((int) fru.getShape().getMinX() + 5, (int) fru.getShape().getMinY() + 5, 15, 15);
        g2.setColor(new Color(0, 255, 51));
        for (Ellipse2D e : Partess) {
            g2.fill(e);
        }
        g2.setPaint(new Color(0, 70, 0));
        g2.fill(Partess.getFirst());
    }
        public void Defecto() {
            fru = new Fruta(20, 20);
            Partess = Collections
                    .synchronizedList(new ArrayList<Ellipse2D.Double>());
            Partess.add(new Ellipse2D.Double(260, 260, 20, 20));
            Partess.add(new Ellipse2D.Double(260, 280, 20, 20));
            Partess.add(new Ellipse2D.Double(260, 300, 20, 20));
            Partess.add(new Ellipse2D.Double(260, 320, 20, 20));
        }



}
