package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "admin_reset_token")
public class AdminResetToken {
 
    private static final int EXPIRATION =  60000 * 2;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
    @Column(name = "token", nullable = false, unique = true)
    private String token;
 
    @OneToOne(targetEntity = Amministratore.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "admin_id")
    private Amministratore admin;
 
    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date expiryDate;

	public static int getExpiration() {
		return EXPIRATION;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Amministratore getAdmin() {
		return admin;
	}

	public void setAdmin(Amministratore admin) {
		this.admin = admin;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(admin, expiryDate, id, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminResetToken other = (AdminResetToken) obj;
		return Objects.equals(admin, other.admin) && Objects.equals(expiryDate, other.expiryDate) && id == other.id
				&& Objects.equals(token, other.token);
	}

	@Override
	public String toString() {
		return "AdminResetToken [id=" + id + ", token=" + token + ", admin=" + admin + ", expiryDate=" + expiryDate
				+ "]";
	}
    
    
    
}