import org.junit.Test;

public class homeTest {
    
    user usertest = new user();   
    
    @Test
        public void testInput(){
             String fileName = "/projects/assignment02/src/main/resources/test.txt";
             usertest.simulate(fileName);
        }
    
    @Test
        public void testInput2(){
             String fileName = "/projects/assignment02/src/main/resources/test1.txt";
             usertest.simulate(fileName);
        }
    
    
}
