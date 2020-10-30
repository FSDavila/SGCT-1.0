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
import model.PCDA;

public class MapeadorPCDA {
	
    private static MapeadorPCDA instancia; //singleton
    private HashMap<Integer, PCDA> cachePCDA = new HashMap<>();
    private final String filename = "PCDAs.dat";
    
    private MapeadorPCDA() {
        load();
    }   
    
    public PCDA get(Integer PCDA) {
        return cachePCDA.get(PCDA);
    }
    
    public static MapeadorPCDA getInstancia(){
        if(instancia == null ){
            instancia = new MapeadorPCDA();
        }
        return instancia;
    }

    public void put(PCDA nova) {
        cachePCDA.put(nova.getIdentificacao(), nova);
    
    }        

    public void remove(Integer PCDA) {
        cachePCDA.remove(PCDA);
    }

    public void persist() throws FileNotFoundException {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream oo = new ObjectOutputStream(fout);
            oo.writeObject(cachePCDA);

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
            
            this.cachePCDA = (HashMap<Integer, PCDA>) oh.readObject();
            
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

    public ArrayList<PCDA> getList() {
        return new ArrayList(cachePCDA.values());
    }
    
}
