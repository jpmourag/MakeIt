package br.com.todo.api.repositories;

import br.com.todo.api.entities.User;
import br.com.todo.api.modules.user.dto.FilteredUserDataDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    
    @Modifying
    @Query("DELETE FROM USER_TABLE u WHERE u.email = :email")
    void deleteByEmail(String email);

    @Query("SELECT new br.com.todo.api.modules.user.dto." +
            "FilteredUserDataDto(u.id, u.name, u.email) " +
            "FROM USER_TABLE u WHERE u.email = :email")
    FilteredUserDataDto getUserByEmail(String email);
}
