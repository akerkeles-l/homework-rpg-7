package com.narxoz.rpg.strategy;

public class TankStrategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (int)(basePower * 0.7);
    }
    
    @Override
    public int calculateDefense(int baseDefense) {
        return (int)(baseDefense * 1.8);
    }
    
    @Override
    public String getName() {
        return "Tank";
    }
}