package com.example.restfuldemoproject;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
public class HomeController {
    private final MessageSource messageSource;
    private final UserService userService;


    @Autowired
    public HomeController(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldI18N() {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println("\n\n\n locale: " + locale + "\n\n\n");
        return messageSource.getMessage("good.morning.yoyo", null, "Default message", locale);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public MappingJacksonValue getUserById(@PathVariable int id) {
//        User user = userService.findOne(id);
        User user = new User(1, "abc", "lucknow");
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "city");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
        mappingJacksonValue.setFilters(filters);

//        EntityModel<User> entityModel = EntityModel.of(user);
//        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
//        entityModel.add(webMvcLinkBuilder.withRel("All users"));

        return mappingJacksonValue;
    }


}
