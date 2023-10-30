package org.skillerkidos1.Tasks.Travel;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.skillerkidos1.Constants;
import org.skillerkidos1.util.Util;

public class travelHelper {
    Util util = new Util();
    Constants c = new Constants();

    public void handleLost() {
        if (!isOnGorilla()) {
            Movement.moveTo(c.HUNTER_STUNTED_GORILLA_AREA.getRandomTile());
        } else {
            Movement.moveTo(c.HUNTER_TRAINING_TILE);
        }
    }


    public void equipKrukGreeGree() {
        Util.state("Equip Kruk monkey greegree");
        Item gree = Inventory.stream().nameContains("Kruk monkey greegree").first();
        if (gree.valid()) {
            if (gree.interact("Hold")) {
                Condition.wait(() ->
                        Equipment.itemAt(Equipment.Slot.MAIN_HAND).valid() &&
                                !util.isAnimating(), 50, 100);
            }
        }
    }

    public void swingAcross() {
        Util.state("Swing across monkeybars");
        GameObject bars = Objects.stream().nameContains("Monkeybars").nearest().first();
        if (bars.valid()) {
            if (!bars.inViewport()) {
                Camera.turnTo(bars);
            } else if (bars.interact("Swing across")) {
                Condition.wait(() ->
                        c.TUNNEL_MONKEYBARS_END_AREA.contains(c.p()), 90, 100);
            }
        }
    }

    public boolean isOnGorilla() {
        return Players.local().renderHeight() > 150;
    }


    public void climbOnGorilla() {
        Util.state("Climb on gorilla");
        Npc gorilla = Npcs.stream().nameContains("Stunted demonic gorilla").nearest().first();
        if (gorilla.valid()) {
            if (!gorilla.inViewport()) {
                Camera.turnTo(gorilla);
            } else if (gorilla.interact("Climb-on")) {
                Condition.wait(() ->
                                !c.p().inMotion() &&
                                        c.p().renderHeight() > 150
                        , 50, 100);
            }
        }
    }

    public void handleJungleGrass() {
        if (!Chat.chatting()) {
            investigateJungleGrass();
        } else if (Chat.canContinue()) {
            if (Chat.clickContinue()) {
                Condition.wait(() ->
                        Chat.pendingInput(), 50, 100);
            }
        } else if (Chat.completeChat("Yes")) {
            Condition.wait(() ->
                    c.TUNNEL_START_AREA.contains(c.p()), 50, 100);

        }
    }

    public void investigateJungleGrass() {
        GameObject grass = Objects.stream()
                .nameContains("Jungle Grass")
                .action("Investigate").first();
        if (grass.valid()) {
            if (!grass.inViewport()) {
                Camera.turnTo(grass);
            } else if (grass.interact("Investigate")) {
                Condition.wait(() ->
                        Chat.chatting(), 50, 100);

            }
        }
    }

    public void glideToUndri() {
        Npc errdo = Npcs.stream().nameContains("Captain Errdo").first();
        if (errdo.valid()) {
            if (!errdo.inViewport()) {
                Camera.turnTo(errdo);
            } else if (errdo.interact("Glider-to Ookookolly Undri")) {
                Condition.wait(() -> c.LANDING_AREA.contains(c.p()), 50, 100);
            }
        }
    }

    public void handleStuntedGorilla() {
        if (!isOnGorilla()) {
            climbOnGorilla();
        } else {
            Movement.moveTo(c.HUNTER_TRAINING_TILE);
        }
    }

    public void handleMonkeyBarsStart() {
        if (!Equipment.itemAt(Equipment.Slot.MAIN_HAND).valid()) {
            equipKrukGreeGree();
        } else {
            swingAcross();
        }
    }

    public void enterPassage() {
        GameObject passage = Objects.stream().nameContains("Passage").nearest().first();
        if (passage.valid() && !util.isAnimating() && !c.p().inMotion() && c.p().movementAnimation() != 3483) {
            if (!passage.inViewport()) {
                Camera.turnTo(passage);
            } else if (passage.interact("Enter")) {
                Condition.wait(() ->
                                !c.p().inMotion() &&
                                        c.HUNTER_ENTRANCE_AREA.contains(c.p())
                        , 50, 100);
            }
        }
    }


}
