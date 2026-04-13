package com.narxoz.rpg.observer;

import java.util.Random;

public class LootDropper implements GameObserver {
    private Random random;
    private String[] lootItems = {
        "Gold Coin x100", "Health Potion", "Magic Scroll", 
        "Enchanted Sword", "Dragon Scale", "Mystic Amulet"
    };
    
    public LootDropper() {
        this.random = new Random();
    }
    
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            String loot = lootItems[random.nextInt(lootItems.length)];
            System.out.println("[LOOT] Phase " + event.getValue() + 
                             " transition reward: " + loot);
        }
        
        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("[LOOT] BOSS DEFEATED! Epic Loot:");
            for (int i = 0; i < 3; i++) {
                String loot = lootItems[random.nextInt(lootItems.length)];
                System.out.println("  - " + loot);
            }
        }
    }
}