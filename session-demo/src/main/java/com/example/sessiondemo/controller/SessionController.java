package com.example.sessiondemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/15
 * @desc
 */
@RestController
@RequestMapping("/session")
public class SessionController {
    @RequestMapping("/save")
    public String saveSession(HttpSession session) {

        session.setAttribute("king", "hello-king");

        return "数据存入Session域！";
    }
    @RequestMapping("/get")
    public String getSession(HttpSession session) {

        String value = (String) session.getAttribute("king");

        return value;
    }
}
