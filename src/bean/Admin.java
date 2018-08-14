package bean;

public class Admin {
	
	private int aid;
	private String name;
	private String password;
	private String job;
	private String power;
	private String isGuiZhang;
	private String isPOS;
	private String isUse;
	
	/*
	 * 提供无参构造器
	 */
	public Admin(){
		
	}
	/*
	 * 最小构造器
	 */
	public Admin(int aid, String name) {
		this.aid = aid;
		this.name = name;
	}
	public Admin(int aid, String name, String password, String isUse) {
		super();
		this.aid = aid;
		this.name = name;
		this.password = password;
		this.isUse = isUse;
	}
	/*
	 * 全构造器
	 */
	public Admin(int aid, String name, String password, String job, String power, String isGuiZhang, String isPOS,
			String isUse) {
		this.aid = aid;
		this.name = name;
		this.password = password;
		this.job = job;
		this.power = power;
		this.isGuiZhang = isGuiZhang;
		this.isPOS = isPOS;
		this.isUse = isUse;
	}
	@Override
	public String toString() {
		return "Admin [aid=" + aid + ", name=" + name + ", job=" + job + ", power=" + power + ", isGuiZhang="
				+ isGuiZhang + ", isPOS=" + isPOS + "]";
	}
	public String toWelcome() {
		return "欢迎您，"+getJob()+"--"+getName()+",祝您今日工作愉快~";
	}
	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getIsGuiZhang() {
		return isGuiZhang;
	}

	public void setIsGuiZhang(String isGuiZhang) {
		this.isGuiZhang = isGuiZhang;
	}

	public String getIsPOS() {
		return isPOS;
	}

	public void setIsPOS(String isPOS) {
		this.isPOS = isPOS;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
}
