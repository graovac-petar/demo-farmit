# Sistem za korisničke sugestije / prijave problema

REST API izgrađen u Spring Boot-u za prijavu i praćenje korisničkih sugestija ili problema u radu aplikacije. Projekat je organizovan po slojevima: Controller, Service, Repository.

---

## 📋 Sadržaj

1. [Opis projekta](#opis-projekta)
2. [Arhitektura i paketi](#arhitektura-i-paketi)
3. [Tehnologije](#tehnologije)
4. [Preduvjeti](#preduvjeti)
5. [Instalacija i pokretanje](#instalacija-i-pokretanje)
6. [API krajnje tačke (Endpoints)](#api-krajnje-tačke-endpoints)
7. [Model podataka (DTO & Entity)](#model-podataka-dto--entity)
8. [Rukovanje greškama](#rukovanje-greškama)
9. [Buduće funkcionalnosti](#buduće-funkcionalnosti)



---

## Opis projekta

Ovaj API omogućava:

- **Kreiranje**, **pregled**, **izmenu** i **brisanje** korisničkih sugestija/prijava problema (`Feedback`).
- **Filtriranje** po tipu (`BUG`, `FEATURE`, `OTHER`) i statusu (`NEW`, `IN_PROGRESS`, `RESOLVED`).
- **Upravljanje korisnicima** (`User`) koji prijavljuju sugestije.
- Automatsko **slanje email obaveštenja** prilikom promene statusa ili hitnosti, kao i dela za odgovor, gde se korisniku odgovara na bug. 
- Brojanje prijavljenih bugova po korisniku (User) u cilju nagradjivanja istih.

---

## Arhitektura i paketi

```
com.farmit.demo_farmit
├── controller        # REST kontroleri (FeedbackController, UserController)
├── service           # Poslovna logika (FeedbackService, UserService, Email Service)
├── repository        # Spring Data JPA repozitorijumi
├── DTO               # Prenos podataka (FeedbackRequest, FeedbackResponse, FeedbackUpdate, UserRequest, UserResponse, EmailDetails)
├── entity            # JPA entiteti (Feedback, User) i Enum-ovi (Type, Status, Urgency)
├── exception         # Globalni handler i custom izuzeci (ResourceNotFoundException, BadRequestException, GlobalExceptionHandler)
```

---

## Tehnologije

- Java 21
- Spring Boot 3
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-mail
- Hibernate / JPA
- Lombok

---

## Preduslovi

- JDK 21&#x20;
- MySQL
- Pristup SMTP serveru za slanje e‑mailova (objasnjeno u daljem tekstu)

---

## Instalacija i pokretanje

1. **Kloniraj repozitorij**

   ```bash
   git clone https://github.com/korisnik/demo-farmit.git
   cd demo-farmit
   ```

2. \*\*Konfiguriši \*\***`application.properties`**

   ```properties
   spring.application.name=demo-farmit
   spring.datasource.url=jdbc:mysql://localhost:3306/demo-farmit
   spring.datasource.username=root (username baze)
   spring.datasource.password=password (sifra baze)
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update

   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=petargraovac01@gmail.com (postaviti email adresu za lokalno pokretanje)
   spring.mail.password=ulpyhjzxptzzmgqv (povezati se sa gmailom sa kog zelite da saljete mejlobve)
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

3. **Build i pokretanje**

4. API je dostupan na `http://localhost:8080/api`

---

## API krajnje tačke (Endpoints)

### Feedback

| Metoda | URL                                                | Opis                            |
| ------ | -------------------------------------------------- | ------------------------------- |
| GET    | `/api/feedback`                                    | Vrati sve prijave               |
| GET    | `/api/feedback/{id}`                               | Vrati prijavu po ID             |
| POST   | `/api/feedback`                                    | Kreiraj novu prijavu            |
| PUT    | `/api/feedback/{id}`                               | Ažuriraj postojeću prijavu      |
| DELETE | `/api/feedback/{id}`                               | Obriši prijavu                  |
| GET    | `/api/feedback/filter?type={type}&status={status}` | Filtriraj po tipu i/ili statusu |

### User

| Metoda | URL              | Opis                    |
| ------ | ---------------- | ----------------------- |
| POST   | `/api/user`      | Kreiraj novog korisnika |
| GET    | `/api/user/{id}` | Vrati korisnika po ID   |

---

## Model podataka (DTO & Entity)

### Feedback

- **Entitet**: `Feedback`

  - `id: Long`
  - `type: Type { BUG, FEATURE, OTHER }`
  - `description: String`
  - `status: Status { NEW, IN_PROGRESS, RESOLVED }`
  - `urgency: Urgency { LOW, MEDIUM, HIGH, CRITICAL }`
  - `response: String`
  - `createdAt`, `updatedAt` (automatski timestamp)
  - `user: User`

- **DTO**:

  - `FeedbackRequest` (za kreiranje)
  - `FeedbackUpdate` (za izmenu statusa/hitnosti/response)
  - `FeedbackResponse` (za vraćanje klijentu)

### User

- **Entitet**: `User`

  - `id: Long`
  - `name: String`
  - `email: String`
  - `numberBug: Integer`
  - `numberFeature: Integer`
  - `feedbacks: List<Feedback>`

- **DTO**:

  - `UserRequest`, `UserResponse`

---

## Rukovanje greškama

Svi izuzeci se presreću u `@RestControllerAdvice` klasi `GlobalExceptionHandler` i vraćaju JSON:

```json
{
  "timestamp": "2025-04-18T22:15:30",
  "status": 404,
  "message": "Feedback not found"
}
```

- **404 Not Found** – `ResourceNotFoundException`
- **400 Bad Request** – `BadRequestException`
- **500 Internal Server Error** – svi ostali izuzeci

---

## Buduće funkcionalnosti

- 🔢 **Ocenjivanje**: Korisnici će moći da ostave poene (rating) na osnovu prijavljenih feature-a i bagova; ocene će se beležiti u sistemu i koristiti za analizu i prioritizaciju, kao i dodavanje poklona i popusta kupcima. 
- 🖼️ **Skladištenje slika**: Mogućnost upload-a i čuvanja slika uz prijave kako bi se lakše reprodukovao i dijagnostikovao bug.
- 📄 **Slanje PDF izveštaja**: Generisanje i slanje PDF dokumenta u odgovoru na prijavljeni bug/feature radi detaljnijeg izveštaja.

