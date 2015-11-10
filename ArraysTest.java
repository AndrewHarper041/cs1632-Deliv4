import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.*;

public class ArraysTest
{
    static Random rand;
    static int[][] arrays;
    static int numArrays = 150; //arbitrary > 100 value
    static int lengthMax = 8192; //arbitrary > 100 value
    
    /*
     * Setup & Tear Down
     */
     
    //Generates the random values for the arrays under test
    @Before
    public void setUp()
    {
        //Initialization
        rand = new Random();
        arrays = new int[numArrays][]; //number of arrays taken as cmd line argument
        
        //Randomly populate the arrays 
        populateArray();
    }
    
    //Reset arrays to null so that every test has newly generated unsorted arrays
    @After
    public void tearDown()
    {
        arrays = null;
    }
    
    /*
     * Property Tests
     */
    
    //Test same size before and after sort
    @Test
    public void sizeTest()
    {
        int[] arrSizes = new int[numArrays]; //array to track number of lists
        
        //Iterate over arrays storing the length of each individual array
        for(int i=0; i<numArrays; i++)
            arrSizes[i] = arrays[i].length;
        
        //Sort the arrays using Arrays.sort()
        sortArrays();
        
        //Iterate over arrays post sort asserting that the size has not changed
        for(int i=0; i<numArrays; i++)
            org.junit.Assert.assertEquals(arrSizes[i], arrays[i].length);   
    }
    
    //Tests that sorting is Idempotent and multiple runs will not change result
    @Test
    public void idempotentTest()
    {
        //Sort arrays first time
        sortArrays();
        
        //Create deep copy of arrays post sorting
        int[][] copyArr = deepCopy(arrays);
        
        //Sort arrays second time
        sortArrays();
        
        //Check that the twice sorted arrays is the same as after the first sort
        for(int i=0; i<arrays.length; i++)
            org.junit.Assert.assertArrayEquals(copyArr[i], arrays[i]);
    }
    
    /*
     * Utility Functions
     */
     
    //Populate arrays with random length arrays having random values
    public static void populateArray()
    {
        for(int i=0; i<arrays.length; i++)
        {
            //Initialize the array to a random length
            arrays[i] = new int[randLength()];
            
            //Populate the array with random values 
            for(int j=0; j<arrays[i].length; j++)
                arrays[i][j] = randValue();
        }
    }
    
    //Sort the arrays 
    public static void sortArrays()
    {
        //Loop over the number of distinct arrays and sort
        for(int i=0; i<arrays.length; i++)
            Arrays.sort(arrays[i]);
    }
    
    //Returns a random value from 0 to max int
    public static int randValue()
    {
        return rand.nextInt(Integer.MAX_VALUE);
    }
    
    //Returns rand int from 0 to lengthMax
    public static int randLength()
    {
        return rand.nextInt(lengthMax);
    }
    
    //Returns a deep copy of a
    public int[][] deepCopy(int[][] input)
    {
        int[][] target = new int[input.length][];
        
        for (int i=0; i <input.length; i++) 
            target[i] = Arrays.copyOf(input[i], input[i].length);
        
        return target;
    }
}