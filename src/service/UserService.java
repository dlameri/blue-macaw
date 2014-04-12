package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.ioc.Component;
import dao.RedisDao;

@Component
public class UserService {
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public RedisDao redisDao;

	public UserService(RedisDao redisDao) {
		this.redisDao = redisDao; 
	}
	
	public boolean notExists(String user) {
		logger.info("Checando a existencia do usuário:" + user);
		return ! redisDao.exists(user);
	}

	public void store(String user) {
		logger.info("Armazenando o usuário:" + user);
		redisDao.store(user);
	}

}
