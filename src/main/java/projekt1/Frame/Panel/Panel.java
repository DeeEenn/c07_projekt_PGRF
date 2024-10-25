package projekt1.Frame.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import projekt1.Objekty.FilledLineRasterizer;
import projekt1.Objekty.RasterBufferedImage;

public class Panel extends JPanel {
    private final RasterBufferedImage raster;
    private static final int width = 1200;
    private static final int height = 800;

    private int startX, startY, endX, endY;
    private boolean isDrawing = false;
    private boolean isShiftPressed = false;
    private boolean isDrawingThick = false;

    private int dx, dy;

    

    public Panel() { 
        
        setPreferredSize(new Dimension(width, height));
        raster = new RasterBufferedImage(width, height);
        
        clear(Color.WHITE.getRGB());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                isDrawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            
                // Kreslení horizontálně
                if (isShiftPressed && isDrawingThick) {
                    if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
                        endY = startY;  
                        System.out.println("Horizontal drawn");
                        System.out.println("[" + startX +"," + startY + "] -> [" + endX + "," + endY + "]");
                        drawLine(startX, startY, endX, endY); 
                        for (int i = 1; i < 3; i++) {
                            drawLine(startX, startY + i, endX, endY + i);        
                        }
                        for (int i = 1; i < 3; i++) {
                            drawLine(startX, startY - i, endX, endY - i);        
                        }
                        isDrawing = false;
                        repaint();
                    } 
                    // Kreslení vertikálně
                    else if (Math.abs(endX - startX) < Math.abs(endY - startY)){
                        endX = startX;  
                        System.out.println("Vertical drawn");
                        System.out.println("[" + startX +"," + startY + "] -> [" + endX + "," + endY + "]");
                        drawLine(startX, startY, endX, endY); 
                        for (int i = 1; i < 3; i++) {
                            drawLine(startX + i, startY, endX + i, endY);        
                        }
                        for (int i = 1; i < 3; i++) {
                            drawLine(startX - i, startY, endX - i, endY);        
                        }
        
                        isDrawing = false;
                        repaint();  
                    }
                
                } 
                // Kreslení tlusté čáry
                else if(isDrawingThick){
                    drawLine(startX, startY, endX, endY); 
                
                    for (int i = 1; i < 3; i++) {
                        drawLine(startX, startY + i, endX, endY + i);        
                    }
                    for (int i = 1; i < 3; i++) {
                        drawLine(startX, startY - i, endX, endY - i);        
                    }
                    
                    for (int i = 1; i < 3; i++) {
                        drawLine(startX + i, startY, endX + i, endY);        
                    }
                    for (int i = 1; i < 3; i++) {
                        drawLine(startX - i, startY, endX - i, endY);        
                    }
    
                    isDrawing = false;
                    System.out.println("[" + startX +"," + startY + "] -> [" + endX + "," + endY + "]");
                    repaint();
                } else {
                    drawLine(startX, startY, endX, endY); 
                    isDrawing = false;
                    System.out.println("[" + startX +"," + startY + "] -> [" + endX + "," + endY + "]");
                    repaint();
                }
            } 
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    endX = e.getX();
                    endY = e.getY();
                    repaint();

                    if(isShiftPressed == true){
                      dx = Math.abs(endX - startX);
                      dy = Math.abs(endY - startY);
                        if (dx < dy){
                            endX = startX;
                        } else if (dx > dy){
                            endY = startY;
                        }                
                    }
                } 
                
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'C' || e.getKeyChar() == 'c') {
                    clear(Color.WHITE.getRGB()); 
                    repaint(); 
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = true;
                } else if (e.getKeyChar() == 'T' || e.getKeyChar() == 't') {
                    isDrawingThick = true;
                } 
                
            }
            @Override
            public void keyReleased(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = false;
                    repaint();
                }  else if (e.getKeyChar() == 'T' || e.getKeyChar() == 't') {
                    isDrawingThick = false;
                }       
            }
        });
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        FilledLineRasterizer lineDrawer = new FilledLineRasterizer(raster);
        lineDrawer.setColor(Color.BLACK); 
        lineDrawer.drawLine(x1, y1, x2, y2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        raster.repaint(g); 
        
                g.setColor(Color.BLACK); 
                g.setFont(new Font("Arial", Font.PLAIN, 16)); 
                String text = "| CLICK -> HOLD -> RELEASE = DRAW LINE | C = Clear Canvas | HOLD SHIFT + MOUSE = VERTICAL/HORIZONTAL | HOLD T FOR THICK LINE "; 
                g.drawString(text, 5, height - 30);
    }

    public void clear(int color) {
        System.out.println("Clearing Canvas");
        raster.setClearColor(color);
        raster.clear();
    }

    public RasterBufferedImage getRaster() {
        return raster;
    }
}
