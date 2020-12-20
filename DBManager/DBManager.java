package DBManager;

import java.sql.*;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;

public class DBManager {
	private String DBName;
	private String DBUrl;
	private String DBUser;
	private String DBPassword;
	
	public DBManager(String name) {
		this.DBName = name;
	}
	
	public void setURL(String url) {
		this.DBUrl = url + this.DBName;
	}
	
	public void setCredentials(String user, String password) {
		this.DBUser = user;
		this.DBPassword = password;
	}
	
	public String getDBName() {
		return this.DBName;
	}
	
	private Connection connect() throws SQLException {
		Properties props = new Properties();
		props.setProperty("user", this.DBUser);
		props.setProperty("password", this.DBPassword);
		return DriverManager.getConnection(this.DBUrl, props);
	}
	
	
	/*
	 * La funzione createTable riceve in input una stringa riportante il nome da assegnare alla tabella
	 * e un array di colonne (Column), ognuna delle quale dispone di un nome, dei tipi di dato contenuto
	 * e delle proprietà booleane 'UNIQUE', 'NOT NULL' e 'PRIMARY KEY'.
	 * Restituisce 'true' a creazione avvenuta, 'false' in caso di errori.
	*/
	public boolean createTable(String name, Column... columns) throws SQLException {
		String SQLCommand = "CREATE TABLE IF NOT EXISTS "+name+"(";
		int index = 0;
		for(Column column: columns) {
			SQLCommand = SQLCommand + column.getName()+" "+column.getDataType().toString()+(column.isUnique() ? " UNIQUE" : "")+(column.isNotNULL() ? " NOT NULL" : "")+(column.isPrimaryKey() ? " PRIMARY KEY" : "");
			index++;
			if(index < columns.length)
				SQLCommand = SQLCommand + ", ";
		}
		SQLCommand = SQLCommand + ");";
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQLCommand);
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}
	
	public boolean deleteTable(String name) throws SQLException {
		String SQLCommand = "DROP TABLE "+name+";";
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQLCommand);
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}
	
	public boolean insertRow(String table, Row... rows) throws SQLException {
		int errors = 0;
		String SQLCommand = "INSERT INTO " + table + " (";
		for(Row row: rows) {
			for(int i=0;i<row.getValuesNumber();i++) {
				SQLCommand = SQLCommand + row.getIndex(i);
				if(i<row.getValuesNumber() - 1)
					SQLCommand = SQLCommand + ", ";
			}
			SQLCommand = SQLCommand + ") VALUES (";
			for(int j=0;j<row.getValuesNumber();j++) {
				SQLCommand = SQLCommand + "'" + row.getObject(j) + "'";
				if(j<row.getValuesNumber() - 1)
					SQLCommand = SQLCommand + ", ";
			}
			SQLCommand = SQLCommand + ");";
			Connection connection = this.connect();
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(SQLCommand);
				statement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println(e.getLocalizedMessage());
				errors++;
			}
		}
		System.out.println(SQLCommand);
		if(errors > 0)
			return false;
		else
			return true;
	}
	
	private String getSelectCommand(String table, String...indexes) {
		String SQLCommand = "SELECT ";
		if(indexes.length == 1)
			SQLCommand = SQLCommand + indexes[0] + " FROM " + table + " ";
		else {
			for(String index: indexes)
				SQLCommand = SQLCommand + index + ", ";
			SQLCommand = SQLCommand.substring(0, SQLCommand.length() - 2) + " FROM " + table;
		}
		return SQLCommand;
	}
	
	public Object[][] select(String table, String... indexes) throws SQLException {
		Object[][] results = new Object[0][0];
		String SQLCommand = this.getSelectCommand(table, indexes);
		SQLCommand = SQLCommand + ";";
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(SQLCommand);
			while(resultset.next()) {
				Object[] row = new Object[0];
				for(String index: indexes) {
					row = ArrayUtils.add(row, resultset.getObject(index));
				}
				results = ArrayUtils.add(results, row);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return results;
	}
	
	public Object[][] select(String table, Condition condition, String... indexes) throws SQLException {
		Object[][] results = new Object[0][0];
		String SQLCommand = this.getSelectCommand(table, indexes);
		SQLCommand = SQLCommand + " " + condition.getSQLCondition() + ";";
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(SQLCommand);
			while(resultset.next()) {
				Object[] row = new Object[0];
				for(String index: indexes) {
					row = ArrayUtils.add(row, resultset.getObject(index));
				}
				results = ArrayUtils.add(results, row);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return results;
	}
	
	private String getUpdateCommand(String table, Row row) {
		String SQLCommand = "UPDATE " + table + " SET ";
		for(int i=0;i<row.getValuesNumber();i++)
			SQLCommand = SQLCommand + row.getIndex(i) + " = '" + row.getObject(i) + "', ";
		SQLCommand = SQLCommand.substring(0, SQLCommand.length() - 2);
		return SQLCommand;
	}
	
	public boolean update(String table, Row row) throws SQLException {
		int errors = 0;
		String SQLCommand = this.getUpdateCommand(table,  row);
		SQLCommand = SQLCommand + ";";
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQLCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			errors++;
		}
		if(errors > 0)
			return false;
		else
			return true;
	}
	
	public boolean update(String table, Condition condition, Row row) throws SQLException {
		int errors = 0;
		String SQLCommand = this.getUpdateCommand(table,  row);
		SQLCommand = SQLCommand + " " + condition.getSQLCondition() + ";";
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQLCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			errors++;
		}
		if(errors > 0)
			return false;
		else
			return true;
	}
	
	public boolean delete(String table) throws SQLException {
		int errors = 0;
		String SQLCommand = "DELETE FROM " + table + ";";
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQLCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			errors++;
		}
		if(errors > 0)
			return false;
		else
			return true;
	}
	
	public boolean delete(String table, Condition condition) throws SQLException {
		int errors = 0;
		String SQLCommand = "DELETE FROM " + table + " " + condition.getSQLCondition();
		SQLCommand = SQLCommand + ";";
		System.out.println(SQLCommand);
		Connection connection = this.connect();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQLCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			errors++;
		}
		if(errors > 0)
			return false;
		else
			return true;
	}
}
