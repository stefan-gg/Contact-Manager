package com.ing.contactmanager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "uid", updatable = false)
    private UUID uid;

    @Size(max = 50, message = "Contact's first name cannot be null or greater than 50")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Size(max = 50, message = "Contact's last name cannot be null or greater than 50")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Size(max = 100, message = "Info cannot be null or greater than 50")
    @Column(name = "info", length = 100)
    private String info;

    @Size(max = 100, message = "Address number cannot be null or greater than 100")
    @Column(name = "address", length = 100)
    private String address;

    @Size(max = 50, message = "Phone number cannot be null or greater than 50")
    @Column(name = "phone_number", nullable = false, length = 50)
    private String phoneNumber;

    @Size(max = 100, message = "Maximum email length is 100")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "contact_type_id", nullable = false)
    private ContactType contactType;
}
