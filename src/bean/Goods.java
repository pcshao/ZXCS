package bean;

/**
 * 商品
 */
public class Goods {
	
	private int id;
	private String name;
	private GoodsType type;
	private String goodsid;
	private Units unit;
	private String norms;
	private String badDate;
	private double inPrice;
	private double sellPrice;
	private double discount;
	private double vipDiscount;
	private double specialPrice;
	private double vipPrice;
	private String deadDate;
	private String bz;
	
	private int tempNum;		//辅助计数,只在进货时辅助存储
	
	public Goods() {
	}
	/**
	 * 辅助POS销售
	 * @param id
	 * @param tempNum
	 */
	public Goods(int id, int tempNum, double sellPrice) {
		this.id = id;
		this.tempNum = tempNum;
		this.sellPrice = sellPrice;
	}
	public int getTempNum() {
		return tempNum;
	}
	public void setTempNum(int tempNum) {
		this.tempNum = tempNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GoodsType getType() {
		return type;
	}
	public void setType(GoodsType type) {
		this.type = type;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public Units getUnit() {
		return unit;
	}
	public void setUnit(Units unit) {
		this.unit = unit;
	}
	public String getNorms() {
		return norms;
	}
	public void setNorms(String norms) {
		this.norms = norms;
	}
	public String getBadDate() {
		return badDate;
	}
	public void setBadDate(String badDate) {
		this.badDate = badDate;
	}
	public double getInPrice() {
		return inPrice;
	}
	public void setInPrice(double inPrice) {
		this.inPrice = inPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getVipDiscount() {
		return vipDiscount;
	}
	public void setVipDiscount(double vipDiscount) {
		this.vipDiscount = vipDiscount;
	}
	public double getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(double specialPrice) {
		this.specialPrice = specialPrice;
	}
	public double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getDeadDate() {
		return deadDate;
	}
	public void setDeadDate(String deadDate) {
		this.deadDate = deadDate;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}
