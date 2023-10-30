package org.skillerkidos1.Tasks.Train;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.skillerkidos1.Constants;
import org.skillerkidos1.KOManiacalMonkey;
import org.skillerkidos1.Task;
import org.skillerkidos1.util.Util;

public class CollectBones extends Task {
    Constants c = new Constants();
    Util util = new Util();
    KOManiacalMonkey main;

    public CollectBones (KOManiacalMonkey main){
        super();
        super.name="CollectBones";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return c.HUNTER_ENTIRE_AREA.contains(c.p()) && !hasItemInInventory(1963) &&
                main.getMethodSelected().equals("Tablet");
    }

    int i;
    @Override
    public void execute() {
        i = 0;
        if (Inventory.isFull()) {
            breakTablet();
            main.setPickUpBones(false);
        } else {
            while (!Inventory.isFull() && i <= 50) {
                GroundItem bones = GroundItems.stream().nameContains("Bones").nearest().first();
                Util.state("Collecting bones");
                if (Inventory.isFull()) {
                    break;
                }
                if (bones.valid()) {
                    if (!bones.inViewport()) {
                        Camera.turnTo(bones);
                    } else if (bones.interact("Take")) {
                        Condition.wait(() ->
                                !util.isAnimating() && !c.p().inMotion() &&
                                        !bones.valid(), 50, 100);
                    }
                } else {
                    Condition.wait(() ->
                            bones.valid(), 50, 100);
                }
            }
            i++;
        }
    }

    private void breakTablet(){
        Item tablet = Inventory.stream().nameContains("Bones to bananas").first();
        Item banana = Inventory.stream().id(1963).first();
        if(tablet.valid()){
            if(tablet.interact("Break")){
                Condition.wait(() ->
                       banana.valid(), 50, 100);
            }
        }
    }

    private boolean hasItemInInventory(int id) {
        Item pod = Inventory.stream().id(id).first();
        return pod.valid();
    }
}
