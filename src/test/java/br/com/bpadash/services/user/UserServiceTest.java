package br.com.bpadash.services.user;

import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Supondo que UserRepository seja a interface que você usa para interagir com o banco de dados

    @InjectMocks
    private UserService userStorageService; // Supondo que UserStorageService seja a classe que contém o método que você quer testar


    @Test
    @DisplayName("Deveria aumentar o armazenamento livre do usuário e diminuir o armazenamento usado")
    void updateStorageAndSave_AddTrue() {
        Long totalBytes = 50L;

        User user = new User();
        user.setStorageUsed(100L);
        user.setStorageFree(200L);


        userStorageService.updateStorageAndSave(user, totalBytes, true);

        // Verifique se os métodos foram chamados corretamente
        verify(userRepository, times(1)).saveAndFlush(user);

        // Verifique se os valores foram atualizados corretamente
        assert(user.getStorageUsed() == 50L);
        assert(user.getStorageFree() == 250L);
    }

    @Test
    @DisplayName("Deveria diminuir o armazenamento livre do usuário e aumentar o armazenamento usado")
    void updateStorageAndSave_AddFalse() {
        Long totalBytes = 50L;

        User user = new User();
        user.setStorageUsed(100L);
        user.setStorageFree(200L);

        userStorageService.updateStorageAndSave(user, totalBytes, false);

        // Verifique se os métodos foram chamados corretamente
        verify(userRepository, times(1)).saveAndFlush(user);
        // Verifique se os valores foram atualizados corretamente
        assert(user.getStorageUsed() == 150L);
        assert(user.getStorageFree() == 150L);
    }


    @Test
    @DisplayName("Deveria ajustar o armazenamento do usuário conforme ele usa")
    void updateStorageBpaiAndSave() {
        Long oldBytes = 20L;
        Long newBytes = 50L;

        User user = new User();
        user.setStorageUsed(100L);
        user.setStorageFree(200L);

        userStorageService.updateStorageBpaiAndSave(user, oldBytes, newBytes);

        verify(userRepository, times(1)).save(user);

        assert(user.getStorageUsed() == 130L);
        assert(user.getStorageFree() == 170L);
    }

}