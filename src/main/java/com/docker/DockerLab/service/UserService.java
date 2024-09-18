//package com.docker.DockerLab.service;
//
//import com.docker.DockerLab.model.User;
//import com.docker.DockerLab.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//
//    private UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
//
//    public User updateUser(Long id, User userDetails) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setName(userDetails.getName());
//                    user.setEmail(userDetails.getEmail());
//                    return userRepository.save(user);
//                })
//                .orElse(null);
//    }
//
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//}
//
