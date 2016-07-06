import org.junit.Test;
import org.junit.Assert.*;


public class userTest {
    user usertest = new user();   
    
    @Test
        public void testuser(){
            appliance app = new appliance();
            app.setId(2);
            assert(app.getId()==2);
        }
        
    @Test
        public void testValue(){
            String fileName = "/projects/assignment02/src/main/resources/test1.txt";
            usertest.simulate(fileName);
            
        }
    
}
