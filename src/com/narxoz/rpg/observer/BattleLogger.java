package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {

        if (event.getType() == GameEventType.ATTACK_LANDED) {
            System.out.println("[LOG] " + event.getSourceName() +
                    " dealt " + event.getValue() + " damage!");
        }

        else if (event.getType() == GameEventType.HERO_LOW_HP) {
            System.out.println("[LOG] " + event.getSourceName() +
                    " is critically low! (" + event.getValue() + " HP)");
        }

        else if (event.getType() == GameEventType.HERO_DIED) {
            System.out.println("[LOG] " + event.getSourceName() + " has fallen!");
        }

        else if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            System.out.println("[LOG] Boss entered Phase " + event.getValue());
        }

        else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("[LOG] The boss has been defeated!");
        }
    }
}