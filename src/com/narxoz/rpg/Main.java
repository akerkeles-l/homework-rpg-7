package com.narxoz.rpg;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Hero warrior = new Hero("Warrior", 150, 25, 15, new BerserkerStrategy());
        Hero paladin = new Hero("Paladin", 200, 20, 25, new TankStrategy());
        Hero rogue = new Hero("Rogue", 120, 30, 10, new NormalStrategy());

        List<Hero> heroes = Arrays.asList(warrior, paladin, rogue);

        DungeonBoss boss = new DungeonBoss("Shadow Dragon", 500, 35, 12);

        DungeonEngine engine = new DungeonEngine(heroes, boss);

        // Observers
        BattleLogger logger = new BattleLogger();
        AchievementTracker achievements = new AchievementTracker();
        PartySupport partySupport = new PartySupport(heroes, 30);
        HeroStatusMonitor statusMonitor = new HeroStatusMonitor(); 
        LootDropper lootDropper = new LootDropper();

        engine.registerObserver(logger);
        engine.registerObserver(achievements);
        engine.registerObserver(partySupport);
        engine.registerObserver(statusMonitor);
        engine.registerObserver(lootDropper);

        System.out.println("Initial Hero Strategies:");
        System.out.println("Warrior: " + warrior.getStrategy().getName());
        System.out.println("Paladin: " + paladin.getStrategy().getName());
        System.out.println("Rogue: " + rogue.getStrategy().getName());

        System.out.println("\nRogue switches strategy mid-battle!\n");

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                rogue.setStrategy(new BerserkerStrategy());
                System.out.println("\n*** ROGUE SWITCHES TO BERSERKER STRATEGY! ***\n");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        EncounterResult result = engine.runEncounter();

        System.out.println("\n=== FINAL RESULTS ===");
        System.out.println("Heroes Won: " + result.isHeroesWon());
        System.out.println("Rounds Played: " + result.getRoundsPlayed());
        System.out.println("Surviving Heroes: " + result.getSurvivingHeroes());

        System.out.println("\nFinal Hero Status:");
        for (Hero hero : heroes) {
            System.out.println(hero.getName() + ": " +
                    hero.getHp() + "/" + hero.getMaxHp() + " HP");
        }
    }
}