package com.example.event_manager.service;

import com.example.event_manager.form.UserForm;
import com.example.event_manager.mapper.UserMapper;
import com.example.event_manager.mapper.UserSimpleInFormMapper;
import com.example.event_manager.mapper.UserSimpleMapper;
import com.example.event_manager.model.User;
import com.example.event_manager.repo.UserRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BasicServiceImpI<User, UserForm, UserRepo, UserMapper, Long>
    implements UserDetailsService, UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserSimpleMapper userSimpleMapper;
  private final UserSimpleInFormMapper userSimpleInFormMapper;

  public UserServiceImpl(
      final UserRepo userRepo,
      final UserMapper mapper,
      @Lazy final PasswordEncoder passwordEncoder,
      final UserSimpleMapper userSimpleMapper,
      final UserSimpleInFormMapper userSimpleInFormMapper) {
    super(userRepo, mapper);
    this.passwordEncoder = passwordEncoder;
    this.userSimpleMapper = userSimpleMapper;
    this.userSimpleInFormMapper = userSimpleInFormMapper;
  }

  @Override
  public boolean save(final UserForm object) {
    if (!repo.existsByLogin(object.getLogin())) {
      object.setPassword(passwordEncoder.encode(object.getPassword()));
      return super.save(object);
    } else {
      return false;
    }
  }

  @Override
  public boolean update(final UserForm user) {
    final User preincipl =
        (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (user.getLogin().equals(preincipl.getLogin())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return super.save(user);
    } else {
      if (!repo.existsByLogin(user.getLogin())) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
      } else {
        return false;
      }
    }
  }

  @Override
  public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
    User user = new User();
    try {
      user = repo.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
          "User with that login have not been find in database"));
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return user;
  }

  @Override
  public UserForm getPrincipal() {
    final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return !principal.equals("anonymousUser")
        ? mapper.mapToDTO((User) principal)
        : UserForm.builder().login(null).id(null).password(null).build();
  }

  @Override
  public boolean checkIsLogin() {
    final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return !principal.equals("anonymousUser");
  }

  @Override
  public UserForm getPrincipalSimple() {
    final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return !principal.equals("anonymousUser")
        ? userSimpleInFormMapper.mapToDTO(userSimpleMapper.mapToDTO((User) principal))
        : UserForm.builder().login(null).id(null).password(null).build();
  }
}
