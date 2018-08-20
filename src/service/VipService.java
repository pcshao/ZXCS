package service;

import java.util.Vector;

import bean.Vip;
import bean.VipRange;
import dao.VipDao;

/**
 * VIP����
 * @author pcshao
 *
 */
public class VipService {

	/**
	 * ��ȡ����VIPʵ����Ϣ
	 * @return
	 */
	public Vector<Vector> getVips(String search) {
		return new VipDao().getVips(search);
	}
	/**
	 * ��ȡVIP���Ѽ�¼����
	 * @param id
	 * @return
	 */
	public Vector<Vector> getVipRecordById(int id) {
		return new VipDao().getVipRecordById(id);
	}
	/**
	 * ������Ա
	 * @return
	 */
	public boolean addVip(Vip vip) {
		return new VipDao().addVip(vip);
	}
	/**
	 * ɾ����Ա
	 * @param id
	 * @return
	 */
	public boolean removeVipByid(int id) {
		return new VipDao().removeVipByid(id);
	}
	/**
	 * ��ȡ��Ա�ȼ���Ϣ
	 * @return
	 */
	public Vector<Vector> getVipRangeInfo() {
		return new VipDao().getVipRangeInfo();
	}
	/**
	 * ������Ա�ȼ�
	 * @return
	 */
	public boolean addVipRange(VipRange vr) {
		return new VipDao().addVipRange(vr);
	}
	/**
	 * �༭��Ա�ȼ�
	 * @param vr
	 * @return
	 */
	public boolean editVipRangeById(VipRange vr) {
		return new VipDao().editVipRangeById(vr);
	}
	/**
	 * �Ƴ���Ա�ȼ�ͨ��id
	 * @param id
	 * @return
	 */
	public boolean removeVipRangeById(int id) {
		return new VipDao().removeVipRangeById(id);
	}

}
