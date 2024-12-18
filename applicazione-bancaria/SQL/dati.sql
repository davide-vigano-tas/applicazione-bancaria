-- ADMIN --Pass01$

INSERT INTO amministratori(cod_admin,nome_admin,cognome_admin,email_admin,password_admin,tentativi_errati) 
VALUES (1,"Mario", "Rossi", "mario.rossi@tasgroup.eu", "$2a$10$YhROqGpOSPT.BBesLIPwq.p72Bxh.UgbIK9tYzp.Hh47x0hKWeVp6",0);
INSERT INTO permessi_amministratori(cod_permesso, ruolo, admin_id)
VALUES (1, 0, 1);
INSERT INTO amministratori(cod_admin,nome_admin,cognome_admin,email_admin,password_admin,tentativi_errati) 
VALUES (2,"Sam", "Mast", "samuel.mastrelli@tasgroup.eu", "$2a$10$YhROqGpOSPT.BBesLIPwq.p72Bxh.UgbIK9tYzp.Hh47x0hKWeVp6",0);
