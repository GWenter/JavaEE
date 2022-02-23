package com.example.springboot_jpa1.DAO;

import com.example.springboot_jpa1.pojo.Person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    //此处可以根据需要自行添加相关方法，注意方法名字遵循约定大于配置 convention over configuration
    //相关方名字需要遵守一定的关键字
    Person findByName(String name);
    List<Person> findByAgeLessThan(int age);
    void deletePersonById(long id);

    List<Person> findByNameContaining(String name);

}