import java.awt.Color;
import javax.swing.*;
/**
 * Defines a panel to draw in
 */
public class FrameDisplay extends JFrame {
    int WIDTH = 1300;
    int HEIGHT = 800;
    public FrameDisplay(Graph g) 
    {
        setBackground(Color.GRAY);
        setTitle("Combinatorial Optimization");
        setSize(WIDTH, HEIGHT);
        GraphDisplay panel = new GraphDisplay(g);
        add(panel);
    }
}