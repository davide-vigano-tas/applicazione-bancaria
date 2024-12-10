package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "Amministratori")
public class Amministratore implements Serializable {

    private static final long serialVersionUID = -2015738704197131910L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cod_admin")
    private long codAdmin;

    @Column(name = "nome_admin", nullable = false, length = 50)
    private String nomeAdmin;

    @Column(name = "cognome_admin", nullable = false, length = 50)
    private String cognomeAdmin;

    @Column(name = "email_admin", nullable = false, unique = true, length = 100)
    private String emailAdmin;

    @Column(name = "password_admin", nullable = false, length = 100)
    private String passwordAdmin;

    @Column(name = "tentativi_errati")
    private short tentativiErrati = 0;

    @Column(name = "account_bloccato")
    private boolean accountBloccato = false;

	public long getCodAdmin() {
		return codAdmin;
	}

	public void setCodAdmin(long codAdmin) {
		this.codAdmin = codAdmin;
	}

	public String getNomeAdmin() {
		return nomeAdmin;
	}

	public void setNomeAdmin(String nomeAdmin) {
		this.nomeAdmin = nomeAdmin;
	}

	public String getCognomeAdmin() {
		return cognomeAdmin;
	}

	public void setCognomeAdmin(String cognomeAdmin) {
		this.cognomeAdmin = cognomeAdmin;
	}

	public String getEmailAdmin() {
		return emailAdmin;
	}

	public void setEmailAdmin(String emailAdmin) {
		this.emailAdmin = emailAdmin;
	}

	public String getPasswordAdmin() {
		return passwordAdmin;
	}

	public void setPasswordAdmin(String passwordAdmin) {
		this.passwordAdmin = passwordAdmin;
	}

	public short getTentativiErrati() {
		return tentativiErrati;
	}

	public void setTentativiErrati(short tentativiErrati) {
		this.tentativiErrati = tentativiErrati;
	}

	public boolean isAccountBloccato() {
		return accountBloccato;
	}

	public void setAccountBloccato(boolean accountBloccato) {
		this.accountBloccato = accountBloccato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountBloccato, codAdmin, cognomeAdmin, emailAdmin, nomeAdmin, passwordAdmin,
				tentativiErrati);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Amministratore other = (Amministratore) obj;
		return accountBloccato == other.accountBloccato && codAdmin == other.codAdmin
				&& Objects.equals(cognomeAdmin, other.cognomeAdmin) && Objects.equals(emailAdmin, other.emailAdmin)
				&& Objects.equals(nomeAdmin, other.nomeAdmin) && Objects.equals(passwordAdmin, other.passwordAdmin)
				&& tentativiErrati == other.tentativiErrati;
	}

	@Override
	public String toString() {
		return "Amministratore [codAdmin=" + codAdmin + ", nomeAdmin=" + nomeAdmin + ", cognomeAdmin=" + cognomeAdmin
				+ ", emailAdmin=" + emailAdmin + ", passwordAdmin=" + passwordAdmin + ", tentativiErrati="
				+ tentativiErrati + ", accountBloccato=" + accountBloccato + "]";
	}

   
}
