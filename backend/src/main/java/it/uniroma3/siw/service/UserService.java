package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.exceptions.DuplicateEmailException;
import it.uniroma3.siw.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly=true)
	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public User saveUser(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new DuplicateEmailException(user.getEmail());
		}
		return userRepository.save(user);
	}
	@Transactional
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

}
