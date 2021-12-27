package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.data.repository.UserRepository;
import com.tahadonuk.restaurantmanagementsystem.dto.Address;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.Stats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.UserStats;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import com.tahadonuk.restaurantmanagementsystem.exception.UserConflictException;
import com.tahadonuk.restaurantmanagementsystem.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public AppUser saveUser(UserDTO userDTO) throws Exception{
        if(isExists(userDTO.getEmail())) {
            throw new UserConflictException("An employee with email  '" + userDTO.getEmail() + "' is already exists.");
        } else {
            AppUser user = new AppUser();

            user.setPassword(encoder.encode(userDTO.getPassword()));
            user.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(userDTO.getDateOfBirth()));
            user.setJoinDate(new Date());
            user.setName(new Name(userDTO.getFirstName(), userDTO.getLastName()));
            user.setAddress(new Address(userDTO.getStreet(), userDTO.getApartment()));
            user.setRole(UserRole.USER); // default value
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setSalary(0); // default value

            userRepository.save(user);

            return user;
        }
    }

    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    public void updateLoginDate(final String email) {
        if(isExists(email)) {
            userRepository.updateLastLogin(new Date(), email);
        }
    }

    public void updateLogoutDate(final String email) {
        if(isExists(email)) {
            userRepository.updateLastLogout(new Date(), email);
        }
    }

    public UserDTO getUserFromEntity(AppUser user) {
        UserDTO userData = new UserDTO();

        userData.setFirstName(user.getName().getFirstName());
        userData.setLastName(user.getName().getLastName());
        userData.setEmail(user.getEmail());
        userData.setRole(user.getRole().toString());

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

        userData.setLastLoginDate(format.format(user.getLastLoginDate()));

        if(user.getLastLogoutDate() != null) {
            userData.setLastLogoutDate(format.format(user.getLastLogoutDate()));
        }

        return userData;
    }

    public void deleteUser(long id) {
        if(isExists(id)) {
            userRepository.deleteById(id);
        }
        else throw new UserNotFoundException("There is no such employee with given id: '" + id + "'");
    }

    public AppUser getUserById(long id) {
        if(isExists(id)) {
            return userRepository.findById(id).get();
        }
        else throw new UserNotFoundException("There is no such employee with given id: '" + id + "'");
    }

    public List<AppUser> getUsersByName(Name name) {
        if(userRepository.existsByName(name)) {
            List<AppUser> users = userRepository.findAppUsersByName(name);

            return users;
        }
        else throw new UserNotFoundException("There is no employee with name '" + name.getFirstName() + " " + name.getLastName() + "'");
    }

    public AppUser getUserByNumber(String phoneNumber) {
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            Optional<AppUser> emp = userRepository.findAppUserByPhoneNumber(phoneNumber);
            return emp.get();
        }
        else throw new UserNotFoundException("There is no employee with given phone number: '" + phoneNumber + "'");
    }

    public List<AppUser> getUsersByRole(UserRole role) {
        List<AppUser> users = userRepository.findAppUsersByRole(role);
        return users;
    }

    public int countUsersByRole(UserRole role) {
        return userRepository.countAppUsersByRole(role);
    }

    public AppUser getUserByEmail(String email) {
        if(isExists(email)) {
            return userRepository.findAppUserByEmail(email).get();
        }
        else throw new NotFoundException("There is no employee with email: '" + email + "'");
    }

    public Stats getStats() {
        UserStats userStats = new UserStats();

        userStats.setTotalCount(userRepository.count());
        userStats.setAdminCount(countUsersByRole(UserRole.ADMIN));
        userStats.setManagerCount(countUsersByRole(UserRole.MANAGER));
        userStats.setEmployeeCount(countUsersByRole(UserRole.EMPLOYEE));
        userStats.setUserCount(countUsersByRole(UserRole.USER));

        return userStats;
    }

    public void changePassword(long id, String password) {
        if(isExists(id)) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            userRepository.updatePassword(id, encoder.encode(password));
        }
        else throw new UserNotFoundException("No such user with given ID.");
    }

    public void changeAddress(long id, Address address) {
        if(isExists(id)) {
            userRepository.changeAddress(id, address);
        }
        else throw new UserNotFoundException("No such user with given ID.");
    }

    public void changeName(long id, Name name) throws UserNotFoundException{
        if(isExists(id)) {
            userRepository.changeName(id, name);
        }
        else throw new UserNotFoundException("No such user with given ID.");
    }

    public void changePhoneNumber(long id, String phoneNumber) throws UserNotFoundException{
        if(isExists(id)) {
            userRepository.changePhoneNumber(id,phoneNumber);
        }
        else throw new UserNotFoundException("No such user with given ID.");
    }

    public void changeEmail(long id, String email) throws UserNotFoundException{
        if(isExists(id)) {
            userRepository.changeEmail(id,email);
        }
        else throw new UserNotFoundException("No such user with given ID.");
    }

    public void assignRole(long id, UserRole role) throws UserNotFoundException{
        if(isExists(id)) {
            userRepository.assignRole(id, role);
        }
        else throw new UserNotFoundException("No such user with given ID.");
    }

    public void updateSalary(long id, double salary) throws UserNotFoundException{
        if(isExists(id)) {
            userRepository.updateSalary(id, salary);
        }
        else throw new UserNotFoundException("No such user with given ID.");
    }

    public boolean isExists(long id) {
        if(userRepository.existsById(id)) {
            return true;
        }
        else return false;
    }

    public boolean isExists(String email) {
        if(userRepository.existsByEmail(email)) {
            return true;
        }
        else return false;
    }
}
