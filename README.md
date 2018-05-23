# spring-redis-aop-tutorial
A tutorial for spring-redis with aop.

------

完整的spring集成Redis的配置
```xml
<!-- redis 相关配置 -->
<bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
    <property name="maxIdle" value="${redis.maxIdle}" />
    <property name="maxWaitMillis" value="${redis.maxWait}" />
    <property name="testOnBorrow" value="true" />
</bean>

<bean id="jedisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory"
  p:host-name="${redis.host}" p:port="${redis.port}" p:pool-config-ref="poolConfig"/>

<!-- redis template definition -->  
<bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">  
    <!-- 设置 Redis 连接工厂-->
    <property name="connectionFactory" ref="jedisConnectionFactory"/>
    <!-- 设置默认 Serializer ，包含 keySerializer & valueSerializer -->
    <property name="defaultSerializer">
        <bean class="com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer"/>
    </property>
    <!-- 单独设置 keySerializer -->
    <property name="keySerializer">
        <bean class="com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer"/>
    </property>
    <!-- 单独设置 valueSerializer -->
    <property name="valueSerializer">
        <bean class="com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer"/>
    </property>
</bean>
```

由于是利用aop来实现redis缓存，考虑到数据存储到redis的性能问题，所以利用fastjson来将对象序列化为json，缓存命中时将json转为对应的对象，如果拦截的方法的返回值为List，则根据自定义注解来实现反序列化为List。
```java
private Object deserialize(String source, Class<?> clazz, Class<?> modelType) {
        // 判断是否为List
        if (List.class.isAssignableFrom(clazz)) {
            return JSON.parseArray(source, modelType);
        }

        // 正常反序列化
        return JSON.parseObject(source, clazz);
}
```

考虑到可以根据类名，方法名和参数来唯一标识调用的mapper，那么生成的key也就由这几者组成。在将数据存到缓存过程中，应当设置缓存的有效时间，这时会根据在方法上的注解设置的缓存时间来设定过期时间，大致实现如下：
```java
// 获取设置的缓存时间
int timeout = targetMethod.getAnnotation(RedisCache.class).expire();
// 如果没有设置过期时间,则无限期缓存(默认-1)
if (timeout <= 0) {
    opsForHash.put(hashName, key, jsonStr);  
} else {
    final TimeUnit unit = TimeUnit.SECONDS;
    final long rawTimeout = TimeoutUtils.toMillis(timeout, unit);
    // 设置缓存时间  
    redisTemplate.execute(new RedisCallback<Object>() {
        @Override
        public Object doInRedis(RedisConnection redisConn) throws DataAccessException {
            // 配置文件中指定了这是一个String类型的连接
            // 所以这里向下强制转换一定是安全的
            StringRedisConnection conn = (StringRedisConnection) redisConn;
            // 判断hash名是否存在
            // 如果不存在，创建该hash并设置过期时间
            if (!conn.exists(hashName)) {
                conn.hSet(hashName, key, jsonStr);
                conn.expire(hashName, rawTimeout);
            } else {
                conn.hSet(hashName, key, jsonStr);
            }

            return null;
        }
    });
}
```
## 博文地址
https://mstao.github.io/2017/11/08/%E5%9C%A8%20Spring%20Data%20Redis%E4%B8%AD%E4%BD%BF%E7%94%A8AOP%E8%BF%9B%E8%A1%8C%E6%95%B0%E6%8D%AE%E7%BC%93%E5%AD%98/
