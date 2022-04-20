import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
/**
 * Defines a panel the drawings will be made in.
 * 
 */
public class GraphDisplay extends JPanel {
    private Graph graph;
    public GraphDisplay(){};
    private int[] localArray;
    private int[] exhaustiveArray;
    private int localDistance;
    private int exhaustDistance;

    public GraphDisplay(Graph g) 
    {
        graph = g;
        localArray = new int[graph.VerticesNumber()];
        exhaustiveArray = new int[graph.VerticesNumber()];
        localDistance = graph.TSP_localSearch(localArray);
        exhaustDistance = graph.TSP_exhaustiveSearch(exhaustiveArray);
    }
    /**
     * Using to visually represent
     * 
     * @param g graphics context
     */
    public void paint(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;

        // saves vertex
        int[] x1 = new int[graph.VerticesNumber()];
        int[] y1 = new int[graph.VerticesNumber()];

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        g.drawString("Local Search: ", 10, 20);
        g.drawString("Shortest Path: " + Arrays.toString(localArray), 10, 40);
        g.drawString("Shortest Path Distance: " + localDistance, 10, 60);

        g.drawString("Exhaustive Search: ", 700, 20);
        g.drawString("Shortest Path: " + Arrays.toString(exhaustiveArray), 700, 40);
        g.drawString("Shortest Path Distance: " + exhaustDistance, 700, 60);

        for (int i = 0; i < graph.VerticesNumber(); i++) 
        {
            //sets far from the axis we are
            int x = graph.Points()[i][0] + 50;
            int y = graph.Points()[i][1] + 100;
            x1[i] = x;
            y1[i] = y;
        }
        g2.setStroke(new BasicStroke(4));
        //draws the lines and weights
        for (int i = 0; i < graph.VerticesNumber() - 1; i++) 
        {
            g.setColor(Color.pink);
            g.drawLine(x1[localArray[i]], y1[localArray[i]], x1[localArray[i + 1]], y1[localArray[i + 1]]);

            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(graph.Matrix()[localArray[i]][localArray[i + 1]]), (x1[localArray[i]] + x1[localArray[i + 1]]) / 2, 
            ((y1[localArray[i]] + y1[localArray[i + 1]]) / 2)+5);

            g.setColor(Color.PINK);
            g.drawLine(x1[exhaustiveArray[i]] + 600, y1[exhaustiveArray[i]], x1[exhaustiveArray[i + 1]] + 600, y1[exhaustiveArray[i + 1]]);

            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(graph.Matrix()[exhaustiveArray[i]][exhaustiveArray[i + 1]]), (x1[exhaustiveArray[i]] + 600 + x1[exhaustiveArray[i + 1]] + 600) / 2, 
            ((y1[exhaustiveArray[i]] + y1[exhaustiveArray[i + 1]]) / 2)+5);
        }
        //This entire portion is needed in order to draw the last line
        g.setColor(Color.PINK);
        g.drawLine(x1[localArray[localArray.length - 1]], y1[localArray[localArray.length - 1]], x1[localArray[0]], y1[localArray[0]]);

        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(graph.Matrix()[localArray[localArray.length - 1]][localArray[0]]), (x1[localArray[localArray.length - 1]] + x1[localArray[0]]) / 2,
        ((y1[localArray[localArray.length - 1]] + y1[localArray[0]]) / 2)+5);

        g.setColor(Color.PINK);
        g.drawLine(x1[exhaustiveArray[exhaustiveArray.length - 1]] + 600, y1[exhaustiveArray[exhaustiveArray.length - 1]], x1[exhaustiveArray[0]] + 600, y1[exhaustiveArray[0]]);

        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(graph.Matrix()[exhaustiveArray[exhaustiveArray.length - 1]][exhaustiveArray[0]]), ((x1[exhaustiveArray[exhaustiveArray.length - 1]] + 600) + (x1[exhaustiveArray[0]] + 600)) / 2,
        ((y1[exhaustiveArray[exhaustiveArray.length - 1]] + y1[exhaustiveArray[0]]) / 2)+5);

        //Draws the points
        for (int i = 0; i < graph.VerticesNumber(); i++) 
        {
            g.setColor(Color.MAGENTA);
            g.fillOval(x1[i] - 12, y1[i] - 20, 35, 35);

            g.setColor(Color.BLACK);
            g.drawOval(x1[i] - 12, y1[i] - 20, 35, 35);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString(String.valueOf(i), x1[i] - 2, y1[i] + 2);

            g.setColor(Color.MAGENTA);
            g.fillOval(x1[i] + 600 - 12, y1[i] - 20, 35, 35);

            g.setColor(Color.BLACK);
            g.drawOval(x1[i] + 600 - 12, y1[i] - 20, 35, 35);
            g.drawString(String.valueOf(i), x1[i] + 600 - 2, y1[i] + 2);
        }
    }
}
