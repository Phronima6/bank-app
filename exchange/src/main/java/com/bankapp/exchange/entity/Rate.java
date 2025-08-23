package com.bankapp.exchange.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "rates", schema = "exchange")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "value", nullable = false)
    Integer value;
    @Column(name = "base", nullable = false)
    boolean base;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(id, rate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
