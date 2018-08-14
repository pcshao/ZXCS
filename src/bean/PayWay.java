package bean;

/**
 * 支付方式
 */
public class PayWay {

	private int pid;
	private String name;
	
	/*
	 * 提供无参构造器
	 */
	public PayWay(){
		
	}
	/*
	 * 最小构造器
	 */
	public PayWay(int pid) {
		this.pid = pid;
	}
	/*
	 * 全构造器
	 */
	public PayWay(int pid, String name) {
		this.pid = pid;
		this.name = name;
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
}
