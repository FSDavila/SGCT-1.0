package persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import model.CDA;
import model.Contribuinte;

public class MapeadorCDA {
	
    private static MapeadorCDA instancia; //singleton
    private HashMap<Integer, CDA> cacheCDA = new HashMap<>();
    private final String filename = "CDAs.dat";
    
    private MapeadorCDA() {
        load();
    }   
    
    public CDA get(Integer nCDA) {
        return cacheCDA.get(nCDA);
    }
    
    public static MapeadorCDA getInstancia(){
        if(instancia == null ){
            instancia = new MapeadorCDA();
        }
        return instancia;
    }

    public void put(CDA nova) {
        cacheCDA.put(nova.getNCDA(), nova);
    
    }        

    public void remove(Integer nCDA) {
        cacheCDA.remove(nCDA);
    }

    public void persist() throws FileNotFoundException {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream oo = new ObjectOutputStream(fout);
            oo.writeObject(cacheCDA);

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
            
            this.cacheCDA = (HashMap<Integer, CDA>) oh.readObject();
            
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

    public ArrayList<CDA> getList() {
        return new ArrayList(cacheCDA.values());
    }
    
}
