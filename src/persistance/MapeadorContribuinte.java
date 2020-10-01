package persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import model.Contribuinte;

public class MapeadorContribuinte{
	
    private static MapeadorContribuinte instancia; //singleton
    private HashMap<String, Contribuinte> cacheContribuintes = new HashMap<>();
    private final String filename = "Contribuintes.dat";
    
    private MapeadorContribuinte() {
        load();
    }   
    
    public Contribuinte get(String identificacao) {
        return cacheContribuintes.get(identificacao);
    }
   
    
    public static MapeadorContribuinte getInstancia(){
        if(instancia == null ){
            instancia = new MapeadorContribuinte();
        }
        return instancia;    }

    public void put(Contribuinte contribuinte) {
        cacheContribuintes.put(contribuinte.getIdentificacao(), contribuinte);
    
    }        

    public void remove(String identificacao) {
        cacheContribuintes.remove(identificacao);
    }

    public void persist() throws FileNotFoundException {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream oo = new ObjectOutputStream(fout);
            oo.writeObject(cacheContribuintes);

            oo.flush();
            fout.flush();

            oo.close();
            fout.close();

            oo = null;
            fout = null;

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void load() {
        try {
            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream oh = new ObjectInputStream(fin);
            
            this.cacheContribuintes = (HashMap<String, Contribuinte>) oh.readObject();
            
            oh.close();
            fin.close();
            
            oh = null;
            fin = null;
           
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public ArrayList<Contribuinte> getList() {
        return new ArrayList(cacheContribuintes.values());
    }
    
}