package com.narxoz.rpg.strategy;

public class BossPhase1Strategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (int)(basePower * 0.8);
    }
    
    @Override
    public int calculateDefense(int baseDefense) {
        return (int)(baseDefense * 1.2);
    }
    
    @Override
    public String getName() {
        return "Calculated Phase";
    }
}