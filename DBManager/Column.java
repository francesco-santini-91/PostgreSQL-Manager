package DBManager;

public class Column {
	private String name;
	private String dataType;
	private boolean unique;
	private boolean notNULL;
	private boolean primaryKey;
	
	public Column(String name, String dataType, boolean unique, boolean notNULL, boolean primaryKey) {
		this.name = name;
		this.dataType = dataType;
		this.unique = unique;
		this.notNULL = notNULL;
		this.primaryKey = primaryKey;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	
	public boolean isUnique() {
		return this.unique;
	}
	
	public boolean isNotNULL() {
		return this.notNULL;
	}
	
	public boolean isPrimaryKey() {
		return this.primaryKey;
	}
}
