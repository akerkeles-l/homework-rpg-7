package com.narxoz.rpg.strategy;

public class BossPhase2Strategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (int)(basePower * 1.3);
    }
    
    @Override
    public int calculateDefense(int baseDefense) {
        return baseDefense;
    }
    
    @Override
    public String getName() {
        return "Aggressive Phase";
    }
}