package com.cefet.bakefy.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "TbAdmin")
public class Admin extends Usuario {
}
