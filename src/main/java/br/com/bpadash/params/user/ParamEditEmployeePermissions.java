package br.com.bpadash.params.user;

public class ParamEditEmployeePermissions {
    private boolean addBpa = true;
    private boolean editBpa = true;
    private boolean deleteBpa = true;
    private boolean downloadBpa = true;
    private boolean addFpo = true;
    private boolean deleteFpo = true;
    private boolean addProf = true;
    private boolean editProf = true;
    private boolean deleteProf = true;

    public ParamEditEmployeePermissions() {
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
