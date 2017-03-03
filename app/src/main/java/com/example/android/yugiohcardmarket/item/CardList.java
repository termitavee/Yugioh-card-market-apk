package com.example.android.yugiohcardmarket.item;

/**
 * Created by termitavee on 24/02/17.
 */

public class CardList {


    //nombre + id
    private String nombre;
    private int id;
    private int size;

    public CardList(String nombre, int id,int size){
        this.nombre = nombre;
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSize() {
        return size;
    }

}
