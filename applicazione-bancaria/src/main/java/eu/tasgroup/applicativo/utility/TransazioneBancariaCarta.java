package eu.tasgroup.applicativo.utility;

import java.util.Objects;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;

public class TransazioneBancariaCarta {
	private TransazioneBancaria transazioneBancaria;
	long codCarta;
	public TransazioneBancaria getTransazioneBancaria() {
		return transazioneBancaria;
	}
	public void setTransazioneBancaria(TransazioneBancaria transazioneBancaria) {
		this.transazioneBancaria = transazioneBancaria;
	}
	public long getCodCarta() {
		return codCarta;
	}
	public void setCodCarta(long codCarta) {
		this.codCarta = codCarta;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codCarta, transazioneBancaria);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransazioneBancariaCarta other = (TransazioneBancariaCarta) obj;
		return codCarta == other.codCarta && Objects.equals(transazioneBancaria, other.transazioneBancaria);
	}
	@Override
	public String toString() {
		return "TransazioneBancariaCarta [transazioneBancaria=" + transazioneBancaria + ", codCarta=" + codCarta + "]";
	}
	
	
	
}
