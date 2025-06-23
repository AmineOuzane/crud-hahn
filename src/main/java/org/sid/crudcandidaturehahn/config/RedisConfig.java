package org.sid.crudcandidaturehahn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {

    /**
     * Defines a RedisCacheManager bean for Spring caching abstraction.
     * Configures cache entries with a 2-minute TTL, disables caching of null values,
     * and serializes values as JSON using Jackson2JsonRedisSerializer.
     * This ensures efficient, readable, and consistent cache storage in Redis.
     */

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        // Créer un convertisseur JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Lui apprendre à gérer les dates modernes
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Créer le "sérialiseur" pour Redis en utilisant notre convertisseur
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        // Implmenter le sérialiseur dans la configuration
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(2))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(serializer));

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
