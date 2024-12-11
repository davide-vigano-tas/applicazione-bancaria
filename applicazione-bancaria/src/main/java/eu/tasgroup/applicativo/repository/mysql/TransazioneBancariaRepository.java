package eu.tasgroup.applicativo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;

public interface TransazioneBancariaRepository extends JpaRepository<TransazioneBancaria, Long> {

}
