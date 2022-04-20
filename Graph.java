import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Graph
{
    private int verticesNumber;
    private int[][] matrix; // adjacency matrix we use to find shortest path
    private int[][] points; // the points we translate into adjacency matrix

    public int VerticesNumber() 
    {
        return verticesNumber;
    }
    public int[][] Points() 
    {
        return points;
    }
    public int[][] Matrix() 
    {
        return matrix;
    }
    /**
     * Instantiates a graph and initializes it with info from a text file.
     * @param filename text file with coordinates of points
     */
    public Graph(String filename) 
    {
        File input = new File(filename);
        Scanner in = null;
        try 
        {
            in = new Scanner(input);
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("File not found!");
            System.exit(0);
        }
        //This turns the text file with points into an array
        while (in.hasNextLine()) 
        {
            verticesNumber = in.nextInt();
            points = new int[verticesNumber][2];

            for (int i = 0; i < verticesNumber; i++) 
            {
                for (int j = 0; j < 2; j++) 
                {
                    points[i][j] = in.nextInt();
                }
            }
        }
        in.close();
        matrix = new int[verticesNumber][verticesNumber];
        //this turns the new points array into an adjacency matrix
        for (int i = 0; i < verticesNumber; i++) 
        {
            for (int j = 0; j < verticesNumber; j++) 
            {
                if (i == j) 
                {
                    matrix[i][j] = 0;
                    matrix[j][i] = 0;
                } else 
                {
                    // This uses the distance formula and rounds the asnwer, puts result into adj array
                    int distance = (int)Math.round(Math.sqrt(Math.pow((points[j][0] - points[i][0]), 2) + Math.pow(points[j][1] - points[i][1], 2)));
                    matrix[i][j] = Math.round(distance);
                    matrix[j][i] = Math.round(distance);
                }
            }
        }
    }
    /**
     * Finds a shortest route that visits every vertex 
     * exactly once and returns to the starting points.
     * Uses exhaustive search.
     *
     * @param shortestRoute shortest path (return value)
     * 
     * @return shortest distance
     */
    public int TSP_exhaustiveSearch(int[] shortestRoute) 
    {
        for (int i = 0; i < verticesNumber; i++) 
        {
            shortestRoute[i] = i;
        }
        int[] a = new int[verticesNumber];
        TSP_exhaustiveSearch(shortestRoute, a, 0);

        return totalDistance(shortestRoute);
    }
    /** Calculates distance of given route.
     *   
     *  @param a route
     * 
     *  @return distance of route
     * */
    private int totalDistance(int[] a) 
    {
        int n = verticesNumber;
        int totalWeight = 0;

        for (int i = 0; i < n; i++) 
        {
            int weight = matrix[a[i]][a[(i + 1) % n]];
            totalWeight += weight;
        }
        return totalWeight;
    }
    /**
     * Recursive Algorithm
     *
     * @param a array partially filled with permutation
     * @param k index of current element in permutation
     * 
     */
    private void TSP_exhaustiveSearch(int[] shortestRoute, int[] a, int k) 
    {
        if (k == a.length) 
        {
            if (totalDistance(a) < totalDistance(shortestRoute)) 
            {
                System.arraycopy(a, 0, shortestRoute, 0, verticesNumber);
            }
        } else 
        {
            ArrayList<Integer> Sk = constructCandidateSet(a, k);
            for (int s : Sk) 
            {
                a[k] = s;
                TSP_exhaustiveSearch(shortestRoute, a, k + 1);
            }
        }
    }
    /**
     * Construct candidate set (set will contain elements
     * not used in locations [0, k-1] of array a)
     */
    private ArrayList<Integer> constructCandidateSet(int[] a, int k) 
    {
        ArrayList<Integer> candidates = new ArrayList<>();
        boolean[] b = new boolean[a.length];

        for (int i = 0; i < k; i++) 
        {
            b[a[i]] = true;
        }

        for (int i = 0; i < a.length; i++) 
        {
            if (!b[i])
                candidates.add(i);
        }
        return candidates;
    }
    /**
     * Iterator class, allows traversal of the set of neighbors
     * of given permutation. There will be n(n-1)/2 neighbors,
     * obtained by swapping all pairs of elements in the 
     * permutation
     */
    public class PermutationNeighborhood 
    {
        private final int[] p; // permutation
        private final int SIZE; // size of permutation
        private int loc1; // loc1 and loc2 are the locations of
        private int loc2; // p that will be swapped next
    
        public PermutationNeighborhood(int[] a) 
        {
            SIZE = a.length;
            p = new int[SIZE];
            System.arraycopy(a, 0, p, 0, SIZE);
            loc1 = 0;
            loc2 = 1;
        }
        /**
         * Returns true if at least a neighbor remains to be generated; false otherwise.
         */
        public boolean hasNext() 
        {
            return loc1 != SIZE - 1;
        }
        /**
         * Returns next permutation neighbor if it exists
         */
        public int[] next() 
        {
            if (hasNext()) 
            {
                // copy p to a
                int[] a = new int[SIZE];
                System.arraycopy(p, 0, a, 0, SIZE);
    
                // exchange elements at locations loc1 and loc2
                a[loc1] = p[loc2];
                a[loc2] = p[loc1];
    
                // advance loc1 and loc2
                if (loc2 == SIZE - 1) 
                {
                    loc1++;
                    loc2 = loc1 + 1;
                } else
                    loc2++;
                return a;
            } else return null;
        }
    }
    public int TSP_localSearch(int[] shortestRoute) 
    {
        int bestDistance;
        //generate initial solution as a random permutation
        int[] a = new int[verticesNumber];
        randomPermutation(a);
        //shortestRoute = initial solution
        System.arraycopy(a, 0, shortestRoute, 0, verticesNumber);
        bestDistance = totalDistance(shortestRoute);
        boolean betterSolutionFound;

        do 
        {
            betterSolutionFound = false;
            PermutationNeighborhood pn = new PermutationNeighborhood(shortestRoute);
            while (pn.hasNext()) 
            {
                a = pn.next();
                int currentDistance = totalDistance(a);
                if (currentDistance < bestDistance) 
                {
                    System.arraycopy(a, 0, shortestRoute, 0, verticesNumber);
                    bestDistance = currentDistance;
                    betterSolutionFound = true;
                }
            }
        } while (betterSolutionFound);
        return bestDistance;
    }
    /**
     * Given an array, generates random permutation of values in [0, n-1],
     * where n is size of given array; random permutation will be stored
     * in the array using Fisher-Yates shuffle algorithm.
     * 
     * @param a output array
     */
    public void randomPermutation(int[] a) 
    {
        for (int i = 0; i < a.length; i++) 
        {
            a[i] = i;
        }
        Random rnd = new Random();
        for (int i = a.length - 1; i > 0; i--) 
        {
            // geenrates a random index in [0, i]
            int randomLocation = rnd.nextInt(i + 1);

            if (randomLocation != i) 
            {
                // swap a[i] and a[randomLocation]
                int temp = a[i];
                a[i] = a[randomLocation];
                a[randomLocation] = temp;
            }
        }
    }
}