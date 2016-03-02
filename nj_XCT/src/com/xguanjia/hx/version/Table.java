package com.xguanjia.hx.version;

import java.util.ArrayList;
import java.util.List;


/**
 * 数据库表结构
 * @author ytg
 * @date 2012-09-13
 */
public class Table {
	private String name;
	private List<Field> fields;
	
	public Table(String name) {
		super();
		this.name = name;
	}
	
	public Table(String name,List<Field> fields) {
		super();
		this.name = name;
		this.fields = fields;
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	/**
	 * 添加字段
	 * @param field
	 */
	public void addField(Field field){
		if(fields == null){
			fields = new ArrayList<Field>();
		}
		fields.add(field);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String toSqlString(){
		StringBuilder sb = new StringBuilder();
		String t = getName();		
		sb.append("create table "+t+" ( ");
		for(Field field : fields){
			sb.append(field.toSqlString());
			sb.append(" ,");
		}
		sb.replace(sb.length()-1, sb.length(), " )");
		return sb.toString();
	}
}