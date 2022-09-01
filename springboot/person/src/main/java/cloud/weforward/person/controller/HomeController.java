//package cloud.weforward.person.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import cloud.weforward.person.command.PersonCmdExe;
//import cloud.weforward.person.service.PersonService;
//import cloud.weforward.person.weforward.param.CreatePersonParam;
//import cloud.weforward.person.weforward.view.PersonView;
//
///**
// * @author daibo
// * @date 2022/9/1 14:59
// */
//@RestController
//public class HomeController {
//
//    @Autowired
//    protected PersonService m_PersonService;
//
//    @Autowired
//    protected PersonCmdExe m_PersonCmdExe;
//
//    @GetMapping("/greeting")
//    public PersonView create(@Validated CreatePersonParam param) {
//        return PersonView.valueOf(m_PersonService.create(param.getName()));
//    }
//}
