package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.User;
import br.com.caelum.vraptor.ioc.Component;
import dao.RedisDao;

@Component
public class UserService {
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public RedisDao redisDao;

	public UserService(RedisDao redisDao) {
		this.redisDao = redisDao; 
	}
	
	public boolean notExists(User user) {
		logger.info("Checando a existencia do usuário:" + user.getScreenName());
		return ! redisDao.exists(user.getScreenName());
	}

	public void store(User user) {
		logger.info("Armazenando o usuário:" + user.getScreenName());
		redisDao.store(user.getScreenName(), user.getName());
	}

}
