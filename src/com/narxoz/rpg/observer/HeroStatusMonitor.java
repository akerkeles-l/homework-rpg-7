package com.narxoz.rpg.observer;

public class HeroStatusMonitor implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {

        if (event.getType() == GameEventType.HERO_LOW_HP) {
            System.out.println("[MONITOR] LOW HP: " + event.getSourceName());
        }

        if (event.getType() == GameEventType.HERO_DIED) {
            System.out.println("[MONITOR] DEAD: " + event.getSourceName());
        }
    }
}