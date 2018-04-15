package springboot.ltq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.ltq.dao.ComputerDao;

@Controller
@RequestMapping(value = "computer")
public class ComputerController {

    @Autowired
    ComputerDao computerDao;

    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("list", computerDao.list(null));
        return "Computer";
    }
}
