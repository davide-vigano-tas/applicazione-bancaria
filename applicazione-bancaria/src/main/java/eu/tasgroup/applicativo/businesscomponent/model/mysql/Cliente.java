package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Clienti")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1630377583176399976L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cod_cliente")
    private long codCliente;

    @Column(name = "nome_cliente", nullable = false, length = 50)
    private String nomeCliente;

    @Column(name = "cognome_cliente", nullable = false, length = 50)
    private String cognomeCliente;

    @Column(name = "email_cliente", nullable = false, unique = true, length = 100)
    private String emailCliente;

    @Column(name = "password_cliente", nullable = false, length = 100)
    private String passwordCliente;

    @Column(name = "tentativi_errati")
    private int tentativiErrati = 0;

    @Column(name = "account_bloccato", columnDefinition = "tinyint(1) default 0")
    private boolean accountBloccato = false;

    @Column(name = "saldo_conto", nullable = false)
    private double saldoConto = 0.0;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Conto> conti = new HashSet<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Carta> carte = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Prestito> prestiti = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RichiestaPrestito> richiestePrestiti = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Pagamento> pagamenti = new HashSet<>();
    
	public long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(long codCliente) {
		this.codCliente = codCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCognomeCliente() {
		return cognomeCliente;
	}

	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getPasswordCliente() {
		return passwordCliente;
	}

	public void setPasswordCliente(String passwordCliente) {
		this.passwordCliente = passwordCliente;
	}

	public int getTentativiErrati() {
		return tentativiErrati;
	}

	public void setTentativiErrati(int tentativiErrati) {
		this.tentativiErrati = tentativiErrati;
	}

	public boolean isAccountBloccato() {
		return accountBloccato;
	}

	public void setAccountBloccato(boolean accountBloccato) {
		this.accountBloccato = accountBloccato;
	}

	public double getSaldoConto() {
		return saldoConto;
	}

	public void setSaldoConto(double saldoConto) {
		this.saldoConto = saldoConto;
	}

	public Set<Conto> getConti() {
		return conti;
	}

	public void setConti(Set<Conto> conti) {
		this.conti = conti;
	}

	public Set<Carta> getCarte() {
		return carte;
	}

	public void setCarte(Set<Carta> carte) {
		this.carte = carte;
	}

	public Set<Prestito> getPrestiti() {
		return prestiti;
	}

	public void setPrestiti(Set<Prestito> prestiti) {
		this.prestiti = prestiti;
	}

	public Set<RichiestaPrestito> getRichiestePrestiti() {
		return richiestePrestiti;
	}

	public void setRichiestePrestiti(Set<RichiestaPrestito> richiestePrestiti) {
		this.richiestePrestiti = richiestePrestiti;
	}

	public Set<Pagamento> getPagamenti() {
		return pagamenti;
	}

	public void setPagamenti(Set<Pagamento> pagamenti) {
		this.pagamenti = pagamenti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountBloccato, codCliente, cognomeCliente, emailCliente, nomeCliente,
				 passwordCliente, saldoConto, tentativiErrati);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return accountBloccato == other.accountBloccato
				&& codCliente == other.codCliente && Objects.equals(cognomeCliente, other.cognomeCliente)
				&& Objects.equals(emailCliente, other.emailCliente)
				&& Objects.equals(nomeCliente, other.nomeCliente) 
				&& Objects.equals(passwordCliente, other.passwordCliente)
				&& Double.doubleToLongBits(saldoConto) == Double.doubleToLongBits(other.saldoConto)
				&& tentativiErrati == other.tentativiErrati;
	}

	@Override
	public String toString() {
		return "Cliente [codCliente=" + codCliente + ", nomeCliente=" + nomeCliente + ", cognomeCliente="
				+ cognomeCliente + ", emailCliente=" + emailCliente + ", passwordCliente=" + passwordCliente
				+ ", tentativiErrati=" + tentativiErrati + ", accountBloccato=" + accountBloccato + ", saldoConto="
				+ saldoConto + "]";
	}

	
	
	
}
