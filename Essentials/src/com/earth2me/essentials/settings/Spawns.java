package com.earth2me.essentials.settings;

import com.earth2me.essentials.storage.MapValueType;
import com.earth2me.essentials.storage.StorageObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;


@Data @EqualsAndHashCode(callSuper = false) public class Spawns implements StorageObject {
    @MapValueType(Location.class)
    private Map<String, Location> spawns = new HashMap<String, Location>();

    public Map<String, Location> getSpawns(){
        return spawns;
    }

    public void setSpawns(HashMap<String, Location> spawns){
        this.spawns = spawns;
    }
}
