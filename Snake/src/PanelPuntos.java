
import javax.swing.JPanel;
import java.awt.*;

public class PanelPuntos extends JPanel {
        public static final int Ancho = 400;
        public static final int Alto  = 50;
        private final Font letra;
        private String puntos;

        public PanelPuntos() {
            setPreferredSize(new Dimension(Ancho, Alto));
            setBackground(Color.DARK_GRAY);

            puntos = "0";
            letra = new Font("Pixel", Font.BOLD, 20);
        }

        public void addPoints(int points) {
            int Anterior = Integer.parseInt(puntos);
            Anterior = Anterior + points;
            puntos = Anterior + "";
            this.repaint();
        }
        public void clear() {
            puntos = "0";
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            Font letra = new Font("Pixel", Font.BOLD, 20);
            g2.setFont(letra);
            g2.setColor(new Color(0, 255, 51));
            g2.drawString("Puntuacion: ", 15, 32);
            g2.setColor(new Color(255, 255, 0));
            g2.drawString(puntos, 130, 32);
        }

}
