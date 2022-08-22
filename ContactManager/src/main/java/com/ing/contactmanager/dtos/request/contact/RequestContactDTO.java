package com.ing.contactmanager.dtos.request.contact;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestContactDTO {

    @Email
    @Size(max = 100)
    @Pattern(regexp = "^(?=.{1,100}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\" +
            ".[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Email format in invalid")
    @CsvBindByName
    private String email;

    @Size(max = 50)
    @NotBlank()
    @CsvBindByName(required = true)
    private String firstName;

    @Size(max = 50)
    @NotBlank()
    @CsvBindByName(required = true)
    private String lastName;

    @Size(max = 100)
    @CsvBindByName
    private String info;

    @Size(max = 100)
    @CsvBindByName
    private String address;

    @NotBlank()
    @Size(max = 50)
    @CsvBindByName(required = true)
    private String phoneNumber;

    @Size(max = 50)
    @CsvBindByName(required = true)
    private String contactTypeName;
}
