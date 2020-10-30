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
import model.PagamentoIntegral;

public class MapeadorPagamentoIntegral {
	
    private static MapeadorPagamentoIntegral instancia; //singleton
    private HashMap<Long, PagamentoIntegral> cachePgtoIntegral = new HashMap<>();
    private final String filename = "PagamentosIntegrais.dat";
    
    private MapeadorPagamentoIntegral() {
        load();
    }   
    
    public PagamentoIntegral get(Long idPagamento) {
        return cachePgtoIntegral.get(idPagamento);
    }
    
    public static MapeadorPagamentoIntegral getInstancia(){
        if(instancia == null ){
            instancia = new MapeadorPagamentoIntegral();
        }
        return instancia;
    }

    public void put(PagamentoIntegral nova) {
        cachePgtoIntegral.put(nova.getIdPagamento(), nova);
    
    }        

    public void remove(Long idPagamento) {
        cachePgtoIntegral.remove(idPagamento);
    }

    public void persist() throws FileNotFoundException {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream oo = new ObjectOutputStream(fout);
            oo.writeObject(cachePgtoIntegral);

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
            
            this.cachePgtoIntegral = (HashMap<Long, PagamentoIntegral>) oh.readObject();
            
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

    public ArrayList<PagamentoIntegral> getList() {
        return new ArrayList(cachePgtoIntegral.values());
    }
    
}
