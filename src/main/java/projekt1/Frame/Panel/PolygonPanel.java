package projekt1.Frame.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import projekt1.Objekty.FilledLineRasterizer;
import projekt1.Objekty.Polygon; 
import projekt1.Objekty.RasterBufferedImage;

public class PolygonPanel extends JPanel {
    private static final int width = 1200;
    private static final int height = 800;
    private final RasterBufferedImage raster;
    private final Polygon polygon; 
    private final ArrayList<Point> points; 

    public PolygonPanel() {
        setPreferredSize(new Dimension(width, height));
        raster = new RasterBufferedImage(width, height);
        polygon = new Polygon();

        points = new ArrayList<>(); 

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'C' || e.getKeyChar() == 'c') {
                    clear(Color.WHITE.getRGB()); 
                    repaint(); 
                } 
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addPoint(e.getX(), e.getY()); 
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        raster.repaint(g);
        drawPolygon(g);

        g.setColor(Color.BLACK); 
        g.setFont(new Font("Arial", Font.PLAIN, 16)); 
        String text = "| C = Clear Canvas |"; 
        g.drawString(text, 5, height - 30);
    }

    public void clear(int color) {
        System.out.println("Clearing Canvas");
        raster.setClearColor(color);
        raster.clear();
        points.clear(); 
    }
    public void clearOnStart(int color){
        System.out.println("Clearing Canvas");
        raster.setClearColor(color);
        raster.clear();
    }

    private void addPoint(int x, int y) {
        points.add(new Point(x, y)); 
        polygon.addPoint(new projekt1.Objekty.Point(x, y)); // Add point to polygon
    }

      private void drawLine(int x1, int y1, int x2, int y2) {
        FilledLineRasterizer lineDrawer = new FilledLineRasterizer(raster);
        lineDrawer.setColor(Color.BLACK); 
        lineDrawer.drawLine(x1, y1, x2, y2);
    }

    private void drawPolygon(Graphics g) {
        if (points.size() < 2) return; 
        g.setColor(Color.BLACK); 
        for (int i = 0; i < points.size() - 1; i++) {
            drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
        }
       
        if (points.size() > 2) { 
            drawLine(points.get(0).x, points.get(0).y, points.get(points.size() - 1).x, points.get(points.size() - 1).y);
            repaint();
        }
    }
}
