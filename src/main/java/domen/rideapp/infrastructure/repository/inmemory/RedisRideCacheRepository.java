package domen.rideapp.infrastructure.repository.inmemory;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.repository.RideCacheRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

public class RedisRideCacheRepository implements RideCacheRepository {
    private static final String HASH_KEY = "pending-rides";
    private final RedisTemplate<String, Ride> redisTemplate;
    private final HashOperations<String, String, Ride> hashOps;

    public RedisRideCacheRepository(RedisTemplate<String, Ride> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(Ride ride) {
        hashOps.put(HASH_KEY, ride.getId(), ride);
    }

    @Override
    public List<Ride> getPendingRides() {
        return new ArrayList<>(hashOps.values(HASH_KEY));
    }

    @Override
    public void deleteBatch(List<String> ids) {
        hashOps.delete(HASH_KEY, ids.toArray());
    }

    @Override
    public void delete(String assignedRideId) {
        hashOps.delete(HASH_KEY, assignedRideId);
    }

    @Override
    public void clear() {
        redisTemplate.delete(HASH_KEY);
    }
}