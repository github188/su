package com.su.dao;

import java.util.List;
import java.util.Map;

import com.su.models.Umbrella;

public interface UmbrellaDao {
	public List<Umbrella> findAllDevice();//�ҳ������豸
	public List<Umbrella> findAllAvlDevice();//�ҳ����п����豸
	public Umbrella findDeviceByUuid(String devUuid);
	public void updateDeviceInfo(String deviceId,Umbrella umbrella);//����ɡ����Ϣ
	public void addDevice(Umbrella umbrella);                    //���ɡ���豸	
	public boolean getDeviceStatus(String uuid);                //��ȡָ��ɡ�ܵ�״̬��Ϣ  ���߻�����
	
	public Map<Integer,Boolean> getUmbrellaSta(String deviceId);//��ȡָ��ɡ�ܸ���ɡ��״̬��Ϣ
	
	public void openUmbrellaById(String deviceId,String umbrellaId);//��ָ��ɡ�ܵ�ָ��ɡ
	
}
