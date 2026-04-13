package com.narxoz.rpg.combatant;

import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.BossPhase1Strategy;
import com.narxoz.rpg.strategy.BossPhase2Strategy;
import com.narxoz.rpg.strategy.BossPhase3Strategy;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import java.util.ArrayList;
import java.util.List;

public class DungeonBoss implements GameObserver {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private CombatStrategy strategy;
    private int phase;
    private final List<GameObserver> observers;
    
    private final CombatStrategy phase1Strategy;
    private final CombatStrategy phase2Strategy;
    private final CombatStrategy phase3Strategy;

    public DungeonBoss(String name, int hp, int attackPower, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.observers = new ArrayList<>();
        
        this.phase1Strategy = new BossPhase1Strategy();
        this.phase2Strategy = new BossPhase2Strategy();
        this.phase3Strategy = new BossPhase3Strategy();
        
        this.phase = 1;
        this.strategy = phase1Strategy;
    }
    
    public void registerObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    private void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
    
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED && 
            event.getSourceName().equals(name)) {
            switchToPhase(event.getValue());
        }
    }
    
    private void switchToPhase(int newPhase) {
        this.phase = newPhase;
        switch (newPhase) {
            case 1 -> strategy = phase1Strategy;
            case 2 -> strategy = phase2Strategy;
            case 3 -> strategy = phase3Strategy;
        }
    }
    
    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
        double hpPercent = (double)hp / maxHp;
        
        if (hpPercent <= 0.3 && phase != 3) {
            phase = 3;
            notifyObservers(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 3));
        } else if (hpPercent <= 0.6 && phase == 1) {
            phase = 2;
            notifyObservers(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 2));
        }
        
        if (hp == 0) {
            notifyObservers(new GameEvent(GameEventType.BOSS_DEFEATED, name, 0));
        }
    }
    
    public int calculateDamage() {
        return strategy.calculateDamage(attackPower);
    }
    
    public int calculateDefense() {
        return strategy.calculateDefense(defense);
    }
    
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getPhase() { return phase; }
    public boolean isAlive() { return hp > 0; }
    public CombatStrategy getStrategy() { return strategy; }
}