### **Wytyczne do aplikacji Spring Boot**

Aplikacja umożliwia zgłaszanie chęci przejazdu trasą od punktu A do punktu B konkretnym dostawcą (Uber, Bolt, FreeNow). 
Posiada trzy główne funkcjonalności:

1. **Obsługa zgłoszeń przejazdu** – jeden endpoint dla wszystkich dostawców.
2. **Zarządzanie cenami przejazdów** – interfejs PricingService z różnymi implementacjami.
3. **Harmonogram wyszukiwania dostępnych kierowców** – trzy niezależne schedulery.

---

## **1. Struktura katalogów aplikacji**
```
src/main/java/com/example/rideapp/
│── controller/            # Warstwa kontrolera
│   ├── RideController.java
│   ├── request/
│   │   ├── RideRequestDto.java
│
│── domain/                # Warstwa domenowa
│   ├── model/
│   │   ├── Ride.java
│   │   ├── RideStatus.java
│   │   ├── Driver.java
│   │
│   ├── repository/
│   │   ├── RideRepository.java
│
│   ├── service/
│   │   ├── RideService.java
│   │   ├── PricingService.java
│
│── infrastructure/        # Warstwa infrastruktury
│   ├── repository/
│   │   ├── UberRideRepository.java
│   │   ├── BoltRideRepository.java
│   │   ├── FreeNowRideRepository.java
│
│   ├── pricing/
│   │   ├── UberPricingService.java
│   │   ├── BoltPricingService.java
│   │   ├── FreeNowPricingService.java
│
│   ├── scheduler/
│   │   ├── UberRideScheduler.java
│   │   ├── BoltRideScheduler.java
│   │   ├── FreeNowRideScheduler.java
│
│   ├── config/
│   │   ├── AppConfig.java
│
│── Application.java       # Klasa główna aplikacji
```

---

## **2. Opis komponentów**

### **2.1. Kontroler**
- `RideController.java` – Obsługuje po jednym endpoicie dla każdego przewoźnika `/ride/request/{przewoźnik}`, przyjmując `RideRequest` jako body.
- `RideRequest.java` – DTO jako **record**, zawiera pola: `from`, `to`, `customer`.

### **2.2. Model domenowy**
- `Ride.java` – Klasa reprezentująca przejazd, zawiera informacje o trasie, statusie, kierowcy i cenie.
- `RideStatus.java` – Enum zawierający statusy przejazdu: `PENDING`, `FOUND`.
- `Driver.java` – Klasa reprezentująca kierowcę.

### **2.3. Repozytorium**
- `RideRepository.java` (interfejs) – Zawiera metody do obsługi przejazdów.
- Implementacje w `infrastructure/repository/`:
    - `UberRideRepository.java`
    - `BoltRideRepository.java`
    - `FreeNowRideRepository.java`

### **2.4. Serwis**
- `RideService.java` – Zawiera metody do tworzenia nowych przejazdów oraz pobierania wolnych kierowców.
- `PricingService.java` (interfejs) – Abstrakcja do pobierania cen przejazdu.
- Implementacje w `infrastructure/pricing/`:
    - `UberPricingService.java`
    - `BoltPricingService.java`
    - `FreeNowPricingService.java`

### **2.5. Scheduler**
- Każdy dostawca ma własny scheduler, który wyszukuje klientów z flagą `PENDING`:
    - `UberRideScheduler.java`
    - `BoltRideScheduler.java`
    - `FreeNowRideScheduler.java`

### **2.6. Konfiguracja**
- `AppConfig.java` – Centralna konfiguracja aplikacji, wstrzykuje URL-e dla PricingService (w zależności od profilu) i obsługuje flagi `application.properties` do włączania schedulerów. W

---

## **3. Działanie aplikacji**
1. Użytkownik zgłasza chęć przejazdu poprzez `RideController`.
2. `RideService` zapisuje zgłoszenie w odpowiednim repozytorium (`RideRepository`).
3. PricingService pobiera cenę dla danej trasy i dostawcy.
4. Scheduler regularnie sprawdza zgłoszenia `PENDING` i przypisuje kierowców.
5. Po znalezieniu kierowcy status zmienia się na `FOUND`.

---

