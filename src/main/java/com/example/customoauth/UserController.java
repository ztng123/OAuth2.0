package com.example.customoauth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/joinProc")
    public String join(DTO dto) {
        try {
            userService.save(dto);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/join?error=User already exists!";
        }
        return "redirect:/login";
    }

    @GetMapping("/editPassword")
    public String showEditPasswordForm(Model model) {
        // 현재 로그인된 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                model.addAttribute("user", user);
                return "updatePassword";
            }
        }
        return "redirect:/";
    }


    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute("user") User user, @RequestParam("newPassword") String newPassword) {
        userService.updatePassword(user.getId(), newPassword);
        return "redirect:/"; // 비밀번호 수정 후 메인 페이지로 리다이렉트
    }

    @PostMapping("/deleteUser")
    public String deleteUser(HttpServletRequest request) {
        // Get the current logged-in user's username
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            userService.deleteUserByUsername(username);

            // Invalidate the session
            request.getSession().invalidate();
        }
        return "redirect:/login"; // Redirect to the main page after user deletion
    }



}
