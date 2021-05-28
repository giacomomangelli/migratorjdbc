package it.prova.datamigration.service;

import it.prova.datamigration.dao.assicurato.AssicuratoDaoImpl;
import it.prova.datamigration.dao.notprocessed.NotProcessedDaoImpl;
import it.prova.datamigration.dao.oldschemaobj.OldSchemaObjDaoImpl;

public class MyServiceFactory {
	
	private static DataMigrationService dataMigrationServiceInstance = null;
	
	public static DataMigrationService getMigrationServiceInstance() {
		if(dataMigrationServiceInstance==null) {
			dataMigrationServiceInstance = new DataMigrationService();
		}
		dataMigrationServiceInstance.setAssicuratoDaoInstance(new AssicuratoDaoImpl());
		dataMigrationServiceInstance.setNotProcessedDaoInstance(new NotProcessedDaoImpl());
		dataMigrationServiceInstance.setOldSchemaDaoObjInstance(new OldSchemaObjDaoImpl());
		return dataMigrationServiceInstance;
	}
	

}
