package service;

import java.util.Vector;

import bean.Vip;
import bean.VipRange;
import dao.VipDao;

/**
 * VIP服务
 * @author pcshao
 *
 */
public class VipService {

	/**
	 * 获取所有VIP实体信息
	 * @return
	 */
	public Vector<Vector> getVips(String search) {
		return new VipDao().getVips(search);
	}
	/**
	 * 获取VIP消费记录详情
	 * @param id
	 * @return
	 */
	public Vector<Vector> getVipRecordById(int id) {
		return new VipDao().getVipRecordById(id);
	}
	/**
	 * 新增会员
	 * @return
	 */
	public boolean addVip(Vip vip) {
		return new VipDao().addVip(vip);
	}
	/**
	 * 删除会员
	 * @param id
	 * @return
	 */
	public boolean removeVipByid(int id) {
		return new VipDao().removeVipByid(id);
	}
	/**
	 * 获取会员等级信息
	 * @return
	 */
	public Vector<Vector> getVipRangeInfo() {
		return new VipDao().getVipRangeInfo();
	}
	/**
	 * 新增会员等级
	 * @return
	 */
	public boolean addVipRange(VipRange vr) {
		return new VipDao().addVipRange(vr);
	}
	/**
	 * 编辑会员等级
	 * @param vr
	 * @return
	 */
	public boolean editVipRangeById(VipRange vr) {
		return new VipDao().editVipRangeById(vr);
	}
	/**
	 * 移除会员等级通过id
	 * @param id
	 * @return
	 */
	public boolean removeVipRangeById(int id) {
		return new VipDao().removeVipRangeById(id);
	}

}
