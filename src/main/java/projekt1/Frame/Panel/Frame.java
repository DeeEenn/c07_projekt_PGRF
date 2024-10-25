package projekt1.Frame.Panel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;


/*  1. ZÁPOČTOVÝ PROJEKT PGRF  */
/*  CREATED BY DAVI  */

public class Frame extends JFrame {
    
    Panel panel = new Panel();
    PolygonPanel polygonPanel = new PolygonPanel();

    public Frame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("PGRF1 2024/2025");
        setVisible(true);
        setResizable(false);

        panel = new Panel();
        polygonPanel = new PolygonPanel();

        add(panel, BorderLayout.CENTER);
        pack();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Lines", panel);
        tabbedPane.add("Polygons", polygonPanel);
        add(tabbedPane);


        panel.setFocusable(true);
        panel.grabFocus();
    }



}
