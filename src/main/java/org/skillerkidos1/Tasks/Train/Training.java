package org.skillerkidos1.Tasks.Train;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.skillerkidos1.Constants;
import org.skillerkidos1.KOManiacalMonkey;
import org.skillerkidos1.Task;
import org.skillerkidos1.util.Util;

public class Training extends Task {
    Constants c = new Constants();
    Util util = new Util();
    KOManiacalMonkey main;

    public Training(KOManiacalMonkey main){
        super();
        super.name = "Training";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return c.HUNTER_TRAINING_TILE.distanceTo(c.p())<=2;
    }

    @Override
    public void execute() {
        if (isPlayerDetected()) {
            Util.state("Player detected");
            main.shouldHop = true;
        } else if (canSetTrap()) {
            Util.state("Can set trap");
            if (hasItemInInventory("Basket")) {
                dropItem("Basket");
            } else if (hasItemInInventory("Damaged monkey tail")) {
                dropItem("Damaged monkey tail");
            } else if (!hasItemInInventory(1963)) {
                if (main.methodSelected.equals("Baskets")) {
                    emptyBasket();
                }else{
                    main.setPickUpBones(true);
                }
            } else if (hasItemInInventory(1963)) {
                setTrap();
            }
        } else if (canCheckBoulder()) {
            checkBoulder();
        }
    }

    private boolean isPlayerDetected(){
        int p = Players.stream().notLocalPlayer().within(10).list().size();
        return p>0;
    }

    private void checkBoulder(){
        GameObject boulderCheck = Objects.stream()
                .nameContains("Large boulder")
                .nearest(c.HUNTER_TRAINING_TILE)
                .action("Check")
                .within(2)
                .first();
        if(boulderCheck.valid()){
            if(!boulderCheck.inViewport()){
                Camera.turnTo(boulderCheck);
            }else if(boulderCheck.interact("Check")){
                Condition.wait(() ->
                        !boulderCheck.valid() && !util.isAnimating(), 50, 100);
            }
        }
    }

    private boolean hasItemInInventory(String name){
        Item item = Inventory.stream().nameContains(name).first();
        return item.valid();
    }

    private boolean hasItemInInventory(int id){
        Item item = Inventory.stream().id(id).first();
        return item.valid();
    }

    private void emptyBasket() {
        Util.state("Remove-one banana");
        Item bananasBasket = Inventory.stream().nameContains("Bananas(").first();
        if (hasItemInInventory("Bananas(")) {
            if (bananasBasket.interact("Empty")) {
                Condition.wait(() ->
                                hasItemInInventory(1963), 50, 100);
            }
        }else{
            Util.state("Out of banana baskets");
            teleportRoyalSeedPod();
        }
    }

    private void teleportRoyalSeedPod(){
        Item pod = Inventory.stream().nameContains("Royal seed pod").first();
        if(pod.valid()){
            if(pod.interact("Commune")){
                Condition.wait(() ->
                        !util.isAnimating() && c.STRONGHOLD_TELE_AREA.contains(c.p())
                        , 50, 100);
            }
        }
    }

    private void dropItem(String name){
        Util.state("Drop item: "+name);
        Item item = Inventory.stream().nameContains(name).first();
        if(item.valid()){
            if(item.interact("Drop")){
                Condition.wait(() ->
                        !item.valid(), 50, 100);
            }
        }
    }

    private boolean canCheckBoulder(){
        GameObject boulderCheck = Objects.stream()
                .nameContains("Large boulder")
                .nearest(c.HUNTER_TRAINING_TILE)
                .action("Check")
                .within(2)
                .first();
        return boulderCheck.valid();
    }

    private void setTrap(){
        Util.state("Set trap");
        GameObject boulderSet = Objects.stream()
                .nameContains("Large boulder")
                .nearest(c.HUNTER_TRAINING_TILE)
                .action("Set-trap")
                .within(2)
                .first();
        if(boulderSet.valid() && boulderSet.distanceTo(c.HUNTER_TRAINING_TILE)<=2){
            if(!boulderSet.inViewport()){
                Camera.turnTo(boulderSet);
            }else if(boulderSet.interact("Set-trap")){
                Condition.wait(() ->
                        !boulderSet.valid(), 100, 100);
            }
        }
    }

    private boolean canSetTrap(){
        GameObject boulderSet = Objects.stream()
                .nameContains("Large boulder")
                .nearest(c.HUNTER_TRAINING_TILE)
                .action("Set-trap")
                .within(2)
                .first();
        return boulderSet.valid();
    }
}
