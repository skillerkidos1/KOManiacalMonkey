package org.skillerkidos1;

import com.google.common.eventbus.Subscribe;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.script.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.api.script.paint.TrackSkillOption;
import org.powbot.mobile.service.ScriptUploader;
import org.skillerkidos1.Tasks.Bank.Banking;
import org.skillerkidos1.Tasks.Hop.WorldHop;
import org.skillerkidos1.Tasks.Train.CollectBones;
import org.skillerkidos1.Tasks.Train.Training;
import org.skillerkidos1.Tasks.Travel.Traveling;
import org.skillerkidos1.util.Util;

import java.util.ArrayList;


@ScriptManifest(
        name = "KO Maniacal Monkey",
        description = "Trains Hunter at Maniacal Monkeys",
        version = "0.1",
        author = "Skillerkidos1")
@ScriptConfiguration(
        name="Method",
        description = "Method for bananas",
        optionType = OptionType.STRING,
        allowedValues = {"Baskets","Tablet"}

)


public class KOManiacalMonkey extends AbstractScript {

    private ArrayList<Task> maniacalMonkeyTasks = new ArrayList<>();

    public static String currentTask = "null";

    public String methodSelected = "null";

    public boolean shouldHop =false;
    public boolean pickUpBones=false;

    public static void main(String[] args) {
        // Start your script with this function. Make sure your device is connected via ADB, and only one is connected
        new ScriptUploader().uploadAndStart("KO Maniacal Monkey", "",
                "127.0.0.1:5815", true, false);
    }

    @Override
    public void poll() {
        if(methodSelected.equals("null")){
            Util.state("Waiting for settings");
            methodSelected = getOption("Method");
        }else{
            for (Task task : getTaskList()) {
                if (task.activate()) {
                    currentTask = task.name;
                    task.execute();
                }
            }
        }
    }


    public ArrayList<Task> getTaskList() {
        return maniacalMonkeyTasks;
    }

    public void onStart() {
        methodSelected = getOption("Method");

        //Task list
        maniacalMonkeyTasks.add(new Banking(this));
        maniacalMonkeyTasks.add(new WorldHop(this));
        maniacalMonkeyTasks.add(new Training(this));
        maniacalMonkeyTasks.add(new Traveling(this));
        maniacalMonkeyTasks.add(new CollectBones(this));


        Paint paint = new PaintBuilder()
                .x(10)
                .y(10)
                .addString(() -> "Current Task: " + currentTask)
                .trackSkill(Skill.Hunter, "Hunter XP Gained", TrackSkillOption.Exp)
                .trackSkill(Skill.Hunter, "Hunter Lvl", TrackSkillOption.Level)
                .addString(() -> "Method: "+methodSelected)
                .build();
        addPaint(paint);

    }

    public void setPickUpBones(boolean b){
        pickUpBones=b;
    }

    public String getMethodSelected(){
        return methodSelected;
    }


}
