package bean;

/**
 * 商品单位
 */
public class Units {
	
	private int unid;
	private String name;
	private String fz;
	
	public Units() {
		
	}

	public Units(int unid, String name) {
		this.unid = unid;
		this.name = name;
	}
	
	public int getUnid() {
		return unid;
	}
	public void setUnid(int unid) {
		this.unid = unid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFz() {
		return fz;
	}
	public void setFz(String fz) {
		this.fz = fz;
	}
	
}
