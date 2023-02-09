package com.workmotion.employee.Models;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class Employee {

    @Id
    @Schema(description = "ID", accessMode = Schema.AccessMode.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema( description = "Employee Name")
    private String name;

    @Schema(description = "Employee State", accessMode = Schema.AccessMode.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private EmployeeState state = EmployeeState.ADDED;

}
