import javax.swing.*;

public class combinatorialOptimization{
    public static void main(String[]  args) 
    {
        Graph g = new Graph("input.txt");
        FrameDisplay frame = new FrameDisplay(g); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}