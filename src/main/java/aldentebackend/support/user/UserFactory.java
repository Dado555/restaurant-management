package aldentebackend.support.user;

import aldentebackend.dto.user.UserCreateDTO;
import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.dto.user.UserUpdateDTO;
import aldentebackend.model.*;

import java.util.Date;

public class UserFactory {

    public static User createUser(UserCreateDTO userCreateDTO) throws Exception {
        User user = createUser(userCreateDTO.getRole());

        if(user != null) {
            user.setFirstName(userCreateDTO.getFirstName());
            user.setLastName(userCreateDTO.getLastName());
            user.setPhoneNumber(userCreateDTO.getPhoneNumber());
            user.setUsername(userCreateDTO.getUsername());
            user.setPassword(userCreateDTO.getPassword());

            Salary salary = new Salary();
            salary.setValue(Double.parseDouble(userCreateDTO.getSalary()));
            salary.setDate(new Date().getTime());
            user.getSalaries().add(salary);
        }

        return user;
    }

    public static User createUser(UserUpdateDTO userUpdateDTO) throws Exception {
        User user = createUser(userUpdateDTO.getRole());

        if(user != null) {
            user.setId(userUpdateDTO.getId());
            user.setFirstName(userUpdateDTO.getFirstName());
            user.setLastName(userUpdateDTO.getLastName());
            user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
            user.setUsername(userUpdateDTO.getUsername());
            user.setPassword(userUpdateDTO.getPassword());
        }

        return user;
    }

    public static User createUser(String role) throws Exception {
        User user = null;

        switch (role) {
            case "BARTENDER":
                user = new Bartender();
                break;
            case "WAITER":
                user = new Waiter();
                break;
            case "COOK":
                user = new Cook();
                break;
            case "MANAGER":
                user = new SuperUser();
                break;
            default:
                throw new Exception("Invalid user role");
        }

        return user;
    }

}
