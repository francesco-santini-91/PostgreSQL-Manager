package DBManager;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

public class Row {
	private String[] indexes;
	private Object[] objects;
	
	public Row() {
		
	}
	
	public void add(String index, Object object) {
		this.indexes = ArrayUtils.add(this.indexes, index);
		switch(object.getClass().toString()) {
			case "class java.lang.Character":		//<-- CHAR
				this.objects = ArrayUtils.add(this.objects, object.toString());
				break;
			case "class java.lang.String":			//<-- STRING
				this.objects = ArrayUtils.add(this.objects, object);
				break;
			case "class java.lang.Boolean":			//<-- BOOLEAN
				this.objects = ArrayUtils.add(this.objects, object.toString());
				break;
			case "class java.lang.Integer":			//<-- INT
				this.objects = ArrayUtils.add(this.objects, object.toString());
				break;
			case "class java.lang.Float":			//<-- FLOAT
				this.objects = ArrayUtils.add(this.objects, object.toString());
				break;
			case "class java.lang.Double":			//<-- DOUBLE
				this.objects = ArrayUtils.add(this.objects, object.toString());
				break;
			case "class java.lang.Long":			//<-- LONG INT
				this.objects = ArrayUtils.add(this.objects,  object.toString());
				break;
			case "class java.util.Date":			//<-- DATE
				this.objects = ArrayUtils.add(this.objects, object.toString());
				break;
			case "class [C":						//<-- CHAR[]
				String arrayCHAR = "{";
				for(char __char: (char[]) object) {
					arrayCHAR = arrayCHAR + '"' + __char + '"' + ",";
				}
				arrayCHAR = arrayCHAR.substring(0, arrayCHAR.length() - 1);
				arrayCHAR = arrayCHAR + "}";
				this.objects = ArrayUtils.add(this.objects, arrayCHAR);
				break;
			case "class [Ljava.lang.String;":		//<-- STRING[]
				String arrayString = "{";
				for(String __string: (String[]) object) {
					arrayString = arrayString + '"' + __string + '"' + ",";
				}
				arrayString = arrayString.substring(0, arrayString.length() - 1);
				arrayString = arrayString + "}";
				this.objects = ArrayUtils.add(this.objects, arrayString);
				break;
			case "class [I":						//<-- INT[]
				String arrayINT = "{";
				for(int __int: (int[]) object) {
					arrayINT = arrayINT + '"' + __int + '"' + ",";
				}
				arrayINT = arrayINT.substring(0, arrayINT.length() - 1);
				arrayINT = arrayINT + "}";
				this.objects = ArrayUtils.add(this.objects, arrayINT);
				break;
			case "class [F":						//<-- FLOAT[]
				String arrayFLOAT = "{";
				for(float __float: (float[]) object) {
					arrayFLOAT = arrayFLOAT + '"' + __float + '"' + ",";
				}
				arrayFLOAT = arrayFLOAT.substring(0, arrayFLOAT.length() - 1);
				arrayFLOAT = arrayFLOAT + "}";
				this.objects = ArrayUtils.add(this.objects, arrayFLOAT);
				break;
			case "class [D":						//<-- DOUBLE[]
				String arrayDOUBLE = "{";
				for(double __double: (double[]) object) {
					arrayDOUBLE = arrayDOUBLE + '"' + __double + '"' + ",";
				}
				arrayDOUBLE = arrayDOUBLE.substring(0, arrayDOUBLE.length() - 1);
				arrayDOUBLE = arrayDOUBLE + "}";
				this.objects = ArrayUtils.add(this.objects, arrayDOUBLE);
				break;
			case "class [J":						//<-- LONG INT[]
				String arrayLONG = "{";
				for(long __long: (long[]) object) {
					arrayLONG = arrayLONG + '"' + __long + '"' + ",";
				}
				arrayLONG = arrayLONG.substring(0, arrayLONG.length() - 1);
				arrayLONG = arrayLONG + "}";
				this.objects = ArrayUtils.add(this.objects, arrayLONG);
				break;
			case "class [Z":						//<-- BOOLEAN[]
				String arrayBOOLEAN = "{";
				for(boolean __boolean: (boolean[]) object) {
					arrayBOOLEAN = arrayBOOLEAN + '"' + __boolean + '"' + ",";
				}
				arrayBOOLEAN = arrayBOOLEAN.substring(0, arrayBOOLEAN.length() - 1);
				arrayBOOLEAN = arrayBOOLEAN + "}";
				this.objects = ArrayUtils.add(this.objects, arrayBOOLEAN);
				break;
			case "class [Ljava.util.Date;":			//<-- DATE[]
				String arrayDATE = "{";
				for(Date __date: (Date[]) object) {
					arrayDATE = arrayDATE + '"' + __date + '"' + ",";
				}
				arrayDATE = arrayDATE.substring(0, arrayDATE.length() - 1);
				arrayDATE = arrayDATE + "}";
				this.objects = ArrayUtils.add(this.objects, arrayDATE);
				break;
			default:
				System.out.println("Tipo di dato " + object.getClass().toString() + " non gestito!");
		}
	}
	
	public void set(String index, Object object) {
		this.add(index, object);
	}
	
	public void remove(String index) {
		int __index = ArrayUtils.indexOf(this.indexes, index);
		this.objects[__index] = null;
	}
	
	public int getValuesNumber() {
		return ArrayUtils.getLength(this.indexes);
	}
	
	public String getIndex(int i) {
		return ArrayUtils.get(this.indexes, i);
	}
	
	public Object getObject(int i) {
		return ArrayUtils.get(this.objects, i);
	}
	
	public Object getObject(String index) {
		int __index = ArrayUtils.indexOf(this.indexes, index);
		return this.objects[__index];
	}
}
