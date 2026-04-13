package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.Random;

public class PartySupport implements GameObserver {
    private final List<Hero> heroes;
    private final int healAmount;
    private final Random random;
    
    public PartySupport(List<Hero> heroes, int healAmount) {
        this.heroes = heroes;
        this.healAmount = healAmount;
        this.random = new Random();
    }
    
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            Hero livingAlly = findRandomLivingAlly(event.getSourceName());
            if (livingAlly != null) {
                livingAlly.heal(healAmount);
                System.out.println("[PARTY SUPPORT] " + livingAlly.getName() + 
                                 " was healed for " + healAmount + " HP!");
            }
        }
    }
    
    private Hero findRandomLivingAlly(String excludedName) {
        List<Hero> livingHeroes = heroes.stream()
            .filter(h -> h.isAlive() && !h.getName().equals(excludedName))
            .toList();
        
        if (livingHeroes.isEmpty()) return null;
        return livingHeroes.get(random.nextInt(livingHeroes.size()));
    }
}