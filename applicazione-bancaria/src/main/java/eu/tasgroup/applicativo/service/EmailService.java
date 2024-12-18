package eu.tasgroup.applicativo.service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;

public interface EmailService {
	void tooManyTries(String email);
	void movimentoEffettuato(String email, MovimentoConto mc);
	void modificaCredenziali(String email);
	void sendResetLinkAdmin(String url, String email);
	void sendPasswordConfirmationAdmin(String email);
}
