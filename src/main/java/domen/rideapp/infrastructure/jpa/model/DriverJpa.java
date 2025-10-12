package domen.rideapp.infrastructure.jpa.model;

import domen.rideapp.domain.model.DriverStatus;
import jakarta.persistence.*;


@Entity
public class DriverJpa {
    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    public DriverJpa() {
    }

    public DriverJpa(String id, String firstName, String lastName, DriverStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }
}
