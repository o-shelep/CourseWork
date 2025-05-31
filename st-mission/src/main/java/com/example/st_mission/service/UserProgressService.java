package com.example.st_mission.service;

import com.example.st_mission.model.UserEntity;
import com.example.st_mission.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProgressService {


    private final UserRepository userRepository;

    public UserProgressService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final List<String> ACHIEVEMENT_POOL = List.of(
            "Newbie", "First Mission", "Mission Accomplished", "On Fire", "Level Up!", "Consistent Cadet",
            "Perfect Score", "Marathoner", "Night Owl", "XP Collector", "Ranked Up", "Veteran",
            "Elite Agent", "Legend", "Mission Master", "Feedback Giver", "Mentor", "Builder",
            "Beta Tester", "Bug Hunter", "Customizer", "Socializer"
    );


    public void updateUserProgress(UserEntity user) {
        int newLevel = user.getPoints() / 10;
        if (user.getLevel() != newLevel) {
            user.setLevel(newLevel);
        }

        int expectedAchievementCount = user.getPoints() / 7;
        List<String> currentAchievements = user.getAchievements();

        for (int i = currentAchievements.size(); i < expectedAchievementCount; i++) {
            if (i < ACHIEVEMENT_POOL.size()) {
                currentAchievements.add(ACHIEVEMENT_POOL.get(i));
            }
        }

        userRepository.save(user);
    }

    public List<String> getAllPossibleAchievements() {
        return ACHIEVEMENT_POOL;
    }
}

