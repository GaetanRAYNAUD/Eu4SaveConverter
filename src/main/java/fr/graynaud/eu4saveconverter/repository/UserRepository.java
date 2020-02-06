package fr.graynaud.eu4saveconverter.repository;

import fr.graynaud.eu4saveconverter.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> getFirstByLogin(String login);

    Optional<User> getFirstByResetKey(String resetKey);
}
