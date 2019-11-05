package com.mul.entity;

import javax.persistence.*;

@Entity
@Table(name = "program_studi", schema = "pbo220191", catalog = "")
public class ProgramStudi {
    private int id;
    private String nama;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nama", nullable = true, length = 100)
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramStudi that = (ProgramStudi) o;

        if (id != that.id) return false;
        if (nama != null ? !nama.equals(that.nama) : that.nama != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nama != null ? nama.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.nama;
    }
}
