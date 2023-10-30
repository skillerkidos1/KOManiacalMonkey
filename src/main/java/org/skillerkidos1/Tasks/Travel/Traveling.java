package org.skillerkidos1.Tasks.Travel;

import org.powbot.api.*;
import org.powbot.api.rt4.*;
import org.powbot.dax.api.models.RunescapeBank;
import org.skillerkidos1.Constants;
import org.skillerkidos1.KOManiacalMonkey;
import org.skillerkidos1.Task;

    public class Traveling extends Task {
        Constants c = new Constants();
        travelHelper travelHelper = new travelHelper();

        KOManiacalMonkey main;

        public Traveling(KOManiacalMonkey main) {
            super();
            super.name = "Traveling";
            this.main = main;
        }

        @Override
        public boolean activate() {
            return main.pickUpBones || (c.HUNTER_TRAINING_TILE.distanceTo(c.p()) >= 2 &&
                    !c.BANKING_AREA.contains(c.p()));
        }

        @Override
        public void execute() {
            if (c.STRONGHOLD_TELE_AREA.contains(c.p())) {
                climbUpLadder(c.SECOND_LADDER);
            } else if (c.SECOND_LADDER.contains(c.p())) {
                if (Inventory.isFull() || hasItemInInventory("Bones to bananas")) {
                    climbUpLadder(c.THIRD_LADDER);
                } else {
                    Movement.moveToBank(RunescapeBank.GNOME_TREE_BANK_SOUTH);
                }
            } else if (c.THIRD_LADDER.contains(c.p())) {
                climbUpLadder(c.FOURTH_LADDER);
            } else if (c.FOURTH_LADDER.contains(c.p()) || c.p().tile().getFloor() == 3) {
                travelHelper.glideToUndri();
            } else if (c.LANDING_AREA.contains(c.p())) {
                Movement.moveTo(c.JUNGLE_GRASS_AREA.getRandomTile());
            } else if (c.JUNGLE_GRASS_AREA.contains(c.p())) {
                travelHelper.handleJungleGrass();
            } else if (c.TUNNEL_START_AREA.contains(c.p())) {
                Movement.moveTo(c.TUNNEL_MONKEYBARS_START_AREA.getRandomTile());
            } else if (c.TUNNEL_MONKEYBARS_START_AREA.contains(c.p())) {
                travelHelper.handleMonkeyBarsStart();
            } else if (c.TUNNEL_MONKEYBARS_END_AREA.contains(c.p())) {
                travelHelper.enterPassage();
            } else if (c.HUNTER_ENTRANCE_AREA.contains(c.p())) {
                Movement.moveTo(c.HUNTER_STUNTED_GORILLA_AREA.getRandomTile());
            } else if (c.HUNTER_STUNTED_GORILLA_AREA.contains(c.p())) {
                travelHelper.handleStuntedGorilla();
            } else if (c.HUNTER_ENTIRE_AREA.contains(c.p())) {
                travelHelper.handleLost();
            }
        }

        private boolean hasItemInInventory(String string) {
            Item pod = Inventory.stream().nameContains(string).first();
            return pod.valid();
        }


        private void climbUpLadder(Area area) {
            GameObject ladder = Objects.stream().nameContains("Ladder").nearest().first();
            if (ladder.valid()) {
                if (!ladder.inViewport()) {
                    Camera.turnTo(ladder);
                } else if (ladder.interact("Climb-up")) {
                    Condition.wait(() -> area.contains(c.p()), 50, 100);
                }
            }
        }

    }





