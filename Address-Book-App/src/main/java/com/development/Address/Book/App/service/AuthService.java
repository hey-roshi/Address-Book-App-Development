package com.development.Address.Book.App.service;

import com.development.Address.Book.App.dto.LoginDTO;
import com.development.Address.Book.App.dto.UserDTO;
import com.development.Address.Book.App.model.User;
import com.development.Address.Book.App.repository.UserRepository;
import com.development.Address.Book.App.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    EmailService emailService;

    public String registerUser(UserDTO userDTO) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
            if (existingUser.isPresent()) {
                return "Email is already in use!";
            }

            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            User user = new User();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(hashedPassword);
            userRepository.save(user);

            String token = JwtUtil.generateToken(user.getEmail());
            return "User registered successfully! Token: " + token;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during registration: " + e.getMessage();
        }
    }

    public String loginUser(LoginDTO loginDTO) {
        try {
            User user = userRepository.findByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found!"));

            if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return "Invalid email or password!";
            }

            sendLoginNotification(user.getEmail());
            String token = JwtUtil.generateToken(user.getEmail());
            return "Login successful! Check your email for a login notification, token: " + token;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during login: " + e.getMessage();
        }
    }

    public void sendLoginNotification(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Login Notification");
            message.setText("Hello,\n\nYou have successfully logged into your account.\n\nIf this wasn't you, please secure your account.");
            mailSender.send(message);
            System.out.println("Login notification email sent to: " + email);
        } catch (Exception e) {
            System.out.println("Error sending login notification email: " + e.getMessage());
        }
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    public String forgotPassword(String email, String newPassword) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return "Sorry! We cannot find the user email: " + email;
            }

            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            emailService.sendEmail(email, "Password Changed", "Your password has been successfully updated.");

            return "Password has been changed successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating password: " + e.getMessage();
        }
    }

    public String resetPassword(String email, String currentPassword, String newPassword) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return "User not found with email: " + email;
            }

            User user = userOptional.get();
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                return "Current password is incorrect!";
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Password reset successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error resetting password: " + e.getMessage();
        }
    }
}