package com.example.event_manager.controller;

import com.example.event_manager.form.UserForm;
import com.example.event_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("user")
@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/register")
  public String register(final Model model) {
    model.addAttribute("userNavBar", userService.getPrincipalSimple());
    model.addAttribute("user", new UserForm());

    return "security/register";
  }

  @PostMapping(value = "/register", params = "sign_up")
  public String signUp(
          @ModelAttribute("user") @Valid final UserForm user,
          final BindingResult bindingResult,
          final Model model,
          final RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("user", user);
      model.addAttribute("userNavBar", userService.getPrincipalSimple());

      return "security/register";
    } else {
      if (!userService.save(user)) {
        model.addAttribute("user", user);
        model.addAttribute("userNavBar", userService.getPrincipalSimple());
        redirectAttributes.addFlashAttribute(
            "registerError", "That username is taken. Try another.");
        return "security/register";
      } else {
        redirectAttributes.addFlashAttribute(
                "message", "Your account has been successfully created. You can log in");
        return "redirect:/user/login";
      }
    }
  }

  @GetMapping("/login")
  public String login(final Model model) {
    model.addAttribute("username", "");
    model.addAttribute("password", "");
    model.addAttribute("user", userService.getPrincipalSimple());
    model.addAttribute("isRedirected", false);
    model.addAttribute("isError", false);

    return "security/login";
  }

  @GetMapping(value = "/login/logout")
  public String loginWithRedirect(final Model model) {
    model.addAttribute("username", "");
    model.addAttribute("password", "");
    model.addAttribute("user", userService.getPrincipalSimple());
    model.addAttribute("isRedirected", true);
    model.addAttribute("isError", false);

    return "security/login";
  }

  @GetMapping(value = "/login/error")
  public String loginError(final Model model) {
    model.addAttribute("username", "");
    model.addAttribute("password", "");
    model.addAttribute("user", userService.getPrincipalSimple());
    model.addAttribute("isRedirected", false);
    model.addAttribute("isError", true);

    return "security/login";
  }

  @GetMapping("/profile")
  public String profile(final Model model) {
    model.addAttribute("user", userService.getPrincipalSimple());
    model.addAttribute("doesUsernameExists", false);
    return "security/profile";
  }

  @GetMapping("/profile/error")
  public String wrongUsername(final Model model) {
    model.addAttribute("user", userService.getPrincipalSimple());
    model.addAttribute("doesUsernameExists", true);
    return "security/profile";
  }

  @PostMapping("/edit")
  public String profile(@ModelAttribute final UserForm user) {
    if (userService.update(user)) {
      return "redirect:/user/logout";
    } else {
      return "redirect:/user/profile/error";
    }
  }
}
