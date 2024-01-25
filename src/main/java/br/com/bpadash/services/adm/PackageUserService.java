package br.com.bpadash.services.adm;

import br.com.bpadash.model.user.PackageUser;
import br.com.bpadash.repository.PackageUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageUserService {

    @Autowired
    private PackageUserRepository packageUserRepository;

    public PackageUser get(String packageName) {
        return packageUserRepository.findFirstByPackageName(packageName);
    }

    public void save(PackageUser packageUser) {
        packageUserRepository.save(packageUser);
    }
}
