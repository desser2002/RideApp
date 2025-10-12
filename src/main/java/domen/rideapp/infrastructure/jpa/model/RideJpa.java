    package domen.rideapp.infrastructure.jpa.model;

    import domen.rideapp.domain.model.RideStatus;
    import jakarta.persistence.*;

    @Entity
    public class RideJpa {
        @Id
        private String id;

        private String customer;
        @Embedded
        private LocalizationJpa localization;

        @Enumerated(EnumType.STRING)
        private RideStatus status;

        @Embedded
        private PriceJpa price;

        @ManyToOne(fetch = FetchType.LAZY)
        private DriverJpa driver;

        public RideJpa() {
        }

        public RideJpa(String id, String customer, LocalizationJpa localization, RideStatus status, PriceJpa price, DriverJpa driver) {
            this.id = id;
            this.customer = customer;
            this.localization = localization;
            this.status = status;
            this.price = price;
            this.driver = driver;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public LocalizationJpa getLocalization() {
            return localization;
        }

        public void setLocalization(LocalizationJpa localization) {
            this.localization = localization;
        }

        public RideStatus getStatus() {
            return status;
        }

        public void setStatus(RideStatus status) {
            this.status = status;
        }

        public PriceJpa getPrice() {
            return price;
        }

        public void setPrice(PriceJpa price) {
            this.price = price;
        }

        public DriverJpa getDriver() {
            return driver;
        }

        public void setDriver(DriverJpa driver) {
            this.driver = driver;
        }
    }
