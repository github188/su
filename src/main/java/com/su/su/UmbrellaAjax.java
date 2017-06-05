package com.su.su;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.su.dao.UmbrellaDao;
import com.su.dao.UserDao;
import com.su.dao.impl.UmbrellaDaoImpl;
import com.su.dao.impl.UserDaoImpl;
import com.su.models.NetResult;
import com.su.models.Umbrella;
import com.su.models.UmbrellaNear;
import com.su.models.User;
import com.su.socket.SocketStart;
import com.su.socket.TcpServerFoward;
import com.su.util.BitUtils;

/**
 * @author Yujie
 *
 */
@Controller
public class UmbrellaAjax {
	@RequestMapping(value ="/getNearUmbrella", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List getNearUmbrella(Locale locale, Model model,double lon,double lat) {
		NetResult r=new NetResult();		
		UmbrellaDao dao=new  UmbrellaDaoImpl();	
		List<UmbrellaNear> nearDevice =dao.findNearDevice(lon, lat);	 
		return nearDevice;			
	}
	@RequestMapping(value = "/barUmAdmin-mobile", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody NetResult adminBarOper(Locale locale, Model model, String devUuid, String umId, String operate,
			HttpSession session
	) {
		
		NetResult r = new NetResult();		
		Map<String, Socket> sockets = SocketStart.getSocketClients();// ��ȡ��ǰSocket�б�
		Socket socket = sockets.get(devUuid);
		byte[] uuidByte=TcpServerFoward.stringToByte(devUuid);
		byte[] umOperate=new byte[22];
		umOperate [0]=0x01;
		umOperate [1]=0x01;
		umOperate [2]=0x03;
		for(int i=0;i<uuidByte.length;i++){
			umOperate [i+3]=uuidByte[i];	
		}
		umOperate [19]=0x16;
		umOperate [20]=(byte) Integer.parseInt(umId); 		
		if(operate.equals("borrow")){			
			umOperate [21]=0x00;			
		}else if(operate.equals("reback")){
			umOperate [21]=0x01;
		}		
		send(socket, umOperate);
		byte[] operateResult = new byte[50];
		try {
			InputStream socketInput = socket.getInputStream();
			System.out.println("whileǰ");
			while (socketInput.read(operateResult) != -1) {
				System.out.println("��ʼ������Ϣ");  
				byte[] revData;
            	revData=Arrays.copyOfRange(operateResult, 0, 19);
            	String recDataStr= TcpServerFoward.byteToString(revData);
            	System.out.println("Controller====="+recDataStr);
				if (operateResult[0] == 0x01 && operateResult[1] == 0x01 && operateResult[2] == 0x00) {					
					byte[] uuid;
					uuid = Arrays.copyOfRange(operateResult, 3, 19);
					devUuid = TcpServerFoward.byteToString(uuid);
					byte operSta = operateResult[20];
					if (operSta == 0x01) {
						System.out.println("�����ɹ�");
						UmbrellaDao dao = new UmbrellaDaoImpl();
						UserDao userDao = new UserDaoImpl();
						User user = userDao.findByName((String)session.getAttribute("admin")); // Ѱ�ҵ�ǰ��ɡ�û�
						Umbrella um = dao.findDeviceByUuid(devUuid); // Ѱ���û���ɡ�豸
						byte[] umSta = um.getUmbrellaSta();

					if (operate.equals("borrow")) {
							byte borro = 0x00;
							if (Integer.parseInt(umId) < 9) {

								umSta[0] = BitUtils.setBitValue(umSta[0], Integer.parseInt(umId) - 1, borro);

							} else {
								umSta[1] = BitUtils.setBitValue(umSta[1], Integer.parseInt(umId) - 9, borro);
							}
							r.setStatus(0);
							r.setContent("��ɡ�ɹ�");
						}
						if (operate.equals("reback")) {

							System.out.println("reback");
							byte reback = 0x01;
							if (Integer.parseInt(umId) < 9) {
								umSta[0] = BitUtils.setBitValue(umSta[0], Integer.parseInt(umId) - 1, reback);
							} else {
								umSta[1] = BitUtils.setBitValue(umSta[1], Integer.parseInt(umId) - 9, reback);
							}
							r.setStatus(1);
							r.setContent("��ɡ�ɹ�");
						}
						um.setUmbrellaSta(umSta);
						dao.addDevice(um); // ������ɡ״̬
						user.setBorrowSta(false);
						userDao.addUser(user);
						
					} else if (operSta == 0x00) {
						r.setStatus(0);
						r.setContent("����ʧ��");
					}
					
					break;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("�˳�ѭ��");
		System.out.println("sockets==========" + sockets);
		System.out.println("socket============" + socket);
		return r;
	}	
	public static void send(Socket socket,byte [] operate){
		PrintWriter	pOut=null;
		OutputStream outPutStream=null;
		
		try {
			outPutStream=socket.getOutputStream();	
			outPutStream.write(operate);
			//outPutStream.close();
			//out.close();
			//clientSocket.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("����ʧ��");
		}finally{
			
		}
	}
}
