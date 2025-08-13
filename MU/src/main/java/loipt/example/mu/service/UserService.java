package loipt.example.mu.service;

import loipt.example.mu.entity.User;
import loipt.example.mu.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
    
    public List<User> searchByCountry(String country) {
        return userRepository.searchByCountry(country);
    }
    
    public List<User> getAllUsersSorted(String sortBy) {
        return userRepository.getAllUsersSorted(sortBy);
    }
    
    public List<User> searchByCountryAndSort(String country, String sortBy) {
        return userRepository.searchByCountryAndSort(country, sortBy);
    }
    
    public void createUser(User user) {
        userRepository.createUser(user);
    }
    
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
    
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }
    
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
}
