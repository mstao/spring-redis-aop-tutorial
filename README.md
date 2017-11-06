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
