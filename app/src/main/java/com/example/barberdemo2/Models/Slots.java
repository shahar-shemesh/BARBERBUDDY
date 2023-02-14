package com.example.barberdemo2.Models;

import java.util.ArrayList;
import java.util.Comparator;

public class Slots {
    ArrayList<String> slot = new ArrayList<>();
    
    public Slots() {
        this.slot.add("10:00");
        this.slot.add("11:00");
        this.slot.add("12:00");
        this.slot.add("13:00");
        this.slot.add("14:00");
        this.slot.add("15:00");
        this.slot.add("16:00");
        this.slot.add("17:00");
        this.slot.add("18:00");
        this.slot.add("19:00");
        this.slot.add("20:00");
        ////
        this.slot.add("10:30");
        this.slot.add("11:30");
        this.slot.add("12:30");
        this.slot.add("13:30");
        this.slot.add("14:30");
        this.slot.add("15:30");
        this.slot.add("16:30");
        this.slot.add("17:30");
        this.slot.add("18:30");
        this.slot.add("19:30");

        this.slot.sort(Comparator.naturalOrder());
    }

    public ArrayList<String> getSlots(){
        return this.slot;
    }
}
