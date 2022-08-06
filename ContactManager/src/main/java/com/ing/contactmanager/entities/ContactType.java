package com.ing.contactmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contact_types")
public class ContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "uid", updatable = false)
    private UUID uid;

    @JsonIgnore
    @OneToMany(mappedBy = "contactType")
    private List<Contact> contacts;

    @Column(name = "contact_type_name", nullable = false, length = 50, unique = true)
    private String contactTypeName;
}
