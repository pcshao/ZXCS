package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.Goods;
import bean.Supplier;
import bean.orders.DbOrder;

/**
 * @author yc;
 * 1、一个转化类型的工具类（Jtable）;
 * 2、对象转vector;
 *
 */
public class CastUtil {
	/**
	 * lxh
	 * ********************************************************
	 */

	//Customer对象转vector
	public Vector<Vector> CustomerToVector(ArrayList<Customer> customers){
		Vector<Vector> ret = new Vector<Vector>();
		for(Customer c : customers){
			Vector item=new Vector();
			item.add(c.getCid());
			item.add(c.getName());
			item.add(c.getContact());
			item.add(c.getPhone());
			item.add(c.getAddress());
			item.add(c.getBz());
			ret.add(item);
		}
		return ret;
	}
	
	//Supplier对象转vector
		public Vector<Vector> SupplierToVector(ArrayList<Supplier> suppliers){
			Vector<Vector> ret = new Vector<Vector>();
			for(Supplier s : suppliers){
				Vector item=new Vector();
				item.add(s.getSid());
				item.add(s.getName());
				item.add(s.getContact());
				item.add(s.getPhone());
				item.add(s.getAddress());
				item.add(s.getBz());
				ret.add(item);
			}
			return ret;
		}
		//vector转Supplier
		public Supplier VectorToSupplier(Vector vector){
			Supplier supplier = new Supplier();
			supplier.setSid(Integer.parseInt(vector.get(0)+""));
			supplier.setName(vector.get(1)+"");
			
			return supplier;
		}
		/**
		 * Verctor 的 verctor 转成hashset(goods)
		 */
		
		public HashSet<Goods> VerctorToHashSet(Vector<Vector> vector){
			HashSet hash=new HashSet();
			for(Vector dual:vector){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(dual.get(0)+""));
				goods.setTempNum(Integer.parseInt(dual.get(5)+""));
				hash.add(goods);
			}
			return hash;
			
		}
	/**
	 * ********************************************************
	 */
	/**
	 * yc
	 * ********************************************************
	 */
	/**
	 * vector对象转goods
	 * @param sellgoods
	 * @return
	 */
	public HashSet<Goods> vectorToGoods_sell(Vector<Vector> sellgoods){
		HashSet<Goods> ret = new HashSet<Goods>();
		for(Vector c : sellgoods){
			Goods g=new Goods();
			g.setId(Integer.parseInt(c.get(0).toString()));
			g.setSellPrice(Double.parseDouble(c.get(4).toString()));//设置当前的单价
			g.setTempNum(Integer.parseInt(c.get(7).toString()));//数量
			ret.add(g);
		}
		return ret;
	}
	public HashSet<Goods> vectorToGoods_tui(Vector<Vector> sellgoods){
		HashSet<Goods> ret = new HashSet<Goods>();
		for(Vector c : sellgoods){
			Goods g=new Goods();
			g.setId(Integer.parseInt(c.get(0).toString()));
			g.setSellPrice(Double.parseDouble(c.get(4).toString()));//设置当前的单价
			g.setTempNum(Integer.parseInt(c.get(5).toString()));//数量
			ret.add(g);
		}
		return ret;
	}
	/**
	 * vector对象转custom
	 * @param customer
	 * @return
	 */
	public Customer vectorToCustomer(Vector customer){
		Customer ret = new Customer();
		ret.setCid(Integer.parseInt(customer.get(0).toString()));
		ret.setCid(Integer.parseInt(customer.get(0).toString()));
		ret.setName(customer.get(1).toString());
		ret.setContact(customer.get(2).toString());	
		return ret;
	}
	/**
	 * ********************************************************
	 */
	/**
	 * zw
	 * ********************************************************
	 */
	public Vector<Vector> depots(Vector<Depot> depo){
		Vector<Vector> ret = new Vector<Vector>();
		for(Depot o : depo){
			Vector item=new Vector();
			item.add(o.getDid());
			item.add(o.getAddress());
			item.add(o.getBz());			
			item.add(o.getContact());
			item.add(o.getName());
			item.add(o.getPhone());			
			ret.add(item);
		}
		return ret;
	}
	/**
	 * 库存调拨
	 * vector对象转goods
	 * @param sellgoods
	 * @return
	 */
	public HashSet<Goods> vectorToGoods(Vector<Vector> dbgoods, DbOrder type){
		HashSet<Goods> ret = new HashSet<Goods>();
		for(Vector c : dbgoods){
			Goods g=new Goods();
			g.setId(Integer.parseInt(c.get(0).toString()));
			g.setTempNum(Integer.parseInt(c.get(3).toString()));//数量
			ret.add(g);
		}
		return ret;
	}
	/**
	 * ********************************************************
	 */
		
	/**
	 * Customer对象转vector
	 * @param customers
	 * @return
	 * ********************************************************
	 */
	public Vector<Vector> objectToVector(ArrayList<Customer> customers){
		Vector<Vector> ret = new Vector<Vector>();
		for(Customer c : customers){
			Vector item=new Vector();
			item.add(c.getCid());
			item.add(c.getName());
			item.add(c.getContact());
			item.add(c.getPhone());
			item.add(c.getAddress());
			item.add(c.getBz());
			ret.add(item);
		}
		return ret;
	}
	/**
	 * Depot对象转vector
	 * @param customers
	 * @return
	 */
	public Vector<Vector> objectToVector(Vector<Depot> depot, Depot type){
		Vector<Vector> ret = new Vector<Vector>();
		for(Depot d : depot){
			Vector item=new Vector();
			item.add(d.getDid());
			item.add(d.getName());
			item.add(d.getContact());
			item.add(d.getPhone());
			item.add(d.getAddress());
			item.add(d.getBz());
			ret.add(item);
		}
		return ret;
	}
	/**
	 * Employee对象转vector
	 * @param customers
	 * @return
	 */
	public Vector<Vector> objectToVector(Vector<Employee> obj, Employee type){
		Vector<Vector> ret = new Vector<Vector>();
		for(Employee o : obj){
			Vector item=new Vector();
			item.add(o.getEid());
			item.add(o.getName());
			item.add(o.getJob());
			item.add(o.getPhone());
			item.add(o.getAddress());
			item.add(o.getBz());
			ret.add(item);
		}
		return ret;
	}
	/**
	 * Admin对象转vector
	 * @param customers
	 * @return
	 */
	public Vector<Vector> objectToVector(Vector<Admin> obj, Admin type){
		Vector<Vector> ret = new Vector<Vector>();
		for(Admin o : obj){
			Vector item=new Vector();
			item.add(o.getAid());
			item.add(o.getName());
			item.add(o.getIsGuiZhang());
			item.add(o.getJob());
			item.add(o.getIsPOS());
			item.add(o.getIsUse());
			ret.add(item);
		}
		return ret;
	}
	/**
	 * Supplier对象转vector
	 * @param customers
	 * @return
	 */
	public Vector<Vector> objectToVector(Vector<Supplier> obj, Supplier type){
		Vector<Vector> ret = new Vector<Vector>();
		for(Supplier o : obj){
			Vector item=new Vector();
			item.add(o.getSid());
			item.add(o.getName());
			item.add(o.getContact());
			item.add(o.getPhone());
			item.add(o.getAddress());
			item.add(o.getBz());
			ret.add(item);
		}
		return ret;
	}
	public Vector<Vector> objectToVector(Vector<Customer> obj, Customer type) {
		Vector<Vector> ret = new Vector<Vector>();
		for(Customer o : obj){
			Vector item=new Vector();
			item.add(o.getCid());
			item.add(o.getName());
			item.add(o.getContact());
			item.add(o.getPhone());
			item.add(o.getAddress());
			item.add(o.getBz());
			ret.add(item);
		}
		return ret;
	}
	/**
	 * Goods对象转Vector（POS用）
	 */
	public Vector<Vector> objectToVector(Vector<Goods> obj, Goods type){
		Vector<Vector> ret = new Vector<Vector>();
		for(Goods o : obj){
			Vector item=new Vector();
			item.add(o.getId());
			item.add(o.getGoodsid());
			item.add(o.getName());
			item.add(o.getUnit().getName());
			item.add(o.getDiscount());
			item.add(o.getSellPrice());
			item.add(o.getSpecialPrice());
			item.add(o.getVipDiscount());
			item.add(o.getVipPrice());
			item.add(1);
			ret.add(item);
		}
		return ret;
	}
}
