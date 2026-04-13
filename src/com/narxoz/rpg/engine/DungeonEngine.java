package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import java.util.ArrayList;
import java.util.List;

public class DungeonEngine {

    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final List<GameObserver> observers;
    private int currentRound;

    private static final int MAX_ROUNDS = 20;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss) {
        this.heroes = new ArrayList<>(heroes);
        this.boss = boss;
        this.observers = new ArrayList<>();
        this.currentRound = 0;
    }

    public void registerObserver(GameObserver observer) {
        observers.add(observer);
        boss.registerObserver(observer);
    }

    private void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }

    private void checkHeroLowHp(Hero hero) {
        if (hero.isAlive() && hero.getHp() < hero.getMaxHp() * 0.3) {
            notifyObservers(new GameEvent(
                    GameEventType.HERO_LOW_HP,
                    hero.getName(),
                    hero.getHp()
            ));
        }
    }

    public EncounterResult runEncounter() {
        System.out.println("=== DUNGEON ENCOUNTER BEGINS ===");
        System.out.println("Boss: " + boss.getName() + " (HP: " + boss.getHp() + ")");

        while (currentRound < MAX_ROUNDS && boss.isAlive() && hasLivingHeroes()) {
            currentRound++;
            System.out.println("\n--- Round " + currentRound + " ---");

            // Heroes attack
            for (Hero hero : heroes) {
                if (hero.isAlive() && boss.isAlive()) {
                    int damage = hero.calculateDamage();
                    int defense = boss.calculateDefense();
                    int actualDamage = Math.max(1, damage - defense);

                    boss.takeDamage(actualDamage);

                    notifyObservers(new GameEvent(
                            GameEventType.ATTACK_LANDED,
                            hero.getName(),
                            actualDamage
                    ));

                    System.out.println(hero.getName() + " hits boss for " + actualDamage);

                    if (!boss.isAlive()) break;
                }
            }

            // Boss attacks
            if (boss.isAlive()) {
                for (Hero hero : heroes) {
                    if (hero.isAlive()) {
                        int damage = boss.calculateDamage();
                        int defense = hero.calculateDefense();
                        int actualDamage = Math.max(1, damage - defense);

                        hero.takeDamage(actualDamage);

                        notifyObservers(new GameEvent(
                                GameEventType.ATTACK_LANDED,
                                boss.getName(),
                                actualDamage
                        ));

                        System.out.println(boss.getName() +
                                " hits " + hero.getName() + " for " + actualDamage);

                        if (!hero.isAlive()) {
                            notifyObservers(new GameEvent(
                                    GameEventType.HERO_DIED,
                                    hero.getName(),
                                    0
                            ));
                        } else {
                            checkHeroLowHp(hero);
                        }
                    }
                }
            }
        }

        boolean heroesWon = !boss.isAlive();
        int survivingHeroes = (int) heroes.stream().filter(Hero::isAlive).count();

        System.out.println("\n=== ENCOUNTER ENDED ===");

        return new EncounterResult(heroesWon, currentRound, survivingHeroes);
    }

    private boolean hasLivingHeroes() {
        return heroes.stream().anyMatch(Hero::isAlive);
    }
}