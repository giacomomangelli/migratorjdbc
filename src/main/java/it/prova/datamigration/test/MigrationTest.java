package it.prova.datamigration.test;

import it.prova.datamigration.service.DataMigrationService;
import it.prova.datamigration.service.MyServiceFactory;

public class MigrationTest {
	
	public static void main(String[] args) throws Exception {
		DataMigrationService service = MyServiceFactory.getMigrationServiceInstance(); 
		service.inserimentoAssicurati();
	}

}
