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
        
        
    
}
