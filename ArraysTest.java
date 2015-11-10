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
        int[][] copyArr = deepCopy();
        
        //Sort arrays second time
        sortArrays();
        
        //Check that the twice sorted arrays is the same as after the first sort
        for(int i=0; i<arrays.length; i++)
            org.junit.Assert.assertArrayEquals(copyArr[i], arrays[i]);
    }
    
    //Tests that an arrays included elements do not change upon sorting
    @Test
    public void sameElementsTest()
    {
        //Get a list of all elements in arrays
        int[][] elements = deepCopy();
        
        //Sort arrays
        sortArrays();
        
        //Check that now sorted arrays contains all the elements within the pre sorted copy
        for(int i=0; i<arrays.length; i++)
        {
            for(int j=0; j<arrays[i].length; j++)
            {
                boolean bool = false; //bool will be set as true if element is found
                
                //Loop over array to find element, as other methods (i.e. Arrays.asList().contains() ) don't play nice with primitives
                for(int num : arrays[i])
                    if(num == elements[i][j])
                        bool = true;

                org.junit.Assert.assertTrue(bool);
            }
        }
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
    
    //Returns a deep copy of array
    public int[][] deepCopy()
    {
        int[][] target = new int[arrays.length][];
        
        for (int i=0; i <arrays.length; i++) 
            target[i] = Arrays.copyOf(arrays[i], arrays[i].length);
        
        return target;
    }
}