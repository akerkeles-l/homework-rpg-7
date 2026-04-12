package com.narxoz.rpg.strategy;

public class BossPhase3Strategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (int)(basePower * 1.8);
    }
    
    @Override
    public int calculateDefense(int baseDefense) {
        return 0;
    }
    
    @Override
    public String getName() {
        return "Desperate Phase";
    }
}