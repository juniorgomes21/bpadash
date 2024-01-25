package br.com.bpadash.repository;

import br.com.bpadash.model.user.PackageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageUserRepository extends JpaRepository<PackageUser, Long> {
    PackageUser findFirstByPackageName(String packageName);
}
