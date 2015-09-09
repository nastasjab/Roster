package lv.javaguru.java2.servlet.mvc.config;

import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDAO userDAO;

    private final String DEFAULT_USER="admin";
    private final String DEFAULT_PASSW="admin";

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDetails loadUserByUsername(String username) {
        // fake user, when no admin user exist
        if (!userDAO.existAtLeastOneAdminUser()) {
            if (!username.equals(DEFAULT_USER))
                throw new UsernameNotFoundException("");

            return new org.springframework.security.core.userdetails.User(DEFAULT_USER, DEFAULT_PASSW,
                    AuthorityUtils
                            .commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        }

        User user = userDAO.getByObjectName(username);
        if (user == null)
            throw new UsernameNotFoundException("");

        // don't allow login usual USER, ADMIN only
        if (!user.getUserType().equals("A"))
            throw new UsernameNotFoundException("");

        List<GrantedAuthority> auth = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_ADMIN");

        String password = user.getPassword();
        return new org.springframework.security.core.userdetails.User(username, password,
                auth);
    }

}