import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Random;
public class Fruta implements Serializable {
    private double Hori;
    private double Verti;
    public Fruta(double x, double y) {
        this.Hori = x;
        this.Verti = y;
    }

    public void SigFruta(Snake snake) {
        for (Ellipse2D.Double e : snake.getCuerpo()) {
            while (Hori == e.getMinX() && Verti == e.getMinY()) {
                Hori = Cordenadas();
                Verti = Cordenadas();
            }
        }
    }
    private double Cordenadas() {
        Random random = new Random();
        double d;
        do {
            d = random.nextInt(400);  
        } while (d % 20 != 0);
        return d;
    }
    public Ellipse2D.Double getShape() {
        return new Ellipse2D.Double(Hori, Verti, 20, 20);
    }
}
