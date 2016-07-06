import org.junit.Test;


public class applianceTest {
    
    appliance test = new appliance();
    @Test
       public void appliance() {	   
          assert((test.getTimer())==2); 
       }
    
    @Test
        public void testgetAutoOff() {	   
          assert((test.getAutoOff())==false);
        }
    
    @Test
        public void testgetID() {	   
          assert((test.getId())==0); 
        }
        
    @Test
        public void testgetLastOn() {	   
          assert((test.getLastOn())==0); 
        }
        
    @Test
        public void testgetTimer() {	   
          assert((test.getTimer())==2); 
        }
        
    @Test
        public void testSwitchOff() {
            test.switchOff(3);
            assert((test.getStatus())==false);
        }
       
    
}
