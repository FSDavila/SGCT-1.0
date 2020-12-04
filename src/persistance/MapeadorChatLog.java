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
import java.util.Vector;

public class MapeadorChatLog {
	
    private static MapeadorChatLog instancia; //singleton
    private HashMap<String, Vector<Vector>> cacheChatLogs = new HashMap<>(); //o integer eh o id da sessao, que puxa um array bidimensional que contem todas as mensagens e dados da sessao
    private final String filename = "ChatLogs.dat";
    
    private MapeadorChatLog() {
        load();
    }   
    
    public Vector<Vector> get(String CPF) {
        return cacheChatLogs.get(CPF);
    }
    
    public static MapeadorChatLog getInstancia(){
        if(instancia == null ){
            instancia = new MapeadorChatLog();
        }
        return instancia;
    }

   /* public void put(Vector<Vector> nova) {
    	if(cacheChatLogs.size() > 0) {
    		cacheChatLogs.put(cacheChatLogs.size()+1, nova); //criacao das chaves com autoincremento padrao
    	} else {
    		cacheChatLogs.put(1, nova); //inicializando hashmap
    	}
    
    } */
    
    public void put(String CPF, Vector<Vector> chatLog) {
    	cacheChatLogs.put(CPF, chatLog);
    }      

    public void remove(String CPF) {
        cacheChatLogs.remove(CPF);
    }

    public void persist() throws FileNotFoundException {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream oo = new ObjectOutputStream(fout);
            oo.writeObject(cacheChatLogs);

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
            
            this.cacheChatLogs = (HashMap<String, Vector<Vector>>) oh.readObject();
            
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
    
    public ArrayList<Vector<Vector>> getList() {
        return new ArrayList(cacheChatLogs.values());
    }
    
    public ArrayList<String> getKeys() {
        return new ArrayList<String>(cacheChatLogs.keySet());
    }
}
