package com.mul.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Mahasiswa {
    private String id;
    private String namaDepan;
    private String namaBelakang;
    private String tempatLahir;
    private Date tanggalLahir;
    private String alamat;
    private String email;
    private ProgramStudi programStudi;

    @Id
    @Column(name = "id", nullable = false, length = 9)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nama_depan", nullable = false, length = 100)
    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    @Basic
    @Column(name = "nama_belakang", nullable = true, length = 100)
    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    @Basic
    @Column(name = "tempat_lahir", nullable = false, length = 80)
    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    @Basic
    @Column(name = "tanggal_lahir", nullable = false)
    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    @Basic
    @Column(name = "alamat", nullable = false, length = 300)
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mahasiswa mahasiswa = (Mahasiswa) o;

        if (id != null ? !id.equals(mahasiswa.id) : mahasiswa.id != null) return false;
        if (namaDepan != null ? !namaDepan.equals(mahasiswa.namaDepan) : mahasiswa.namaDepan != null) return false;
        if (namaBelakang != null ? !namaBelakang.equals(mahasiswa.namaBelakang) : mahasiswa.namaBelakang != null)
            return false;
        if (tempatLahir != null ? !tempatLahir.equals(mahasiswa.tempatLahir) : mahasiswa.tempatLahir != null)
            return false;
        if (tanggalLahir != null ? !tanggalLahir.equals(mahasiswa.tanggalLahir) : mahasiswa.tanggalLahir != null)
            return false;
        if (alamat != null ? !alamat.equals(mahasiswa.alamat) : mahasiswa.alamat != null) return false;
        if (email != null ? !email.equals(mahasiswa.email) : mahasiswa.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (namaDepan != null ? namaDepan.hashCode() : 0);
        result = 31 * result + (namaBelakang != null ? namaBelakang.hashCode() : 0);
        result = 31 * result + (tempatLahir != null ? tempatLahir.hashCode() : 0);
        result = 31 * result + (tanggalLahir != null ? tanggalLahir.hashCode() : 0);
        result = 31 * result + (alamat != null ? alamat.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "program_studi_id", referencedColumnName = "id", nullable = false)
    public ProgramStudi getProgramStudi() {
        return programStudi;
    }

    public void setProgramStudi(ProgramStudi programStudi) {
        this.programStudi = programStudi;
    }
}
