import com.almundo.callcenter.dispatch.Dispatcher;

public class Principal {
    
    public static void main(String[] args){
        
        Dispatcher dispatcher = new Dispatcher();
        for(int i = 0; i < 10; i++){
            dispatcher.dispatchCall();
        }

    }
    
}
