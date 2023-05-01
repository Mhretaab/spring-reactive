package com.example.springreactive.sport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sport")
public class Sport {

  @Id
  private Integer id;

  @Column("name")
  private String name;

  public Sport(String name) {
    this.name = name;
  }
}