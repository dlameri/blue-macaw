package service;

import dao.RedisDao;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class UserService {
	
	public RedisDao redisDao;

	public UserService(RedisDao redisDao) {
		this.redisDao = redisDao; 
	}
	
	public boolean notExists(String user) {
		return ! redisDao.exists(user);
	}

	public void store(String user) {
		redisDao.store(user);
	}

}
