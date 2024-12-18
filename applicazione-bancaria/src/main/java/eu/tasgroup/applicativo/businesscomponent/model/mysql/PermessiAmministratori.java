package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Objects;

import eu.tasgroup.applicativo.businesscomponent.enumerated.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "permessi_amministratori")
public class PermessiAmministratori implements Serializable {

	private static final long serialVersionUID = 1842970590973093381L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cod_permesso")
    private long codPermesso;
    
    @Column(name = "ruolo" , nullable = false)
    private Role ruolo;
    
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Amministratore amministratore;

	public long getCodPermesso() {
		return codPermesso;
	}

	public void setCodPermesso(long codPermesso) {
		this.codPermesso = codPermesso;
	}

	public Role getRuolo() {
		return ruolo;
	}

	public void setRuolo(Role ruolo) {
		this.ruolo = ruolo;
	}

	public Amministratore getAmministratore() {
		return amministratore;
	}

	public void setAmministratore(Amministratore amministratore) {
		this.amministratore = amministratore;
	}

	@Override
	public String toString() {
		return "PermessiAmministratori [codPermesso=" + codPermesso + ", ruolo=" + ruolo + ", amministratore="
				+ amministratore + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amministratore, codPermesso, ruolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermessiAmministratori other = (PermessiAmministratori) obj;
		return Objects.equals(amministratore, other.amministratore) && codPermesso == other.codPermesso
				&& ruolo == other.ruolo;
	}

    
    
}
