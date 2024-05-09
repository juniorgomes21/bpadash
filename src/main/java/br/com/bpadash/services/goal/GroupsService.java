package br.com.bpadash.services.goal;

import br.com.bpadash.model.GroupsPa.GroupsPa;
import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.goal.GroupsRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupsService {

    @Autowired
    private GroupsRepositoy groupsRepositoy;

    public Optional<GroupsPa> get(User user) {
        return groupsRepositoy.getByUser(user);
    }

    /**
     * Adiciona Um PA um grupo de PAs de um determinado usuário.
     * @param groupsPa
     * @param pa
     * @return
     */
    public GroupsPa addPa(GroupsPa groupsPa , String pa) {

        int count = groupsPa.getCount();

        if(count > 0) {
            groupsPa.setCount(groupsPa.getCount() - 1);
            groupsPa.getPaList().add(pa);

            return groupsPa;
        }

        return null;
    }


    /**
     * Cria um novo Grupo e adiciona um PA, e associa a um usuário.
     * @param user
     * @param pa
     * @return
     */
    public GroupsPa create(User user , String pa) {
        GroupsPa groupsPa = new GroupsPa();

        groupsPa.setUser(user);
        groupsPa.getPaList().add(pa);

        return groupsPa;
    }

    /**
     * Remove um PA do grupo.
     * @param groupsPa
     * @param pa
     * @return
     */
    public GroupsPa deletePa(GroupsPa groupsPa , String pa) {
        groupsPa.setCount(groupsPa.getCount() + 1);
        groupsPa.getPaList().remove(pa);

        return groupsPa;
    }

    public void save(GroupsPa groupsPa) {
        groupsRepositoy.save(groupsPa);
    }



}
