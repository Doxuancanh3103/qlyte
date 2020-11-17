package vt.qlkdtt.yte.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
//        User user = userRepository.findByUsername(username);
        User user = new User();
        user.setUserId(1L);
        user.setUserName("admin");
        user.setPassword("$2a$10$Ng89Cx3NgjJXZaZ9QANRYeeKmzZuohrokPo4c6b906FkioE3VNxce");
        //admin/1234
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    // JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("admin");
        user.setPassword("$2a$10$Ng89Cx3NgjJXZaZ9QANRYeeKmzZuohrokPo4c6b906FkioE3VNxce");
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with id : " + id)
//        );
        return new CustomUserDetails(user);
    }

}