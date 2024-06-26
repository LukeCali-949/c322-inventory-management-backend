package edu.iu.habahram.ducksservice.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(schema = "guitars", name="guitars")
public final class Guitar {
    @Id
    private String serialNumber;
    private String model;
    private double price;
    private Builder builder;
    private Type type;
    private Wood backWood;

    private Wood topWood;

    public Guitar() {
    }

    public Guitar(String serialNumber, double price,
                  Builder builder, String model, Type type,
                  Wood backWood, Wood topWood) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.builder = builder;
        this.model = model;
        this.type = type;
        this.backWood = backWood;
        this.topWood = topWood;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(float newPrice) {
        this.price = newPrice;
    }
    public Builder getBuilder() {
        return builder;
    }
    public String getModel() {
        return model;
    }
    public Type getType() {
        return type;
    }
    public Wood getBackWood() {
        return backWood;
    }
    public Wood getTopWood() {
        return topWood;
    }
}
