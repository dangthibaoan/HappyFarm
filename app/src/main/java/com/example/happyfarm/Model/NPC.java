package com.example.happyfarm.Model;

public class NPC {
    private int npcID;
    private int npcName;
    private String npcAvatar;

    public NPC(){}

    public NPC(int npcID, int npcName, String npcAvatar) {
        this.npcID = npcID;
        this.npcName = npcName;
        this.npcAvatar = npcAvatar;
    }

    public int getNpcID() {
        return npcID;
    }

    public void setNpcID(int npcID) {
        this.npcID = npcID;
    }

    public int getNpcName() {
        return npcName;
    }

    public void setNpcName(int npcName) {
        this.npcName = npcName;
    }

    public String getNpcAvatar() {
        return npcAvatar;
    }

    public void setNpcAvatar(String npcAvatar) {
        this.npcAvatar = npcAvatar;
    }
}
