package br.com.bpadash.services.user;

import br.com.bpadash.model.user.*;
import br.com.bpadash.params.user.*;
import br.com.bpadash.repository.user.EmployeeRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserService userService;

    public Optional<Employee> get(User user, String employeeKey) {
        List<AccountLogged> accountLoggeds = user.getSessionUser().getAccountLoggeds();

        Optional<AccountLogged> accountLoggedOptional = accountLoggeds.stream()
            .filter( accountLogged -> accountLogged.getKeyEmployee().equals(employeeKey))
            .findFirst();

        if(accountLoggedOptional.isEmpty()) return Optional.empty();

        AccountLogged accountLogged = accountLoggedOptional.get();

        return Optional.of(accountLogged.getEmployee());
    }


    /**
     * Cria um novo funcionário caso o número máximo de funcionários não foi alcançado.
     * @param user
     * @param paramCreateEmployee
     * @return
     */
    public String newEmployeeAndSave(User user, ParamCreateEmployee paramCreateEmployee) {
        if(isMaxSession(user)) {
            if(!paramCreateEmployee.getPassword().equals(paramCreateEmployee.getPasswordConfirm())) {
                return "DIFFERENT PASSWORDS";
            }

            String response = this.existEmployeeCreate(user, paramCreateEmployee.getName(), paramCreateEmployee.getEmail());
            if(!response.equals("NOT")) return response;

            user.getEmployeeRegistered().add(new Employee(paramCreateEmployee, user));

            userService.save(user);

            return "CREATE";
        }

        return "MAX EMPLOYEE REGISTERED";
    }


    /**
     * Verifica se existe um funcionário com o nome e email.
     * @param user
     * @param employee
     * @return
     */
    private String existEmployeeEdit(User user, Employee employee) {

        for(Employee employeex: user.getEmployeeRegistered()) {
            if(!employeex.getId().equals(employee.getId())) {
                if(employeex.getName().equals(employee.getName())) {
                    return "EXIST NAME";
                } else if(employeex.getEmail().equals(employee.getEmail())) {
                    return "EXIST EMAIL";
                }
            }
        }

        return "NOT";
    }

    /**
     * Verifica se existe um funcionário com o nome e email.
     * @param user
     * @param paramEdit
     * @return
     */
    private String existEmployeeEdit(User user, Long id, ParamEditEmployee paramEdit) {

        for(Employee employeex: user.getEmployeeRegistered()) {
            if(!employeex.getId().equals(id)) {
                if(employeex.getName().equals(paramEdit.getName())) {
                    return "EXIST NAME";
                } else if(employeex.getEmail().equals(paramEdit.getEmail())) {
                    return "EXIST EMAIL";
                }
            }
        }

        return "NOT";
    }

    /**
     * Verifica se existe um funcionário com o nome e email.
     * @param user
     * @param name
     * @param email
     * @return
     */
    private String existEmployeeCreate(User user, String name, String email) {
        List<Employee> employeeList = user.getEmployeeRegistered();

        boolean existName = employeeList.stream().anyMatch( employeex ->  employeex.getName().equals(name));
        if(existName) {
            return "EXIST NAME";
        }

        boolean existEmail = employeeList.stream().anyMatch( employeex ->  employeex.getEmail().equals(EnCryptionAESService.encrypt(email)));
        if(existEmail) {
            return "EXIST EMAIL";
        }

        return "NOT";
    }

    public String editAndSave(Employee employee, User user, ParamEditEmployee paramEditEmployee) {

        String response = existEmployeeEdit(user, employee.getId(), paramEditEmployee);
        if(!response.equals("NOT")) return response;

        employee.setName(paramEditEmployee.getName());
        employee.setEmail(EnCryptionAESService.encrypt(paramEditEmployee.getEmail()));

        userService.save(user);

        return "OK";
    }

    /**
     * Master Editando um funcionário.
     * @param user
     * @param paramEditEmployeeMaster
     * @return
     */
    public String editMasterAndSave(String id, User user, ParamEditEmployeeMaster paramEditEmployeeMaster) {

        Optional<Employee> employeeOptional = this.get(user, id);

        if(employeeOptional.isEmpty()) return "NOT FOUND EMPLOYEE";

        Employee employee = employeeOptional.get();

        String response = existEmployeeEdit(user, employee);
        if(!response.equals("NOT")) return response;

        employee.setName(paramEditEmployeeMaster.getName());
        employee.setEmail(EnCryptionAESService.encrypt(paramEditEmployeeMaster.getEmail()));

        userService.save(user);

        return "OK";
    }


    /**
     * Remove o funcionário da lista de funcionários registrados.
     * @param user
     * @param employee
     * @return
     */
    public String deleteEmployeeAndSave(Employee employee, User user) {
        if(employee.isMaster()) return "NOT DELETE MASTER";

        user.getEmployeeRegistered().remove(employee);

        userService.save(user);

        return "OK";
    }


    /**
     * Verifica se existe o ID do funcionário dentro da conta de um usuário.
     * @param id
     * @param user
     * @return
     */
    public boolean existId(String id, User user) {
        try {
            Long idDecrypt = Long.parseLong(EnCryptionAESService.decrypt(id));

            return user.getEmployeeRegistered().stream().anyMatch(employee -> employee.getId().equals(idDecrypt));
        } catch (EncryptionOperationNotPossibleException e) {
            return false;
        }
    }


    /**
     * Verifica se o número máximo de Funcionários já está registrado.
     * @param user
     * @return
     */
    private boolean isMaxSession(User user) {

        int countSession = user.getEmployeeRegistered().size() + 1;
        int countMaxEmployee = user.getCountMaxEmployee();

        return countSession <= countMaxEmployee;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }


    /**
     * Verifica se é o funcionário master.
     * @param user
     * @param employeeKey
     * @return
     */
    public boolean isMaster(User user , String employeeKey) {
        try {
            Optional<Employee> employeeOptional = this.get(user, employeeKey);

            return employeeOptional.map(Employee::isMaster).orElse(false);

        } catch (EncryptionOperationNotPossibleException e) {
            return false;
        }
    }

    public void editPermissionAndSave(Employee employee , User user , ParamEditEmployeePermissions paramEdit) {
        PermissionsEmployee permissions = employee.getPermissions();
        permissions.setAddBpa(paramEdit.isAddBpa());
        permissions.setEditBpa(paramEdit.isEditBpa());
        permissions.setDownloadBpa(paramEdit.isDownloadBpa());
        permissions.setDeleteBpa(paramEdit.isDeleteBpa());
        permissions.setAddFpo(paramEdit.isAddFpo());
        permissions.setDeleteFpo(paramEdit.isDeleteFpo());
        permissions.setAddProf(paramEdit.isAddProf());
        permissions.setEditProf(paramEdit.isEditProf());
        permissions.setDeleteProf(paramEdit.isDeleteProf());

        userService.save(user);
    }

    public boolean testPassword(Employee employee, String password) {
        return employee.getPassword().equals(EnCryptionAESService.hashString(password));
    }

    public String editPasswordAndSave(Employee employee , User user , ParamNewPassword paramEdit) {
        String newPassword = paramEdit.getNewPassword();

        if(!newPassword.equals(paramEdit.getConfPassword())) return "NOT EQUALS PASSWORD";

        employee.setPassword(EnCryptionAESService.hashString(newPassword));

        userService.save(user);

        return "OK";
    }
}
