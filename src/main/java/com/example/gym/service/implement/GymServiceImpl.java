package com.example.gym.service.implement;

import com.example.gym.dto.request.GymRequest;
import com.example.gym.dto.response.GymResponse;
import com.example.gym.entities.Gym;
import com.example.gym.mapper.GymMapper;
import com.example.gym.repositories.GymRepository;
import com.example.gym.service.GymService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GymServiceImpl implements GymService {
    GymRepository gymRepository;
    GymMapper gymMapper;

    @Override
    public GymResponse createGym(GymRequest gymRequest) {
        Gym gym = gymMapper.toGym(gymRequest);
        return gymMapper.toGymResponse(gymRepository.save(gym));
    }

    @Override
    public GymResponse updateGym(GymRequest gymRequest, Integer gymId) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        gymMapper.updateGym(gym, gymRequest);
        return gymMapper.toGymResponse(gymRepository.save(gym));
    }

    @Override
    public List<GymResponse> getAllGyms() {
        return gymRepository.findAll()
                .stream().
                map(gymMapper::toGymResponse)
                .toList();
    }

    @Override
    public GymResponse getGymById(Integer gymId) {
        return gymMapper.toGymResponse(gymRepository.findById(gymId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public void deleteGymById(Integer gymId) {
        gymRepository.deleteById(gymId);
    }
}