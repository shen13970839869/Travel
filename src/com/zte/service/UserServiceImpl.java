package com.zte.service;

import com.zte.dao.UserDao;
import com.zte.dao.UserDaoImpl;
import com.zte.pojo.User;
import com.zte.utils.MailUtils;
import com.zte.utils.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * ע���û�
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.�����û�����ѯ�û�����
        User u = userDao.findByUsername(user.getUsername());
        //�ж�u�Ƿ�Ϊnull
        if(u != null){
            //�û������ڣ�ע��ʧ��
            return false;
        }
        //2.�����û���Ϣ
        //2.1���ü����룬Ψһ�ַ���
        user.setCode(UuidUtil.getUuid());
        //2.2���ü���״̬
        user.setStatus("N");
        userDao.save(user);
        //3.�����ʼ����ͣ��ʼ�����?
        String content = "<a href='http://localhost:8080/Travel/activeUserServlet?code="+user.getCode()+"'>"+"�������"+"</a>"+"��ɼ���";
        MailUtils.sendMail(user.getEmail(), content, "�����ʼ�");
        
        return true;
    }
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
	@Override
	public boolean active(String code) {
		//1.���ݼ������ѯ�û�����
	    User user = userDao.findByCode(code);
	    if(user != null){
	        //2.����dao���޸ļ���״̬�ķ���
	        userDao.updateStatus(user);
	        return true;
	    }else{
	        return false;
	    }

	}

}
