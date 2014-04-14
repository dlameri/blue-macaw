package dao;

import redis.clients.jedis.Jedis;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class RedisDao {

	private Jedis jedis;
	
	public RedisDao() {
		jedis = new Jedis("127.0.0.1", 6379);
	}
	
	public boolean exists(String key) {
		return jedis.exists(key);
	}
	
	public void store(String key, String value) {
		jedis.set(key, value);
	}
}
