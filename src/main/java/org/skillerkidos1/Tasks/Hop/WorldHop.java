package org.skillerkidos1.Tasks.Hop;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.World;
import org.powbot.api.rt4.Worlds;
import org.skillerkidos1.KOManiacalMonkey;
import org.skillerkidos1.Task;
import org.skillerkidos1.util.Util;

import static org.powbot.dax.shared.helpers.General.random;

public class WorldHop extends Task {


    KOManiacalMonkey main;

    public WorldHop(KOManiacalMonkey main) {
        super();
        super.name = "WorldHop";
        this.main = main;
    }


    @Override
    public boolean activate() {
        return main.shouldHop;
    }

    @Override
    public void execute() {
        Util.state("Hopping worlds");

        World hopWorld = Worlds.stream().id(random(300, 580)).filtered(a ->
                a.getType().equals(World.Type.MEMBERS) &&
                        (a.getSpecialty().equals(World.Specialty.NONE) ||
                                a.getSpecialty().equals(World.Specialty.MINI_GAME))).any();

        Game.tab(Game.Tab.LOGOUT);
        if (hopWorld.valid() && hopWorld.hop()) {
            if (Condition.wait(() -> Worlds.current().getNumber() == hopWorld.getNumber(), 100, 50)) {
                main.shouldHop = false;
            }
        }
    }
}
