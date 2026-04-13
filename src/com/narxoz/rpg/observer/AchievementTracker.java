package com.narxoz.rpg.observer;

import java.util.HashSet;
import java.util.Set;

public class AchievementTracker implements GameObserver {
    private final Set<String> unlockedAchievements;
    private int attackCount;
    
    public AchievementTracker() {
        this.unlockedAchievements = new HashSet<>();
        this.attackCount = 0;
    }
    
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED) {
            attackCount++;
            if (attackCount >= 10 && !unlockedAchievements.contains("Relentless")) {
                unlockedAchievements.add("Relentless");
                System.out.println("[ACHIEVEMENT] Relentless - Landed 10 attacks!");
            }
        }
        
        if (event.getType() == GameEventType.HERO_DIED && 
            !unlockedAchievements.contains("Sacrifice")) {
            unlockedAchievements.add("Sacrifice");
            System.out.println("[ACHIEVEMENT] Sacrifice - A hero has fallen in battle!");
        }
        
        if (event.getType() == GameEventType.BOSS_DEFEATED && 
            !unlockedAchievements.contains("Dragon Slayer")) {
            unlockedAchievements.add("Dragon Slayer");
            System.out.println("[ACHIEVEMENT] Dragon Slayer - Defeated the dungeon boss!");
        }
    }
}