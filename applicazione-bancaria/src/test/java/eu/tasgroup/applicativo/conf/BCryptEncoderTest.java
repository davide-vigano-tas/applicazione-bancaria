package eu.tasgroup.applicativo.conf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BCryptEncoderTest {
	
	@Test
	void testEncode() {
		String password = "Pass01$";
		String passwordCriptata = BCryptEncoder.encode(password);
		
		assertTrue(BCryptEncoder.passwordMatch(password, passwordCriptata));
		System.out.println("Password Criptata: " + passwordCriptata);
	}

}
