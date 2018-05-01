package com.earth2me.essentials.settings;

import com.earth2me.essentials.storage.MapValueType;
import com.earth2me.essentials.storage.StorageObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;


@Data @EqualsAndHashCode(callSuper = false) public class Jails implements StorageObject {
    @MapValueType(Location.class)
    private Map<String, Location> jails = new HashMap<String, Location>();

    public Map<String, Location> getJails(){
        return jails;
    }

    public void setJails(HashMap<String, Location> jails){
        this.jails = jails;
    }
}
