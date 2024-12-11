package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transazione_bancaria")
public class TransazioneBancaria implements Serializable {

	private static final long serialVersionUID = -2264301147966168476L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_transazione_bancaria")
	private long codTransazioneBancaria; 
	
	@Column(name="importo", nullable = false)
	private double importo;
	
	@Column(name = "data_transazione", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataTransazione;
	
	@Column(name="tipo_transazione",  nullable = false)
	private TipoTransazione tipoTransazione;
	
	@ManyToOne
	@JoinColumn(name = "cod_conto_origine", nullable = false)
	private Conto contoOrigine;
	
	@ManyToOne
	@JoinColumn(name = "cod_conto_destinazione", nullable = false)
	private Conto contoDestinazione;
}
