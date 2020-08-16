package com.example.vcafe.order.model;

public class OrderItem extends Item implements ChangeQuantity{
    private int quantity;
    private String note;
    public OrderItem(Item item) {
        this.setName(item.getName());
        this.setNote("");
        this.setQuantity(1);
        this.setCategory(item.getCategory());
        this.setDescription(item.getDescription());
        this.setImg_link(item.getImg_link());

    }
    public OrderItem(int quantity, String note) {
        this.quantity = quantity;
        this.note = note;
    }

    public OrderItem(String name, String img_link, int price, String category, String description, int quantity, String note) {
        super(name, img_link, price, category, description);
        this.quantity = quantity;
        this.note = note;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void increase(int i) {
        this.quantity+=i;
    }

    @Override
    public void decrease(int i) {
        this.quantity-=i;
    }
}
