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
import model.PagamentoParcela;

public class MapeadorPagamentoParcela {

	private static MapeadorPagamentoParcela instancia; // singleton
	private HashMap<Long, PagamentoParcela> cachePgtoParcela = new HashMap<>();
	private final String filename = "PagamentosParcelas.dat";

	private MapeadorPagamentoParcela() {
		load();
	}

	public PagamentoParcela get(Long idPagamento) {
		return cachePgtoParcela.get(idPagamento);
	}

	public static MapeadorPagamentoParcela getInstancia() {
		if (instancia == null) {
			instancia = new MapeadorPagamentoParcela();
		}
		return instancia;
	}

	public void put(PagamentoParcela nova) {
		cachePgtoParcela.put(nova.getIdPagamento(), nova);

	}

	public void remove(Long idPagamento) {
		cachePgtoParcela.remove(idPagamento);
	}

	public void persist() throws FileNotFoundException {
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oo = new ObjectOutputStream(fout);
			oo.writeObject(cachePgtoParcela);

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

			this.cachePgtoParcela = (HashMap<Long, PagamentoParcela>) oh.readObject();

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

	public ArrayList<PagamentoParcela> getList() {
		return new ArrayList(cachePgtoParcela.values());
	}

}
