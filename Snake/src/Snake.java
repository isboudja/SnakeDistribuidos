import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Snake implements Serializable {
    private PanelJuego PJ;
    private PanelPuntos PP;
    private List<Ellipse2D.Double> partes;
    private Flechas dir;

    private Ellipse2D.Double temp;
    private Ellipse2D.Double nuevaP;

    private boolean over = false;

    public Snake(PanelJuego j, PanelPuntos p) {
        this.PJ = j;
        this.PP = p;
        iniciarPorDefecto();
    }

    public List<Ellipse2D.Double> getCuerpo() {
        return partes;
    }


    public void Colisiones() {
        Ellipse2D.Double head = partes.getFirst();
        Fruta fru = PJ.getFruta();

        for (int i = 1; i < partes.size(); i++) {
            if (head.getMinX() == partes.get(i).getMinX()
                    && head.getMinY() == partes.get(i).getMinY()) {
                over = true;
                return;
            }
        }
        if (head.getMinX() == fru.getShape().getMinX()
                && head.getMinY() == fru.getShape().getMinY()) {
            PP.addPoints(25);
            fru.SigFruta(this);
            partes.add(nuevaP);
        }
    }


    public boolean isGameOver() {
        return over;
    }

    public void nuevaDireccion(Flechas d) {
        this.dir = d;
    }
    private void moverCuerpo() {
        for (int i = partes.size() - 1; i > 0; i--) {
            if (i == partes.size() - 1) {
                nuevaP = (Ellipse2D.Double) partes.get(i).clone();
            }
            temp = (Ellipse2D.Double) partes.get(i - 1).clone();
            partes.set(i, temp);
        }
    }

    private void iniciarPorDefecto() {
        partes = Collections
                .synchronizedList(new ArrayList<Ellipse2D.Double>());
        partes.add(new Ellipse2D.Double(260, 260, 20, 20));
        partes.add(new Ellipse2D.Double(260, 280, 20, 20));
        partes.add(new Ellipse2D.Double(260, 300, 20, 20));
        partes.add(new Ellipse2D.Double(260, 320, 20, 20));
    }

    public void move() {
        switch (dir) {
            case ARRIBA:
                moverCuerpo();
                double nuevaYA = partes.getFirst().getMinY() - 20;
                partes.set(0, new Ellipse2D.Double(partes.getFirst().getMinX(), nuevaYA, 20, 20));
                if (nuevaYA < 0) {
                    over = true;
                }
                break;

            case IZQUIERDA:
                moverCuerpo();
                double nuevaXI = partes.getFirst().getMinX() - 20;
                partes.set(0, new Ellipse2D.Double(nuevaXI, partes.getFirst().getMinY(), 20, 20));
                if (nuevaXI < 0) {
                    over = true;
                }
                break;

            case DERECHA:
                moverCuerpo();
                double nuevaXD = partes.getFirst().getMinX() + 20;
                partes.set(0, new Ellipse2D.Double(nuevaXD, partes.getFirst().getMinY(),20, 20));

                if (nuevaXD > PJ.getBounds().getMaxX()) {
                    over = true;
                }
                break;

            case ABAJO:
                moverCuerpo();
                double nuevaYAb = partes.getFirst().getMinY() + 20;
                partes.set(0, new Ellipse2D.Double(partes.getFirst().getMinX(), nuevaYAb, 20, 20));

                if (nuevaYAb > PJ.getBounds().getMaxY()) {
                    over = true;
                }
                break;

            default:
                break;
        }
    }
}
