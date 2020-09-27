package com.travel.status.controller;

import com.travel.status.config.JwtTokenUtil;
import com.travel.status.model.JwtRequest;
import com.travel.status.model.UserDTO;
import com.travel.status.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate")
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession(true);
        session.removeAttribute("token");

        return "redirect:/home?logout=true";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(JwtRequest authenticationRequest, HttpServletRequest req) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        HttpSession oldSession = req.getSession();
        if(oldSession != null){
            oldSession.invalidate();
        }

        HttpSession session = req.getSession();
        session.setAttribute("token", "Bearer "+ token);
        return "redirect:/posts";
    }

    @RequestMapping(value = "/register")
    public String getSignUpPage() {
        return "sign-up";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(UserDTO user) throws Exception {
        userDetailsService.save(user);
        return "redirect:/authenticate";
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
