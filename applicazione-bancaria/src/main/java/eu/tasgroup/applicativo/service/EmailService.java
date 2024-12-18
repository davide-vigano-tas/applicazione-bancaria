package eu.tasgroup.applicativo.service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;

public interface EmailService {
	void tooManyTries(String email);
	void movimentoEffettuato(String email, MovimentoConto mc);
	void sendResetLinkAdmin(String url, String email);
	void sendPasswordConfirmationAdmin(String email);
	void sendResetLinkClient(String url, String email);
	void sendPasswordConfirmationCliente(String emailCliente);
}
