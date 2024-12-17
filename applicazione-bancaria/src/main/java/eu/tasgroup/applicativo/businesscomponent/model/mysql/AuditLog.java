package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "audit_log")
public class AuditLog implements Serializable {

	private static final long serialVersionUID = 5914875033315550569L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cod_accessi")
    private long codAccessi;
    
    @Column(name = "data_log", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date dataLog;
    
    
    @Column(name = "dettagli", nullable = false)
    private String dettagli;
    
    
    @ManyToOne
    @JoinColumn(name = "admin", nullable = false)
    private Amministratore admin;
    
    


	public Amministratore getAdmin() {
		return admin;
	}


	public void setAdmin(Amministratore admin) {
		this.admin = admin;
	}


	public long getCodAccessi() {
		return codAccessi;
	}


	public void setCodAccessi(long codAccessi) {
		this.codAccessi = codAccessi;
	}


	public Date getDataLog() {
		return dataLog;
	}


	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}


	public String getDettagli() {
		return dettagli;
	}


	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}


	@Override
	public int hashCode() {
		return Objects.hash(codAccessi, dataLog, dettagli);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditLog other = (AuditLog) obj;
		return codAccessi == other.codAccessi && Objects.equals(dataLog, other.dataLog)
				&& Objects.equals(dettagli, other.dettagli);
	}


	@Override
	public String toString() {
		return "AuditLog [codAccessi=" + codAccessi + ", dataLog=" + dataLog + ", dettagli=" + dettagli + "]";
	}


	


	
    
    
	

}
