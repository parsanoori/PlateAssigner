package Core;

import java.util.Objects;

public class Plate {

    private User user;
    public final String plateNumber;
    public final PlateIDProducer.Scope scope;

    public Plate(User user, String plateNumber) {
        Objects.requireNonNull(plateNumber);
        setUser(user);
        this.plateNumber = plateNumber;
        this.scope = user.scope;
    }

    public void changeOwner(User user){
        setUser(user);
    }

    private void setUser(User user){
        Objects.requireNonNull(user);
        this.user = user;
    }

    public String getPlateNumber() {
        return plateNumber;
    }
}
