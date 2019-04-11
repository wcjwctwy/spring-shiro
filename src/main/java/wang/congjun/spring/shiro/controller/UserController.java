package wang.congjun.spring.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author wangcongjun
 * @date 2019/4/11 15:43
 */
@Controller
public class UserController {
    @RequestMapping("hello")
    @ResponseBody
    public String test(){
        return "ok";
    }
    @RequestMapping({"/","/index"})
    public String index(Model model,String title){
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        Session session = subject.getSession();
        System.out.println(session);
        model.addAttribute("title",title);
        model.addAttribute("username",principal);
        return "index";
    }
    @RequestMapping("user/add")
    public String add(Model model,String title){
        model.addAttribute("title",title);
        return "user/add";
    }
    @RequestMapping("user/update")
    public String update(Model model,String title){
        model.addAttribute("title",title);
        return "user/update";
    }
    @RequestMapping("toLogin")
    public String login(Model model,String title){
        model.addAttribute("title",title);
        return "login";
    }
    @RequestMapping("noAuth")
    public String noAuth(){
        return "noAuth";
    }
    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:index";
    }
    @RequestMapping("login")
    public String login(Model model,String username,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
            model.addAttribute("msg","login success");
            return "redirect:index";
        } catch (UnknownAccountException e) {
            System.out.println("user not exist");
            model.addAttribute("msg","user not exist");
        } catch (IncorrectCredentialsException e1){
            System.out.println("password is Incorrect");
            model.addAttribute("msg","password is Incorrect");
        }
        return "login";
    }
}
