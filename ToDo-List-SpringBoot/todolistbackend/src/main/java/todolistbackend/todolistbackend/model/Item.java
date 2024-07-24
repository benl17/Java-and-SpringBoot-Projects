package todolistbackend.todolistbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long itemID;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "item_importance")
    private int itemImportance;

    public Item() {

    }

    public Item (String itemName, String dueDate, int itemImportance) {
        super();
        this.itemName = itemName;
        this.dueDate = dueDate;
        this.itemImportance = itemImportance;
    }

    public long getItemID() {
        return this.itemID;
    }

    public void setItemID(long id) {
        this.itemID = id;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String date) {
        this.dueDate = date;
    }

    public int getItemImportance() {
        return this.itemImportance;
    }

    public void setItemImportance(int importance) {
        this.itemImportance = importance;
    }
}
