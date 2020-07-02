package Core;

import Tools.ObjectsExtensions;

import java.util.Objects;

public class User {

    public final String name;
    public final String lastName;

    private Plate plateOwned;
    public final String id;

    public final PlateIDProducer.Scope scope ;

    public User(String name, String lastName, String id, String scope) {
        ObjectsExtensions.requireNonNulls(name,lastName,id);
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.scope = PlateIDProducer.Scope.valueOf(scope);
    }

    public Plate getPlateOwned() {
        return plateOwned;
    }

    public void setPlateOwned(Plate plateOwned) {
        Objects.requireNonNull(plateOwned);
        this.plateOwned = plateOwned;
    }

    public boolean hasPlate()
    {
        return plateOwned != null;
    }

    public void removePlate(){
        plateOwned = null;
    }

    @Override
    public boolean equals(Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof User)
            return ((User) obj).id.equals(this.id);
        return false;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Lastname: " + lastName + ", Id: " + id;
    }
}
