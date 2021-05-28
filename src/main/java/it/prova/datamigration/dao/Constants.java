package it.prova.datamigration.dao;

public interface Constants {

	public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public static final String CONNECTION_URL_OLD = "jdbc:mysql://localhost:3306/schema_old?user=root&password=root&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	public static final String CONNECTION_URL_NEW = "jdbc:mysql://localhost:3306/schemanew?user=root&password=root&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

}
