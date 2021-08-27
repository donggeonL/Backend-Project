package com.project.nmt.model;

import com.project.nmt.dto.UserUpdateForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "\"user\"")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;          // 아이디
    private String password;        // 비번
    private String name;            // 이름
    private int age;                // 나이
    private String email;           // 메일 주소
    private int budget;             // 자산

    @OneToMany(mappedBy = "user")
    private final List<Order> orderList = new ArrayList<>();


    @Builder
    public User(String userId, String password, String name, int age, String email, int budget) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.budget = budget;
    }

    public void update(UserUpdateForm form) {
        if(!form.getEmail().equals("")) {
            this.email = form.getEmail();
        }
        if(!form.getPassword().equals("")) {
            this.password = form.getPassword();
        }
    }

    public void updateBudget(int updateBudget) {
        this.budget = updateBudget;
    }

    public int getTotalOrderPrice() {
        int sum = 0;

        for (Order order : orderList) {
            sum += order.getPrice() * order.getQuantity();
        }

        return sum;
    }
}
