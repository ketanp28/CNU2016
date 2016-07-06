import java.util.*;

public class appliance {
    
    private boolean status=false;
    private boolean autoOff = false;
    private int timer = 2;
    private int lastOn=0;
    private int id = 0 ;

    public void setId(int n){
        this.id = n;
    }
    
    public void setAutoOff(){
        this.autoOff = true;
    }
    
    public int getId(){
        return this.id;
    }
    
    public boolean getStatus(){
        return this.status;
    }
    
    
    public boolean getAutoOff()
    {
        return this.autoOff;
    }
    
    public int getLastOn(){
        return this.lastOn;
    }
    
    public int getTimer(){
        return this.timer;
    }
    
    public void switchOn(int key,Map<Integer, Boolean> app_map){
        if(this.status==false){
            this.status=true;
            System.out.println("app "+ this.id +" got ON at "+key);
            this.lastOn = key;
            
        }
    }
    
    
    
    public void switchOff(int key){
        
            if(this.status==true){
                this.status=false;
                System.out.println("app "+ this.id +" got OFF at "+key);
            }
        
    }
    
}
