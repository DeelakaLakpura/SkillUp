package com.dsmini.skillup.Students.models;

public class notemodel {

        String Note,Title,Model;

    notemodel()
    {

    }
    public notemodel(String Note, String Title, String Model) {
        this.Note = Note;
        this.Title = Title;
        this.Model = Model;

    }



    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }
}
