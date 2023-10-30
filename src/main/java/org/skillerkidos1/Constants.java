package org.skillerkidos1;

import org.powbot.api.Area;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Player;
import org.powbot.api.rt4.Players;

public class Constants {
    public Constants(){super();
    }

    public Player p() {
        return Players.local();
    }


    //Areas
    public final static Area BANKING_AREA = new Area(new Tile(2448, 3486, 1), new Tile(2450, 3478, 1));
    public final static Area SECOND_LADDER = new Area(new Tile(2464, 3496, 1), new Tile(2467, 3493, 1));
    public final static Area THIRD_LADDER = new Area(new Tile(2467, 3494, 2), new Tile(2465, 3496, 2));
    public final static Area FOURTH_LADDER = new Area(new Tile(2463, 3497, 3), new Tile(2468, 3493, 3));
    public final static Area LANDING_AREA = new Area(new Tile(2708, 2808, 0), new Tile(2717, 2800, 0));
    public final static Area JUNGLE_GRASS_AREA = new Area(new Tile(2712, 2787, 0), new Tile(2716, 2790, 0));
    public final static Area TUNNEL_START_AREA = new Area(new Tile(2504, 9168, 1), new Tile(2516, 9176, 1));
    public final static Area TUNNEL_MONKEYBARS_START_AREA= new Area(new Tile(2507, 9187, 1), new Tile(2499, 9192, 1));
    public final static Area HUNTER_ENTIRE_AREA = new Area(new Tile(2892, 9091, 0), new Tile(2930, 9148, 0));
    public final static Area TUNNEL_MONKEYBARS_END_AREA = new Area(new Tile(2509, 9199, 1), new Tile(2501, 9208, 1));
    public final static Area HUNTER_ENTRANCE_AREA = new Area(new Tile(2906, 9091, 0), new Tile(2917, 9102, 0));
    public final static Area HUNTER_STUNTED_GORILLA_AREA = new Area(new Tile(2920, 9120, 0), new Tile(2923, 9129, 0));
    public final static Area STRONGHOLD_TELE_AREA = new Area(new Tile(2462, 3499, 0), new Tile(2469, 3492, 0));

    public final static Tile HUNTER_TRAINING_TILE = new Tile(2912,9127,0);


}
