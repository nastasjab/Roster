package lv.javaguru.java2.core.user;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.domain.user.User;

import java.util.List;

public interface UserFactory extends GenericService {

    List<User> getAllSorted()  throws Exception;

}
