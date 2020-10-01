package persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import model.Funcionario;

public class MapeadorFuncionario{
	
    private static MapeadorFuncionario instancia; //singleton
    private HashMap<String, Funcionario> cacheFuncionarios = new HashMap<>();
    private final String filename = "Funcionarios.dat";
    
    private MapeadorFuncionario() {
        load();
    }   
    
    public Funcionario get(String CPF) {
        return cacheFuncionarios.get(CPF);
    }
    public Funcionario get4(String CPF) {
        return cacheFuncionarios.get(CPF);
    }
    
    public static MapeadorFuncionario getInstancia(){
        if(instancia == null ){
            instancia = new MapeadorFuncionario();
        }
        return instancia;    }

    public void put(Funcionario funcionario) {
        cacheFuncionarios.put(funcionario.getCPF(), funcionario);
    
    }        

    public void remove(String CPF) {
        cacheFuncionarios.remove(CPF);
    }

    public void persist() throws FileNotFoundException {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream oo = new ObjectOutputStream(fout);
            oo.writeObject(cacheFuncionarios);

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
            
            this.cacheFuncionarios = (HashMap<String, Funcionario>) oh.readObject();
            
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

    public ArrayList<Funcionario> getList() {
        return new ArrayList(cacheFuncionarios.values());
    }
    
}