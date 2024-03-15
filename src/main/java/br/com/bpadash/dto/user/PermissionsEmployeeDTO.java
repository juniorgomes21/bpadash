package br.com.bpadash.dto.user;

import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.PermissionsEmployee;

import javax.persistence.OneToOne;

public class PermissionsEmployeeDTO {

    private Long id;
    private boolean addBpa; // ok
    private boolean editBpa; // ok
    private boolean deleteBpa; // ok
    private boolean downloadBpa; // ok
    private boolean addFpo;
    private boolean deleteFpo;
    private boolean addProf;
    private boolean editProf;
    private boolean deleteProf;

    public PermissionsEmployeeDTO() {
    }

    public PermissionsEmployeeDTO(PermissionsEmployee permissionsEmployee) {
        this.id = permissionsEmployee.getId();
        this.addBpa = permissionsEmployee.isAddBpa();
        this.editBpa = permissionsEmployee.isEditBpa();
        this.deleteBpa = permissionsEmployee.isDeleteBpa();
        this.downloadBpa = permissionsEmployee.isDownloadBpa();
        this.addFpo = permissionsEmployee.isAddFpo();
        this.deleteFpo = permissionsEmployee.isDeleteFpo();
        this.addProf = permissionsEmployee.isAddProf();
        this.editProf = permissionsEmployee.isEditProf();
        this.deleteProf = permissionsEmployee.isDeleteProf();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAddBpa() {
        return addBpa;
    }

    public void setAddBpa(boolean addBpa) {
        this.addBpa = addBpa;
    }

    public boolean isEditBpa() {
        return editBpa;
    }

    public void setEditBpa(boolean editBpa) {
        this.editBpa = editBpa;
    }

    public boolean isDeleteBpa() {
        return deleteBpa;
    }

    public void setDeleteBpa(boolean deleteBpa) {
        this.deleteBpa = deleteBpa;
    }

    public boolean isDownloadBpa() {
        return downloadBpa;
    }

    public void setDownloadBpa(boolean downloadBpa) {
        this.downloadBpa = downloadBpa;
    }

    public boolean isAddFpo() {
        return addFpo;
    }

    public void setAddFpo(boolean addFpo) {
        this.addFpo = addFpo;
    }

    public boolean isDeleteFpo() {
        return deleteFpo;
    }

    public void setDeleteFpo(boolean deleteFpo) {
        this.deleteFpo = deleteFpo;
    }

    public boolean isAddProf() {
        return addProf;
    }

    public void setAddProf(boolean addProf) {
        this.addProf = addProf;
    }

    public boolean isEditProf() {
        return editProf;
    }

    public void setEditProf(boolean editProf) {
        this.editProf = editProf;
    }

    public boolean isDeleteProf() {
        return deleteProf;
    }

    public void setDeleteProf(boolean deleteProf) {
        this.deleteProf = deleteProf;
    }

}
