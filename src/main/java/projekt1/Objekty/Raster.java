package projekt1.Objekty;

public interface Raster {
    
    void clear();

    void setClearColor(int color);

    int getPixel(int x, int y);

    void setPixel(int x, int y, int color);

    int getWidth();

    int getHeight();

}
