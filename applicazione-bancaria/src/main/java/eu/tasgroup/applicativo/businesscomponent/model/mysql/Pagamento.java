package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMetodo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "pagamenti")
public class Pagamento implements Serializable{

	
	private static final long serialVersionUID = -4685309063918028660L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_pagamento")
	private long codPagamento; 
	
	@Column(name = "importo",  nullable = false)
	private double importo;
	
	@Column(name = "data_pagamento",  nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataPagamento;
	
	@Column(name="metodo_pagamento",  nullable = false)
	private TipoMetodo metodoPagamento;
	
	@ManyToOne
	@JoinColumn(name = "cod_cliente", nullable = false)
	private Cliente cliente;

	public long getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(long codPagamento) {
		this.codPagamento = codPagamento;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public TipoMetodo getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(TipoMetodo metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(cliente, codPagamento, dataPagamento, importo, metodoPagamento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		return Objects.equals(cliente, other.cliente) && codPagamento == other.codPagamento
				&& Objects.equals(dataPagamento, other.dataPagamento)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& metodoPagamento == other.metodoPagamento;
	}

	@Override
	public String toString() {
		return "Pagamento [codPagamento=" + codPagamento + ", importo=" + importo + ", dataPagamento=" + dataPagamento
				+ ", metodoPagamento=" + metodoPagamento + ", cliente=" + cliente + "]";
	}

	
	
}
