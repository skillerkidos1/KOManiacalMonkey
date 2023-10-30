package org.skillerkidos1.Tasks.Bank;


import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Movement;
import org.skillerkidos1.Constants;
import org.skillerkidos1.KOManiacalMonkey;
import org.skillerkidos1.Task;
import org.skillerkidos1.util.Util;

public class Banking extends Task {
    Constants c = new Constants();

    KOManiacalMonkey main;

    public Banking(KOManiacalMonkey main) {
        super();
        super.name = "Banking";
        this.main = main;
    }

    private boolean hasDepositedAll = false;


    @Override
    public boolean activate() {

        return c.BANKING_AREA.contains(c.p());
    }

    @Override
    public void execute() {
        Util.state("Banking");
        if (!Bank.opened()) {
            if (Bank.open()) {
                return;
            }
        }

        if (hasDepositedAll) {
            if (!hasItemInInventory("Royal seed pod")) {
                grabItemFromBank("Royal seed pod", 1);
            } else if (!hasItemInInventory("Kruk monkey greegree")) {
                grabItemFromBank("Kruk monkey greegree", 1);
            } else {

                switch (main.methodSelected) {
                    case "Baskets":
                        handleBasketBanking();
                    case "Tablet":
                        handleTabletBanking();
                }
            }
        } else if (Inventory.isEmpty()) {
            hasDepositedAll = true;
        } else if (Bank.depositInventory()) {
            Condition.wait(() -> Inventory.isEmpty(), 50, 100);
        }
    }

    private void handleBasketBanking() {
        Util.state("Handle basket banking");
        if (!hasItemInInventory("Bananas(5)")) {
            grabItemFromBank("Bananas(5)", 26);
        } else {
            Util.state("Has all supplies");
            Movement.moveTo(c.SECOND_LADDER.getRandomTile());
        }
    }

    private void handleTabletBanking() {
        Util.state("Handle tablet banking");
        if (!hasItemInInventory("Bones to bananas")) {
            grabItemFromBank("Bones to bananas", 99999);
        } else {
            Util.state("Has all supplies");
            Movement.moveTo(c.SECOND_LADDER.getRandomTile());
        }
    }

    private boolean hasItemInInventory(String string) {
        Item pod = Inventory.stream().nameContains(string).first();
        return pod.valid();
    }

    private void grabItemFromBank(String name, int amount) {
        Util.state("Grab item: " + name);
        if (Bank.withdraw(name, amount)) {
            Condition.wait(() ->
                    Inventory.stream().nameContains(name).first().valid(), 50, 100);
        }
    }

}
