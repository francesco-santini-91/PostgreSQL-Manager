package DBManager;

public class Condition {
	private String index;
	private IS condition;
	private Object[] value;
	
	public Condition(String index, IS condition, Object... value) {
		this.index = index;
		this.condition = condition;
		this.value = value;
	}
	
	protected String getSQLCondition() {
		String SQLCondition = "";
		switch(this.condition) {
			case EQUAL:
				SQLCondition = "WHERE " + this.index + " = " + this.value[0];
				break;
			case NOT_EQUAL:
				SQLCondition = "WHERE " + this.index + " != " + this.value[0];
				break;
			case LESS_THAN:
				SQLCondition = "WHERE " + this.index + " < " + this.value[0];
				break;
			case LESS_OR_EQUAL_THAN:
				SQLCondition = "WHERE " + this.index + " <= " + this.value[0];
				break;
			case GREATER_THAN:
				SQLCondition = "WHERE " + this.index + " > " + this.value[0];
				break;
			case GREATER_OR_EQUAL_THAN:
				SQLCondition = "WHERE " + this.index + " >= " + this.value[0];
				break;
			case BETWEEN:
				SQLCondition = "WHERE " + this.index + " BETWEEN " + this.value[0] + " AND " + this.value[1];
				break;
			case NULL:
				SQLCondition = "WHERE " + this.index + " IS NULL";
				break;
			case NOT_NULL:
				SQLCondition = "WHERE " + this.index + " IS NOT NULL";
				break;
			case LIKE:
				SQLCondition = "WHERE " + this.index + " LIKE '" + this.value[0] + "%'";
				break;
			default:
				System.out.println("Errore! Condizione non gestita!");
		}
		return SQLCondition;
	}
	
	
}
