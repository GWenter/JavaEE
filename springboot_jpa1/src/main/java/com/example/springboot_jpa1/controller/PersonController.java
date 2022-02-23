package com.example.springboot_jpa1.controller;

import com.example.springboot_jpa1.DAO.PersonRepository;
import com.example.springboot_jpa1.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    // 增加函数
    @PostMapping(path = "addPerson")
    public void addPerson(@RequestBody Person person) {

        personRepository.save(person);
        //personRepository.
    }
    //删除函数
    @DeleteMapping(path = "deletePerson")
   /* public void deletePerson(@RequestBody Person person) {
        personRepository.delete(person);
    }*/
    public  void deletePerson(long id){
        personRepository.deleteById(id);
    }
//    @GetMapping("/findByName")
//    public  Person findByName(/*@RequestParam("name")*/ String name){
//       return personRepository.findByName(name);
//    }

    //模糊查询
    @PostMapping("/findByNameLike")
    public List<Person> findByName(String name){
        return personRepository.findByNameContaining(name);
    }

    //显示数据
    @GetMapping("/findAll")
//    public List<Person> findAll( Sort var1){
//
//         return personRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
//    }
    public List<Person> findAll( ){
        return personRepository.findAll();
    }
    @GetMapping("/findByAgeLessThan")
    public  List<Person> findByAgeLessThan( int age){
        return personRepository.findByAgeLessThan(age);
    }

    //添加数据
    @PostMapping("/add")
    public String add(@RequestBody Person person){
        personRepository.save(person);
        return "true";
    }

    //修改数据时get要修改的值的id
    @GetMapping("/findById")
    public Person find (Long id){
        return  personRepository.findById(id).get();
        }


    //修改函数
    @PostMapping("/edit")
    public String edit(@RequestBody Person person){
        personRepository.save(person);
        return "true";
    }
}
