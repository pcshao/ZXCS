package bean.orders;

import java.util.ArrayList;

import bean.Goods;


/**
 * 盘点单据
 */
public class PdOrder {
	
	private String id;
	private String odate;
	private String dname;
	private String operator;
	private String bz;
	ArrayList<Goods> pdGoods;	//被盘点商品，在pdOrdersDetails表内
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOdate() {
		return odate;
	}
	public void setOdate(String odate) {
		this.odate = odate;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}

}
