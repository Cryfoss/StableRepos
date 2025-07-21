package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class Employee {
    public String name;
    List<String> skills;
}
