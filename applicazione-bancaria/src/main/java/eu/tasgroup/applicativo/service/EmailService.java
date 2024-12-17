package eu.tasgroup.applicativo.service;

public interface EmailService {
	void tooManyTries(String email);
	void movimentoEffettuato(String email);
	void modificaCredenziali(String email);
}
