import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class user {
    
    // maps for appliances
    Map<Integer, Boolean> ac = new TreeMap<Integer, Boolean>();
    Map<Integer, Boolean> heater = new TreeMap<Integer, Boolean>();
    Map<Integer, Boolean> oven = new TreeMap<Integer, Boolean>();
    
    public void simulate(String fileName){
        
        appliance air = new appliance();
        appliance heat = new appliance();
        appliance cooker = new appliance();
        
        air.setId(1);
        heat.setId(2);
        cooker.setId(3);
        
        heat.setAutoOff();
        
        // read input
        readInput(fileName);

		doJob(air,ac,air.getAutoOff());
		doJob(heat,heater,heat.getAutoOff());
		doJob(cooker,oven,cooker.getAutoOff());
		
	}
        
        public void readInput(String fileName){
            BufferedReader br = null;
        
            // Input fornat : APP_ID TIME COMMAND
            try {
                String line;
                br = new BufferedReader(new FileReader(fileName));
    
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" ");
                    Integer appliance_id = Integer.parseInt(parts[0]);
                    Integer time = Integer.parseInt(parts[1]);
                    String cmd = parts[2];
                    
                    
                    if(appliance_id == 1){
                        if(cmd.equals("On")){
                            ac.put(time,true);
                        }
                        else{
                            ac.put(time,false);
                        }
                    }
                    if(appliance_id == 2){
                        if(cmd.equals("On")){
                            heater.put(time,true);
                        }
                        else{
                            heater.put(time,false);
                        }
                    }
                    if(appliance_id == 3){
                        if(cmd.equals("On")){
                            oven.put(time,true);
                        }
                        else{
                            oven.put(time,false);
                        }
                    }
    
                }
            }catch (IOException e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				if (br != null)br.close();
    			} catch (IOException ex) {
    				ex.printStackTrace();
    			}
    		}
        }
        
		public static void doJob(appliance app, Map<Integer, Boolean> app_map, boolean autoOff){
        
        if(autoOff==true){
            Map<Integer, Boolean> temp = new TreeMap<Integer, Boolean>();
            
            for(Map.Entry<Integer, Boolean> entry : app_map.entrySet()) {
              Integer key = entry.getKey();
              Boolean value = entry.getValue();
              temp.put(key,value);
              if(value == true){
                  temp.put(key+app.getTimer(),false);
              }
          }
        app_map = temp;

        }
        
        for(Map.Entry<Integer, Boolean> entry : app_map.entrySet()) {
              Integer key = entry.getKey();
              Boolean value = entry.getValue();
            
              //System.out.println(key + " => " + value);
              
              // doing job
              if(value == true){
                  app.switchOn(key,app_map);
              }
              else{
                  app.switchOff(key);
              }
              
        }
    }
}
