package com.rk.hrm.models;

import com.rk.hrm.enums.PRIORITY;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "todo_note")
public class TodoNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PRIORITY priority;
    private String desc;
    @Temporal(TemporalType.TIMESTAMP)
    private Date added;
    private Boolean completed;
    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TodoNote todoNote = (TodoNote) o;
        return id != null && Objects.equals(id, todoNote.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}