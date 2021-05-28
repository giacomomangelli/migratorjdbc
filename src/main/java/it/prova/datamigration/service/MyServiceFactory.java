package it.prova.datamigration.service;

import it.prova.datamigration.dao.assicurato.AssicuratoDaoImpl;
import it.prova.datamigration.dao.notprocessed.NotProcessedDaoImpl;
import it.prova.datamigration.dao.oldschemaobj.OldSchemaObjDaoImpl;

public class MyServiceFactory {
	
	public static DataMigrationService dataMigrationServiceImpl() {
		DataMigrationService dataMigrationServiceInstance = new DataMigrationService();
		dataMigrationServiceInstance.setAssicuratoDaoInstance(new AssicuratoDaoImpl());
		dataMigrationServiceInstance.setNotProcessedDaoInstance(new NotProcessedDaoImpl());
		dataMigrationServiceInstance.setOldSchemaDaoObjInstance(new OldSchemaObjDaoImpl());
		return dataMigrationServiceInstance;
	}
	

}
