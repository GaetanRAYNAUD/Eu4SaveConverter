package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.model.User;
import fr.graynaud.eu4saveconverter.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }
}
