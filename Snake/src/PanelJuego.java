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
        

        /**
         * Constructs the game field, which is the rectangular area where snake can
         * move
         */
        public PanelJuego() {
            setPreferredSize(new Dimension(Ancho, Alto));
            setBackground(Color.BLACK);
            initDefaults();
        }

        /**
         * Initializes the default snake and the apple
         */
        public void initDefaults() {

            Partess = Collections
                    .synchronizedList(new ArrayList<Ellipse2D.Double>());
            Partess.add(new Ellipse2D.Double(260, 260, 20, 20));
            Partess.add(new Ellipse2D.Double(260, 280, 20, 20));
            Partess.add(new Ellipse2D.Double(260, 300, 20, 20));
            Partess.add(new Ellipse2D.Double(260, 320, 20, 20));
        }

        public void setSnakeParts(List<Ellipse2D.Double> snakeParts) {
            this.Partess = snakeParts;
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(Color.WHITE);
            g2.setPaint(new Color(0, 255, 51));
            for (Ellipse2D e : Partess) {
                g2.fill(e);
            }
            g2.setPaint(new Color(215, 34, 38));
            g2.fill(Partess.get(0));
        }

}
