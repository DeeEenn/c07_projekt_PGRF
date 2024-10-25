package projekt1.Objekty;

import java.awt.Color;


/* 
DDA (Digital Differential Analyzer) algoritmus slouží k vykreslení čáry mezi dvěma body v rastrové grafice. 
Vypočítá rozdíly v osách x a y, určí počet kroků podle většího rozdílu a následně v každém kroku postupně 
přičítá malou hodnotu k souřadnicím, aby čára byla rovnoměrná. Každý bod čáry je vykreslen zaokrouhlením aktuálních 
souřadnic na nejbližší pixel.
 */

public class FilledLineRasterizer extends LineRasterizer {
    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            raster.setPixel(Math.round(x), Math.round(y), Color.BLACK.getRGB());
            x += xIncrement;
            y += yIncrement;
        }
    }
}
