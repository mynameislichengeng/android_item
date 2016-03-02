package com.xguanjia.hx.version;

/**
 * 表结果中的字段
 * @author ytg
 * @date 2012-09-13
 */
public class Field implements Cloneable {
	/** Value returned by {@link #getType(int)} if the specified  column type is integer */
	public static final int FIELD_TYPE_INTEGER = 1;
	
	/** Value returned by {@link #getType(int)} if the specified column type is float */
	public static final int FIELD_TYPE_FLOAT = 2;

    /** Value returned by {@link #getType(int)} if the specified column type is text */
	public static final int FIELD_TYPE_TEXT = 3;

    /** Value returned by {@link #getType(int)} if the specified column type is blob */
	public static final int FIELD_TYPE_BLOB = 4;
	
	/** Value returned by {@link #getType(int)} if the specified column type is char */
	public static final int FIELD_TYPE_CHAR = 5;
	
	/** Value returned by {@link #getType(int)} if the specified column type is varchar */
	public static final int FIELD_TYPE_VARCHAR = 6;
	
	
	//域名称(字段名)	
	private String name;	
	//类型	
	private int type;
	//长度	
	private int length;
	//默认值	
	private Object defaultValue;
	//是否主键	
	private boolean primateKey;
	//是否索引	
	private boolean indexFlag;
	public String getName() {
		return name;
	}
	public void setName(String name) {		
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	public boolean isPrimateKey() {
		return primateKey;
	}
	public void setPrimateKey(boolean primateKey) {
		this.primateKey = primateKey;
	}
	public boolean isIndexFlag() {
		return indexFlag;
	}
	public void setIndexFlag(boolean indexFlag) {
		this.indexFlag = indexFlag;
	}	
	
	/**
	 * Method of construction
	 * @param name
	 * @param type
	 * @param length
	 */
	public Field(String name, int type, int length) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;		
		this.primateKey = false;
		this.indexFlag = false;
	}
	
	/**
	 * Method of construction
	 * @param name
	 * @param type
	 * @param length
	 * @param default_value
	 * @param primateKey
	 * @param indexFlag
	 */
	public Field(String name, int type, int length, Object default_value,
			boolean primateKey, boolean indexFlag) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
		this.defaultValue = default_value;
		this.primateKey = primateKey;
		this.indexFlag = indexFlag;
	}
	
	public String toSqlString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name+" ");
		switch(type){
			case FIELD_TYPE_INTEGER :
				sb.append("INT");
				break;
			case FIELD_TYPE_FLOAT :
				sb.append("FLOAT");
				break;	
			case FIELD_TYPE_TEXT :
				sb.append("TEXT");
				break;	
			case FIELD_TYPE_BLOB :
				sb.append("BLOB");
				break;	
			case FIELD_TYPE_CHAR :
				sb.append("CHAR");
				break;	
			case FIELD_TYPE_VARCHAR :
				sb.append("VARCHAR");
				break;	
		}
		if(length > 0){
			sb.append("("+length+")");
		}
		if(primateKey){
			sb.append(" primary key");			
		}
		if(defaultValue != null){
			sb.append(" " +String.valueOf(defaultValue));
		}
		return sb.toString();
	}
	
	public Field clone(){
		try {
			return (Field)super.clone();
		} catch (CloneNotSupportedException e) {			
			e.printStackTrace();
		}
		return null;
	}

}
